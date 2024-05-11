package com.tdisk.database.publictoken

import com.tdisk.model.publictoken.{ContentPublicToken, PublicToken, PublicTokenId, TextPublicToken}
import com.tdisk.model.text.TextId
import doobie.ConnectionIO
import doobie.implicits.toSqlInterpolator

object TextPublicTokenQueries {
  def get(id: PublicTokenId): ConnectionIO[Option[TextPublicToken]] =
    sql"select token, text_id from text_public_tokens where id = ${id.id}"
      .query[TextPublicToken]
      .option

  def save(textId: TextId): ConnectionIO[(PublicTokenId, TextPublicToken)] =
    sql"insert into text_public_tokens (text_id) values (${textId.id})"
      .update
      .withUniqueGeneratedKeys[(PublicTokenId, PublicToken)]("id", "token")
      .map { case (publicTokenId, token) => (publicTokenId, ContentPublicToken[TextId](token, textId)) }

  def getTextIdByToken(token: PublicToken): ConnectionIO[Option[TextId]] =
    sql"select text_id from text_public_tokens where token = ${token.token}"
      .query[TextId]
      .option
}
