package com.example.cvacc

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
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

    @SuppressLint("ResourceAsColor")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val args = NotEligibleFragmentArgs.fromBundle(requireArguments())
        val activity: MainActivity = activity as MainActivity
        answers.addAll(args.sazetak)
        //answers.add(activity.sendData().toString())

        mAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        userId = mAuth.currentUser.uid
        val docRef = firestore.collection("users").document(userId)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val questionLayout: LinearLayout = binding.apptFailLinearlayout
                    var count = 0
                    for(i in answers){
                        //ADD A TEXTVIEW FOR EACH QUESTION AND ANSWER

                        val questionAndAnswers = TextView(context)
                        questionAndAnswers.text = i
                        questionAndAnswers.layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        if(count%2 == 0){
                            questionAndAnswers.setTextColor(Color.parseColor("#085087"))
                            questionAndAnswers.typeface = Typeface.DEFAULT_BOLD
                        }
                        questionLayout.addView(questionAndAnswers)
                        count++
                    }
                }
            }
        docRef.update("appointment-scheduled", "No")

        val backBtn = binding.apptFailBackBtn
        backBtn.setOnClickListener {
            val action = NotEligibleFragmentDirections.actionNotEligibleFragmentToNavHome()
            findNavController().navigate(action)
        }

    }

}