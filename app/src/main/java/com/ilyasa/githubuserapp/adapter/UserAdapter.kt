package com.ilyasa.githubuserapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.ilyasa.githubuserapp.R
import com.ilyasa.githubuserapp.data.User
import com.ilyasa.githubuserapp.databinding.ItemUserBinding
import java.lang.StringBuilder

/**
 * Created by Ilyasa Ridho
on 18,March,2021
 */
class UserAdapter internal  constructor(private  val  context: Context): BaseAdapter() {
    internal var users = arrayListOf<User>()

    override fun getCount(): Int {

        return users.size
    }

    override fun getItem(position: Int): Any {
        return users[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false)
        }
        val viewHolder = ViewHolder(itemView as View)
        val user = getItem(position) as User
        viewHolder.bind(user)
        return itemView
    }

    private inner class ViewHolder constructor(view: View) {

        private val binding = ItemUserBinding.bind(view)
        fun bind(user: User) {
            binding.tvItemUsername.text = StringBuilder("@${user.username}")
            binding.tvItemName.text = user.name
            binding.tvItemCompany.text = user.company

            Glide.with(context)
                .load(user.avatar)
                .into(binding.imgUser)



        }

    }

}
