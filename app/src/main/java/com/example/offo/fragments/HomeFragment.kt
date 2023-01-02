package com.example.offo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.offo.R
import com.example.offo.databinding.FragmentHomeBinding
import com.example.offo.repository.RecordRepository
import com.example.offo.repository.UserRepository

class HomeFragment(
): Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        val view = binding.root

        var currentRecord : Int = arguments?.getInt("key")!!


        //boite de rangement de composants on peut y importer tous les composants que l'on souhaite intégrer au recyclerView

        val recordArtistName = view.findViewById<TextView>(R.id.home_record_artist_name)
        val recordTitle = view.findViewById<TextView>(R.id.home_record_title)
        val recordKind = view.findViewById<TextView>(R.id.home_record_kind)
        val recordVoiceStyle = view.findViewById<TextView>(R.id.home_record_voice_style)
        val recordNumberOfPlay = view.findViewById<TextView>(R.id.home_record_number_of_play)
        val recordNumberOfMoons = view.findViewById<TextView>(R.id.home_record_number_of_play)
        val addButton = binding.homeRecordPlus
        val playButton= view.findViewById<ImageButton>(R.id.home_record_send_message)


        //En cas d'un attribut potentiellement vide ex :
        //val recordKind:TextView? = view.findViewByIdR.id.record_page_number_of_play)


        //On charge nos records ici en instanciant le repository
        val repo = RecordRepository()

        //mettre à jour la liste des records
        repo.updateData{

            val theCurrentRecord = RecordRepository.Singleton.recordList[currentRecord]

            //mettre à jour le record

            UserRepository().getUser(theCurrentRecord.artistUUID) {

                if (UserRepository.Singleton.currentUser != null) {
                    recordArtistName.text = UserRepository.Singleton.currentUser?.name
                } else{
                    recordArtistName.text = "OFFO"
                }
            }

            recordTitle.text = theCurrentRecord.title
            recordKind.text = theCurrentRecord.kind
            recordVoiceStyle.text = theCurrentRecord.voiceStyle
            recordNumberOfPlay.text = theCurrentRecord.numberOfPlay.toString()
            recordNumberOfMoons.text = theCurrentRecord.numberOfMoons.toString()




            //rajouter une intéraction

            addButton.setOnClickListener{
                val toRecordPage = Bundle()
                toRecordPage.putString("artistIdToRecordPage", theCurrentRecord.artistUUID)
                toRecordPage.putInt("recordIdToRecordPage", currentRecord)

                    findNavController().navigate(R.id.action_homeFragment_to_recordPageFragment, toRecordPage)
            }
            /*addButton.setOnClickListener {
                //augmenter la valeur
                theCurrentRecord.numberOfMoons++
                repo.updateRecord(theCurrentRecord)
            }

            //intéraction au click plus record
            playButton.setOnClickListener{
                ModifyRecordPopup(HomeRecyclerRecordActivity, currentRecord).show()
            }*/
        }
        return view

    }


}