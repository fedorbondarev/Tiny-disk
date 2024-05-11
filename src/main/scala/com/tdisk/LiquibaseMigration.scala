package com.tdisk

import cats.effect.std.Console
import cats.effect.{ExitCode, IO, IOApp}
import com.tdisk.config.DbConf
import com.tdisk.database.DbModule
import doobie.FC
import doobie.implicits.toConnectionIOOps
import liquibase.command.CommandScope
import liquibase.command.core.{ClearChecksumsCommandStep, UpdateCommandStep}
import liquibase.command.core.helpers.DbUrlConnectionArgumentsCommandStep
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import pureconfig.ConfigSource

object LiquibaseMigration extends IOApp {
  def run(args: List[String]): IO[ExitCode] =
    for {
      _ <- Console[IO].println("Starting liquibase migration...")

      dbConf        = ConfigSource.default.at("db").loadOrThrow[DbConf]
      changeLogFile = "migrations/changelog.xml"

      dbModule <- DbModule.init[IO, IO](dbConf)

      _ <- FC.raw { conn =>
        val database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(conn))
        val commands = List(
          new CommandScope(ClearChecksumsCommandStep.COMMAND_NAME: _*),
          new CommandScope(UpdateCommandStep.COMMAND_NAME: _*)
        )

        commands.foreach(
          _
            .addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, database)
            .addArgumentValue(UpdateCommandStep.CHANGELOG_FILE_ARG, changeLogFile)
            .execute()
        )
      }.transact(dbModule.transactor)

      _ <- Console[IO].println("Migration finished.")
    } yield ExitCode.Success
}
