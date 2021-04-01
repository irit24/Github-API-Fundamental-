package com.ilyasa.githubuserapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.ilyasa.githubuserapp.data.User
import com.ilyasa.githubuserapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    companion object{
        const val  EXTRA_USER = "extra_user"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val users = intent.getParcelableExtra<User>(EXTRA_USER) as User

        binding.tvName.text = users.name
        binding.tvFollowers.text = users.followers
        binding.tvLocation.text = users.location
        binding.tvFollowing.text = users.following
        binding.tvCompany.text =users.company
        binding.tvRepository.text = users.repository

        Glide.with(this)
            .load(users.avatar)
            .into(binding.imgPhoto)

        supportActionBar?.title = "@${users.username}"
    }

}



