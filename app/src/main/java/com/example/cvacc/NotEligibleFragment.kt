package com.example.cvacc

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cvacc.databinding.FragmentNotEligibleBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class NotEligibleFragment : Fragment() {
    private lateinit var binding: FragmentNotEligibleBinding
    lateinit var mAuth: FirebaseAuth;
    lateinit var firestore: FirebaseFirestore
    lateinit var userId: String
    var answers: MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

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
                    answers.add(document.data.toString())
                    val odg : TextView = binding.apptFailQuestions
                    odg.text = document.getString("name")
                    Log.d("alla", "DocumentSnapshot data: ${document.data}")
                } else {
                    Log.d("TAG", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }
        docRef.update("appointment-scheduled", "No")
        val args = NotEligibleFragmentArgs.fromBundle(requireArguments())
        val activity: MainActivity = activity as MainActivity
        answers.addAll(args.sazetak)
        answers.add(activity.sendData().toString())

        val back = binding.apptFailBackBtn

        back.setOnClickListener {
            val action = NotEligibleFragmentDirections.actionNotEligibleFragmentToNavHome()
            findNavController().navigate(action)
        }

    }

}