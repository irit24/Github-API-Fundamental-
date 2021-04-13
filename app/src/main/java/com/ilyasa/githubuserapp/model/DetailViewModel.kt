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
import org.json.JSONObject
import java.lang.Exception

/**
 * Created by Ilyasa Ridho
on 04,April,2021
 */
class DetailViewModel : ViewModel() {


    companion object {
        private val TAG = MainViewModel::class.java.simpleName
        private const val API = BuildConfig.GITHUB_TOKEN

    }

    private val detailUser = MutableLiveData<ArrayList<User>>()


    fun setDetailUser(username: String?,context: Context) {
        val userDetail = ArrayList<User>()
        val url = "https://api.github.com/users/$username"


        val asyncClient = AsyncHttpClient()
        asyncClient.addHeader("Authorization", "token $API")
        asyncClient.addHeader("User-Agent", "request")
        asyncClient.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray) {


                try {
                    val result = String(responseBody)
                    Log.d(TAG, result)


                    val jsonObject = JSONObject(result)
                    val user = User()
                    user.username = jsonObject.getString("login")
                    user.name = jsonObject.getString("name")
                    user.avatar_url = jsonObject.getString("avatar_url")
                    user.company = jsonObject.getString("company")
                    user.followers = jsonObject.getString("followers")
                    user.following = jsonObject.getString("following")
                    user.location = jsonObject.getString("location")
                    user.repository = jsonObject.getString("public_repos")
                    user.bio = jsonObject.getString("bio")
                    userDetail.add(user)

                    detailUser.postValue(userDetail)


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

    fun getDetailUser(): LiveData<ArrayList<User>> {

        return detailUser
    }


}