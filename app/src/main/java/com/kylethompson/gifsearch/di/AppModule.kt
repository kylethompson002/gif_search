package com.kylethompson.gifsearch.di

import com.kylethompson.gifsearch.BuildConfig
import com.kylethompson.gifsearch.api.TenorApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {
    single { createOkHttpClient() }
    single { createApi(get()) }
}

private fun createOkHttpClient(): OkHttpClient {

    val logInterceptor = HttpLoggingInterceptor()
        .apply {
            level = if (BuildConfig.DEBUG) Level.BODY else Level.NONE
        }

    return OkHttpClient.Builder()
        .addInterceptor(logInterceptor)
        .build()
}

private fun createApi(okHttpClient: OkHttpClient): TenorApi {
    return Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://api.tenor.com")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(TenorApi::class.java)
}