package com.example.cvacc.ui.appointment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cvacc.MainActivity
import com.example.cvacc.NotEligibleFragmentArgs
import com.example.cvacc.R
import com.example.cvacc.databinding.FragmentAppointmentBinding
import com.example.cvacc.databinding.FragmentNotEligibleBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AppointmentFragment : Fragment() {

    private lateinit var appointmentViewModel: AppointmentViewModel
    lateinit var binding: FragmentAppointmentBinding
    lateinit var mAuth: FirebaseAuth;
    lateinit var firestore: FirebaseFirestore
    lateinit var userId: String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAppointmentBinding.inflate(inflater)
        return binding.root
    }

        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)

            mAuth = FirebaseAuth.getInstance()

            firestore = FirebaseFirestore.getInstance()
            userId = mAuth.currentUser.uid
            val docRef = firestore.collection("users").document(userId)
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        if(document.getString("appointment-scheduled").equals("No")){
                            binding.textGallery.text = "Ne moze"
                        }
                        else {
                            binding.textGallery.text = document.getString("name")
                        }
                    } else {
                        Log.d("TAG", "No such document")
                    }
                }


        }


    }
