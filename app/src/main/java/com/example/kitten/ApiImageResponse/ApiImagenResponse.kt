package com.example.kitten.ApiImageResponse


import com.google.gson.annotations.SerializedName

data class ApiImagenResponse(
    @SerializedName("breeds")
    val breeds: List<Breed>,
    @SerializedName("height")
    val height: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int
)