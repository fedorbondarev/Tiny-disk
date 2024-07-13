package com.tdisk.services.filestorage

import cats.Monad
import cats.effect.Concurrent
import cats.implicits.{catsSyntaxApplicativeError, toFunctorOps}
import com.tdisk.config.FileStorageConf
import com.tdisk.error.{FileStorageError, UnexpectedFileStorageError}
import fs2.Stream
import fs2.io.file.{Files, Path}
import tofu.syntax.collections.CatsTraverseSyntax

import java.nio.file.{Path => JPath}

class FileStorageImpl[F[_]: Monad: Files: Concurrent](
  val fileStorageConf: FileStorageConf
) extends FileStorage[F] {
  @inline private def getFullPath(fileName: String): Path =
    Path.fromNioPath(JPath.of(fileStorageConf.pathToDirectory, fileName))

  def saveFile(data: Stream[F, Byte], name: String): F[Either[FileStorageError, Unit]] =
    data
      .through(
        Files[F].writeAll(getFullPath(name))
      )
      .compile
      .drain
      .attempt.map(_.left.map(e =>
        UnexpectedFileStorageError(e.getMessage)
      ))

  def readFile(name: String): F[Either[FileStorageError, Stream[F, Byte]]] =
    Files[F].readAll(getFullPath(name))
      .attempt
      .map(_.left.map(e =>
        UnexpectedFileStorageError(e.getMessage)
      ))
      .compile
      .toList
      .map(_.traverse(identity))
      .map(_.map(Stream.emits[F, Byte](_)))
}

object FileStorageImpl {
  def apply[F[_]: Files: Concurrent](fileStorageConf: FileStorageConf): FileStorage[F] =
    new FileStorageImpl[F](fileStorageConf)
}
