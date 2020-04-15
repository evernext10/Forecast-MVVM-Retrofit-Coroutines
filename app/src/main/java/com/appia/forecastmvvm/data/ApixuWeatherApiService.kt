package com.appia.forecastmvvm.data

import com.appia.forecastmvvm.data.response.CurrentWeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val API_KEY = "9785094787e09b5c982d59441672fe7c"
const val BASE_URL = "http://api.weatherstack.com"
//http://api.weatherstack.com/current?access_key=9785094787e09b5c982d59441672fe7c&query=New%20York&lang=es
interface ApixuWeatherApiService {

    @GET("/current")
    fun getTCurrentWeather(
        @Query("query") location : String,
        @Query("lang") languageCode : String = "es"
    ): Deferred<CurrentWeatherResponse>

    companion object{
        operator fun invoke(): ApixuWeatherApiService{
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("access_key",API_KEY)
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApixuWeatherApiService::class.java)
        }
    }
}