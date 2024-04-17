package com.tdisk.error

sealed trait DbError extends Throwable

final case class UnexpectedDbError(message: String) extends DbError
