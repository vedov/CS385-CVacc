package com.example.cvacc

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.cvacc.databinding.FragmentEligibleBinding
import com.example.cvacc.databinding.FragmentNotEligibleBinding
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Timestamp.now
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.security.Timestamp
import java.time.Instant.now
import java.time.LocalDate.now
import java.util.*

class EligibleFragment : Fragment() {
    private lateinit var binding: FragmentEligibleBinding
    lateinit var mAuth: FirebaseAuth;
    lateinit var firestore: FirebaseFirestore
    lateinit var userId: String
    var odg: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEligibleBinding.inflate(inflater)
        binding.cVaccEligible = this
        return binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mAuth = FirebaseAuth.getInstance()

        firestore = FirebaseFirestore.getInstance()
        userId = mAuth.currentUser.uid

        val args = EligibleFragmentArgs.fromBundle(requireArguments())
        val activity: MainActivity = activity as MainActivity
        odg=args.vaccine
        val docRef = firestore.collection("users").document(userId)
        docRef.update("vaccine",odg)
        val timestamp = FieldValue.serverTimestamp()
        docRef.update("appointment-date",timestamp)
        docRef.update("appointment-scheduled","Yes")
        Log.d("bbbbb", odg)
        //Log.d("bbbb",FieldValue.serverTimestamp().toString())
    }



}