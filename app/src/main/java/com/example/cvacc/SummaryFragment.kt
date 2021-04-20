package com.example.cvacc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cvacc.databinding.FragmentSummaryBinding

class SummaryFragment : Fragment() {
    private lateinit var binding: FragmentSummaryBinding
    var odgovori: MutableList<String> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSummaryBinding.inflate(inflater)
        binding.cVaccSummary = this
        return binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val args = SummaryFragmentArgs.fromBundle(requireArguments())
        val activity: MainActivity = activity as MainActivity
        odgovori.addAll(args.sazetak)
        odgovori.add(activity.sendData().toString())
    }


}