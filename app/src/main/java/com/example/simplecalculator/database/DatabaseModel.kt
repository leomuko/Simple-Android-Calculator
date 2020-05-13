package com.example.simplecalculator.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "results_table")
data class DatabaseModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @ColumnInfo(name = "expected")
    var expected: String? = null,
    @ColumnInfo(name = "Result")
    var result: String? = null,
    @ColumnInfo(name= "one")
    var one: Int? = null,
    @ColumnInfo(name = "two")
    var two: Int? = null,
    @ColumnInfo(name= "passed")
    var passed: String? = null
)