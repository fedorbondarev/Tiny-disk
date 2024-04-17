package com.tdisk.controller

import cats.effect.kernel.Sync
import cats.effect.std.Console
import cats.implicits.catsSyntaxApplicativeId
import com.tdisk.controller.endpoints.PublicSavesEndpoints
import com.tdisk.model.publictoken.PublicToken
import com.tdisk.services.publicfile.PublicFileService
import sttp.tapir.server.ServerEndpoint

final class PublicSavesServerEndpoints[F[_]: Sync: Console](
  publicFileService: PublicFileService[F]
) {
  private val uploadPublicTextServerEndpoint: ServerEndpoint[Any, F] =
    PublicSavesEndpoints.uploadPublicText.serverLogicSuccess(t => t.text.pure[F])

  private val uploadPublicFileServerEndpoint: ServerEndpoint[Any, F] =
    PublicSavesEndpoints.uploadPublicFile.serverLogic { file =>
      publicFileService.save(file.name, file.data)
    }

  private val getPublicFileServerEndpoint: ServerEndpoint[Any, F] =
    PublicSavesEndpoints.getPublicFile.serverLogic { token =>
      publicFileService.get(PublicToken(token))
    }

  val all: List[ServerEndpoint[Any, F]] =
    List(
      uploadPublicTextServerEndpoint,
      uploadPublicFileServerEndpoint,
      getPublicFileServerEndpoint
    )
}
