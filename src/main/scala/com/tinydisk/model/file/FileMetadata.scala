package com.tinydisk.model.file

import derevo.derive
import derevo.tethys.{tethysReader, tethysWriter}
import sttp.tapir.Schema.annotations.validate
import sttp.tapir.Validator
import sttp.tapir.derevo.schema

@derive(tethysReader, tethysWriter, schema)
case class FileMetadata(
  @validate(Validator.nonEmptyString) name: String,
  @validate(Validator.nonEmptyString) uniqueName: String
)
