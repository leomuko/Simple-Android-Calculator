package com.example.simplecalculator.services


import com.example.simplecalculator.models.requestModel
import com.example.simplecalculator.models.resultModel
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {
    @POST("v4/")
    fun getResults(@Body postData: requestModel): Call<resultModel>
}