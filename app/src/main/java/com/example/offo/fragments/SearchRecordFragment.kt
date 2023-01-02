package com.example.offo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.offo.R
import com.example.offo.activity.HomeActivity
import com.example.offo.adapter.RecordAdapter
import com.example.offo.databinding.FragmentResearchRecordBinding
import com.example.offo.repository.RecordRepository
import com.example.offo.repository.RecordRepository.Singleton.recordList

class SearchRecordFragment (
): Fragment() {
    private var _binding: FragmentResearchRecordBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResearchRecordBinding.inflate(layoutInflater, container, false)

        val view = binding.root


        val researchValue = binding.researchRecordSearchview
        val recordSearchRecycler = binding.researchRecordRecycler

        binding.researchRecordUser.setOnClickListener{
            findNavController().navigate(R.id.action_searchRecordFragment_to_searchUserFragment)
        }

        RecordRepository().updateData() {
            val navController = findNavController()

            recordSearchRecycler.adapter = RecordAdapter(
                navController,
                HomeActivity(),
                recordList,
                R.layout.items_record_recycled_home
            )
        }

        researchValue.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {

                    RecordRepository().getRecords(query) {
                        val navController = findNavController()

                        recordSearchRecycler.adapter = RecordAdapter(
                            navController,
                            HomeActivity(),
                            recordList,
                            R.layout.items_record_recycled_home
                        )
                    }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                RecordRepository().getRecords(newText) {
                    val navController = findNavController()

                    recordSearchRecycler.adapter = RecordAdapter(
                        navController,
                        HomeActivity(),
                        recordList,
                        R.layout.items_record_recycled_home
                    )
                }
                return false
            }
        })



        return view
    }
}