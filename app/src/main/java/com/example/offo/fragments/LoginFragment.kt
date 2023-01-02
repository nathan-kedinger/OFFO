package com.example.offo.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.offo.activity.HomeActivity
import com.example.offo.R

class  LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.login_fragment, container, false) // on ajoute ici le layout

        val toHomeButton : Button = view.findViewById<Button>(R.id.connexion_continue_without_conexion)

        toHomeButton.setOnClickListener {
            val intent = Intent(activity, HomeActivity::class.java)
            startActivity(intent)

        }
        return view
    }

}