package com.tdisk.model.file

import derevo.derive
import sttp.tapir.Schema.annotations.validate
import sttp.tapir.Validator
import sttp.tapir.derevo.schema

import java.io.InputStream

@derive(schema)
case class FileData(
  @validate(Validator.nonEmptyString) name: String,
  data: InputStream
)
