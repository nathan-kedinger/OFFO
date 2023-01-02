package com.example.offo.repositoryManager

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.offo.repository.UserRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class UserManager {

    fun getCurrentUser(): FirebaseUser?{
        return FirebaseAuth.getInstance().currentUser
    }

    fun isCurrentUserLogged() : Boolean{
        return (this.getCurrentUser() != null)
    }

    fun deleteUser(context : Context) : Task<Void>{
        return UserRepository().deleteUser(context)
    }

    fun createUser(){
        UserRepository().createUser()
    }

    fun setProfilePicture(photoUrl: Uri?, context: Context, destination: ImageView) {
        Glide.with(context)
            .load(photoUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(destination)

    }

    fun updateUsername(){

    }
}