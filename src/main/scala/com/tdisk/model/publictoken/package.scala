package com.tdisk.model

import com.tdisk.model.file.FileMetadataId
import com.tdisk.model.text.TextId
import derevo.derive
import derevo.tethys.{tethysReader, tethysWriter}
import doobie.Read
import io.estatico.newtype.macros.newtype
import sttp.tapir.derevo.schema
import sttp.tapir.{Schema, SchemaType, Validator}

package object publictoken {
  @derive(tethysReader, tethysWriter, schema)
  @newtype
  case class PublicTokenId(id: Long)
  object PublicTokenId {
    implicit val read: Read[PublicTokenId] = deriving
  }

  type FilePublicToken = ContentPublicToken[FileMetadataId]
  type TextPublicToken = ContentPublicToken[TextId]

  @derive(tethysReader, tethysWriter)
  @newtype
  case class PublicToken(token: String)
  object PublicToken {
    implicit val read: Read[PublicToken] = deriving
    implicit val schema: Schema[PublicToken] =
      Schema(SchemaType.SString()).validate(Validator.nonEmptyString.contramap(_.token))
  }
}
