package com.tdisk.services.publictext

import com.tdisk.error.ApiError
import com.tdisk.model.publictoken.PublicToken
import com.tdisk.model.text.Text

trait PublicTextService[F[_]] {
  def get(token: PublicToken): F[Either[ApiError, Text]]
  def save(text: Text): F[Either[ApiError, PublicToken]]
}
