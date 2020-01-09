package com.sajorahasan.tinderclone.api.callback

import com.google.gson.annotations.SerializedName
import com.sajorahasan.tinderclone.model.User

data class Results(
    @SerializedName("user") val user: User,
    @SerializedName("seed") val seed: String,
    @SerializedName("version") val version: Double
)