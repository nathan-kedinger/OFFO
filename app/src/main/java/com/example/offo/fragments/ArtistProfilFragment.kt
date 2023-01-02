package com.example.offo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.offo.R
import com.example.offo.databinding.FragmentArtistProfilBinding
import com.example.offo.repository.RecordRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ArtistProfilFragment : Fragment() {
    private lateinit var auth: FirebaseAuth

    private lateinit var binding: FragmentArtistProfilBinding

    val artistUUID : String? = arguments?.getString("recordToArtistProfileArtistUUID")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = Firebase.auth
        binding = FragmentArtistProfilBinding.inflate(layoutInflater)
        val view = binding.root





        return view
    }
}