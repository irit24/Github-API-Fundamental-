package com.ilyasa.githubuserapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes

import androidx.lifecycle.ViewModelProvider

import com.bumptech.glide.Glide

import com.google.android.material.tabs.TabLayoutMediator
import com.ilyasa.githubuserapp.R
import com.ilyasa.githubuserapp.adapter.SectionsPagerAdapter
import com.ilyasa.githubuserapp.databinding.ActivityDetailBinding
import com.ilyasa.githubuserapp.model.DetailViewModel
import java.lang.StringBuilder

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.txt_followers,
            R.string.txt_following
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setPager()
        detailUser()

    }
    private fun setPager() {


        val sectionsPager = SectionsPagerAdapter(this)
        val users = intent.getStringExtra(EXTRA_USER)

        sectionsPager.username = users
        binding.viewPager.adapter = sectionsPager

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }


    private fun detailUser() {
        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)

        val users = intent.getStringExtra(EXTRA_USER)

        detailViewModel.setDetailUser(users, applicationContext)
        detailViewModel.getDetailUser().observe(this, {
            with(binding) {

                tvName.text = it[0].name
                tvLocation.text = it[0].location
                tvFollowers.text = it[0].followers
                tvFollowing.text = it[0].following
                tvCompany.text = it[0].company
                tvRepository.text = it[0].repository
                tvBio.text = it[0].bio
                Glide.with(applicationContext)
                    .load(it[0].avatar_url)
                    .into(imgPhoto)
                supportActionBar?.title = StringBuilder("@${it[0].username}")

            }
        }
        )


    }

}

