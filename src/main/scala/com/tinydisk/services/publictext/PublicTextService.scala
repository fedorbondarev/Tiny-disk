package com.tinydisk.services.publictext

import com.tinydisk.error.ApiError
import com.tinydisk.model.publictoken.PublicToken
import com.tinydisk.model.text.Text

trait PublicTextService[F[_]] {
  def get(token: PublicToken): F[Either[ApiError, Text]]
  def save(text: Text): F[Either[ApiError, PublicToken]]
}
