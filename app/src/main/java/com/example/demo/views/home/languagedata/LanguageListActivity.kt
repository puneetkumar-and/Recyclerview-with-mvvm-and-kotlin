package com.example.demo.views.home.languagedata

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo.R
import com.example.demo.data.models.LanguageApiResponse
import com.example.demo.data.repos.AppRepository
import com.example.demo.databinding.ActivityMainBinding
import com.example.demo.utils.ConnectivityState
import com.example.demo.utils.Utils
import com.example.demo.utils.setViewModelAndLifecycle
import com.example.demo.views.AppConstants
import com.example.demo.views.adapter.CommonAdapter
import com.example.demo.views.adapter.ItemClickListener
import com.example.demo.views.home.languagedetailitem.LanguageItemDetailActivity
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.layout_error_view.view.*
import javax.inject.Inject

class LanguageListActivity : DaggerAppCompatActivity(), ItemClickListener<LanguageItemViewModel> {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: LanguageViewModel
    private lateinit var adapter: CommonAdapter<LanguageItemViewModel>
    private var adapterList: MutableList<LanguageItemViewModel> = ArrayList()
    private var selectedPackage: LanguageApiResponse? = null
    @Inject
    lateinit var appRepository: AppRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LanguageViewModel::class.java)
        binding.setViewModelAndLifecycle(viewModel, this)
        initViews()
        bindObservers()
        setupNetworkObserver()

    }

    private fun initViews() {
        adapter = CommonAdapter(adapterList, this)
        binding.recyclerView.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.setHasFixedSize(true)

        binding.recyclerView.adapter = adapter


        binding.swipeRefresh.setOnRefreshListener {
            setupNetworkObserver()
        }

    }

    private fun bindObservers() {


        viewModel.loaderVisible.observe(this, Observer {
            it?.let {

                val visible = it.first
                val message = it.second

                binding.loader?.let {
                    it.progressLayout.visibility = if (visible) View.VISIBLE else View.GONE
                    it.loadingText.text = message
                }
            }
        })
        viewModel.languageListData.observe(this, Observer {
            binding.swipeRefresh.isRefreshing = false
            if (it.isNotEmpty()) {
                it?.let {
                    adapterList.clear()
                    it.forEach {
                        adapterList.add(
                            LanguageItemViewModel(
                                it
                            )
                        )
                    }
                    adapter.notifyDataSetChanged()
                }
            } else {
                viewModel.errorViewCommand.postValue(AppConstants.NO_DATA_VIEW)
            }
            viewModel.showDataView.set(!it.isEmpty())

        })
        viewModel.errorViewCommand.observe(this, Observer {

            it?.let {
                binding.recyclerView.visibility = View.GONE
                viewModel.showDataView.set(false)
                binding.swipeRefresh.isRefreshing = false

                showErrorView(it)
            }
        })
    }

    override fun onItemClick(value: LanguageItemViewModel) {
        selectedPackage = value.incomingPackage
        value.incomingPackage.repo?.let { appRepository.saveRepo(it) }
        startActivity(Intent(this, LanguageItemDetailActivity::class.java))
    }

    private fun showErrorView(errorView: String) {

        var errorTitle = ""
        var errorMessage = ""
        var errorImage = 0

        when (errorView) {
            AppConstants.NO_DATA_VIEW -> {
                errorTitle = ""
                errorMessage = getString(R.string.error_empty_message)
                errorImage = R.drawable.ic_error_list_is_empty
            }
            AppConstants.NO_NETWORK_VIEW -> {
                errorTitle = getString(R.string.error_no_network_connection_error_title)
                errorMessage = getString(R.string.error_no_network_connection_error_message)
                errorImage = R.drawable.ic_error_network_unavailable
            }
            AppConstants.OH_SNAP_VIEW -> {
                errorTitle = getString(R.string.error_oh_snap_error_title)
                errorMessage = getString(R.string.error_oh_snap_error_message)
                errorImage = R.drawable.ic_error_oh_snap
            }
        }

        binding.errorView?.let {

            it.errorLayout.visibility = View.VISIBLE
            it.errorLayout.errorImage.setImageResource(errorImage)
            it.errorLayout.errorTitle.visibility = if (errorTitle == "") View.GONE else View.VISIBLE
            it.errorLayout.errorTitle.text = errorTitle
            it.errorLayout.errorMessage.text = errorMessage
        }
    }




    private fun setupNetworkObserver() {
        viewModel.getNetworkMonitorLiveData().observe(this, Observer {
            when (it) {

                ConnectivityState.CONNECTED -> {
                    viewModel.refreshList()

                }

                ConnectivityState.DISCONNECTED -> {
                    viewModel.errorViewCommand.postValue(AppConstants.NO_NETWORK_VIEW)

                }

                else -> {

                }
            }
        })
    }
}
