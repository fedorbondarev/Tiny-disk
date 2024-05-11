package com.tdisk.database

import com.tdisk.model.file.{FileMetadata, FileMetadataId}
import com.tdisk.model.text.TextId
import doobie.ConnectionIO
import doobie.implicits.toSqlInterpolator

object FileMetadataQueries {
  def get(id: FileMetadataId): ConnectionIO[Option[FileMetadata]] =
    sql"select name, unique_name from files_metadata where id = ${id.id}"
      .query[FileMetadata]
      .option

  def save(name: String): ConnectionIO[(FileMetadataId, FileMetadata)] =
    sql"insert into files_metadata (name) values ($name)"
      .update
      .withUniqueGeneratedKeys[(FileMetadataId, String)]("id", "unique_name")
      .map { case (fileMetadataId, uniqueName) => (fileMetadataId, FileMetadata(name, uniqueName)) }
}
