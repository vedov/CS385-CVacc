package com.example.cvacc

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.cvacc.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    lateinit var mAuth: FirebaseAuth;
    lateinit var firestore: FirebaseFirestore
    lateinit var userId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater)

        mAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        userId = mAuth.currentUser.uid
        val logoutBtn = binding.profileLogoutBtn

        val docRef = firestore.collection("users").document(userId)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val name: TextView = binding.profileName
                    val mail: TextView = binding.profileEmail
                    val age: TextView = binding.profileAge
                    name.text = document.getString("name")
                    mail.text = document.getString("email")
                    age.text = document.getString("age")
                }
            }

        logoutBtn.setOnClickListener {
            mAuth.signOut()
            Toast.makeText(context, getString(R.string.logout_toast), Toast.LENGTH_SHORT)
                .show()
            val intent = Intent(context, WelcomeActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }
}