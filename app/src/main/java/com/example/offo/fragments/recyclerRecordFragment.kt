package com.example.offo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.offo.R
import com.example.offo.activity.HomeActivity
import com.example.offo.adapter.RecordAdapter
import com.example.offo.adapter.SearchUserAdapter
import com.example.offo.databinding.FragmentRecyclerRecordBinding
import com.example.offo.repository.RecordRepository
import com.example.offo.repository.UserRepository

class recyclerRecordFragment : Fragment() {
    private var _binding: FragmentRecyclerRecordBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecyclerRecordBinding.inflate(layoutInflater, container, false)

        val view = binding.root

        RecordRepository().updateData {
            val navController = findNavController()

            val recordsHomeRecycler = binding.recordsHomeRecycler
            recordsHomeRecycler.adapter = RecordAdapter(
                navController,
                HomeActivity(),
                RecordRepository.Singleton.recordList,
                R.layout.items_record_recycled_home
            )
        }
        return view
    }
}