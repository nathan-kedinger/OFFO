package com.example.offo.model

import android.net.Uri

class UserModel (
    val uuid: String? = "null",
    val name: String? = "Jon Doe",
    val urlPicture: Uri? = null,
    val email: String? = "a@gt.fr",
    val phone: String? = "0600000000",
    var numberOfFriends: Int = 0,
    var numberOfFollowers: Int = 0,
    var friends: String? = "",
    val description: String? = ""
)