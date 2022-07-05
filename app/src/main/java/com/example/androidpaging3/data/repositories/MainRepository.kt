package com.example.androidpaging3.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.androidpaging3.data.dto.Car
import com.example.androidpaging3.data.remote.RetrofitService
import kotlinx.coroutines.flow.Flow

class MainRepository private constructor(private val retrofitService: RetrofitService) {
    companion object {

        private var instance: MainRepository? = null
        fun getInstance(retrofitService: RetrofitService): MainRepository {
            if (instance == null)
                instance = MainRepository(retrofitService)
            return instance!!

        }


    }

    fun getAllCars(): Flow<PagingData<Car>> {

        return Pager(
            config = PagingConfig(
                pageSize = 15,
                enablePlaceholders = false,
                initialLoadSize = 2
            ),
            pagingSourceFactory = {
                CarPagingSource(retrofitService)
            }, initialKey = 1
        ).flow
    }

}