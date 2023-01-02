package com.example.offo.popup

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
import com.example.offo.R
import com.example.offo.activity.ConexionActivity


class LoginPopup(context: Context) : Dialog(context){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE) // on évite de générer un titre puisqu'on en a déjà un
        setContentView(R.layout.popup_require_login) // On injecte le layout

        val loginButton = findViewById<Button>(R.id.popup_require_login_conexion)
        loginButton.setOnClickListener{
            goToLogin()
        }

        val closeButton = findViewById<ImageButton>(R.id.popup_require_login_close)
        closeButton.setOnClickListener{
            closePopup()
        }

    }

    private fun goToLogin() {
        context?.let{
            val intent = Intent (it, ConexionActivity::class.java)
            it.startActivity(intent)
        }
        dismiss()
    }

    private fun closePopup(){
        dismiss()
    }
}