package com.tinydisk.services.publicfile

import com.tinydisk.error.ApiError
import com.tinydisk.model.file.FileData
import com.tinydisk.model.publictoken.PublicToken
import fs2.Stream

trait PublicFileService[F[_]] {
  def save(fileData: FileData[F]): F[Either[ApiError, PublicToken]]
  def get(token: PublicToken): F[Either[ApiError, (Stream[F, Byte], String)]]
}
