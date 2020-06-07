package com.medialink.kotlinrecycler

import io.reactivex.Single
import retrofit2.http.GET

interface UsersApi {
    // TODO: pelajari rxJava Single !!!
    @GET("5ed6871f79382f568bd1c948")
    fun getUsers(): Single<Data>
}