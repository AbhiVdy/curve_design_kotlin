package com.core.arcassignment.utils

class Utils {

    enum class REPORT_TYPE {
        DAY, WEEK, MONTH
    }

    enum class REPORT_STATUS {
        ARCHIVE, PENDING, PROGRESS
    }

    enum class BIND_STATUS {
        LOADING, SUCCESS, ERROR
    }

    companion object {
        val BASE_URL = "https://mocki.io/v1/"
    }
}