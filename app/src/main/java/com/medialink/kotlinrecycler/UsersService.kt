package com.medialink.kotlinrecycler

import io.reactivex.Single
import javax.inject.Inject

class UsersService {
    @Inject
    lateinit var api: UsersApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getUsers(): Single<Data> {
        return api.getUsers()
    }
}
