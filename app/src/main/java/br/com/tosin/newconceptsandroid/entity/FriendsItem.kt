package br.com.tosin.newconceptsandroid.entity

import androidx.room.PrimaryKey
import androidx.annotation.NonNull
import com.google.gson.annotations.SerializedName

data class FriendsItem(@SerializedName("id")
                       @PrimaryKey
                       @NonNull
                       val id: Int = 0,
                       @SerializedName("name")
                       val name: String = "")