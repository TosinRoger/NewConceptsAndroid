package br.com.tosin.newconceptsandroid.entity

import androidx.annotation.NonNull
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

//@Entity (
//    tableName = "friend_item",
//        indices = [Index(value = "id", name = "name")],
//        foreignKeys = [ForeignKey(
//                entity = FakeData::class,
//                parentColumns = arrayOf("friends"),
//                childColumns = arrayOf("id")
//        )]
//)

data class FriendsItem(@SerializedName("id")
                       @PrimaryKey
                       @NonNull
                       val id: Int = 0,
                       @SerializedName("name")
                       val name: String = "")