package com.sajorahasan.tinderclone.api.callback

import com.google.gson.annotations.SerializedName

data class UserCallback(
    @SerializedName("results") val results: List<Results>
)