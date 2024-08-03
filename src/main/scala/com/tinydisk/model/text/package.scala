package com.tinydisk.model

import derevo.derive
import derevo.tethys.{tethysReader, tethysWriter}
import doobie.Read
import io.estatico.newtype.macros.newtype
import sttp.tapir.derevo.schema

package object text {
  @derive(schema, tethysWriter, tethysReader)
  @newtype
  case class TextId(id: Long)
  object TextId {
    implicit val read: Read[TextId] = deriving
  }

  @derive(schema, tethysWriter, tethysReader)
  @newtype
  case class Text(text: String)
  object Text {
    implicit val read: Read[Text] = deriving
  }
}
