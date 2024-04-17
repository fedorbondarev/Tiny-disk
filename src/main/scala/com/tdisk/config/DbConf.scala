package com.tdisk.config

import pureconfig.ConfigReader
import pureconfig.generic.semiauto._

final case class DbConf(
  driver: String,
  url: String,
  user: String,
  password: String
)

object DbConf {
  implicit val reader: ConfigReader[DbConf] = deriveReader
}
