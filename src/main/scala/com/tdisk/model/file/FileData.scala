package com.tdisk.model.file

import derevo.derive
import sttp.model.Part
import sttp.tapir.derevo.schema

import java.io.InputStream

@derive(schema)
case class FileData(name: String, data: InputStream)
