package com.example.offo.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.offo.R
import com.example.offo.databinding.ActivityHomeBinding
import com.example.offo.repository.RecordRepository
import com.example.offo.classes.PushedButtonView
import com.example.offo.popup.LoginPopup
import com.example.offo.repository.UserRepository
import com.example.offo.repositoryManager.RecordManager
import com.example.offo.repositoryManager.UserManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    // Firebase instance variables
    private var auth: FirebaseAuth= Firebase.auth
    private lateinit var db: FirebaseDatabase

    //record
    private var currentRecordId : Int = 0

    //play bloc
    private var mStartPlaying = true
    //private var player: MediaPlayer? = null
    /*lateinit var recordArtistName : TextView
    lateinit var recordTitle : TextView*/
    lateinit var addButton : ImageButton
    lateinit var playButton : ImageButton

    // MainFragment actions
    private var MainFragmentOn : Boolean = false

   // val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
    //val navController = navHostFragment?.navController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.homeMainProfil.setOnClickListener{
            this.findNavController(R.id.nav_host_fragment).navigate(R.id.personalProfilFragment)
            MainFragmentOn = false

        }

        handleResponseAfterSignIn()

        // TO OTHER ACTIVITIES
        binding.homeMainToRecycler.setOnClickListener {
            this.findNavController(R.id.nav_host_fragment).navigate(R.id.recyclerRecordFragment)
            //startActivity(Intent(this, HomeRecyclerRecordActivity::class.java))
        }

        binding.homeMainRecordRecord.setOnClickListener{
            // Check if user is signed in.
            if (auth.currentUser == null) {
                LoginPopup(this).show()


            } else {
                startActivity(Intent(this, StudioActivity::class.java))
                finish()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        MainFragmentOn = true

        // FRAGMENTS

        /*val transactionMain = supportFragmentManager.beginTransaction() //manipulation des fragments
        transactionMain.replace(R.id.home_main_fragment, MainFragment(currentRecordId))// on remplace le fragment
        transactionMain.addToBackStack(null)
        MainFragmentOn = true
        transactionMain.commit()*/
        //nav host controller


        /*binding.homeMainMessages.setOnClickListener{

           navController.navigate(R.id.artistProfilFragment)
            /*val transactionTest = supportFragmentManager.beginTransaction() //manipulation des fragments
            transactionTest.replace(R.id.home_main_fragment, TestFragment())// on remplace le fragment
            MainFragmentOn = false
            transactionTest.addToBackStack(null)
            transactionTest.commit()*/
        }

        binding.homeMainProfil.setOnClickListener{
            val transaction2 = supportFragmentManager.beginTransaction() //manipulation des fragments
            transaction2.replace(R.id.home_main_fragment, PersonalProfilFragment())// on remplace le fragment
            MainFragmentOn = false
            transaction2.addToBackStack(null)
            transaction2.commit()
        }*/

        binding.homeMainResearch.setOnClickListener{
            this.findNavController(R.id.nav_host_fragment).navigate(R.id.searchUserFragment)

        }

        // RECORDS BLOC

        val recordArtistName = binding.homeMainRecordArtistName
        val recordTitle = binding.homeMainRecordTitle
        playButton= binding.homeMainRecordPlay


        //On charge nos records ici en instanciant le repository
        val repo = RecordRepository()

        //mettre à jour la liste des records


        repo.updateData{
        //trouver une solution pour adapter le nom à l'id de l'utilisateur et non au nom entré avec ModelRecord
            var currentRecord = RecordRepository.Singleton.recordList[currentRecordId]
            recordTitle.text = currentRecord.title

                UserRepository().getUser(currentRecord.artistUUID) {

                    //mettre à jour le nom du record
                    recordArtistName.text = UserRepository.Singleton.currentUser?.name
                }


            binding.homeMainRecordForward.setOnClickListener {
                //RecordManager().nextRecord()

                currentRecordId++
                currentRecord = RecordRepository.Singleton.recordList[currentRecordId]
                recordTitle.text = currentRecord.title
                UserRepository().getUser(currentRecord.artistUUID) {
                    recordArtistName.text = UserRepository.Singleton.currentUser?.name
                }
                if (MainFragmentOn) {
                    val args = Bundle()
                    args.putInt("key", currentRecordId)
                    this.findNavController(R.id.nav_host_fragment).navigate(R.id.homeFragment, args)

                    /*val transaction = supportFragmentManager.beginTransaction() //manipulation des fragments
                    transaction.replace(R.id.fragment_home,HomeFragment()
                    )// on remplace le fragment
                    transaction.addToBackStack(null)
                    transaction.commit()*/
                }

            }


            binding.homeMainRecordRewind.setOnClickListener{
                currentRecordId--
                if (MainFragmentOn){
                    val args = Bundle()
                    args.putInt("key", currentRecordId)
                    this.findNavController(R.id.nav_host_fragment).navigate(R.id.homeFragment, args)

                }
                currentRecord = RecordRepository.Singleton.recordList[currentRecordId]
                    recordTitle.text = currentRecord.title
                UserRepository().getUser(currentRecord.artistUUID) {
                    recordArtistName.text = UserRepository.Singleton.currentUser?.name
                }
            }
        }

        playButton.setOnClickListener {

            RecordManager().onPlay(mStartPlaying, currentRecordId)
            PushedButtonView().changeImageButtonOnPush(
                playButton,
                mStartPlaying,
                R.drawable.ic_play,
                R.drawable.ic_pause
            )

            mStartPlaying = !mStartPlaying

        }
    }

// Method that handles response after SignIn Activity close
     private fun handleResponseAfterSignIn(){

            if (auth.currentUser != null) {
                UserManager().createUser()
                Toast.makeText(this,"connecté", Toast.LENGTH_LONG).show()
            } else {
                // ERRORS
            }
    }



   /* // RECORDS
    fun nextRecord(){
        currentRecordId++

        if (MainFragmentOn){
            val transaction = supportFragmentManager.beginTransaction() //manipulation des fragments
            transaction.replace(R.id.home_main_fragment, MainFragment(currentRecordId))// on remplace le fragment
            transaction.addToBackStack(null)
            transaction.commit()
        }

    }

    fun previousRecord(){
        currentRecordId--

        if (MainFragmentOn){
            val transaction = supportFragmentManager.beginTransaction() //manipulation des fragments
            transaction.replace(R.id.home_main_fragment, MainFragment(currentRecordId))// on remplace le fragment
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    private fun onPlay(start: Boolean) = if (start) {
        startPlaying()
    } else {
        stopPlaying()
    }

    private fun startPlaying() {
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

        Toast.makeText(this,recordPath.toString(), Toast.LENGTH_LONG).show()



    }

    private fun stopPlaying() {
        player?.release()
        player = null
    }*/
}


/*val recordsHomeRecycler = findViewById<RecyclerView>(R.id.records_home_recycler)
    recordsHomeRecycler.adapter = RecordAdapter(
        this, RecordRepository.Singleton.recordList,
        R.layout.items_record_recylcled_home
    )//on récupère la recordList du repository
}*/
/*// créer liste de stockage des records
val recordList = arrayListOf<RecordModel>()

//enregistrer un nouveau record dans notre liste
recordList.add(RecordModel(

    1,
    "Nathan",
    "First Record",
    "experimental",
    "basse",
    0,
    0
))*/