//package com.tdisk.services.uploadpublic
//
//import cats.effect.kernel.MonadCancelThrow
//import cats.implicits.{catsSyntaxApplicativeError, catsSyntaxApplicativeId, toFlatMapOps, toFunctorOps}
//import com.tdisk.database.{FileMetadataQueries, PublicTokenQueries, PublicTokenToFileQueries}
//import com.tdisk.error.{ApiError, BusinessApiError}
//import com.tdisk.model.file.FileData
//import com.tdisk.model.publictoken.PublicToken
//import com.tdisk.model.publictokento.PublicTokenToFile
//import com.tdisk.services.filestorage.FileStorage
//import doobie.implicits.toConnectionIOOps
//import doobie.util.transactor.Transactor
//
//class UploadPublicFileServiceImpl[F[_]: MonadCancelThrow](
//  transactor: Transactor[F],
//  fileStorage: FileStorage[F]
//) extends UploadPublicFileService[F] {
//  override def uploadFile(fileData: FileData): F[Either[ApiError, PublicToken]] =
//    (for {
//      (fileMetadataId, fileMetadata) <- FileMetadataQueries.save(fileData.name)
//      (publicTokenId, publicToken)   <- PublicTokenQueries.createToken()
//      _                              <- PublicTokenToFileQueries.save(PublicTokenToFile(publicTokenId, fileMetadataId))
//    } yield (fileMetadata.uniqueName, publicToken)).transact[F](transactor)
//      .attempt
//      .map(_.left.map(_ => BusinessApiError))
//      .flatMap {
//        case Left(_) =>
//          Left[ApiError, PublicToken](BusinessApiError("Unexpected db error")).withRight[PublicToken].pure[F]
//        case Right((uniqueName, publicToken)) =>
//          for {
//            savedFile <- fileStorage.saveFile(fileData.data, uniqueName)
//          } yield savedFile match {
//            case Left(_)  => Left(BusinessApiError("Unexpected file storage error"))
//            case Right(_) => Right(publicToken)
//          }
//      }
//
//  override def getFile(token: PublicToken): F[Either[ApiError, FileData]] = ???
////    for {
////      publicTokenId <- PublicTokenQueries.getIdByString(token.token)
////      publicTokenId.
////    } yield Left(BusinessApiError("Unexpected db error"))
//}
