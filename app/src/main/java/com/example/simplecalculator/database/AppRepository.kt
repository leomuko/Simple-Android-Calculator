package com.example.simplecalculator.database

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.*

class AppRepository(application: Application) {
    private var viewModelJob = Job()
    private var uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private var resultDao: DatabaseDao
    private  var allResults: LiveData<List<DatabaseModel>>

    init {
        val database: AppDatabase = AppDatabase.getInstance(
            application.applicationContext
        )!!
        resultDao = database.databaseDao
        allResults = resultDao.getAllResults()

    }
    fun insertResult(result: DatabaseModel){
        uiScope.launch {
            insert(result)
        }
    }
    private suspend fun insert(result: DatabaseModel){
            withContext(Dispatchers.IO){
                resultDao.insert(result)
            }
    }
    fun getAllResults(): LiveData<List<DatabaseModel>>{
        return allResults
    }
    fun deleteResult(result: DatabaseModel){
        uiScope.launch {
            delete(result)
        }
    }
    private suspend fun delete(result: DatabaseModel){
        withContext(Dispatchers.IO){
            resultDao.delete(result)
        }
    }

    fun deleteAllResults(){
        uiScope.launch {
            deleteAll()
        }
    }
    private suspend fun deleteAll(){
        withContext(Dispatchers.IO){
            resultDao.deleteAll()
        }
    }


}