package com.example.androidpaging3.data.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.androidpaging3.data.dto.Car
import com.example.androidpaging3.data.remote.RetrofitService

class CarPagingSource private constructor(private val apiService: RetrofitService) :
    PagingSource<Int, Car>() {
    companion object {
        private var instance: CarPagingSource? = null

        fun getInstance(): PagingSource<Int, Car> {
            if (instance == null)
                instance = CarPagingSource(RetrofitService.getInstance())
            return instance!!
        }
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Car> {

        return try {
            val position = params.key ?: 1
            val response = apiService.getCars(position)
            LoadResult.Page(
                data = response.body()?: listOf(), prevKey = if (position == 1) null else position - 1,
                nextKey = position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Car>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}