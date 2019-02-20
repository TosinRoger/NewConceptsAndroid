package br.com.tosin.newconceptsandroid.entity

import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import br.com.tosin.newconceptsandroid.repository.database.IntConverter
import br.com.tosin.newconceptsandroid.repository.database.StringConverter
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
                    @SerializedName("range")
                    @TypeConverters(IntConverter::class)
                    val range: List<Int>?,
                    @SerializedName("isActive")
                    val isActive: Boolean,
                    @SerializedName("picture")
                    val picture: String,
//                    @SerializedName("friends")
//                    val friends: List<FriendsItem>?,
                    @SerializedName("tags")
                    @TypeConverters(StringConverter::class)
                    val tags: List<String>?,
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