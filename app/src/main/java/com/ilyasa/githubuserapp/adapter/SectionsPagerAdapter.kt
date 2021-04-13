package com.ilyasa.githubuserapp.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ilyasa.githubuserapp.fragment.FollowersFragment
import com.ilyasa.githubuserapp.fragment.FollowingFragment

/**
 * Created by Ilyasa Ridho
on 04,April,2021
 */
class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var username: String? =null

    override fun getItemCount(): Int {

        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? =null
        when (position){
            0 -> fragment = FollowersFragment.newInstance(username)
            1 -> fragment = FollowingFragment.newInstance(username)
        }
        return  fragment as Fragment
    }
}
