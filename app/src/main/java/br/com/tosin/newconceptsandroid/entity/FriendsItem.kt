package br.com.tosin.newconceptsandroid.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import com.google.gson.annotations.SerializedName

@Entity(tableName = "friend_table")
data class FriendsItem(@SerializedName("id")
                       @PrimaryKey
                       @NonNull
                       val id: Int = 0,
                       @SerializedName("name")
                       val name: String = "")