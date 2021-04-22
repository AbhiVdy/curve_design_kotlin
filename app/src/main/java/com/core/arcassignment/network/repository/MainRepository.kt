package com.core.arcassignment.network.repository

import com.core.arcassignment.network.NetworkHelper
import com.core.arcassignment.network.models.ResponseModel
import io.reactivex.Single

class MainRepository(private val networkHelper: NetworkHelper) {
    fun getStatsList() : Single<ResponseModel> {
        return networkHelper.getStatsData()
    }
}