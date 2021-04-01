package com.ilyasa.githubuserapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Ilyasa Ridho
on 18,March,2021
 */

@Parcelize
data class User(
        val username: String? = "ilyasa24",
        val name: String? = "ilyasa",
        val location: String? ="bandung",
        val repository: String? = "",
        val company: String? = "",
        val followers: String? = "",
        val following: String? = "",
        val avatar: Int? = 0
):Parcelable
