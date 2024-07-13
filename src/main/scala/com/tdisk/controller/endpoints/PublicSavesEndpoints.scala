package com.tdisk.controller.endpoints

import com.tdisk.error.{ApiError, BusinessApiError, ServerApiError}
import com.tdisk.model.publictoken.PublicToken
import com.tdisk.model.text.Text
import fs2.Stream
import sttp.capabilities.fs2.Fs2Streams
import sttp.model.HeaderNames
import sttp.tapir._
import sttp.tapir.codec.newtype.codecForNewType
import sttp.tapir.json.tethysjson.jsonBody

object PublicSavesEndpoints {
  private val oneOfApiError = oneOf[ApiError](
    oneOfVariant(jsonBody[BusinessApiError]),
    oneOfVariant(jsonBody[ServerApiError])
  )

  val uploadPublicText: PublicEndpoint[Text, ApiError, PublicToken, Any] =
    endpoint.post
      .in("uploadPublicText")
      .in(jsonBody[Text])
      .errorOut(oneOfApiError)
      .out(jsonBody[PublicToken])

  val getPublicText: PublicEndpoint[PublicToken, ApiError, Text, Any] =
    endpoint.get
      .in("text" / path[PublicToken]("publicToken"))
      .errorOut(oneOfApiError)
      .out(plainBody[Text])

  def uploadPublicFile[F[_]]: PublicEndpoint[(Stream[F, Byte], String), ApiError, PublicToken, Fs2Streams[F]] =
    endpoint.post
      .in("uploadPublicFile")
      .in(streamBinaryBody(Fs2Streams[F])(CodecFormat.OctetStream()))
      .in(header[String](HeaderNames.ContentDisposition))
      .errorOut(oneOfApiError)
      .out(jsonBody[PublicToken])

  def getPublicFile[F[_]]: PublicEndpoint[PublicToken, ApiError, (Stream[F, Byte], String), Fs2Streams[F]] =
    endpoint.get
      .in("file" / path[PublicToken]("publicToken"))
      .errorOut(oneOfApiError)
      .out(streamBinaryBody(Fs2Streams[F])(CodecFormat.OctetStream()))
      .out(header(HeaderNames.ContentType, "application/octet-stream"))
      .out(header[String](HeaderNames.ContentDisposition))
}
