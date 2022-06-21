package com.example.thi_ck.Class

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class User(val uid:String,val username:String, val uri_image:String ) :Parcelable {


}