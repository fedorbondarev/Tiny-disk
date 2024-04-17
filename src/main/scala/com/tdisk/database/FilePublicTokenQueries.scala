package com.tdisk.database

import com.tdisk.model.file.FileMetadataId
import com.tdisk.model.publictoken.{ContentPublicToken, FilePublicToken, PublicToken, PublicTokenId}
import doobie.ConnectionIO
import doobie.implicits.toSqlInterpolator
import sttp.tapir.codec.newtype._

object FilePublicTokenQueries {
  def get(id: PublicTokenId): ConnectionIO[Option[FilePublicToken]] =
    sql"select token, file_metadata_id from file_public_tokens where id = ${id.id}"
      .query[FilePublicToken]
      .option

  def save(fileMetadataId: FileMetadataId): ConnectionIO[(PublicTokenId, FilePublicToken)] =
    sql"insert into file_public_tokens (file_metadata_id) values (${fileMetadataId.id})"
      .update
      .withUniqueGeneratedKeys[(Long, PublicToken)]("id", "token")
      .map { case (id, token) => (PublicTokenId(id), ContentPublicToken[FileMetadataId](token, fileMetadataId)) }

  def getFileIdByToken(token: PublicToken): ConnectionIO[Option[FileMetadataId]] =
    sql"select file_metadata_id from file_public_tokens where token = ${token.token}"
      .query[FileMetadataId]
      .option
}
