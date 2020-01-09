package com.sajorahasan.tinderclone.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity
data class User(
    @SerializedName("gender")
    @ColumnInfo
    val gender: String,
    @SerializedName("name")
    @ColumnInfo
    val name: Name,
    @SerializedName("location")
    @ColumnInfo
    val location: Location,
    @SerializedName("email")
    @ColumnInfo
    val email: String,
    @SerializedName("username")
    @PrimaryKey
    val username: String,
    @SerializedName("password")
    @ColumnInfo
    val password: String,
    @SerializedName("salt")
    @ColumnInfo
    val salt: String,
    @SerializedName("md5")
    @ColumnInfo
    val md5: String,
    @SerializedName("sha1")
    @ColumnInfo
    val sha1: String,
    @SerializedName("sha256")
    @ColumnInfo
    val sha256: String,
    @SerializedName("registered")
    @ColumnInfo
    val registered: Int,
    @SerializedName("dob")
    @ColumnInfo
    val dob: String,
    @SerializedName("phone")
    @ColumnInfo
    val phone: String,
    @SerializedName("cell")
    @ColumnInfo
    val cell: String,
    @SerializedName("SSN")
    @ColumnInfo
    val sSN: String,
    @SerializedName("picture")
    @ColumnInfo
    val picture: String
)