package com.example.cvacc

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil.decode
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.carlosmuvi.segmentedprogressbar.SegmentedProgressBar


import com.example.cvacc.Question
import com.example.cvacc.databinding.FragmentQuestionBinding
import java.lang.Byte.decode

class QuestionFragment : Fragment() {


    lateinit var binding: FragmentQuestionBinding
    lateinit var currentQuestion: Question
    var priority = false;
    var notEligibleQuestion: MutableList<Question> = mutableListOf()
    var questionIndex: Int = 0
    var ListaLosihOdgovora: MutableList<String> = mutableListOf()
    var ListaIzabranihOdgovora: MutableList<String> = mutableListOf()
    var questions = arrayListOf<Question>(
        Question("Are you a citizen of Bosnia & Herzegovina?","",false,false),
        Question("Do you have COVID-19?","",false,false),
        Question("Did you have COVID-19 in the last 6 months?",
            " individuals may wish to defer their own COVID-19 vaccination for up to 6 " +
                    "months from the time of SARS-CoV-2 infection. " +
                    "As more data becomes available on duration of immunity after infection, " +
                    "this time period may be adjusted.",
            false,false),

        Question("Do you have a history of severe allergic reaction to any component " +
                "of the vaccine?",
            "To view the components of the vaccine, click here.",
            false,false),

        Question("Are you pregnant or breastfeeding?",
            "Pregnant women are at higher risk of severe COVID-19 than non-pregnant women," +
                    " and COVID-19 has been associated with an increased risk of pre-term birth." +
                    "However due to insufficient data, WHO does not recommend the vaccination of " +
                    "pregnant women at this time.",
            false,false),

        Question("Do you have any of the listed medical conditions?",
            "This includes hypertension, diabetes, asthma, pulmonary, " +
                    "liver or kidney disease, as well as chronic infections that are stable and" +
                    " controlled.",
            false,false),

        Question("Are you working in a medical field?",
            "Such as nanananannanana",true,true),

        Question("Do you have any chronic ilnesses?",
            "Such as nananannana",true,true),

        Question("Do you work or reside in Elderly Care? ",
            "nanannana",true,true),

        Question("Choose vakcinu  ",
            "nanannana"),
        
        )
    var vakcina = ""
    //var counterNE: Int = 0

    private fun setQuestion() {
        currentQuestion = questions[questionIndex]
    }

    //Next Question
    private fun checkAnswer(answer: String) {
        questionIndex++
        showButtons()
        if (questionIndex < questions.size) {
            setQuestion()
            binding.invalidateAll()
        } else {
            getListaOdgovora()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentQuestionBinding.inflate(inflater)
        setQuestion()
        binding.cVaccQuestions = this
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var yes = binding.button2
        var no = binding.button3
        var pfizer = binding.button4
        var moderna = binding.button5
        var astraZe = binding.button6
        var sputnik = binding.button7


        var segmentedProgressBar: SegmentedProgressBar = binding.segmentedProgressbar
        segmentedProgressBar.setSegmentCount(questions.size);

        segmentedProgressBar.setContainerColor(Color.parseColor("#dbdfe2"));
        segmentedProgressBar.setFillColor(Color.parseColor("#0B72C1"));
        segmentedProgressBar.playSegment(500);
        segmentedProgressBar.setCompletedSegments(questionIndex);

        yes.setOnClickListener {
            ListaIzabranihOdgovora.add(yes.text.toString())
            //Provjeri da li je tu prepreka,ako jeste,preskoci sve i prikazi da ne ispunjava uslov za vakcinaciju
            if (currentQuestion.eligible == false) {
                //counterNE++
                notEligibleQuestion.add(currentQuestion)
                ListaLosihOdgovora.add(currentQuestion.theQuestion)
                ListaLosihOdgovora.add("Yes")
                val reason = notEligibleQuestion[0].theQuestion
                Log.d("aaa",notEligibleQuestion.toString())
                checkAnswer(yes.text.toString())
                segmentedProgressBar.incrementCompletedSegments();
                /*val action =
                    QuestionFragmentDirections.actionQuestionFragmentToNoAppointmentFragment(reason)
                findNavController().navigate(action)*/
                //questionIndex++
            }
            else{
                //POSTAVLJANJE PRIORITETA ZA KORISNIKA, DOSTUPNI DATUMI PRIJE ONIH BEZ PRIORITETA, ODP SEDMICA DVIJE
                if(currentQuestion.priority==true){
                    priority = true
                    Log.d("PRIORITY QUESTION",priority.toString())
                }
                checkAnswer(yes.text.toString())

                segmentedProgressBar.incrementCompletedSegments();
            }

        }

        no.setOnClickListener {
            ListaIzabranihOdgovora.add(no.text.toString())

                checkAnswer(no.text.toString())
                segmentedProgressBar.incrementCompletedSegments()


        }

        pfizer.setOnClickListener {
            vakcina = pfizer.text.toString()
            checkAnswer(pfizer.text.toString())

            segmentedProgressBar.incrementCompletedSegments()
        }
        moderna.setOnClickListener {
            vakcina = moderna.text.toString()
            checkAnswer(moderna.text.toString())

            segmentedProgressBar.incrementCompletedSegments()
        }
        astraZe.setOnClickListener {
            vakcina = astraZe.text.toString()
            checkAnswer(astraZe.text.toString())

            segmentedProgressBar.incrementCompletedSegments()
        }
        sputnik.setOnClickListener {
            vakcina = sputnik.text.toString()
            checkAnswer(sputnik.text.toString())

            segmentedProgressBar.incrementCompletedSegments()
        }


    }

    private fun showButtons() {
        if(questionIndex == questions.size - 1){
            Log.d("broj",questionIndex.toString())
            var pfizer = binding.button4
            var moderna = binding.button5
            var astraZe = binding.button6
            var sputnik = binding.button7
            var yes = binding.button2
            var no = binding.button3
            pfizer.visibility = View.VISIBLE
            moderna.visibility = View.VISIBLE
            astraZe.visibility = View.VISIBLE
            sputnik.visibility = View.VISIBLE
            yes.visibility = View.GONE
            no.visibility = View.GONE
        }
    }
    private fun getListaOdgovora() {

        val actionNE = QuestionFragmentDirections.actionQuestionFragmentToNotEligibleFragment(
            ListaLosihOdgovora.toTypedArray())
        val actionE = QuestionFragmentDirections.actionQuestionFragmentToEligibleFragment(vakcina)
        if (questionIndex >= questions.size - 1) {
            if(ListaLosihOdgovora.size>0){

                findNavController().navigate(actionNE)
            }
            else findNavController().navigate(actionE)
        }
    }

}

