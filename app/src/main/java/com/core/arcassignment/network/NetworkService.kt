package com.core.arcassignment.network

import com.core.arcassignment.network.models.ResponseModel
import io.reactivex.Single
import retrofit2.http.GET

interface NetworkService {
    @GET("e89ac12a-26eb-4718-a14f-d26b760a9839")
    fun getStatsData(): Single<ResponseModel>
}