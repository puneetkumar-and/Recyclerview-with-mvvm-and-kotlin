package com.example.demo.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.LiveData
import javax.inject.Inject


class NetworkLiveData @Inject constructor(context: Context) : LiveData<ConnectivityState>() {

    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {

        override fun onLost(network: Network?) {
            super.onLost(network)
            postValue(ConnectivityState.DISCONNECTED)
        }

        override fun onAvailable(network: Network?) {
            super.onAvailable(network)
            postValue(ConnectivityState.CONNECTED)
        }

    }

    override fun onActive() {
        super.onActive()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            cm.registerDefaultNetworkCallback(networkCallback)
        } else {
            val networkRequest = NetworkRequest.Builder().build()
            cm.registerNetworkCallback(networkRequest, networkCallback)
        }
    }

    override fun onInactive() {
        super.onInactive()
        cm.unregisterNetworkCallback(networkCallback)
    }

}




