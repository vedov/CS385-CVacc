package com.example.cvacc

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.cvacc.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.w3c.dom.Text


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    lateinit var mAuth: FirebaseAuth;
    lateinit var firestore: FirebaseFirestore
    lateinit var userId: String

    var odgovori: MutableList<String> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater)


        mAuth = FirebaseAuth.getInstance()

        firestore = FirebaseFirestore.getInstance()
        userId = mAuth.currentUser.uid
        val logout = binding.logout
        val docRef = firestore.collection("users").document(userId)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val name : TextView = binding.name
                    val mail: TextView = binding.email
                    val age: TextView = binding.age
                    name.text = document.getString("name")
                    mail.text = document.getString("email")
                    age.text = document.getString("dob")
                    Log.d("aaaaa",name.text.toString())

                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }
        //val args = SummaryFragmentArgs.fromBundle(requireArguments())
        //val activity: MainActivity = activity as MainActivity
        //odgovori.addAll(args.sazetak)
        //odgovori.addAll()
        //odgovori.add(activity.sendData().toString())

        logout.setOnClickListener {
            mAuth.signOut()
            Toast.makeText(context, "Logged Out", Toast.LENGTH_SHORT)
                .show()
            val intent = Intent(context, WelcomeActivity::class.java)
            startActivity(intent)
        }

        //binding.cVacc = this
        return binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



    }
}