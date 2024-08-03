package com.tinydisk.database.publictoken

import com.tinydisk.model.file.FileMetadataId
import com.tinydisk.model.publictoken.{ContentPublicToken, FilePublicToken, PublicToken, PublicTokenId}
import doobie.ConnectionIO
import doobie.implicits.toSqlInterpolator

object FilePublicTokenQueries {
  def get(id: PublicTokenId): ConnectionIO[Option[FilePublicToken]] =
    sql"select token, file_metadata_id from file_public_tokens where id = ${id.id}"
      .query[FilePublicToken]
      .option

  def save(fileMetadataId: FileMetadataId): ConnectionIO[(PublicTokenId, FilePublicToken)] =
    sql"insert into file_public_tokens (file_metadata_id) values (${fileMetadataId.id})"
      .update
      .withUniqueGeneratedKeys[(PublicTokenId, PublicToken)]("id", "token")
      .map { case (publicTokenId, token) => (publicTokenId, ContentPublicToken[FileMetadataId](token, fileMetadataId)) }

  def getFileIdByToken(token: PublicToken): ConnectionIO[Option[FileMetadataId]] =
    sql"select file_metadata_id from file_public_tokens where token = ${token.token}"
      .query[FileMetadataId]
      .option
}
