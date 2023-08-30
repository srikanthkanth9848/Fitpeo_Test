package com.example.fitpeo_test.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.fitpeo_test.R
import com.example.fitpeo_test.model.ResponseDataItem
class RecyclerViewAdapter(private val clickListener: ClickListener) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private val data = mutableListOf<ResponseDataItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_view_list_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtName.text = data[position].title
        setImageUrl(holder.imageView, data[position].thumbnailUrl)

        holder.relativeLayout.setOnClickListener {
            clickListener.launchIntent(
                data[position].id, data[position].title, data[position].url,
                data[position].thumbnailUrl,
                data[position].albumId
            )
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtName: TextView = itemView.findViewById(R.id.titleName)
        val imageView: ImageView = itemView.findViewById(R.id.imageID)
        val relativeLayout: RelativeLayout = itemView.findViewById(R.id.constraintLayout)
    }

    interface ClickListener {
        fun launchIntent(
            id: Int?,
            title: String?,
            url: String?,
            thumbnailUrl: String?,
            albumId: String?
        )
    }

    fun setData(data: List<ResponseDataItem>) {
        this.data.addAll(data.toMutableList())
        notifyDataSetChanged()
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
}