package com.core.arcassignment.utils

class UIHelper<out T>(val status: Utils.BIND_STATUS, val data: T?, val msg: String?) {
    companion object {
        fun <T> showLoading(data: T?): UIHelper<T> {
            return UIHelper(Utils.BIND_STATUS.LOADING, data, null)
        }

        fun <T> success(data: T?): UIHelper<T> {
            return UIHelper(Utils.BIND_STATUS.SUCCESS, data, null)
        }

        fun <T> failure(msg: String, data: T?): UIHelper<T> {
            return UIHelper(Utils.BIND_STATUS.ERROR, data, msg)
        }
    }
}