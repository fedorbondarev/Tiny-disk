package com.tinydisk.error

import derevo.derive
import derevo.tethys.{tethysReader, tethysWriter}
import sttp.tapir.Schema

sealed trait ApiError extends Throwable {
  def message: String
  override def getMessage: String = message
}

@derive(tethysWriter, tethysReader)
final case class ServerApiError(message: String) extends ApiError
object ServerApiError {
  implicit val schema: Schema[ServerApiError] =
    Schema
      .derived[ServerApiError]
      .modify(_.message)(_.description("Error message"))
      .description("Internal error")
}

@derive(tethysWriter, tethysReader)
final case class BusinessApiError(message: String) extends ApiError
object BusinessApiError {
  implicit val schema: Schema[BusinessApiError] =
    Schema
      .derived[BusinessApiError]
      .modify(_.message)(_.description("Error message"))
      .description("Error processing request")
}
