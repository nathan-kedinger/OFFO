package com.example.offo.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.offo.*
import com.example.offo.activity.HomeActivity
import com.example.offo.classes.PushedButtonView
import com.example.offo.model.RecordModel
import com.example.offo.popup.ModifyRecordPopup
import com.example.offo.repository.RecordRepository
import com.example.offo.repository.UserRepository
import com.example.offo.repositoryManager.RecordManager

class RecordAdapter(
    val navController: NavController,
    val context: Context,//recuperation du context où se déroule le recyclerview
    private val recordList: List<RecordModel>,
    private val layoutId: Int
    ) : RecyclerView.Adapter<RecordAdapter.ViewHolder>(){


    //boite de rangement de composants on peut y importer tous les composants que l'on souhaite intégrer au recyclerView
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
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
    }
    private var mStartPlaying = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layoutId, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //recupérer les informations du record spécifique
        val currentRecord = recordList[position]
        //ex : currentRecord.artistName Ici on récupère les noms


        val repo = RecordRepository()

        //utiliser glide pour récupérer une image à partir d'un lien -> composant
        //Glide.with(context).load(Uri.parse(currentRecord.imageUrl)).into(holder.recordImage)// context est une sorte de base de données interne qui contient toutes les informations contextuelles de l'application

        //mettre à jour le record
        UserRepository().getUser(currentRecord.artistUUID) {

            if (UserRepository.Singleton.currentUser != null) {
                holder.recordArtistName.text = UserRepository.Singleton.currentUser?.name
            } else{
                holder.recordArtistName.text = "OFFO"
            }
        }

        holder.recordTitle.text = currentRecord.title
        holder.recordKind.text = currentRecord.kind
        holder.recordVoiceStyle.text = currentRecord.voiceStyle
        holder.recordNumberOfPlay.text = currentRecord.numberOfPlay.toString()
        holder.recordNumberOfMoons.text = currentRecord.numberOfMoons.toString()

        //rajouter une intéraction des moons
        holder.addButton.setOnClickListener {
            val toRecordPage = Bundle()
            toRecordPage.putString("artistIdToRecordPage", currentRecord.artistUUID)
            toRecordPage.putInt("recordIdToRecordPage", position)

            navController.navigate(R.id.recordPageFragment, toRecordPage)
        }

        //intéraction au click plus record
        holder.playButton.setOnClickListener{
            RecordManager().onPlay(mStartPlaying, position)
            PushedButtonView().changeImageButtonOnPush(
                holder.playButton,
                mStartPlaying,
                R.drawable.ic_play,
                R.drawable.ic_pause
            )

            mStartPlaying = !mStartPlaying
        }

    }

    override fun getItemCount(): Int {
        return recordList.size
    }

}