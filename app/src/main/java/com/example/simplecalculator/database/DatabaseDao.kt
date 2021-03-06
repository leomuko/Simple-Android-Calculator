package com.example.simplecalculator.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DatabaseDao {
    @Insert
    fun insert(result: DatabaseModel)

    @Query("SELECT * FROM results_table ORDER BY id ASC")
    fun getAllResults(): LiveData<List<DatabaseModel>>

    @Delete
    fun delete(result: DatabaseModel)

    @Query("DELETE  FROM results_table ")
    fun deleteAll()
}