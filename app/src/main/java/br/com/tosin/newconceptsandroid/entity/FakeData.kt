package br.com.tosin.newconceptsandroid.entity

import androidx.room.*
import androidx.annotation.NonNull
import com.google.gson.annotations.SerializedName

@Entity(tableName = "fake_data_table")
data class FakeData(
                    @SerializedName("_id")
                    @PrimaryKey
                    @NonNull
                    val Id: String,
                    @SerializedName("address")
                    val address: String,
                    @SerializedName("latitude")
                    val latitude: String,
                    @SerializedName("greeting")
                    val greeting: String,
                    @SerializedName("about")
                    val about: String,
                    @SerializedName("index")
                    val index: Int,
                    @SerializedName("registered")
                    val registered: String,
//                    @SerializedName("range")
//                    val range: List<Integer>?,
                    @SerializedName("isActive")
                    val isActive: Boolean,
                    @SerializedName("picture")
                    val picture: String,
//                    @SerializedName("friends")
//                    val friends: List<FriendsItem>?,
//                    @SerializedName("tags")
//                    @TypeConverters(StringTypeConvert::class)
//                    val tags: List<String>?,
                    @SerializedName("favoriteFruit")
                    val favoriteFruit: String,
                    @SerializedName("balance")
                    val balance: String,
                    @SerializedName("eyeColor")
                    val eyeColor: String,
                    @SerializedName("phone")
                    val phone: String,
                    @SerializedName("name")
                    @Embedded
                    val name: Name,
                    @SerializedName("guid")
                    val guid: String,
                    @SerializedName("company")
                    val company: String,
                    @SerializedName("age")
                    val age: Int,
                    @SerializedName("email")
                    val email: String,
                    @SerializedName("longitude")
                    val longitude: String)