package com.example.plugins

import com.example.data.model.tables.tables.ItemTable
import com.example.data.model.tables.tables.UserTable
import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import io.ktor.server.application.*
import io.ktor.server.config.*
import org.jetbrains.exposed.sql.*
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.transactions.transaction

object DatabasesFactory {

    // appConfig
    private val appConfig = HoconApplicationConfig(ConfigFactory.load())
    // database url
    private val dbUrl = System.getenv("DB_POSTGRES_URL")
    private val dbUser = System.getenv("DB_POSTGRES_USER")
    private val dbPassword = System.getenv("DB_PASSWORD")

    // Инициализирование базы данных
    fun Application.initializationDatabase() {
        Database.connect(getHikariDatasource())

        transaction {
            SchemaUtils.create(
                UserTable,
                ItemTable
            )
        }
    }

    private fun getHikariDatasource(): HikariDataSource {
        println("DB URL: $dbUrl")
        println("DB USER: $dbUser")

        val config = HikariConfig()
        config.driverClassName = "org.postgresql.Driver"
        config.jdbcUrl = dbUrl
        config.username = dbUser
        config.password = dbPassword
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
        return HikariDataSource(config)
    }

    suspend fun <T> dbQuery(block: () -> T): T {
        return withContext(Dispatchers.IO) {
            transaction { block() }
        }
    }
}
