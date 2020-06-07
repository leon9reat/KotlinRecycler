package com.medialink.kotlinrecycler

import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {
    private val BASE_URL = "https://api.jsonbin.io/b/"

    @Provides
    fun provideUsersApi(): UsersApi{
        val okHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(
                Interceptor { chain ->
                    val builder = chain.request().newBuilder()
                    builder.header(
                        "secret-key",
                        "\$2b\$10\$dJrjpKUe6blyvLTA.jCDvOjvu1mX3bZ9bZ1cI/scqAE/nEKM95vfu"
                    )

                    return@Interceptor chain.proceed(builder.build())
                }
            ).build()

        }
        val client = okHttpClient.build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(UsersApi::class.java)
    }

    @Provides
    fun provideUsersService(): UsersService {
        return UsersService()
    }
}