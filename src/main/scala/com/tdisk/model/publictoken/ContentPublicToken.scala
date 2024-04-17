package com.tdisk.model.publictoken

import sttp.tapir.Schema
import tethys.derivation.semiauto.{jsonReader, jsonWriter}
import tethys.{JsonReader, JsonWriter}

case class ContentPublicToken[C: Schema: JsonWriter: JsonReader](token: PublicToken, content: C) {
  implicit def schema: Schema[ContentPublicToken[C]]                       = Schema.derived[ContentPublicToken[C]]
  implicit def contentPublicTokenWriter: JsonWriter[ContentPublicToken[C]] = jsonWriter[ContentPublicToken[C]]
  implicit def contentPublicTokenReader: JsonReader[ContentPublicToken[C]] = jsonReader[ContentPublicToken[C]]
}
