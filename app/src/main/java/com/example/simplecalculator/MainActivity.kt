package com.example.simplecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calculate_button.setOnClickListener {
            var firstNumber: String? = number_one_input.text.toString()
            var secondNumber: String? = number_two_input.text.toString()
            var stringToInput : String = "${firstNumber}*${secondNumber}"
            

            var requestItem : requestModel = requestModel(stringToInput)

            getResults(requestItem)
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
