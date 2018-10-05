package br.com.tosin.newconceptsandroid.entity

import com.google.gson.annotations.SerializedName

data class FriendsItem(@SerializedName("name")
                       val name: String = "",
                       @SerializedName("id")
                       val id: Int = 0)