package com.core.arcassignment.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.core.arcassignment.network.NetworkHelper
import com.core.arcassignment.network.repository.MainRepository

class ViewModelProviderFactory(
    val networkHelper: NetworkHelper
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(MainRepository(networkHelper)) as T
        }
        throw IllegalArgumentException("Something went wrong")
    }
}