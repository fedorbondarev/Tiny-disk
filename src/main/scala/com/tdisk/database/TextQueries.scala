package com.tdisk.database

import com.tdisk.model.text.{Text, TextId}
import doobie.ConnectionIO
import doobie.implicits.toSqlInterpolator

object TextQueries {
  def get(id: TextId): ConnectionIO[Option[Text]] =
    sql"select text from texts where id = ${id.id}"
      .query[Text]
      .option

  def save(text: Text): ConnectionIO[(TextId, Text)] =
    sql"insert into texts (text) values (${text.text})"
      .update
      .withUniqueGeneratedKeys[(Long, String)]("id", "text")
      .map { case (id, text) => (TextId(id), Text(text)) }
}
