package com.example.androidpaging3.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.androidpaging3.data.dto.Car
import com.example.androidpaging3.data.remote.RetrofitService
import kotlinx.coroutines.flow.Flow

class MainRepository constructor(private val retrofitService: RetrofitService) {

    fun getAllCars(): Flow<PagingData<Car>> {

        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                initialLoadSize = 2
            ),
            pagingSourceFactory = {
                CarPagingSource(retrofitService)
            }, initialKey = 1
        ).flow
    }

}