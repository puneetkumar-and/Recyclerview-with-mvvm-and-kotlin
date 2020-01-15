package com.example.demo.views.home.languagedata

import com.example.demo.R
import com.example.demo.data.models.LanguageApiResponse
import com.example.demo.views.adapter.ViewModel

class LanguageItemViewModel(var incomingPackage: LanguageApiResponse): ViewModel {
    var packageImageUrl = ""
    init {
        incomingPackage.avatar?.let {
            packageImageUrl = it
        }


    }
    override fun getViewType(): Int {
        return R.layout.item_language

    }
}