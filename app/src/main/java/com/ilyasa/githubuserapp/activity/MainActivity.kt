package com.ilyasa.githubuserapp.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isEmpty
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilyasa.githubuserapp.R
import com.ilyasa.githubuserapp.adapter.UserAdapter
import com.ilyasa.githubuserapp.data.User
import com.ilyasa.githubuserapp.model.MainViewModel
import com.ilyasa.githubuserapp.databinding.ActivityMainBinding
import net.steamcrafted.materialiconlib.MaterialMenuInflater

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadList()
        getRandomUser()

    }

    private fun loadList() {
        adapter = UserAdapter()

        binding.rv.setHasFixedSize(true)
        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.adapter = adapter

        adapter.notifyDataSetChanged()
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java
        )



        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {


            override fun onItemClicked(data: User) {
                val moveIntent = Intent(this@MainActivity, DetailActivity::class.java)
                moveIntent.putExtra(DetailActivity.EXTRA_USER, data.username)
                startActivity(moveIntent)

                data.username?.let { Log.d("data Terkirim", it) }
                Toast.makeText(this@MainActivity, data.username, Toast.LENGTH_SHORT).show()

            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        MaterialMenuInflater.with(this)
            .setDefaultColor(Color.WHITE)
            .inflate(R.menu.menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                getDataUser(query)
                showLoading(true)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {

                if (searchView.isEmpty() && TextUtils.isEmpty(newText)) {


                    getDataUser(newText)
                    Toast.makeText(this@MainActivity, "Cari Username", Toast.LENGTH_SHORT).show()
                } else {


                    getDataUser(newText)
                    showLoading(false)
                    Toast.makeText(this@MainActivity, newText, Toast.LENGTH_SHORT).show()

                }


                return false
            }
        })
        return true
    }

    private fun getDataUser(username: String) {

        if (username.isNotEmpty()) {
            showLoading(true)
            mainViewModel.setUser(username, applicationContext)
        }

        mainViewModel.getUser().observe(this, {

            if (it != null) {
                adapter.setData(it)
                showLoading(false)


            }
        }


        )


    }

    private fun getRandomUser() {


        showLoading(true)
        mainViewModel.setRandomUser(applicationContext)

        mainViewModel.getRandomUser().observe(this, {

            if (it != null) {
                adapter.setData(it)
                showLoading(false)
            }
        })

    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}
