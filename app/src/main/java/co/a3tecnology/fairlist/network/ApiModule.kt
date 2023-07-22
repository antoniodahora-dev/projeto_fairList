package co.a3tecnology.fairlist.network

import co.a3tecnology.fairlist.App
import co.a3tecnology.fairlist.model.ApiService
//import com.google.gson.Gson
//import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

fun client() =
        OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .addInterceptor {
                    // request sem token
                    val request = it.request()

                    if (App.getToken().isNullOrBlank())
                        return@addInterceptor it.proceed(request)

                    // request com token
                    val newRequest = request.newBuilder()
                            .addHeader("Authorization", "Token ${App.getToken()}")
                            .build()

                    it.proceed(newRequest)
                }
                .build()

//fun gson(): Gson = GsonBuilder().create()

fun retrofit() : Retrofit =
        Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client())
//                .addConverterFactory(GsonConverterFactory.create(gson())) // GSON
//                .addConverterFactory(MoshiConverterFactory.create().asLenient()) // Moshi
                .addConverterFactory(
                        Json {
                            ignoreUnknownKeys = true
                        }.asConverterFactory("application/json".toMediaType())
                )
                .build()

fun services() =
        retrofit().create(ApiService::class.java)