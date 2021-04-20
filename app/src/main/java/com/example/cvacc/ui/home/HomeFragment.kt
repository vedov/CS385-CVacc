package com.example.cvacc.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.cvacc.R
import com.example.cvacc.databinding.FragmentHomeBinding
import com.example.cvacc.databinding.FragmentRegisterBinding

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //homeViewModel =
           // ViewModelProvider(this).get(HomeViewModel::class.java)

        //val root = inflater.inflate(R.layout.fragment_home, container, false)
        //val textView: TextView = root.findViewById(R.id.text_home)
        //homeViewModel.text.observe(viewLifecycleOwner, Observer {
        //    textView.text = it
        //})

        binding = FragmentHomeBinding.inflate(inflater)

        val btn = binding.registerbtn
        btn.setOnClickListener {
            val action = HomeFragmentDirections.actionNavHomeToQuestionFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }
}