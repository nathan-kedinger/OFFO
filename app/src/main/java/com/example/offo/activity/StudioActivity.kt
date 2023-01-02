package com.example.offo.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION_CODES.S
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.example.offo.R
import com.example.offo.databinding.ActivityStudioBinding
import com.example.offo.model.RecordModel
import com.example.offo.repository.RecordRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.io.File
import java.io.IOException
import java.util.*

private const val LOG_TAG = "AudioRecordTest"
    private const val REQUEST_RECORD_AUDIO_PERMISSION = 200

class StudioActivity : AppCompatActivity() {
        private lateinit var binding: ActivityStudioBinding

        private var fileName: String = ""

        private var recorder: MediaRecorder? = null
        private var mStartRecording = false

        private var player: MediaPlayer? = null
        private var mStartPlaying = true

        val randomId = UUID.randomUUID().toString()
        var recordUri : Uri = Uri.fromFile(File(fileName))

    // Requesting permission to RECORD_AUDIO
        private var permissionToRecordAccepted = false
        private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)


        override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
        ) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            permissionToRecordAccepted = if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
                grantResults[0] == PackageManager.PERMISSION_GRANTED
            } else {
                false
            }
            if (!permissionToRecordAccepted) finish()
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.studioBackHome.setOnClickListener{
            startActivity(Intent(this, HomeActivity::class.java))

        }
        // Record to the external cache directory for visibility
         //fileName = "${externalCacheDir?.absolutePath}/ ${randomId}.3gp"
        fileName = "${filesDir.absolutePath}/${randomId}.mp4"
        Toast.makeText(this, fileName, Toast.LENGTH_LONG).show()


        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION)

        val recordButton: ImageButton = binding.studioRecordButton
        recordButton.setOnClickListener {

            onRecord(mStartRecording)
            when (mStartRecording) {
                true -> recordButton.setBackgroundResource(R.drawable.ic_record_pushed)
                false -> recordButton.setBackgroundResource(R.drawable.ic_record)
            }

            mStartRecording = !mStartRecording
        }

        val playButton: ImageButton = binding.studioPlayButton
        playButton.setOnClickListener {

            onPlay(mStartPlaying)
            when (mStartPlaying) {
                true -> playButton.setBackgroundResource(R.drawable.ic_pause)
                false -> playButton.setBackgroundResource(R.drawable.ic_play)
            }
            mStartPlaying = !mStartPlaying
        }


        val publishButton: ImageButton = binding.studioPublishButton
        publishButton.setOnClickListener {
            publish()
        }
    }

        fun publish(){
            val repo = RecordRepository()
            recordUri = Uri.fromFile(File(fileName))
            val recordArtistUUID = Firebase.auth.currentUser?.uid
            val recordArtistName = Firebase.auth.currentUser?.displayName
            val recordTitle = binding.studioRecordName.text.toString()
            val recordKind = binding.studioCategorie.selectedItem.toString()
            val recordVoiceType = binding.studioVoiceType.selectedItem.toString()
            val record = RecordModel(
                randomId,
                recordArtistUUID,
                recordArtistName,
                recordTitle,
                recordKind,
                recordVoiceType
            )

            repo.uploadRecordFile(recordUri, randomId)
            repo.saveRecordDataIntDatabase(record)

        }

        private fun onRecord(start: Boolean) = if (start) {
                startRecording()
        } else {
                stopRecording()
        }

        private fun onPlay(start: Boolean) = if (start) {
            startPlaying()
        } else {
            stopPlaying()
        }
        private fun startPlaying() {
            player = MediaPlayer().apply {
                try {
                    setDataSource(fileName)
                    prepare()
                    start()

                } catch (e: IOException) {
                    Log.e(LOG_TAG, "prepare() failed")
                }
            }
        }

        private fun stopPlaying() {
            player?.release()
            player = null
        }

        private fun startRecording() {

            if (Build.VERSION.SDK_INT >= S) {
                recorder = MediaRecorder(this).apply {
                    setAudioSource(MediaRecorder.AudioSource.MIC)
                    setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                    setOutputFile(fileName)
                    setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

                    try {
                        prepare()
                    } catch (e: IOException) {
                        Log.e(LOG_TAG, "prepare() failed")
                    }

                    start()
                }
            } else {
                recorder = MediaRecorder().apply {
                    setAudioSource(MediaRecorder.AudioSource.DEFAULT)
                    setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                    setOutputFile(fileName)
                    setAudioEncoder(MediaRecorder.AudioEncoder.HE_AAC)

                    try {
                        prepare()
                    } catch (e: IOException) {
                        Log.e(LOG_TAG, "prepare() failed")
                    }

                    start()
                }
            }
        }

    private fun stopRecording() {
        recorder?.apply {
            stop()
            release()
        }
        recorder = null
    }

        override fun onStop() {
            super.onStop()
            recorder?.release()
            recorder = null
            player?.release()
            player = null
        }
    }







