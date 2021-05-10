package com.example.cvacc.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cvacc.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater)

        val scheduleBtn = binding.homeScheduleBtn
        scheduleBtn.setOnClickListener {
            val action = HomeFragmentDirections.actionNavHomeToQuestionFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }
}