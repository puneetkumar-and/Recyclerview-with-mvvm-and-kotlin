package com.example.demo.views.adapter


interface ItemClickListener<in T> {

    fun onItemClick(value: T)
}
