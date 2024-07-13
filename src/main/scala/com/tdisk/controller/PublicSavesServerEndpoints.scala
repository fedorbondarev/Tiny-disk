package com.tdisk.controller

import cats.Functor
import cats.implicits.toFunctorOps
import com.tdisk.controller.endpoints.PublicSavesEndpoints
import com.tdisk.services.publicfile.PublicFileService
import com.tdisk.services.publictext.PublicTextService
import sttp.tapir.server.ServerEndpoint

final class PublicSavesServerEndpoints[F[_]: Functor](
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

  private val uploadPublicFileServerEndpoint: ServerEndpoint[Any, F] =
    PublicSavesEndpoints.uploadPublicFile.serverLogic(
      publicFileService.save
    )

  private val getPublicFileServerEndpoint: ServerEndpoint[Any, F] =
    PublicSavesEndpoints.getPublicFile.serverLogic(token =>
      publicFileService.get(token).map(_.map {
        case (data, name) => (data, s"attachment; filename=\"$name\"")
      })
    )

  val all: List[ServerEndpoint[Any, F]] =
    List(
      uploadPublicTextServerEndpoint,
      getPublicTextServerEndpoint,
      uploadPublicFileServerEndpoint,
      getPublicFileServerEndpoint
    )
}
