package com.example.offo.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.ui.text.toLowerCase
import com.example.offo.model.UserModel
import com.example.offo.repository.UserRepository.Singleton.currentUser
import com.example.offo.repository.UserRepository.Singleton.firestoreDatabaseUser
import com.example.offo.repository.UserRepository.Singleton.userList
//import com.example.offo.repository.UserRepository.Singleton.userList
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.*

class UserRepository {

    object Singleton{
        val userList = arrayListOf<UserModel>()
        val firestoreDatabaseUser = FirebaseFirestore.getInstance().collection("users")
        var currentUser: UserModel? = null
   }
    private var auth : FirebaseAuth = Firebase.auth



    fun createUser() {

        val currentUser : FirebaseUser? = auth.currentUser

        //
        if(currentUser != null ){
            val urlPicture : Uri? = if(currentUser.photoUrl != null ) currentUser.photoUrl else null
            val username :String? = currentUser.displayName
            val uid : String = currentUser.uid
            val email : String? = currentUser.email
            val phone : String? = if(currentUser.phoneNumber != null) currentUser.phoneNumber.toString() else null
            val friendsListUid : String? = currentUser.uid
            val description: String? = null

            val user = UserModel(uid, username, urlPicture, email, phone, 0, 0,friendsListUid, description)

            // use the set() method to create or update a document inside users collection
            firestoreDatabaseUser
                .document(uid)
                .set(user)
                .addOnSuccessListener {
                    Log.d(TAG, "Added document with ID ${it}")
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error adding document $exception")
                }
        }
    }

    fun getUser(uuid : String?, callback: ()-> Unit){

        val thecurrentUser = firestoreDatabaseUser.whereEqualTo("uuid", uuid)

        thecurrentUser
            .get()
            .addOnSuccessListener { querySnapshot ->
                querySnapshot.forEach { document ->
                    Log.d(TAG, "Read document with ID ${document.data}")
                    val user = document.toObject(UserModel::class.java)

                    currentUser = user
                }
                callback()

            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents $exception")
            }
    }

    fun getSearchedUserList(query: String, callback: ()-> Unit){

        val currentLookedUser = firestoreDatabaseUser.whereGreaterThanOrEqualTo("name", query)
        currentLookedUser
            .get()
            .addOnSuccessListener { querySnapshot ->
                querySnapshot.forEach { document ->
                    Log.d(TAG, "Read document with ID ${document.data}")
                    val user = document.toObject(UserModel::class.java)

                    userList.add(user)
                }
                callback()

            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents $exception")
            }
    }

    fun getUserList(callback: ()-> Unit){

        firestoreDatabaseUser
            .get()
            .addOnSuccessListener { querySnapshot ->
                querySnapshot.forEach{ document->
                    val userInList = document.toObject(UserModel::class.java)

                    userList.add(userInList)
                }
                callback()
            }
    }



    fun deleteUser(context : Context) : Task<Void> {
        return AuthUI.getInstance().delete(context)
    }
   /* fun readData(callback: ()-> Unit){

        firestoreDatabaseUser
                .get()
                .addOnSuccessListener { querySnapshot ->
                    querySnapshot.forEach { document ->
                            Log.d(TAG, "Read document with ID ${document.data}")
                            val user = document.toObject(UserModel::class.java)

                        userList.add(user)

                    }
                    callback()

                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents $exception")
                }

    }*/



    /*fun getData(userUUID: String){
        var artistName = "b"

        firestoreDatabaseUser
            .document(userUUID)
            .collection("name")
            .get()
            .addOnSuccessListener { document->
                if (document != null){
                    artistName = document
                } else{
                    Log.w(TAG, "you have an account")
                }
            }
            .addOnFailureListener{ e->
                Log.d(TAG, "get failed with", e)
            }
    }*/


}
