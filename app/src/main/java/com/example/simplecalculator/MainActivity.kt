package com.example.simplecalculator

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplecalculator.database.DatabaseModel
import com.example.simplecalculator.models.requestModel
import com.example.simplecalculator.models.resultModel
import com.example.simplecalculator.services.ApiClient
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.ceil

class MainActivity : AppCompatActivity(), OnItemClickListener {


    private lateinit var resultViewModel: ResultsViewModel
    var operationSign: String = "+"
    var resultAdapter: theAdapter = theAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val inputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val operations = resources.getStringArray(R.array.operation_array)
        val spinner = findViewById<Spinner>(R.id.spinner_view)


        val adapter = ArrayAdapter(this, R.layout.spinner_item_view, operations)
        adapter.setDropDownViewResource(R.layout.spinner_item_view)
        spinner.adapter = adapter
        spinner.setSelection(0, false)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@MainActivity, "Nothing Selected", Toast.LENGTH_SHORT).show()

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(this@MainActivity, operations[position], Toast.LENGTH_SHORT).show()
                operationSign = resultViewModel.chooseOperator(operations[position])

            }
        }

        recycler_view.adapter = resultAdapter
        recycler_view.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, true)


        resultViewModel = ViewModelProvider(this).get(ResultsViewModel::class.java)
        resultViewModel.getAllResults().observe(this, Observer { results ->
            resultAdapter.setResults(results)

        })




        calculate_button.setOnClickListener {
            inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            var firstNumber: String? = number_one_input.text.toString()
            var secondNumber: String? = number_two_input.text.toString()
            var stringToInput: String = "${firstNumber}${operationSign}${secondNumber}"


            val requestItem: requestModel = requestModel(stringToInput)

            getResults(requestItem)


        }


    }





    private fun getResults(requestString: requestModel) {

        val call: Call<resultModel> = ApiClient.getClient.getResults(requestString)
        call.enqueue(object : Callback<resultModel> {
            override fun onFailure(call: Call<resultModel>, t: Throwable) {
                Toast.makeText(this@MainActivity, "No results", Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<resultModel>, response: Response<resultModel>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {

                        resultViewModel.expectedAnswer.value = try { body.theResult.toInt()} catch (e: NumberFormatException) {0}

                        var firstNumber: String? = number_one_input.text.toString()
                        var secondNumber: String? = number_two_input.text.toString()

                        var finalResult: DatabaseModel = DatabaseModel()
                        finalResult.expected = resultViewModel.expectedAnswer.value.toString()
                        Log.i("Main", "${finalResult.expected}")
                        finalResult.one = firstNumber?.toInt()
                        finalResult.two = secondNumber?.toInt()

                        finalResult.result =
                            resultViewModel.determineResult(finalResult.expected?.toInt()).toString()
                        finalResult.passed =
                            resultViewModel.passedValue(finalResult.expected?.toInt(), finalResult.result?.toInt())
                        resultViewModel.insert(finalResult)


                    }

                } else {
                    Toast.makeText(this@MainActivity, "Failed", Toast.LENGTH_SHORT).show()
                }


            }

        })

    }

    override fun onDeleteIconClick(position: Int){
        resultViewModel.delete(resultAdapter.getResultAt(position))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.delete_option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item?.itemId){
            R.id.delete_all_results ->{
                resultViewModel.deleteAll()
                true
            }else ->{
                super.onOptionsItemSelected(item)
            }
        }
    }
}
