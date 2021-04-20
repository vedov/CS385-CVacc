package com.example.cvacc

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController


import com.example.cvacc.databinding.FragmentWelcomeBinding
import com.example.cvacc.WelcomeFragmentDirections

//GOTOV
class WelcomeFragment : Fragment() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentWelcomeBinding>(inflater,
            R.layout.fragment_welcome, container, false)
        activity?.window?.statusBarColor = resources.getColor(R.color.blue_500)
        var login = binding.btnlogin
        var register = binding.btnregister

        login.setOnClickListener { view : View ->
            val action = WelcomeFragmentDirections.actionWelcomeFragmentToLoginFragment()
            view.findNavController().navigate(action)
        }
        register.setOnClickListener { view : View ->
            val action = WelcomeFragmentDirections.actionWelcomeFragmentToRegisterFragment2()
            view.findNavController().navigate(action)
        }
        return binding.root
    }

}