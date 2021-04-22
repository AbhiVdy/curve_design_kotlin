package com.core.arcassignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.core.arcassignment.network.models.ResponseModel
import com.core.arcassignment.network.repository.MainRepository
import com.core.arcassignment.utils.UIHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val statsData = MutableLiveData<UIHelper<ResponseModel>>()
    private val compositeDisposable = CompositeDisposable()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            getData()
        }
    }

    private fun getData() {
        statsData.postValue(UIHelper.showLoading(null))
        compositeDisposable.add(
            mainRepository.getStatsList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { obj ->
                        statsData.postValue(UIHelper.success(obj))
                    },
                    { throwable ->
                        statsData.postValue(
                            UIHelper.failure(
                                "Error : " + throwable.localizedMessage,
                                null
                            )
                        )
                    }
                )
        )
    }

    fun getStatsData(): LiveData<UIHelper<ResponseModel>> {
        return statsData
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}