package com.example.offo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.offo.R
import com.example.offo.activity.HomeActivity
import com.example.offo.databinding.FragmentRecordPageBinding
import com.example.offo.repository.RecordRepository
import com.example.offo.repository.UserRepository
import com.example.offo.repositoryManager.UserManager
import com.google.firebase.auth.FirebaseAuth

class RecordPageFragment: Fragment() {
    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentRecordPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecordPageBinding.inflate(layoutInflater, container, false)

        val view = binding.root

        val artistUUID : String? = arguments?.getString("artistIdToRecordPage")
        val recordId: Int = arguments?.getInt("recordIdToRecordPage")!!



        RecordRepository().updateData {
            val currentRecord = RecordRepository.Singleton.recordList[recordId]

            binding.recordPageRecordTitle.text = currentRecord.title
            binding.recordPageNumberOfMoon.text = currentRecord.numberOfMoons.toString()
            binding.recordPageNumberOfPlay.text = currentRecord.numberOfPlay.toString()
            binding.recordPageRecordDate.text = currentRecord.date.toString()
        }

        UserRepository().getUser(artistUUID){
            val currentArtist = UserRepository.Singleton.currentUser

            binding.recordPageArtistName.text = currentArtist?.name

            if (currentArtist?.urlPicture != null) {
                val profilePicture: ImageView = binding.recordPageArtistPicture
                UserManager().setProfilePicture(
                    currentArtist?.urlPicture,
                    HomeActivity(),
                    profilePicture
                )
            }
        }

        binding.recordPageGoToArtist.setOnClickListener{
            val toArtistProfile = Bundle()
            toArtistProfile.putString("recordToArtistProfileArtistUUID", artistUUID)
            findNavController().navigate(R.id.action_recordPageFragment_to_artistProfilFragment, toArtistProfile)


        }
        return view
    }
}




// useless?
/*
        //boite de rangement de composants on peut y importer tous les composants que l'on souhaite intégrer au recyclerView

            val recordArtistName = view.findViewById<TextView>(R.id.record_page_artist)
            val recordTitle = view.findViewById<TextView>(R.id.record_page_record_title)
            val recordKind = view.findViewById<TextView>(R.id.record_page_number_of_play)
            val recordVoiceStyle = view.findViewById<TextView>(R.id.record_page_voice_style)
            val recordNumberOfPlay = view.findViewById<TextView>(R.id.record_page_number_of_play)
            val recordNumberOfMoons = view.findViewById<TextView>(R.id.record_page_moon_number)
            val addButton = view.findViewById<ImageButton>(R.id.record_page_add_button)
            val playButton= view.findViewById<ImageButton>(R.id.record_page_play_button)


            //En cas d'un attribut potentiellement vide ex :
            //val recordKind:TextView? = view.findViewByIdR.id.record_page_number_of_play)


        //On charge nos records ici en instanciant le repository
        val repo = RecordRepository()

        //mettre à jour la liste des records
        repo.updateData{

        val currentRecord = RecordRepository.Singleton.recordList[0]


        //mettre à jour le record
        recordArtistName.text = currentRecord.artistUUID
        recordTitle.text = currentRecord.title
        recordKind.text = currentRecord.kind
        recordVoiceStyle.text = currentRecord.voiceStyle
        recordNumberOfPlay.text = currentRecord.numberOfPlay.toString()
        recordNumberOfMoons.text = currentRecord.numberOfMoons.toString()

        //rajouter une intéraction des moons
        addButton.setOnClickListener {
            //augmenter la valeur
            currentRecord.numberOfMoons++
            repo.updateRecord(currentRecord)
        }

        /*//intéraction au click plus record
        playButton.setOnClickListener{
            ModifyRecordPopup(HomeRecyclerRecordActivity, currentRecord).show()
        }*/
        }



        */