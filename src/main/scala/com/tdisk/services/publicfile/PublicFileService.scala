package com.tdisk.services.publicfile

import com.tdisk.error.ApiError
import com.tdisk.model.file.FileData
import com.tdisk.model.publictoken.PublicToken
import fs2.Stream

trait PublicFileService[F[_]] {
  def save(fileData: FileData[F]): F[Either[ApiError, PublicToken]]
  def get(token: PublicToken): F[Either[ApiError, (Stream[F, Byte], String)]]
}
