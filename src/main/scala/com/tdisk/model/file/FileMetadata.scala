package com.tdisk.model.file

import derevo.derive
import derevo.tethys.{tethysReader, tethysWriter}
import sttp.tapir.derevo.schema

@derive(tethysReader, tethysWriter, schema)
case class FileMetadata(name: String, uniqueName: String)
