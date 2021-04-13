package com.ilyasa.githubuserapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ilyasa.githubuserapp.data.User
import com.ilyasa.githubuserapp.databinding.ItemUsersBinding
import java.lang.StringBuilder

/**
 * Created by Ilyasa Ridho
on 18,March,2021
 */



class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null
    private  val mData = ArrayList<User>()

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class UserViewHolder(private val binding: ItemUsersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            with(binding){
                Glide.with(itemView.context)
                        .load(user.avatar_url)
                        .apply(RequestOptions().override(55,55))
                        .into(imgUser)
                tvItemUsername.text = StringBuilder("@${user.username}")
                tvItemUrl.text = user.link
                itemView.setOnClickListener{onItemClickCallback?.onItemClicked(user)}

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserViewHolder(binding)


    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int {
        return mData.size

    }

    fun setData(items: ArrayList<User>){
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    interface OnItemClickCallback {

        fun onItemClicked(data: User)
    }
}

