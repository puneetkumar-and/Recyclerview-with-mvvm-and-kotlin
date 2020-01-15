package com.example.demo.views.home.languagedetailitem

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.demo.data.models.Repo
import com.example.demo.utils.SharedPrefsHelperImpl
import javax.inject.Inject

class LanguageItemDetailViewModel @Inject constructor(private val sharedPrefsHelperImpl: SharedPrefsHelperImpl) : ViewModel() {
    val loggedInUser = MutableLiveData<Repo>()
    init {
        loggedInUser.postValue(sharedPrefsHelperImpl.getRepo())
    }
}