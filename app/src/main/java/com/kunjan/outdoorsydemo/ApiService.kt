package com.kunjan.outdoorsydemo

import com.kunjan.outdoorsydemo.pojo.Rentals
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("rentals")
    fun getAllRentals(): Observable<Rentals>

    companion object{
        fun create() : ApiService{
            val BASE_URL = "https://search.outdoorsy.co/"
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                    GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}