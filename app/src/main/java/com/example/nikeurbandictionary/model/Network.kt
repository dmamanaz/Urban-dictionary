package com.example.nikeurbandictionary.model

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val baseUrl = "https://mashape-community-urban-dictionary.p.rapidapi.com/"
const val rapidApiHost = "mashape-community-urban-dictionary.p.rapidapi.com"
const val rapidKey = "6b806dd214msh585207d010d812fp163207jsnbab67eb171e2"

fun Context.isConnectedToNetwork(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    return connectivityManager?.activeNetworkInfo?.isConnectedOrConnecting() ?: false
}

fun buildNetworkRequest(context: Context): UrbanDictionaryAPI =
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(buildOkHttpClient(context))
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(UrbanDictionaryAPI::class.java)

fun buildOkHttpClient(context: Context): OkHttpClient {
    val cacheSize = (5 * 1024 * 1024).toLong()
    val urbanReponseCache = Cache(context.cacheDir, cacheSize)
    val client = OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(50, TimeUnit.SECONDS)
        .cache(urbanReponseCache)
        .addInterceptor { chain ->
            var request = chain.request()
            request = if (context.isConnectedToNetwork()) {
                request.newBuilder().header("Cache-Control", "public, max-age" + 5)
                    .removeHeader("Pragma")
                    .build()
            } else {
                request.newBuilder()
                    .header("Cache-Control", "public, only-if-cached,max-stale=" + 60 * 60 * 24 * 7)
                    .removeHeader("Pragma")
                    .build()
            }
            chain.proceed(request)
        }
        .build()
    return client
}
