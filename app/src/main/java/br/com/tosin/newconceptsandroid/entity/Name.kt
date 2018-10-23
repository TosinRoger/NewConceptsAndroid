package br.com.tosin.newconceptsandroid.entity

import com.google.gson.annotations.SerializedName

data class Name(
                @SerializedName("first")
                val first: String,
                @SerializedName("last")
                val last: String)