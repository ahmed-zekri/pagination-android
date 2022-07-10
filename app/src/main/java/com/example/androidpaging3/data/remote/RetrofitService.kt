package com.example.androidpaging3.data.remote

import com.example.androidpaging3.data.common.BASE_URL
import com.example.androidpaging3.data.dto.Car
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface RetrofitService {

    @GET("/")
    suspend fun getCars(

        @Query("page") page: Int
    ): Response<List<Car>>


    @GET("/search")
    suspend fun searchCar(

        @Query("page") page: Int,
        @Query("search_term") search: String
    ): Response<List<Car>>

    companion object {
        private var retrofitService: RetrofitService? = null
        fun getInstance(): RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }

    }
}
