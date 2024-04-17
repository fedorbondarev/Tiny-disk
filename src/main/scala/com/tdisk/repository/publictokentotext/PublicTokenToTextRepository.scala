//package com.tdisk.repository.publictokentotext
//
//import com.tdisk.error.DbError
//import com.tdisk.model.publictokento.{PublicTokenToText, PublicTokenToTextId}
//
//trait PublicTokenToTextRepository[F[_]] {
//  def get(id: PublicTokenToTextId): F[Either[DbError, PublicTokenToText]]
//  def save(publicTokenToText: PublicTokenToText): F[Either[DbError, PublicTokenToTextId]]
//}
