package com.example.simplecalculator.models

import com.google.gson.annotations.SerializedName

data class resultModel (
    @SerializedName("result")
    var theResult : String,
    @SerializedName("error")
    var error : String?

)