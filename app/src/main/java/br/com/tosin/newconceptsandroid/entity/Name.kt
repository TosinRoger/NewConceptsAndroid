package br.com.tosin.newconceptsandroid.entity

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "name_table", primaryKeys = ["first", "last"])
data class Name(@SerializedName("last")
                val last: String = "",
                @SerializedName("first")
                val first: String = "")