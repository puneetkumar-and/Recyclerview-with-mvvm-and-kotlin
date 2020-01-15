package com.example.demo.utils

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel

fun ViewDataBinding.setViewModelAndLifecycle(viewModel: ViewModel,lifecycleOwner: LifecycleOwner){
this.setVariable(BR.viewModel,viewModel)
this.setLifecycleOwner(lifecycleOwner)
    this.executePendingBindings()
}