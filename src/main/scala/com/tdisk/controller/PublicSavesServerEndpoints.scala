package com.tdisk.controller

import cats.Applicative
import cats.implicits.{catsSyntaxApplicativeId, toFunctorOps}
import com.tdisk.controller.endpoints.PublicSavesEndpoints
import com.tdisk.error.ServerApiError
import com.tdisk.model.file.FileData
import com.tdisk.services.publicfile.PublicFileService
import com.tdisk.services.publictext.PublicTextService
import sttp.capabilities.fs2.Fs2Streams
import sttp.tapir.server.ServerEndpoint

final class PublicSavesServerEndpoints[F[_]: Applicative](
  publicFileService: PublicFileService[F],
  publicTextService: PublicTextService[F]
) {
  private val uploadPublicTextServerEndpoint: ServerEndpoint[Any, F] =
    PublicSavesEndpoints.uploadPublicText.serverLogic(
      publicTextService.save
    )

  private val getPublicTextServerEndpoint: ServerEndpoint[Any, F] =
    PublicSavesEndpoints.getPublicText.serverLogic(
      publicTextService.get
    )

  private val uploadPublicFileServerEndpoint: ServerEndpoint[Fs2Streams[F], F] =
    PublicSavesEndpoints.uploadPublicFile.serverLogic {
      case (data, s"attachment; filename=\"$filename\"") =>
        publicFileService.save(FileData(filename, data))
      case _ =>
        Left(ServerApiError("File data or headers has invalid type")).pure[F].map(identity)
    }

  private val getPublicFileServerEndpoint: ServerEndpoint[Fs2Streams[F], F] =
    PublicSavesEndpoints.getPublicFile.serverLogic(token =>
      publicFileService.get(token).map(_.map {
        case (data, name) => (data, s"attachment; filename=\"$name\"")
      })
    )

  val all: List[ServerEndpoint[Fs2Streams[F], F]] =
    List(
      uploadPublicTextServerEndpoint,
      getPublicTextServerEndpoint,
      uploadPublicFileServerEndpoint,
      getPublicFileServerEndpoint
    )
}
