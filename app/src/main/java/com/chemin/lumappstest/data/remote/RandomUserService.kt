package com.chemin.lumappstest.data.remote

import com.chemin.lumappstest.data.remote.model.UserListsDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserService {
    @GET("api")
    fun userPageList(
        @Query("seed") seed: String = "toto",
        @Query("results") result: Int,
        @Query("page") page: Int,
    ): Call<UserListsDTO>
}
