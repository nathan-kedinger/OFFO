package com.example.offo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.offo.databinding.FragmentTestBinding
import com.example.offo.repository.UserRepository

class TestFragment : Fragment(){
    private var _binding: FragmentTestBinding? = null
    private val binding get() = _binding!!
 override fun onCreateView(
     inflater: LayoutInflater,
     container: ViewGroup?,
     savedInstanceState: Bundle?
 ): View? {
     _binding = FragmentTestBinding.inflate(layoutInflater, container, false)
     val view = binding.root


         UserRepository().getUser("38mUrG7luHWs1vt0qtIxQOylep02"){
         val recordArtistName = UserRepository.Singleton.currentUser
         binding.textToChange.text = recordArtistName?.name
     }


     /*repo.readData {
         val currentUser = UserRepository.Singleton.userList[0].name
         binding.textToChange.text = currentUser
     }*/


     return view
 }
}