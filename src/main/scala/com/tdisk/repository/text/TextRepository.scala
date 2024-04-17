package com.tdisk.repository.text

import com.tdisk.error.DbError
import com.tdisk.model.text.{Text, TextId}

trait TextRepository[F[_]] {
  def get(id: TextId): F[Either[DbError, Text]]
  def save(text: Text): F[Either[DbError, TextId]]
}
