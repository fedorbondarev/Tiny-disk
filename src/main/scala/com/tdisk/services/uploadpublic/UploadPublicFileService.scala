package com.tdisk.services.uploadpublic

import com.tdisk.error.ApiError
import com.tdisk.model.publictoken.ContentPublicToken

trait UploadPublicService[F[_], D, I] {
  def save(data: D): F[Either[ApiError, ContentPublicToken[I]]]
  def get(token: String): F[Either[ApiError, D]]
}
