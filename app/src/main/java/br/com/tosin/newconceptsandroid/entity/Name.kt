package br.com.tosin.newconceptsandroid.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.jar.Attributes

//@Entity(tableName = "name_table", primaryKeys = ["first", "last"])

//@Entity(foreignKeys = [ForeignKey(
//        entity = FakeData::class,
//        parentColumns = arrayOf("Id"),
//        childColumns = arrayOf("parentId"),
//        onDelete = ForeignKey.CASCADE)]
//    )

data class Name(
//                @PrimaryKey(autoGenerate = true)
//                val id: String,
                @SerializedName("first")
                val first: String,
                @SerializedName("last")
                val last: String)
//                val parentId: String)