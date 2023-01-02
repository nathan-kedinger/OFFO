package com.example.offo.model

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.net.IDN
import java.util.Date

class RecordModel(
    val id : String = "0",
    val artistUUID: String? = "0",
    val artistName: String? = "anonymous",
    val title: String? = "I\'m Talking",
    val kind: String? = "news",
    var voiceStyle: String? = "Ténor",
    var numberOfPlay: Int = 0,
    var numberOfMoons: Int = 0,
    var date: Date = Date()
)