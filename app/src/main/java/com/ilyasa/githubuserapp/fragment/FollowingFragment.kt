package com.ilyasa.githubuserapp.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilyasa.githubuserapp.activity.DetailActivity
import com.ilyasa.githubuserapp.adapter.UserAdapter
import com.ilyasa.githubuserapp.data.User
import com.ilyasa.githubuserapp.databinding.FragmentFollowingBinding
import com.ilyasa.githubuserapp.model.FollowingViewModel

class FollowingFragment : Fragment() {

    private  var _binding : FragmentFollowingBinding? =null
    private  lateinit var   followingViewModel: FollowingViewModel
    private lateinit var  adapter: UserAdapter
    private val binding get() = _binding!!

    companion object {

        private const val ARG_USERNAME = "username"
        fun newInstance(username:String?): Fragment {
            val fragment = FollowingFragment()
            val mBundle = Bundle()
            mBundle.putString(ARG_USERNAME,username)
            fragment.arguments =mBundle
            return fragment
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowingBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding =null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val username = arguments?.getString(ARG_USERNAME)

        loadListFollowers()
        getFollowingUser(username)


    }

    private fun getFollowingUser(username: String?) {

        if (username != null) {
            if (username.isNotEmpty()) {
                showLoading(true)
                followingViewModel.setFollowingUser(username, context)
            }

            followingViewModel.getFollowingUser().observe(viewLifecycleOwner, {

                if (it != null) {
                    adapter.setData(it)
                    Log.d("Terkirim",adapter.setData(it).toString())
                    adapter.notifyDataSetChanged()

                    showLoading(false)
                }
            })
        }


    }


    private fun loadListFollowers() {

        adapter = UserAdapter()
        _binding?.rv?.setHasFixedSize(true)
        _binding?.rv?.layoutManager = LinearLayoutManager(context)
        _binding?.rv?.adapter  =adapter

        adapter.notifyDataSetChanged()
        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowingViewModel::class.java)



        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {


            override fun onItemClicked(data: User) {
                val moveIntent = Intent(activity, DetailActivity::class.java)
                moveIntent.putExtra(DetailActivity.EXTRA_USER,data.username)
                startActivity(moveIntent)
                Log.d("data Terkirim", data.username!! )
                Toast.makeText(activity, data.username, Toast.LENGTH_SHORT).show()

            }
        })
    }


    private fun showLoading(state: Boolean) {
        if (state) {
            _binding?.progressBar?.visibility = View.VISIBLE
        } else {
            _binding?.progressBar?.visibility = View.GONE
        }
    }

}