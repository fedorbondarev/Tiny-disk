package com.tinydisk.services.publictext
import cats.data.OptionT
import cats.effect.kernel.MonadCancelThrow
import cats.implicits.toFunctorOps
import com.tinydisk.database.TextQueries
import com.tinydisk.database.publictoken.TextPublicTokenQueries
import com.tinydisk.error.{ApiError, BusinessApiError}
import com.tinydisk.model.publictoken.{ContentPublicToken, PublicToken}
import com.tinydisk.model.text.Text
import doobie.Transactor
import doobie.implicits.{toConnectionIOOps, toOptionTConnectionIOOps}

class PublicTextServiceImpl[F[_]: MonadCancelThrow](
  transactor: Transactor[F]
) extends PublicTextService[F] {

  override def get(token: PublicToken): F[Either[ApiError, Text]] =
    (for {
      textId <- OptionT(TextPublicTokenQueries.getTextIdByToken(token))
      text   <- OptionT(TextQueries.get(textId))
    } yield text)
      .transact(transactor)
      .toRight[ApiError](BusinessApiError("text not found by token"))
      .value

  override def save(text: Text): F[Either[ApiError, PublicToken]] =
    (for {
      textId                            <- TextQueries.save(text)
      (_, ContentPublicToken(token, _)) <- TextPublicTokenQueries.save(textId)
    } yield token)
      .transact(transactor)
      .map(Right(_))
}
