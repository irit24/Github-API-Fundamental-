package com.ilyasa.githubuserapp.model

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ilyasa.githubuserapp.BuildConfig
import com.ilyasa.githubuserapp.R
import com.ilyasa.githubuserapp.data.User
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import java.lang.Exception

/**
 * Created by Ilyasa Ridho
on 05,April,2021
 */
class FollowingViewModel : ViewModel() {

    companion object {
        private val TAG = FollowingViewModel::class.java.simpleName
        private const val API = BuildConfig.GITHUB_TOKEN

    }

    private val listFollowing = MutableLiveData<ArrayList<User>>()


    fun setFollowingUser(username: String?, context: Context?) {
        val listMengikuti = ArrayList<User>()
        val url = "https://api.github.com/users/$username/following"



        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", "token $API")
        asyncClient.addHeader("User-Agent", "request")
        asyncClient.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray) {
                try {
                    val result = String(responseBody)
                    Log.d(TAG, result)

                    val jsonArray = JSONArray(result)

                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val user = User()
                        user.username = jsonObject.getString("login")
                        user.avatar_url = jsonObject.getString("avatar_url")
                        user.link = jsonObject.getString("html_url")
                        listMengikuti.add(user)
                    }

                    listFollowing.postValue(listMengikuti)

                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {

                Log.d("onFailure", error?.message.toString())

                Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show()

            }


        })
    }

    fun getFollowingUser(): LiveData<ArrayList<User>> {

        return listFollowing
    }


}