package com.example.androidpaging3.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.androidpaging3.data.dto.Car
import com.example.androidpaging3.domain.repositories.MainRepository
import kotlinx.coroutines.flow.Flow

class MainRepositoryImpl private constructor() : MainRepository {
    companion object {

        private var instance: MainRepositoryImpl? = null
        fun getInstance(): MainRepositoryImpl {
            if (instance == null)
                instance = MainRepositoryImpl()
            return instance!!

        }


    }

    override fun getAllCars(): Flow<PagingData<Car>> {

        return Pager(
            config = PagingConfig(
                pageSize = 15,
                enablePlaceholders = false,
                initialLoadSize = 2
            ),
            pagingSourceFactory = {
                CarPagingSource.getInstance()
            }, initialKey = 1
        ).flow
    }

    override fun getSearchCars(s: String): Flow<PagingData<Car>> {
        return Pager(
            config = PagingConfig(
                pageSize = 15,
                enablePlaceholders = false,
                initialLoadSize = 2
            ),
            pagingSourceFactory = {
                CarPagingSourceSearch.getInstance(s)
            }, initialKey = 1
        ).flow
    }

}