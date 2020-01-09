package com.sajorahasan.tinderclone.api


import com.sajorahasan.tinderclone.api.callback.UserCallback
import io.reactivex.Single
import retrofit2.http.GET


interface RestAPI {
    @GET("?randomapi")
    fun getUser(): Single<UserCallback>
}