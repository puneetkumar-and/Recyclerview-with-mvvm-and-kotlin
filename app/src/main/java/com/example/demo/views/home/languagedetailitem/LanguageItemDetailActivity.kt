package com.example.demo.views.home.languagedetailitem

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.demo.R
import com.example.demo.databinding.ActivityLanguageItemDetailBinding
import com.example.demo.utils.setViewModelAndLifecycle
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class LanguageItemDetailActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var binding: ActivityLanguageItemDetailBinding
    lateinit var viewModel: LanguageItemDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_language_item_detail)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LanguageItemDetailViewModel::class.java)
        binding.setViewModelAndLifecycle(viewModel, this)
        bindObservers(viewModel)

    }

    private fun bindObservers(viewModel: LanguageItemDetailViewModel) {
        viewModel.loggedInUser.observe(this, Observer {
            it?.let {
                binding.repoNameValue.text=it.name
                binding.repoUrlValue.text=it.url
                binding.repoDescriptionValue.text=it.description
            }
        })


    }


}
