package com.example.data.model.tables.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object ItemTable: Table() {
    val id: Column<Int> = integer("item_id").autoIncrement()
    val owner: Column<Int> = integer("item_order").references(UserTable.id)
    val itemTitle: Column<String> = varchar("item_title", 50)
    val itemDescription: Column<String> = varchar("item_description", 2000)
    val itemDate: Column<String> = varchar("item_create_date", 50)

    override val primaryKey = PrimaryKey(id)
}