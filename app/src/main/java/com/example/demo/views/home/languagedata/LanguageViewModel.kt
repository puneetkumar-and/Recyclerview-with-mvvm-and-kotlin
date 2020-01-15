package com.example.demo.views.home.languagedata

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.demo.R
import com.example.demo.data.models.LanguageApiResponse
import com.example.demo.data.repos.AppRepository
import com.example.demo.di.ResourceProvider
import com.example.demo.utils.ConnectivityState
import com.example.demo.utils.NetworkLiveData
import com.example.demo.views.AppConstants
import javax.inject.Inject

class LanguageViewModel @Inject constructor(private val networkLiveData: NetworkLiveData,
                                            private val appRepository: AppRepository,
                                            private val resourceProvider: ResourceProvider) : ViewModel() {

    val loaderVisible = MutableLiveData<Pair<Boolean, String>>()
    val errorViewCommand = MutableLiveData<String>()
    val languageListData = MediatorLiveData<List<LanguageApiResponse>>()
    val showDataView = ObservableBoolean(true)

    init {
        loaderVisible.postValue(Pair(true, resourceProvider.getStringResource(R.string.loading)))
        loadPackages()
    }

    private fun loadPackages() {
        languageListData.addSource(appRepository.getPackagesList()) {
            it?.let {
                it.response?.let {
                    languageListData.postValue(it);
                    loaderVisible.postValue(Pair(false, ""))
                }
            }
            it.status?.let {
                loaderVisible.postValue(Pair(false, ""))
                when (it.code) {
                    AppConstants.NETWORK_NOT_AVAILABLE -> errorViewCommand.postValue(AppConstants.NO_NETWORK_VIEW)
                    AppConstants.UNKNOWN_ERROR -> errorViewCommand.postValue(AppConstants.OH_SNAP_VIEW)
                }
            }
        }
    }
    fun refreshList() {
        appRepository.languageList()
    }
    fun getNetworkMonitorLiveData(): LiveData<ConnectivityState> =
        networkLiveData
  }