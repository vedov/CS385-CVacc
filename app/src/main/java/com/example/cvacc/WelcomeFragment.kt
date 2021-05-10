package com.example.cvacc

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.cvacc.databinding.FragmentWelcomeBinding
import com.google.firebase.auth.FirebaseAuth

class WelcomeFragment : Fragment() {
    lateinit var mAuth: FirebaseAuth;

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentWelcomeBinding>(
            inflater,
            R.layout.fragment_welcome, container, false
        )
        activity?.window?.statusBarColor = resources.getColor(R.color.blue_500)

        val loginBtn = binding.welcomeLoginBtn
        val registerBtn = binding.welcomeRegisterBtn
        val email = binding.welcomeEmail
        val password = binding.welcomePw

        mAuth = FirebaseAuth.getInstance()
        loginBtn.setOnClickListener { view: View ->
            if (email.text.toString().isEmpty()) {
                email.setError("Please enter your email.")
                return@setOnClickListener
            } else if (password.text.toString().isEmpty()) {
                password.setError("Please enter your password.")
                return@setOnClickListener
            }
            mAuth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        activity?.supportFragmentManager?.popBackStack()
                        val intent = Intent(context, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            context,
                            "Login failed,please try again!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

        }
        registerBtn.setOnClickListener { view: View ->
            val action = WelcomeFragmentDirections.actionWelcomeFragmentToRegisterFragment2()
            view.findNavController().navigate(action)
        }
        return binding.root
    }

}