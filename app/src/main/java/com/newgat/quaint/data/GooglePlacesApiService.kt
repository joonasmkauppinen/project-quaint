package com.newgat.quaint.data

import android.content.res.Resources
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.newgat.quaint.Config
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GooglePlacesApiService {

    @GET("json?")
    fun getAddressAutocompletePredictions(
        @Query("address") address: String,
        @Query("types") type: String = "address"
    ): Deferred<GooglePlacesAutocompleteResponse>

    companion object {
        operator fun invoke(): GooglePlacesApiService {
            val API_KEY = Config.GCP_API_KEY.value

            // Intercept the request to add api key into the request
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("key", API_KEY)
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
                .baseUrl("https://maps.googleapis.com/maps/api/place/autocomplete/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GooglePlacesApiService::class.java)
        }
    }

}