package com.example.offo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.toLowerCase
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.offo.R
import com.example.offo.activity.HomeActivity
import com.example.offo.adapter.SearchUserAdapter
import com.example.offo.databinding.FragmentResearchPersonBinding
import com.example.offo.repository.UserRepository
import com.example.offo.repository.UserRepository.Singleton.userList
import java.util.*

class SearchUserFragment (
): Fragment() {
    private var _binding: FragmentResearchPersonBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResearchPersonBinding.inflate(layoutInflater, container, false)

        val view = binding.root


        val researchValue = binding.researchPersonSearchview
        val userSearchRecycler = binding.researchPersonRecycler

        binding.researchPersonRecord.setOnClickListener{
            findNavController().navigate(R.id.action_searchUserFragment_to_searchRecordFragment)
        }

        UserRepository().getUserList() {

            userSearchRecycler.adapter = SearchUserAdapter(
                HomeActivity(),
                userList,
                R.layout.items_users
            )
        }

        researchValue.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                userList.clear()

                UserRepository().getSearchedUserList(query.lowercase(Locale.ROOT)
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }) {

                    userSearchRecycler.adapter = SearchUserAdapter(
                        HomeActivity(),
                        userList,
                        R.layout.items_users
                    )
                }

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                userList.clear()

                UserRepository().getSearchedUserList(newText) {

                        userSearchRecycler.adapter = SearchUserAdapter(
                            HomeActivity(),
                            userList,
                            R.layout.items_users
                        )
                    }
                return false
            }
        })

        return view
    }
}