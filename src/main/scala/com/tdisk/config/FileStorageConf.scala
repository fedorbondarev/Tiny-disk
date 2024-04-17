package com.tdisk.config

import pureconfig.ConfigReader
import pureconfig.generic.semiauto.deriveReader

final case class FileStorageConf(pathToDirectory: String)

object FileStorageConf {
  implicit val reader: ConfigReader[FileStorageConf] = deriveReader
}
