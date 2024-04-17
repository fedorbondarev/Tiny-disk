package com.tdisk.repository.publictoken
import cats.effect.kernel.MonadCancelThrow
import cats.implicits.catsSyntaxApplicativeError
import cats.syntax.functor._
import com.tdisk.error.{DbError, UnexpectedDbError}
import com.tdisk.model.publictoken.{ContentPublicToken, PublicTokenId}
import doobie.implicits.{toConnectionIOOps, toSqlInterpolator}
import doobie.util.transactor.Transactor
import tofu.syntax.feither.EitherFOps

//class PublicTokenRepositoryDbImpl[F[_]: MonadCancelThrow](
//  transactor: Transactor[F]
//) extends PublicTokenRepository[F] {
//
//  override def getIdByString(token: String): F[Either[DbError, PublicTokenId]] =
//    sql"select id from public_tokens where token = $token"
//      .query[PublicTokenId]
//      .option
//      .transact(transactor)
//      .map(_.toRight(UnexpectedDbError("Unexpected db error")))
//
//  override def get(id: PublicTokenId): F[Either[DbError, PublicToken]] =
//    sql"select token from public_tokens where id = ${id.id}"
//      .query[PublicToken]
//      .option
//      .transact(transactor)
//      .map(_.toRight(UnexpectedDbError("Unexpected db error")))
//
//  override def createToken(): F[Either[DbError, PublicToken]] =
//    sql"insert into public_tokens values ()"
//      .update
//      .withUniqueGeneratedKeys[String]("token")
//      .transact(transactor)
//      .map(token => PublicToken(token))
//      .attempt
//      .leftMapIn(err => UnexpectedDbError(err.getMessage))
//}
