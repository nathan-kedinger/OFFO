package com.example.offo.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.offo.AudioRecord
import com.example.offo.R
import com.example.offo.activity.ConexionActivity
import com.example.offo.activity.HomeActivity
import com.example.offo.activity.StudioActivity
import com.example.offo.databinding.FragmentPlayBlocBinding
import com.firebase.ui.auth.AuthUI

class PlayBlocFragment : Fragment(){
    private var _binding: FragmentPlayBlocBinding? = null
    private val binding get() = _binding!!
    /* override fun onCreateView(
         inflater: LayoutInflater,
         container: ViewGroup?,
         savedInstanceState: Bundle?
     ): View? {
         _binding = FragmentPlayBlocBinding.inflate(layoutInflater, container, false)

         val view = binding.root

         binding.homeMainRecordRecord.setOnClickListener{
             startActivity(Intent(activity, AudioRecord::class.java))
         }

         return view
     }

     override fun onDestroyView() {
         super.onDestroyView()
         _binding = null


    }
    */
}