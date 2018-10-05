package br.com.tosin.newconceptsandroid.entity

import com.google.gson.annotations.SerializedName

data class FakeData(@SerializedName("address")
                    val address: String = "",
                    @SerializedName("latitude")
                    val latitude: String = "",
                    @SerializedName("greeting")
                    val greeting: String = "",
                    @SerializedName("about")
                    val about: String = "",
                    @SerializedName("index")
                    val index: Int = 0,
                    @SerializedName("registered")
                    val registered: String = "",
                    @SerializedName("range")
                    val range: List<Integer>?,
                    @SerializedName("isActive")
                    val isActive: Boolean = false,
                    @SerializedName("picture")
                    val picture: String = "",
                    @SerializedName("friends")
                    val friends: List<FriendsItem>?,
                    @SerializedName("tags")
                    val tags: List<String>?,
                    @SerializedName("favoriteFruit")
                    val favoriteFruit: String = "",
                    @SerializedName("balance")
                    val balance: String = "",
                    @SerializedName("eyeColor")
                    val eyeColor: String = "",
                    @SerializedName("phone")
                    val phone: String = "",
                    @SerializedName("name")
                    val name: Name,
                    @SerializedName("guid")
                    val guid: String = "",
                    @SerializedName("company")
                    val company: String = "",
                    @SerializedName("_id")
                    val Id: String = "",
                    @SerializedName("age")
                    val age: Int = 0,
                    @SerializedName("email")
                    val email: String = "",
                    @SerializedName("longitude")
                    val longitude: String = "")