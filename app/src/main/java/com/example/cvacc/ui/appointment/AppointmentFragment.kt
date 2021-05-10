package com.example.cvacc.ui.appointment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cvacc.databinding.FragmentAppointmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AppointmentFragment : Fragment() {

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
                    if (document.getString("appointment-scheduled").equals("No")) {
                        binding.noteligibleLayout.visibility = View.VISIBLE
                        binding.noteligibletext.text = "Ne moze"
                    } else {
                        binding.eligibleLayout.visibility = View.VISIBLE
                        binding.apptName.text = document.getString("name")
                        binding.apptDate.text = document.getString("appointment-date")
                        binding.apptVaccine.text = document.getString("vaccine")
                        binding.apptLocation.text = "ZETRA"
                    }
                }
            }
    }

}
