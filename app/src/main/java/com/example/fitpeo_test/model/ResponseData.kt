package com.example.fitpeo_test

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class ResponseData(

    @field:SerializedName("ResponseData")
    val responseData: List<ResponseDataItem?>? = null
) : Parcelable

@Parcelize
data class ResponseDataItem(

    @field:SerializedName("albumId")
    var albumId: String? = null,

    @field:SerializedName("id")
    var id: Int? = null,

    @field:SerializedName("title")
    var title: String? = null,

    @field:SerializedName("url")
    var url: String? = null,

    @field:SerializedName("thumbnailUrl")
    var thumbnailUrl: String? = null
) : Serializable, Parcelable {

    constructor(id: Int?,title: String?,url: String?,thumbnailUrl: String?,albumId: String?) : this() {
        this.id = id
        this.title = title
        this.url = url
        this.thumbnailUrl = thumbnailUrl
        this.albumId = albumId
    }
    //methods...
}
