package com.example.offo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.offo.R
import com.example.offo.activity.HomeActivity
import com.example.offo.model.UserModel
import com.example.offo.repository.UserRepository
import com.example.offo.repository.UserRepository.Singleton.userList

class SearchUserAdapter(
    val context: HomeActivity,//recuperation du context où se déroule le recyclerview
    private val userList: List<UserModel>,
    private val layoutId: Int
) : RecyclerView.Adapter<SearchUserAdapter.ViewHolder>(){

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val userName = view.findViewById<TextView>(R.id.items_users_user_name)
        val userNumberOfFollowers = view.findViewById<TextView>(R.id.items_users_number_of_followers)
        val userNumberOfFriends = view.findViewById<TextView>(R.id.items_users_number_of_friends)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layoutId, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentUser: UserModel =  userList[position]

        holder.userName.text = currentUser.name
        holder.userNumberOfFriends.text = currentUser.numberOfFriends.toString()
        holder.userNumberOfFollowers.text = currentUser.numberOfFollowers.toString()

    }

    override fun getItemCount(): Int {
        return userList.size
    }
}