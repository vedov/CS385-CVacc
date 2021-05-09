package com.example.cvacc

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.cvacc.databinding.FragmentNotEligibleBinding
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class NotEligibleFragment : Fragment() {
    private lateinit var binding: FragmentNotEligibleBinding
    lateinit var mAuth: FirebaseAuth;
    lateinit var firestore: FirebaseFirestore
    lateinit var userId: String

    var odgovori: MutableList<String> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNotEligibleBinding.inflate(inflater)
        binding.cVaccNotEligible = this
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
                    odgovori.add(document.data.toString())
                    val odg : TextView = binding.textView9
                    odg.text = document.getString("name")
                    Log.d("Allah", "DocumentSnapshot data: ${document.data}")
                } else {
                    Log.d("TAG", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }
        val args = NotEligibleFragmentArgs.fromBundle(requireArguments())
        val activity: MainActivity = activity as MainActivity
        odgovori.addAll(args.sazetak)
        odgovori.add(activity.sendData().toString())
    }

   // private fun checkAnswers(MutableList<String>)

}