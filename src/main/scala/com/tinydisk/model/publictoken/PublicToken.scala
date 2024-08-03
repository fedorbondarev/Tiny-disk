package com.tinydisk.model.publictoken

import derevo.derive
import derevo.tethys.{tethysReader, tethysWriter}
import doobie.Read
import sttp.tapir.{Codec, CodecFormat, FieldName, Schema, Validator}

@derive(tethysReader, tethysWriter)
final case class PublicToken(token: String) extends AnyVal
object PublicToken {
  implicit val read: Read[PublicToken] = Read[String].map(PublicToken(_))
  implicit val schema: Schema[PublicToken] =
    Schema.wrapWithSingleFieldProduct(
      Schema.string.validate(Validator.nonEmptyString).map((s: String) => Some(PublicToken(s)))(_.token),
      FieldName("token")
    )
  implicit val codec: Codec[String, PublicToken, CodecFormat.TextPlain] = Codec.derivedValueClass
}
