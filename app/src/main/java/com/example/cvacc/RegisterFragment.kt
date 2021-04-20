package com.example.cvacc

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.cvacc.R
import com.example.cvacc.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    var podaci: MutableList<String> = mutableListOf()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentRegisterBinding>(inflater,
            R.layout.fragment_register, container, false)

        val register = binding.register
        val name = binding.name.text
        val email = binding.email.text
        val phone = binding.phone.text
        val password = binding.password.text
        val confirm = binding.confirmpassword.text

        register.setOnClickListener {
            podaci.add(name.toString())
            podaci.add(email.toString())
            podaci.add(phone.toString())
            podaci.add(password.toString())
            podaci.add(confirm.toString())
            Log.d("a", podaci.toString())

            //create an Intent object
            //create an Intent object
            val intent = Intent(context, MainActivity::class.java)
            //add data to the Intent object
            //add data to the Intent object
            intent.putExtra("text", podaci.toString())
            //start the second activity
            //start the second activity
            startActivity(intent)

        }
        return binding.root
    }

}