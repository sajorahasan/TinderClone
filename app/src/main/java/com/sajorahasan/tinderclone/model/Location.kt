package com.sajorahasan.tinderclone.model

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("street") val street: String,
    @SerializedName("city") val city: String,
    @SerializedName("state") val state: String,
    @SerializedName("zip") val zip: Int
)