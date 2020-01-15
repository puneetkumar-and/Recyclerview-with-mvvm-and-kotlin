package com.example.demo.di

import android.content.Context
import android.content.res.Resources
import javax.inject.Inject

class ResourceProvider @Inject constructor(private val context: Context) {

    fun getStringResource(stringId: Int): String {
        return context.getString(stringId)
    }

    fun getResources(): Resources {
        return context.resources
    }
}