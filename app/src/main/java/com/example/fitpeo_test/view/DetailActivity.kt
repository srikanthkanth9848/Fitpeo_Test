package com.example.fitpeo_test.view

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.fitpeo_test.R
import com.example.fitpeo_test.ResponseDataItem
import com.example.fitpeo_test.databinding.ActivityDetailBinding
import com.example.fitpeo_test.network.ApiInterface
import javax.inject.Inject


class DetailActivity : AppCompatActivity() {

    private lateinit var activityDetailBinding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = "Display Data"
        // showing the back button in action bar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeButtonEnabled(true)

        val responseDataItem: ResponseDataItem? =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("responseDataItemList", ResponseDataItem::class.java)
            } else {
                intent.getParcelableExtra("responseDataItemList")
            }
        activityDetailBinding.albumIDValue.text = responseDataItem?.albumId
        activityDetailBinding.uldIDValue.text = responseDataItem?.url
        activityDetailBinding.titleName.text = responseDataItem?.title
        activityDetailBinding.idValue.text = responseDataItem?.id.toString()
        setImageUrl(activityDetailBinding.imageID, responseDataItem?.url)

        Log.e("DetailActivity", "Object  $responseDataItem")
    }

    @BindingAdapter("imageUrl")
    fun setImageUrl(imgView: ImageView, imgUrl: String?) {
        Glide.with(imgView.context)
            .load(imgUrl)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
            )
            .into(imgView)
    }

    // function to the button on press
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}