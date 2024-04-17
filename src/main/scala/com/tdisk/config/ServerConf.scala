package com.tdisk.config

import pureconfig.ConfigReader
import pureconfig.generic.semiauto._

final case class ServerConf(
  host: String,
  port: Int
)

object ServerConf {
  implicit val reader: ConfigReader[ServerConf] = deriveReader
}
