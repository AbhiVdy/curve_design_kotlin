package com.core.arcassignment.network

import com.core.arcassignment.network.models.ResponseModel
import com.core.arcassignment.utils.Utils
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NetworkServiceImpl : NetworkService {

    override fun getStatsData(): Single<ResponseModel> {
        val retrofit = Retrofit.Builder()
            .baseUrl(Utils.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: NetworkService = retrofit.create(NetworkService::class.java)
        return service.getStatsData()
    }
}