package com.example.cvacc

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cvacc.databinding.FragmentEligibleBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class EligibleFragment : Fragment() {
    private lateinit var binding: FragmentEligibleBinding
    lateinit var mAuth: FirebaseAuth;
    lateinit var firestore: FirebaseFirestore
    lateinit var userId: String
    var vaccineName: String = ""
    var priorityFlag = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEligibleBinding.inflate(inflater)
        binding.cVaccEligible = this
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun getShareIntent() : Intent {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
            .putExtra(Intent.EXTRA_TEXT, "OPAA")
        return shareIntent
    }

    private fun shareSuccess() {
        startActivity(getShareIntent())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main, menu)
        if(getShareIntent().resolveActivity(requireActivity().packageManager)==null){
            menu.findItem(R.id.share).isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.share -> shareSuccess()
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        userId = mAuth.currentUser.uid

        val args = EligibleFragmentArgs.fromBundle(requireArguments())
        vaccineName=args.vaccineName
        priorityFlag=args.priorityFlag

        val docRef = firestore.collection("users").document(userId)
        docRef.update("vaccine", vaccineName)

        val date = Date()
        val format = SimpleDateFormat("dd/M/yyyy")
        val calendar = Calendar.getInstance()
        calendar.time = format.parse(format.format(date))
        if(priorityFlag==true){
            calendar.add(Calendar.DATE, 7)
        }
        else {
            calendar.add(Calendar.DATE, 14)
        }

        docRef.update("appointment-date",format.format(calendar.time))
        docRef.update("appointment-scheduled", "Yes")

        val finishBtn = binding.apptSuccessFinishBtn

        finishBtn.setOnClickListener {
            val action = EligibleFragmentDirections.actionEligibleFragmentToNavHome()
            findNavController().navigate(action)
        }
    }
}