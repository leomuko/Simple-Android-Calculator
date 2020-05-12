package com.example.simplecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.simplecalculator.models.requestModel
import com.example.simplecalculator.models.resultModel
import com.example.simplecalculator.services.ApiClient
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {


    var operationSign: String = ""

    //val operations = resources.getStringArray(R.array.operation_array)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val operations = resources.getStringArray(R.array.operation_array)
        val spinner = findViewById<Spinner>(R.id.spinner_view)

            val adapter = ArrayAdapter(this, R.layout.spinner_item_view, operations)
            spinner.adapter = adapter
            spinner.setSelection(0, false)
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
//                    Toast.makeText(this@MainActivity, operations[position], Toast.LENGTH_SHORT)
//                        .show()
                   operationSign = chooseOperator(operations[position])

                }
            }




        calculate_button.setOnClickListener {
            val firstNumber: String? = number_one_input.text.toString()
            val secondNumber: String? = number_two_input.text.toString()
            val stringToInput : String = "${firstNumber}${operationSign}${secondNumber}"



            var requestItem : requestModel = requestModel(stringToInput)

            getResults(requestItem)
        }


    }
    private fun chooseOperator(a: String):String{
        return when (a) {
            "Add" -> "+"
            "Subtract" -> "-"
             "Multiply" -> "*"
            else -> "/"

        }
    }

    private fun getResults(requestString: requestModel){
        val call: Call<resultModel> = ApiClient.getClient.getResults(requestString)
        call.enqueue(object : Callback<resultModel>{
            override fun onFailure(call: Call<resultModel>, t: Throwable) {
                Toast.makeText(this@MainActivity, "No results", Toast.LENGTH_SHORT).show()
                Log.i("Error","${requestString}")
            }

            override fun onResponse(call: Call<resultModel>, response: Response<resultModel>) {
                if (response.isSuccessful){
                    val body = response.body()
                    Toast.makeText(this@MainActivity, "${body?.theResult}", Toast.LENGTH_SHORT).show()
                }
               else{
                    Toast.makeText(this@MainActivity, "Failed", Toast.LENGTH_SHORT).show()
                }




            }

        })
    }
}
