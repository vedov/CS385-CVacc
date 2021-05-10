package com.example.cvacc

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.cvacc.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


class RegisterFragment : Fragment() {
    lateinit var mAuth: FirebaseAuth;
    lateinit var firestore: FirebaseFirestore
    lateinit var userId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentRegisterBinding>(
            inflater,
            R.layout.fragment_register, container, false
        )

        val registerBtn = binding.registerBtn
        val name = binding.registerName
        val email = binding.registerEmail
        val password = binding.registerPw
        val confirm = binding.registerConfirmPw
        val age = binding.registerAge

        mAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        registerBtn.setOnClickListener {
            if (email.text.toString().isEmpty()) {
                email.setError("Please enter your email.")
                return@setOnClickListener
            } else if (password.text.toString().isEmpty()) {
                password.setError("Please enter your password.")
                return@setOnClickListener
            } else if (name.text.toString().isEmpty()) {
                name.setError("Please enter your name.")
                return@setOnClickListener
            } else if (confirm.text.toString()
                    .isEmpty() || confirm.text.toString() != password.text.toString()
            ) {
                confirm.setError("Passwords don't match.")
                return@setOnClickListener
            } else if (age.text.toString().isEmpty()) {
                age.setError("Please enter your password.")
                return@setOnClickListener
            }

            mAuth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        userId = mAuth.currentUser.uid
                        val docRef: DocumentReference =
                            firestore.collection("users").document(userId)
                        val user = hashMapOf<String, Any?>()
                        user.put("name", name.text.toString())
                        user.put("email", email.text.toString())
                        user.put("age", age.text.toString())
                        docRef.set(user)

                        Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(context, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            context,
                            "Registration failed,please try again!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
        return binding.root
    }

}