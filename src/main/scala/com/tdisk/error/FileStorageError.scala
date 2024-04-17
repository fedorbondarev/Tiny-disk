package com.tdisk.error

sealed trait FileStorageError extends Throwable {
  def message: String
  override def getMessage: String = message
}

case class UnexpectedFileStorageError(message: String) extends FileStorageError
