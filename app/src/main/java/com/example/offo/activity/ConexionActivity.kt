package com.example.offo.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import com.example.offo.R
import com.example.offo.databinding.ActivityConnexionBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class ConexionActivity : AppCompatActivity() {


        private lateinit var binding: ActivityConnexionBinding

        // ActivityResultLauncher
        private val signIn: ActivityResultLauncher<Intent> =
            registerForActivityResult(FirebaseAuthUIActivityResultContract(), this::onSignInResult)

        // Firebase instance variables
        private lateinit var auth: FirebaseAuth


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            // This codelab uses View Binding
            // See: https://developer.android.com/topic/libraries/view-binding
            binding = ActivityConnexionBinding.inflate(layoutInflater)
            setContentView(binding.root)

            // Initialize FirebaseAuth
            auth = Firebase.auth
        }

        public override fun onStart() {
            super.onStart()

            // If there is no signed in user, launch FirebaseUI
            // Otherwise head to MainActivity
            if (Firebase.auth.currentUser == null) {
                // Sign in with FirebaseUI, see docs for more details:
                // https://firebase.google.com/docs/auth/android/firebaseui
                val signInIntent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setLogo(R.mipmap.ic_launcher)
                    .setTheme(R.style.Theme_OFFO)
                    .setAvailableProviders(listOf(
                        AuthUI.IdpConfig.EmailBuilder().build(),
                        AuthUI.IdpConfig.GoogleBuilder().build(),

                    ))
                    .build()

                signIn.launch(signInIntent)
            } else {
                goToHomeActivity()
            }

        }

        private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {

            if (result.resultCode == RESULT_OK) {
                Log.d(TAG, "Sign in successful!")
                Toast.makeText(this, "success", Toast.LENGTH_LONG)
                goToHomeActivity()
            } else {
                Toast.makeText(
                    this,
                    "There was an error signing in",
                    Toast.LENGTH_LONG).show()

                val response = result.idpResponse
                if (response == null) {
                    Log.w(TAG, "Sign in canceled")
                } else {
                    Log.w(TAG, "Sign in error", response.error)
                }
            }

        }

        private fun goToHomeActivity() {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }



        companion object {
            private const val TAG = "SignInActivity"
        }

    // Initialize Firebase Auth and check if the user is signed in
        fun CheckIfSigned(){
            auth = Firebase.auth
            if (auth.currentUser == null) {
                // Not signed in, launch the Sign In activity
                startActivity(Intent(this, ConexionActivity::class.java))
                finish()
                return
        }
    }

        fun signOut(){
            AuthUI.getInstance().signOut(HomeActivity())
            startActivity(Intent(HomeActivity(), ConexionActivity::class.java))
            finish()
        }

    fun createAccount(){

    }

    }



