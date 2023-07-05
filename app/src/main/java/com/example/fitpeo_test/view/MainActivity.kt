package com.example.fitpeo_test.view

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitpeo_test.network.ApiInterface
import com.example.fitpeo_test.network.NetworkConnection
import com.example.fitpeo_test.R
import com.example.fitpeo_test.adapter.RecyclerViewAdapter
import com.example.fitpeo_test.ResponseDataItem
import com.example.fitpeo_test.databinding.ActivityMainBinding
import com.example.fitpeo_test.viewmodel.MyViewModelFactory
import com.example.fitpeo_test.viewmodel.ResponseViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), RecyclerViewAdapter.ClickListener {
    var responseDataItemList: List<ResponseDataItem>? = null

    @Inject
    lateinit var apiInterface: ApiInterface

    lateinit var recyclerView: RecyclerView

    private lateinit var recyclerViewAdapter: RecyclerViewAdapter


    private lateinit var responseViewModel: ResponseViewModel
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as MyApp).appComponent.inject(MyApp())

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        responseViewModel = ViewModelProvider(
            this@MainActivity,
            MyViewModelFactory(apiInterface)
        )[ResponseViewModel::class.java]

        recyclerView = activityMainBinding.recyclerview

        recyclerView.layoutManager = LinearLayoutManager(this)
        responseViewModel.loadingData.observe(this, Observer {
            Log.e("MainActivity", "loadingData status " + it.status)
            if (it.status.equals("RUNNING")) {
                activityMainBinding.progressBar.visibility = View.VISIBLE
                activityMainBinding.recyclerview.visibility = View.GONE
            } else if (it.status.equals("SUCCESS")) {
                activityMainBinding.progressBar.visibility = View.GONE
                activityMainBinding.recyclerview.visibility = View.VISIBLE
            }else if (it.status.equals("FAILED")){
                activityMainBinding.recyclerview.visibility = View.GONE
            }
        })

        responseDataItemList = ArrayList()
        recyclerViewAdapter = RecyclerViewAdapter(this)

        checkNetwork()

        activityMainBinding.myNetwork.retry.setOnClickListener {
            checkNetwork()
        }
    }

    private fun checkNetwork() {
        val networkConnection = NetworkConnection(this@MainActivity)
        networkConnection.observe(this) { isConnected ->
            if (isConnected) {
                activityMainBinding.networkError.visibility = View.GONE
                activityMainBinding.recyclerview.visibility = View.VISIBLE
                Log.e("MainActivity", "internet connected... ")
                getData()
            } else {
                activityMainBinding.networkError.visibility = View.VISIBLE
                activityMainBinding.recyclerview.visibility = View.GONE
                activityMainBinding.progressBar.visibility = View.GONE
                Log.e("MainActivity", "internet disconnected...  ")
            }
        }
    }

    private fun getData() {
        responseViewModel.getData.observe(this, Observer {
            if (it.isNullOrEmpty()) {
                activityMainBinding.recyclerview.visibility = View.GONE
                activityMainBinding.progressBar.visibility = View.VISIBLE
                Log.e("MainActivity", "getData() size:  " + it.size)
            } else {
                activityMainBinding.recyclerview.visibility = View.VISIBLE
                activityMainBinding.progressBar.visibility = View.GONE
                Log.e("MainActivity", "getData() size: " + it.size)
            }
            recyclerViewAdapter.setData(it)
        })
        recyclerView.adapter = recyclerViewAdapter
    }

    override fun launchIntent(
        id: Int?,
        title: String?,
        url: String?,
        thumbnailUrl: String?,
        albumId: String?
    ) {
        val responseDataItem = ResponseDataItem(id, title, url, thumbnailUrl, albumId)
        Log.e("MainActivity", "launchIntent $responseDataItem")
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra("responseDataItemList", responseDataItem as Parcelable)
        startActivity(intent)
    }
}

