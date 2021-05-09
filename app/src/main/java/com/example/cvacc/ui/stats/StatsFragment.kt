package com.example.cvacc.ui.stats

import android.content.Context
import android.os.Bundle
import android.util.Log
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

    private lateinit var statsViewModel: StatsViewModel
    lateinit var binding: FragmentStatsBinding
    lateinit var mAuth: FirebaseAuth;
    lateinit var firestore: FirebaseFirestore
    lateinit var userId: String
    var pfizercounter = 0
    var modernacounter = 0
    var sputnikcounter = 0
    var astracounter = 0
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
        val hours: ArrayList<PieEntry> = ArrayList()

        val docRef = firestore.collection("users")
        val pfizerquery = docRef.whereEqualTo("vaccine", "Pfizer")
        val modernaquery = docRef.whereEqualTo("vaccine", "Moderna")
        val sputnikquery = docRef.whereEqualTo("vaccine", "Sputnik IV")
        val astraquery = docRef.whereEqualTo("vaccine", "Astra Zeneca")

        pfizerquery.get().addOnSuccessListener { documents ->
            for (document in documents) {
                //Log.d("ccc", "${document.id} => ${document.data}")
                    var count = 0
                count++
                pfizercounter += 1

            }

        }

        modernaquery.get().addOnSuccessListener { documents ->
            for (document in documents) {
                //Log.d("ccc", "${document.id} => ${document.data}")
                modernacounter += 1

            }
        }

        sputnikquery.get().addOnSuccessListener { documents ->
            for (document in documents) {
                //Log.d("ccc", "${document.id} => ${document.data}")
                sputnikcounter += 1

            }
        }
        astraquery.get().addOnSuccessListener { documents ->
            for (document in documents) {
                //Log.d("ccc", "${document.id} => ${document.data}")
                astracounter += 1

            }
        }

        Timer("Getting data...", false).schedule(1000){
            Log.d("ccc", pfizercounter.toString())
            hours.add(PieEntry(pfizercounter.toFloat(), "Pfizer"))
            hours.add(PieEntry(modernacounter.toFloat(), "Moderna"))
            hours.add(PieEntry(astracounter.toFloat(), "Astra Zeneca"))
            hours.add(PieEntry(sputnikcounter.toFloat(), "Sputnik IV"))
            val set = PieDataSet(hours, "Vaccine Popularity")

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