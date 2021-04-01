package com.ilyasa.githubuserapp.activity

import android.content.Intent
import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.Toast
import com.ilyasa.githubuserapp.R
import com.ilyasa.githubuserapp.activity.DetailActivity.Companion.EXTRA_USER

import com.ilyasa.githubuserapp.adapter.UserAdapter
import com.ilyasa.githubuserapp.data.User
import com.ilyasa.githubuserapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var  adapter: UserAdapter
    private lateinit var dataUserName:Array<String>
    private lateinit var dataName:Array<String>
    private lateinit var dataLocation:Array<String>
    private lateinit var dataCompany:Array<String>
    private lateinit var dataRepository:Array<String>

    private lateinit var dataFollowers:Array<String>
    private lateinit var dataFollowing:Array<String>

    private lateinit var dataAvatar : TypedArray

    private lateinit var  binding: ActivityMainBinding
    private var users = arrayListOf<User>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter(this)
                binding.lvList.adapter = adapter

        binding.lvList.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            Toast.makeText(this@MainActivity, users[position].username, Toast.LENGTH_SHORT).show()
            val moveParcel = Intent(this@MainActivity, DetailActivity::class.java)
            moveParcel.putExtra(EXTRA_USER, users[position])
            startActivity(moveParcel)


        }
        loadList()
        addItem()


    }



    private fun addItem() {
        for (position in dataUserName.indices){
            val user = User(
                    dataUserName[position],
                    dataName[position],
                    dataLocation[position],
                    dataRepository[position],
                    dataCompany[position],
                    dataFollowing[position],
                    dataFollowers[position],
                    dataAvatar.getResourceId(position, -1)

                    )
            users.add(user)
        }
        adapter.users = users
    }


    private fun loadList() {
        dataUserName = resources.getStringArray(R.array.username)
        dataName = resources.getStringArray(R.array.name)
        dataLocation = resources.getStringArray(R.array.location)
        dataRepository = resources.getStringArray(R.array.repository)
        dataCompany = resources.getStringArray(R.array.company)
        dataFollowing = resources.getStringArray(R.array.following)
        dataFollowers = resources.getStringArray(R.array.followers)
        dataAvatar = resources.obtainTypedArray(R.array.avatar)

    }
}
