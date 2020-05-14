package com.example.simplecalculator

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.simplecalculator.database.AppDatabase
import com.example.simplecalculator.database.AppRepository
import com.example.simplecalculator.database.DatabaseModel
import kotlin.math.ceil

class ResultsViewModel(application: Application): AndroidViewModel(application) {

    private var repository: AppRepository = AppRepository(application)
    private var allResults: LiveData<List<DatabaseModel>> = repository.getAllResults()
    var expectedAnswer = MutableLiveData<Int>()
    init {
        expectedAnswer.value = 0
    }


    fun insert(result: DatabaseModel){
        repository.insertResult(result)
    }

    fun getAllResults(): LiveData<List<DatabaseModel>>{
        return allResults
    }
    fun passedValue(a: Int?, b: Int?): String {
        if (a == b) {
            return "Yes"
        } else {
            return "No"
        }
    }

    fun determineResult(result: Int?): Int? {
        val firstRandomNumber = Math.round(Math.random())
        val secondRandomNumber = Math.random() * 4000
        if (firstRandomNumber.toInt() == 1) {
            return ceil(secondRandomNumber.toDouble()).toInt()
        } else {
            return result
        }
    }
    fun chooseOperator(a: String): String {
        return when (a) {
            "Add" -> "+"
            "Subtract" -> "-"
            "Multiply" -> "*"
            else -> "/"

        }
    }
    fun delete(result: DatabaseModel){
        repository.deleteResult(result)
    }
    fun deleteAll(){
        repository.deleteAllResults()
    }
}