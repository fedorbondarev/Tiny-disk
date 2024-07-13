package com.tdisk

import cats.effect.kernel.Sync
import cats.effect.std.{Random, SecureRandom}
import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits.catsSyntaxFlatMapOps
import com.comcast.ip4s.{Host, IpLiteralSyntax, Port}
import com.tdisk.config.{DbConf, FileStorageConf, ServerConf}
import com.tdisk.controller.PublicSavesServerEndpoints
import com.tdisk.database.DbModule
import com.tdisk.services.filestorage.FileStorageImpl
import com.tdisk.services.publicfile.PublicFileServiceImpl
import com.tdisk.services.publictext.PublicTextServiceImpl
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.Router
import pureconfig.ConfigSource
import sttp.tapir.server.http4s.Http4sServerInterpreter
import sttp.tapir.swagger.bundle.SwaggerInterpreter

object Main extends IOApp {
  private type Init[A] = IO[A]
  private type App[A]  = IO[A]

  override def run(args: List[String]): IO[ExitCode] = {
    val conf = ConfigSource.default
    SecureRandom.javaSecuritySecureRandom[App] >>= { implicit appRandom: Random[App] =>
      (for {
        serverConf      <- Sync[Init].delay(conf.at("server").loadOrThrow[ServerConf])
        fileStorageConf <- Sync[Init].delay(conf.at("fileStorage").loadOrThrow[FileStorageConf])

        fileStorage = FileStorageImpl[App](fileStorageConf)
        dbModule <- DbModule.init[Init, App](conf.at("db").loadOrThrow[DbConf])

        publicFileService = new PublicFileServiceImpl[App](fileStorage, dbModule.transactor)
        publicTextService = new PublicTextServiceImpl[App](dbModule.transactor)

        apiEndpoints = new PublicSavesServerEndpoints[App](publicFileService, publicTextService).all
        routes = Http4sServerInterpreter[IO]().toRoutes(
          apiEndpoints ++
            SwaggerInterpreter().fromServerEndpoints[IO](apiEndpoints, "t-disk", "1.0.0")
        )

        service: EmberServerBuilder[IO] = EmberServerBuilder
          .default[IO]
          .withHost(Host.fromString(serverConf.host).getOrElse(ipv4"0.0.0.0"))
          .withPort(Port.fromInt(serverConf.port).getOrElse(port"8080"))
          .withHttpApp(Router("/" -> routes).orNotFound)
      } yield service).flatMap(_.build.useForever).as(ExitCode.Success)
    }
  }
}
