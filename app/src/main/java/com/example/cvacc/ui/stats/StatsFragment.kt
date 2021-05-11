package com.example.cvacc.ui.stats

import android.annotation.SuppressLint
import android.graphics.Color.RED
import android.graphics.Color.parseColor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cvacc.R
import com.example.cvacc.databinding.FragmentStatsBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule


class StatsFragment : Fragment() {

    lateinit var binding: FragmentStatsBinding
    lateinit var mAuth: FirebaseAuth;
    lateinit var firestore: FirebaseFirestore
    lateinit var userId: String
    var registeredUsers = 0
    var pfizerCounter = 0
    var modernaCounter = 0
    var sputnikCounter = 0
    var astrazeCounter = 0
    val vaccines: ArrayList<PieEntry> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStatsBinding.inflate(inflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        mAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        userId = mAuth.currentUser.uid


        val docRef = firestore.collection("users")
        Timer("Getting Data,false").schedule(750){
            getChartData(docRef)
        }




    }
    fun getChartData(docRef: CollectionReference){
        val chart: PieChart = binding.chart
        val statsTitle = binding.statsTitle
        val pfizerQuery = docRef.whereEqualTo("vaccine", "Pfizer")
        val modernaQuery = docRef.whereEqualTo("vaccine", "Moderna")
        val sputnikQuery = docRef.whereEqualTo("vaccine", "Sputnik IV")
        val astrazeQuery = docRef.whereEqualTo("vaccine", "Astra Zeneca")
        docRef.whereEqualTo("appointment-scheduled","Yes").get().addOnSuccessListener { documents ->
            for(document in documents){
                registeredUsers++
            }
        }

        pfizerQuery.get().addOnSuccessListener { documents ->
            for (document in documents) {
                pfizerCounter++
            }
        }

        modernaQuery.get().addOnSuccessListener { documents ->
            for (document in documents) {
                modernaCounter++
            }
        }

        sputnikQuery.get().addOnSuccessListener { documents ->
            for (document in documents) {
                sputnikCounter++
            }
        }

        astrazeQuery.get().addOnSuccessListener { documents ->
            for (document in documents) {
                astrazeCounter++
            }
        }
        docRef.get().addOnCompleteListener {
            if(it.isSuccessful){
                statsTitle.text = "Registered Users: $registeredUsers"
                vaccines.add(PieEntry((pfizerCounter.toFloat()/registeredUsers)*100, "Pfizer"))
                vaccines.add(PieEntry((modernaCounter.toFloat()/registeredUsers)*100, "Moderna"))
                vaccines.add(PieEntry((astrazeCounter.toFloat()/registeredUsers)*100, "Astra Zeneca"))
                vaccines.add(PieEntry((sputnikCounter.toFloat()/registeredUsers)*100, "Sputnik IV"))
                val set = PieDataSet(vaccines,"Vaccines")
                set.setColors(
                    intArrayOf(R.color.red_200, R.color.blue_900, R.color.gray_900, R.color.green_600),
                    context)
                val data = PieData(set)
                chart.description.isEnabled = false
                chart.setData(data)
                chart.notifyDataSetChanged()
                chart.invalidate()
            }




        }
    }
}