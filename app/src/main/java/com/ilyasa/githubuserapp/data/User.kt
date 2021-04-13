package com.ilyasa.githubuserapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Ilyasa Ridho
on 18,March,2021
 */

@Parcelize


data class User(
    var id: Int? = 0,
    var username: String? = "",
    var name: String? = "",
    var location: String? = "",
    var repository: String? = "",
    var bio: String? = "",
    var company: String? = "",
    var followers: String? = "",
    var following: String? = "",
    var items: String? = "",
    var link: String? = "",
    var avatar_url: String? = ""
) : Parcelable
