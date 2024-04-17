package com.tdisk.repository.text

import cats.effect.kernel.MonadCancelThrow
import cats.implicits.catsSyntaxApplicativeError
import cats.syntax.functor._
import com.tdisk.error.{DbError, UnexpectedDbError}
import com.tdisk.model.text.{Text, TextId}
import doobie.implicits.{toConnectionIOOps, toSqlInterpolator}
import doobie.util.transactor.Transactor
import tofu.syntax.feither.EitherFOps

class TextRepositoryDbImpl[F[_]: MonadCancelThrow](
  transactor: Transactor[F]
) extends TextRepository[F] {

  override def get(id: TextId): F[Either[DbError, Text]] =
    sql"select text from texts where id = ${id.id}"
      .query[Text]
      .option
      .transact(transactor)
      .map(_.toRight(UnexpectedDbError("Unexpected db error")))

  override def save(text: Text): F[Either[DbError, TextId]] =
    sql"insert into texts (text) values (${text.text})"
      .update
      .withUniqueGeneratedKeys[Long]("id")
      .transact(transactor)
      .map(TextId(_))
      .attempt
      .leftMapIn(err => UnexpectedDbError(err.getMessage))
}
