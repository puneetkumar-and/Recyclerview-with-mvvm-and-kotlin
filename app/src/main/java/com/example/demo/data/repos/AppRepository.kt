package com.example.demo.data.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.demo.data.Response
import com.example.demo.data.apis.WebServices
import com.example.demo.data.models.*
import com.example.demo.utils.SharedPrefsHelperImpl
import com.example.demo.views.ApiStatus
import com.example.demo.views.App
import com.example.demo.views.AppConstants
import kotlinx.coroutines.*
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AppRepository @Inject constructor(
    val app: App,
    private val webServices: WebServices,
    private val sharedPrefsHelperImpl: SharedPrefsHelperImpl
) {

    val packageListData = MutableLiveData<Response<List<LanguageApiResponse>>>()

    init {
        languageList()
    }

    fun getPackagesList(): LiveData<Response<List<LanguageApiResponse>>> = packageListData
    fun languageList() {
        var apiStatus: ApiStatus
        var response: Response<List<LanguageApiResponse>>

        GlobalScope.launch {

            try {
                val apiResponse = webServices.getPackage("java", "weekly").await()
                if (apiResponse.isSuccessful) {
                    apiStatus = ApiStatus(apiResponse.code())
                    response = Response(apiStatus, apiResponse.body())
                } else {
                    response = Response(ApiStatus(apiResponse.code()), null)

                }
            } catch (e: Exception) {
                val code = getStatusCode(e)
                response = Response(ApiStatus(code), null)
            }
            packageListData.postValue(response)

        }

    }

    fun saveRepo(repoData: Repo) {
        sharedPrefsHelperImpl.saveRepo(repoData)
    }

    fun getRepo(): Repo? {
        return sharedPrefsHelperImpl.getRepo()
    }

    private fun getStatusCode(e: Exception): Int {
        return when (e) {
            is ConnectException -> AppConstants.NETWORK_NOT_AVAILABLE
            is UnknownHostException -> AppConstants.SERVER_CONNECTION_ERROR
            else -> AppConstants.UNKNOWN_ERROR
        }
    }

/*
    fun languageList(): LiveData<Response<List<LanguageApiResponse>>> {
        val liveData = MutableLiveData<Response<List<LanguageApiResponse>>>()
        var apiStatus: ApiStatus
        var response: Response<List<LanguageApiResponse>>

        launch {
            try {
                val apiResponse = webServices.getPackage("j", "weekly").await()
                apiStatus = ApiStatus(apiResponse.code())
                response = Response(apiStatus, apiResponse.body())
            } catch (e: Exception) {
                val code = getStatusCode(e)
                response = Response(ApiStatus(code), null)
            }
            liveData.postValue(response)
        }
        return liveData

    }
*/


}