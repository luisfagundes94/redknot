package com.luisfagundes.trip.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.luisfagundes.trip.BuildConfig
import com.luisfagundes.trip.data.service.UnsplashApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import javax.inject.Qualifier
import javax.inject.Singleton

private const val BASE_URL = "https://api.unsplash.com/"
private const val AUTHORIZATION_HEADER = "Authorization"
private const val CLIENT_ID_PREFIX = "Client-ID"

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UnsplashHttpClient

@Module
@InstallIn(SingletonComponent::class)
internal object TripNetworkModule {

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(): Interceptor = Interceptor { chain ->
        val original = chain.request()
        val request = original.newBuilder()
            .header(
                name = AUTHORIZATION_HEADER,
                value = "$CLIENT_ID_PREFIX ${BuildConfig.UNSPLASH_ACCESS_KEY}")
            .build()
        chain.proceed(request)
    }

    @Provides
    @Singleton
    @UnsplashHttpClient
    fun provideUnsplashOkHttpClient(
        authInterceptor: Interceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        })
        .build()

    @Provides
    @Singleton
    fun provideUnsplashRetrofit(
        @UnsplashHttpClient okHttpClient: OkHttpClient,
        json: Json
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    @Provides
    @Singleton
    fun provideUnsplashApiService(retrofit: Retrofit): UnsplashApiService =
        retrofit.create(UnsplashApiService::class.java)
}