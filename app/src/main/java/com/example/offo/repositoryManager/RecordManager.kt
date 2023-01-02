package com.example.offo.repositoryManager

import android.media.MediaPlayer
import android.util.Log
import android.widget.ImageButton
import com.example.offo.repository.RecordRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.io.IOException

class RecordManager {
    // Firebase instance variables
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseDatabase

    //record

    //play bloc
    private var mStartPlaying = true
    private var player: MediaPlayer? = null
    var recordArtistName : String? = ""
     var recordTitle : String? = ""
    lateinit var addButton : ImageButton
    lateinit var playButton : ImageButton



    // MainFragment actions
    private var MainFragmentOn : Boolean = false
    // RECORDS

    fun onPlay(start: Boolean, currentRecordId: Int) = if (start) {
        startPlaying(currentRecordId)
    } else {
        stopPlaying()
    }

    private fun startPlaying(currentRecordId: Int) {
        val repo = RecordRepository()
        var currentRecord = RecordRepository.Singleton.recordList[currentRecordId]
        val recordId = currentRecord.id
        var recordPath = repo.getRecordPath(recordId)


        recordPath.downloadUrl
            .addOnCompleteListener { task->

                if(!task.isSuccessful){
                    task.exception?.let {throw it}
                }
                else if(task.isSuccessful){
                    val downloadUri = task.result
                    player = MediaPlayer().apply {
                        try {

                            setDataSource(downloadUri.toString())
                            prepare()
                            start()

                        } catch (e: IOException) {
                            Log.e(" LOG_TAG","prepare() failed $recordPath")
                        }
                    }
                }
            }.addOnCanceledListener {  }

    }

    private fun stopPlaying() {
        player?.release()
        player = null
    }
}