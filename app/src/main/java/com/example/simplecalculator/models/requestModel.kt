package com.example.simplecalculator.models

import com.google.gson.annotations.SerializedName

data class requestModel (
    @SerializedName("expr")
    var expr : String,
    @SerializedName("precision")
    var precision : Int? = null
)