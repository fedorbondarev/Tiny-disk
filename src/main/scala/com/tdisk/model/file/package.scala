package com.tdisk.model

import derevo.derive
import derevo.tethys.{tethysReader, tethysWriter}
import doobie.Read
import io.estatico.newtype.macros.newtype
import sttp.tapir.derevo.schema

package object file {
  @derive(schema, tethysWriter, tethysReader)
  @newtype
  case class FileMetadataId(id: Long)
  object FileMetadataId {
    implicit val read: Read[FileMetadataId] = deriving
  }
}
