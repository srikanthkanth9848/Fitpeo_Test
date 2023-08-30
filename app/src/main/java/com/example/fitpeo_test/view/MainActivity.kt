package com.example.fitpeo_test.view

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitpeo_test.network.ApiInterface
import com.example.fitpeo_test.network.NetworkConnection
import com.example.fitpeo_test.R
import com.example.fitpeo_test.adapter.RecyclerViewAdapter
import com.example.fitpeo_test.model.ResponseDataItem
import com.example.fitpeo_test.databinding.ActivityMainBinding
import com.example.fitpeo_test.viewmodel.MyViewModelFactory
import com.example.fitpeo_test.viewmodel.ResponseViewModel
import com.example.fitpeo_test.viewmodel.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), RecyclerViewAdapter.ClickListener {
    private val myList: MutableList<ResponseDataItem> = mutableListOf()
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
        responseViewModel.loadingData.observe(this) {
            Log.e("reponseData", "myData 1 " + it.status)
        }

        responseDataItemList = ArrayList()
        recyclerViewAdapter = RecyclerViewAdapter(this)

        checkNetwork()

        activityMainBinding.myNetwork.retry.setOnClickListener {
            checkNetwork()
        }
    }

    //this is belongs to flow concept
    private fun myFlows() {
        lifecycleScope.launch {
            responseViewModel.commentState.collect {
                when (it.status) {
                    Status.LOADING -> {

                    }

                    Status.SUCCESS -> {
                        Log.e(
                            "responseDataItemList",
                            "myResponse $it"
                        )

                        it.data?.let { it1 -> myList.addAll(it1) }

                        recyclerViewAdapter.setData(myList)
                        delay(100)
                        if (myList.isNotEmpty()) {
                            activityMainBinding.recyclerview.visibility = View.VISIBLE
                            activityMainBinding.progressBar.visibility = View.GONE
                            Log.e(
                                "responseDataItemList",
                                "responseDataItemList1 " + myList.size
                            )
                        } else {
                            activityMainBinding.recyclerview.visibility = View.GONE
                            activityMainBinding.progressBar.visibility = View.VISIBLE
                            Log.e(
                                "responseDataItemList",
                                "responseDataItemList2 " + myList.size
                            )
                        }
                        recyclerView.adapter = recyclerViewAdapter
                    }

                    else -> {
                        Toast.makeText(
                            this@MainActivity,
                            "${it.message}", Toast.LENGTH_SHORT
                        )
                    }
                }
            }
        }
    }

    private fun checkNetwork() {
        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this) { isConnected ->
            if (isConnected) {
                //getData()
                myFlows()//flow concept
                activityMainBinding.networkError.visibility = View.GONE
                activityMainBinding.recyclerview.visibility = View.VISIBLE
            } else {
                activityMainBinding.networkError.visibility = View.VISIBLE
                activityMainBinding.recyclerview.visibility = View.GONE
            }
        }
    }

    //this is belongs to live data concept...
    private fun getData() {
        responseViewModel.getData.observe(this, Observer {
            recyclerViewAdapter.setData(it)

            if (it.isNotEmpty()) {
                activityMainBinding.recyclerview.visibility = View.VISIBLE
                activityMainBinding.progressBar.visibility = View.GONE
                Log.e("responseDataItemList", "responseDataItemList1 " + it.size)
            } else {
                activityMainBinding.recyclerview.visibility = View.GONE
                activityMainBinding.progressBar.visibility = View.VISIBLE
                Log.e("responseDataItemList", "responseDataItemList2 " + it.size)
            }
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
        Log.e("responseDataItem", "responseDataItem2 $responseDataItem")
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra("responseDataItemList", responseDataItem as Parcelable)
        startActivity(intent)
    }
}

