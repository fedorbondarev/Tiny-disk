package com.tinydisk.database

import cats.effect.kernel.{Async, Sync}
import com.tinydisk.config.DbConf
import doobie.Transactor

final case class DbModule[F[_]](
  transactor: Transactor[F]
)

object DbModule {
  def init[I[_]: Sync, F[_]: Async](dbConf: DbConf): I[DbModule[F]] =
    Sync[I].delay(
      DbModule(
        Transactor.fromDriverManager[F](
          driver = dbConf.driver,
          url    = dbConf.url,
          user   = dbConf.user,
          pass   = dbConf.password
        )
      )
    )
}
