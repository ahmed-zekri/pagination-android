package com.example.androidpaging3.domain.repositories

import androidx.paging.PagingData
import com.example.androidpaging3.data.dto.Car
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun getAllCars(): Flow<PagingData<Car>>

    fun getSearchCars(s: String): Flow<PagingData<Car>>

}