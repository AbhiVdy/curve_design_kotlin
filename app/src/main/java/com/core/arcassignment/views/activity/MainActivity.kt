package com.core.arcassignment.views.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.core.arcassignment.utils.NetworkLiveData
import com.core.arcassignment.R
import com.core.arcassignment.databinding.ActivityMainBinding
import com.core.arcassignment.network.NetworkHelper
import com.core.arcassignment.network.NetworkServiceImpl
import com.core.arcassignment.network.models.ReportModel
import com.core.arcassignment.network.models.ResponseModel
import com.core.arcassignment.utils.Utils
import com.core.arcassignment.viewmodel.MainViewModel
import com.core.arcassignment.viewmodel.ViewModelProviderFactory
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    private var responseModel: ResponseModel? = null
    private lateinit var dayObj: ReportModel
    private lateinit var weekObj: ReportModel
    private lateinit var monthObj: ReportModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProviderFactory(NetworkHelper(NetworkServiceImpl()))
        ).get(MainViewModel::class.java)

        //Checks network state
        NetworkLiveData.init(application)
        NetworkLiveData.observe(this, Observer {
            if (it) {
                if (this::mainViewModel.isInitialized) {
                    getDataFromServer()
                }
            } else {
                Snackbar.make(binding.root, "No network available", Snackbar.LENGTH_SHORT).show()
            }
        })
        setListeners()
    }

    private fun getDataFromServer() {
        mainViewModel.getStatsData().observe(this, Observer {
            when (it.status) {
                Utils.BIND_STATUS.LOADING -> {
                }
                Utils.BIND_STATUS.SUCCESS -> {
                    responseModel = it.data
                    responseModel?.let { respObj ->
                        onResponse(respObj)
                    }
                }
                Utils.BIND_STATUS.ERROR -> {
                    Snackbar.make(binding.root, it.toString(), Snackbar.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun onResponse(response: ResponseModel) {
        dayObj = response.dayData
        weekObj = response.weekData
        monthObj = response.monthData

        binding.tvDay.callOnClick()
    }

    private fun setClickUI(type: Utils.REPORT_TYPE) {
        when (type) {
            Utils.REPORT_TYPE.DAY -> {
                binding.tvDay.setTextColor(resources.getColor(R.color.ivory, null))
                binding.tvWeek.setTextColor(resources.getColor(R.color.grey, null))
                binding.tvMonth.setTextColor(resources.getColor(R.color.grey, null))

                binding.bulletDay.visibility = View.VISIBLE
                binding.bulletWeek.visibility = View.GONE
                binding.bulletMonth.visibility = View.GONE
                binding.viewArc.setChart(dayObj)
            }
            Utils.REPORT_TYPE.WEEK -> {
                binding.tvDay.setTextColor(resources.getColor(R.color.grey, null))
                binding.tvWeek.setTextColor(resources.getColor(R.color.ivory, null))
                binding.tvMonth.setTextColor(resources.getColor(R.color.grey, null))

                binding.bulletDay.visibility = View.GONE
                binding.bulletWeek.visibility = View.VISIBLE
                binding.bulletMonth.visibility = View.GONE
                binding.viewArc.setChart(weekObj)
            }
            Utils.REPORT_TYPE.MONTH -> {
                binding.tvDay.setTextColor(resources.getColor(R.color.grey, null))
                binding.tvWeek.setTextColor(resources.getColor(R.color.grey, null))
                binding.tvMonth.setTextColor(resources.getColor(R.color.ivory, null))

                binding.bulletDay.visibility = View.GONE
                binding.bulletWeek.visibility = View.GONE
                binding.bulletMonth.visibility = View.VISIBLE
                binding.viewArc.setChart(monthObj)
            }
        }
    }

    private fun setListeners() {
        binding.tvDay.setOnClickListener { setClickUI(Utils.REPORT_TYPE.DAY) }
        binding.tvMonth.setOnClickListener { setClickUI(Utils.REPORT_TYPE.MONTH) }
        binding.tvWeek.setOnClickListener { setClickUI(Utils.REPORT_TYPE.WEEK) }
    }
}