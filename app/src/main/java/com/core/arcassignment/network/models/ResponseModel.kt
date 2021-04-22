package com.core.arcassignment.network.models

import com.google.gson.annotations.SerializedName

data class ResponseModel(
    @SerializedName("day")
    val dayData: ReportModel,
    @SerializedName("week")
    val weekData: ReportModel,
    @SerializedName("month")
    val monthData: ReportModel
)

data class ReportModel(
    @SerializedName("archived")
    val archived: Int,
    @SerializedName("inprogress")
    val inprogress: Int,
    @SerializedName("pending")
    val pending: Int,
    @SerializedName("total")
    val total: Int
)
