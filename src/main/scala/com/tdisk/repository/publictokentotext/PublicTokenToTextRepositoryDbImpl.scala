package com.tdisk.repository.publictokentotext

import cats.effect.kernel.MonadCancelThrow
import cats.implicits.catsSyntaxApplicativeError
import cats.syntax.functor._
import com.tdisk.error.{DbError, UnexpectedDbError}
import doobie.implicits.{toConnectionIOOps, toSqlInterpolator}
import doobie.util.transactor.Transactor
import tofu.syntax.feither.EitherFOps

//class PublicTokenToTextRepositoryDbImpl[F[_]: MonadCancelThrow](
//  transactor: Transactor[F]
//) extends PublicTokenToTextRepository[F] {
//  override def get(id: PublicTokenToTextId): F[Either[DbError, PublicTokenToText]] =
//    sql"select public_token_id, text_id from public_tokens_to_texts where id = ${id.id}"
//      .query[PublicTokenToText]
//      .option
//      .transact(transactor)
//      .map(_.toRight(UnexpectedDbError("Unexpected db error")))
//
//  override def save(publicTokenToText: PublicTokenToText): F[Either[DbError, PublicTokenToTextId]] =
//    sql"insert into public_tokens_to_texts (public_token_id, text_id) values (${publicTokenToText.publicTokenId.id}, ${publicTokenToText.contentId.id})"
//      .update
//      .withUniqueGeneratedKeys[Long]("id")
//      .transact(transactor)
//      .map(PublicTokenToTextId(_))
//      .attempt
//      .leftMapIn(err => UnexpectedDbError(err.getMessage))
//}
