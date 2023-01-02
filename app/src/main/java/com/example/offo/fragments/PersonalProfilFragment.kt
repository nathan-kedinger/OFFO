package com.example.offo.fragments

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.system.Os.remove
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.offo.R
import com.example.offo.activity.HomeActivity
import com.example.offo.databinding.FragmentPersonalProfilBinding
import com.example.offo.repositoryManager.UserManager
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseUser


class PersonalProfilFragment() : Fragment() {


    private var _binding: FragmentPersonalProfilBinding? = null
    private val binding get() = _binding!!

 override fun onCreateView(
     inflater: LayoutInflater,
     container: ViewGroup?,
     savedInstanceState: Bundle?
 ): View? {
     _binding = FragmentPersonalProfilBinding.inflate(layoutInflater, container, false)

     val view = binding.root

     updateUIWithUserData()

    return view
}

    override fun onStart() {
        super.onStart()

        binding.personalProfilLogout.setOnClickListener {
            signOut()
        }
        binding.personalProfilDeleteProfil.setOnClickListener{
            deleteProfil()
        }
    }

    private fun updateUIWithUserData(){
        if(UserManager().isCurrentUserLogged()){
            val user : FirebaseUser? = UserManager().getCurrentUser()

            if(user?.photoUrl != null){
                setProfilePicture(user?.photoUrl!!)
            }
            setTextUserData(user)
        }
    }

    private fun setProfilePicture(photoUrl: Uri) {
    Glide.with(this)
        .load(photoUrl)
        .apply(RequestOptions.circleCropTransform())
        .into(binding.personalProfilMyPicture)

    }

    private fun setTextUserData(user: FirebaseUser?) {
        //We get the email and username from user
        val email = if(TextUtils.isEmpty(user?.email)) getString(R.string.personal_profil_mail) else user?.email

        val username = if(TextUtils.isEmpty(user?.displayName)) getString(R.string.personal_profil_artist_name) else user?.displayName

        binding.personalProfilMyName.text = username
        binding.personalProfilMail.text = email
    }

    //fonction de modification des données

    private fun signOut() {
        activity?.let{
            AuthUI.getInstance().signOut(it)

            val intent = Intent (it, HomeActivity::class.java)
            it.startActivity(intent)
        }

        onDestroy()
    }

    private fun deleteProfil() {
        activity?.let{
            AlertDialog.Builder(it)
                .setMessage(R.string.popup_delete_profil_warning)
                .setPositiveButton(R.string.popup_delete_profil_yes)
                { dialogInterface, i ->
                    UserManager().deleteUser(it)
                        .addOnSuccessListener {
                            activity?.let{
                                AuthUI.getInstance().signOut(it)

                                val intent = Intent (it, HomeActivity::class.java)
                                it.startActivity(intent)
                            }

                        }
                }
                .setNegativeButton(R.string.popup_delete_profil_no, null)
                .show()
        }

    }
}
