package com.tdisk.controller.endpoints

import com.tdisk.error.{ApiError, BusinessApiError, ServerApiError}
import com.tdisk.model.file.FileData
import com.tdisk.model.publictoken.PublicToken
import com.tdisk.model.text.Text
import sttp.model.HeaderNames
import sttp.tapir.Codec.string
import sttp.tapir._
import sttp.tapir.codec.newtype.codecForNewType
import sttp.tapir.json.tethysjson.jsonBody

import java.io.InputStream

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

  val uploadPublicFile: PublicEndpoint[FileData, ApiError, PublicToken, Any] =
    endpoint.post
      .in("uploadPublicFile")
      .in(multipartBody[FileData])
      .errorOut(oneOfApiError)
      .out(jsonBody[PublicToken])

  val getPublicFile: PublicEndpoint[PublicToken, ApiError, (InputStream, String), Any] =
    endpoint.get
      .in("file" / path[PublicToken]("publicToken"))
      .errorOut(oneOfApiError)
      .out(inputStreamBody)
      .out(header(HeaderNames.ContentType, "application/octet-stream"))
      .out(header[String](HeaderNames.ContentDisposition))
}
