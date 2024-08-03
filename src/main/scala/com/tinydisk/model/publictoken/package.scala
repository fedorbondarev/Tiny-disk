package com.tinydisk.model

import com.tinydisk.model.file.FileMetadataId
import com.tinydisk.model.text.TextId
import derevo.derive
import derevo.tethys.{tethysReader, tethysWriter}
import doobie.Read
import io.estatico.newtype.macros.newtype
import sttp.tapir.derevo.schema

package object publictoken {
  @derive(tethysReader, tethysWriter, schema)
  @newtype
  case class PublicTokenId(id: Long)
  object PublicTokenId {
    implicit val read: Read[PublicTokenId] = deriving
  }

  type FilePublicToken = ContentPublicToken[FileMetadataId]
  type TextPublicToken = ContentPublicToken[TextId]
}
