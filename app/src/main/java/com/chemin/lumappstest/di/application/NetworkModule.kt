package com.chemin.lumappstest.di.application

import com.chemin.lumappstest.data.remote.RandomUserService
import com.squareup.anvil.annotations.ContributesTo
import com.squareup.anvil.annotations.optional.SingleIn
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@ContributesTo(AppScope::class)
class NetworkModule {

    @SingleIn(AppScope::class)
    @Provides
    fun provideRetrofit(
        @BaseUrl baseUrl: String,
    ) = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Provides
    fun provideRandomUserService(
        retrofit: Retrofit,
    ): RandomUserService = retrofit.create(RandomUserService::class.java)
}
