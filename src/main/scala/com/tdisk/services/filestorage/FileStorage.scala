package com.tdisk.services.filestorage

import com.tdisk.error.FileStorageError

import java.io.InputStream

trait FileStorage[F[_]] {
  def saveFile(data: InputStream, name: String): F[Either[FileStorageError, Unit]]
  def readFile(name: String): F[Either[FileStorageError, InputStream]]
}
