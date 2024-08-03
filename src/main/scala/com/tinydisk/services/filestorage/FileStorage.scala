package com.tinydisk.services.filestorage

import com.tinydisk.error.FileStorageError
import fs2.Stream

trait FileStorage[F[_]] {
  def saveFile(data: Stream[F, Byte], name: String): F[Either[FileStorageError, Unit]]
  def readFile(name: String): F[Either[FileStorageError, Stream[F, Byte]]]
}
