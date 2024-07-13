package com.tdisk.services.filestorage

import cats.effect.kernel.Sync
import cats.implicits.{catsSyntaxApplicativeError, toFunctorOps}
import com.tdisk.config.FileStorageConf
import com.tdisk.error.{FileStorageError, UnexpectedFileStorageError}

import java.io.{BufferedInputStream, InputStream}
import java.nio.file.{Files, Path}

class FileStorageImpl[F[_]: Sync](
  val fileStorageConf: FileStorageConf
) extends FileStorage[F] {
  def saveFile(data: InputStream, name: String): F[Either[FileStorageError, Unit]] =
    Sync[F].blocking(
      Files.copy(data, Path.of(fileStorageConf.pathToDirectory, name))
    ).attempt.map {
      case Left(e)  => Left(UnexpectedFileStorageError(e.getMessage))
      case Right(_) => Right(())
    }

  def readFile(name: String): F[Either[FileStorageError, InputStream]] =
    Sync[F].blocking(
      new BufferedInputStream(Files.newInputStream(Path.of(fileStorageConf.pathToDirectory, name)))
    ).attempt.map(_.left.map(e => UnexpectedFileStorageError(e.getMessage)))
}

object FileStorageImpl {
  def apply[F[_]: Sync](fileStorageConf: FileStorageConf): FileStorage[F] =
    new FileStorageImpl[F](fileStorageConf)
}
