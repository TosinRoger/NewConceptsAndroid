package br.com.tosin.newconceptsandroid.entity

import com.google.gson.annotations.SerializedName

data class Name(@SerializedName("last")
                val last: String = "",
                @SerializedName("first")
                val first: String = "")