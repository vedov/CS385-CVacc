package com.example.cvacc

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController


import com.example.cvacc.databinding.FragmentWelcomeBinding
import com.example.cvacc.WelcomeFragmentDirections
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

//GOTOV
class WelcomeFragment : Fragment() {
    lateinit var mAuth: FirebaseAuth;

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
        var email = binding.loginemail
        var password = binding.loginpassword
        mAuth = FirebaseAuth.getInstance()
        login.setOnClickListener { view : View ->
            //val action = WelcomeFragmentDirections.actionWelcomeFragmentToLoginFragment()
            if(email.text.toString().isEmpty()){
                email.setError("Please enter your email.")
                return@setOnClickListener
            } else if(password.text.toString().isEmpty()){
                password.setError("Please enter your password.")
                return@setOnClickListener
            }
            mAuth.signInWithEmailAndPassword(email.text.toString(),password.text.toString()).addOnCompleteListener {
                if(it.isSuccessful){
                    activity?.supportFragmentManager?.popBackStack()

                    //create an Intent object
                    //create an Intent object
                    val intent = Intent(context, MainActivity::class.java)
                    //add data to the Intent object
                    //add data to the Intent object
                    //intent.putExtra("text", podaci.toString())
                    //start the second activity
                    //start the second activity
                    startActivity(intent)
                }
                else {
                    Toast.makeText(context,"Login failed,please try again!", Toast.LENGTH_SHORT).show()
                }
            }

        }
        register.setOnClickListener { view : View ->
            val action = WelcomeFragmentDirections.actionWelcomeFragmentToRegisterFragment2()
            view.findNavController().navigate(action)
        }
        return binding.root
    }

}