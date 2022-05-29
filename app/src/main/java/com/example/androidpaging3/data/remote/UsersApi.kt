package com.example.androidpaging3.data.remote

import retrofit2.http.GET


interface UsersApi {

    @GET
    suspend fun getUsers()

}
