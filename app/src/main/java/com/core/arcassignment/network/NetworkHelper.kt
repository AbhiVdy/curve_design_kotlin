package com.core.arcassignment.network

class NetworkHelper(private val networkService: NetworkService) {
    fun getStatsData() = networkService.getStatsData()
}