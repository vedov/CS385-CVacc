package com.example.cvacc.ui.stats

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
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule


class StatsFragment : Fragment() {

    lateinit var binding: FragmentStatsBinding
    lateinit var mAuth: FirebaseAuth;
    lateinit var firestore: FirebaseFirestore
    lateinit var userId: String
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val chart: PieChart = binding.chart
        mAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        userId = mAuth.currentUser.uid


        val docRef = firestore.collection("users")
        val pfizerQuery = docRef.whereEqualTo("vaccine", "Pfizer")
        val modernaQuery = docRef.whereEqualTo("vaccine", "Moderna")
        val sputnikQuery = docRef.whereEqualTo("vaccine", "Sputnik IV")
        val astrazeQuery = docRef.whereEqualTo("vaccine", "Astra Zeneca")

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

        Timer("Getting data...", false).schedule(1000) {
            vaccines.add(PieEntry(pfizerCounter.toFloat(), "Pfizer"))
            vaccines.add(PieEntry(modernaCounter.toFloat(), "Moderna"))
            vaccines.add(PieEntry(astrazeCounter.toFloat(), "Astra Zeneca"))
            vaccines.add(PieEntry(sputnikCounter.toFloat(), "Sputnik IV"))
            val set = PieDataSet(vaccines, "Vaccine Popularity")

            set.setColors(
                intArrayOf(R.color.blue_500, R.color.blue_900, R.color.gray_900, R.color.blue_400),
                context
            )
            val data = PieData(set)
            chart.setData(data)
            chart.invalidate()
        }
    }
}