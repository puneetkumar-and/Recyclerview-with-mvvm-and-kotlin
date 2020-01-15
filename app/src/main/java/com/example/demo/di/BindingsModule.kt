package com.example.demo.di

import com.example.demo.views.home.languagedetailitem.LanguageItemDetailActivity
import com.example.demo.views.home.languagedata.LanguageListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class BindingsModule {

    @ContributesAndroidInjector
    abstract fun mainActivity(): LanguageListActivity

    @ContributesAndroidInjector
    abstract fun languageItemDetailActivity(): LanguageItemDetailActivity


}