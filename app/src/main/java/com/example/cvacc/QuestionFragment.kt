package com.example.cvacc

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.carlosmuvi.segmentedprogressbar.SegmentedProgressBar
import com.example.cvacc.databinding.FragmentQuestionBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class QuestionFragment : Fragment() {

    lateinit var binding: FragmentQuestionBinding
    lateinit var currentQuestion: Question
    var priorityFlag = false;
    var notEligibleQuestions: MutableList<Question> = mutableListOf()
    var questionIndex: Int = 0
    var vaccineName = ""
    lateinit var mAuth: FirebaseAuth;
    lateinit var firestore: FirebaseFirestore
    lateinit var userId: String
    private var badAnswersList: MutableList<String> = mutableListOf()
    private var choosenAnswersList: MutableList<String> = mutableListOf()
    private var questions = arrayListOf<Question>(
        Question("Are you a citizen of Bosnia & Herzegovina?", "", false, false),
        Question("Do you have COVID-19?", "", false, false),
        Question(
            "Did you have COVID-19 in the last 6 months?",
            " individuals may wish to defer their own COVID-19 vaccination for up to 6 " +
                    "months from the time of SARS-CoV-2 infection. " +
                    "As more data becomes available on duration of immunity after infection, " +
                    "this time period may be adjusted.",
            false, false
        ),

        Question(
            "Do you have a history of severe allergic reaction to any component " +
                    "of the vaccine?",
            "To view the components of the vaccine visit who.int/vaccines",
            false, false
        ),

        Question(
            "Are you pregnant or breastfeeding?",
            "Pregnant women are at higher risk of severe COVID-19 than non-pregnant women," +
                    " and COVID-19 has been associated with an increased risk of pre-term birth. " +
                    "However due to insufficient data, WHO does not recommend the vaccination of " +
                    "pregnant women at this time.",
            false, false
        ),

        Question(
            "Do you have any of the listed medical conditions?",
            "This includes hypertension, diabetes, asthma, pulmonary, " +
                    "liver or kidney disease, as well as chronic infections that are stable and" +
                    " controlled.",
            false, false
        ),

        Question(
            "Are you working in a medical field?",
            "", true, true
        ),

        Question(
            "Do you have any chronic ilnesses?",
            "Such as Cancer, Chronic Lung Diseases,"+
                    " Dementia or other neurological conditions, HIV, Heart Conditions, etc.", true, true
        ),

        Question(
            "Do you work or reside in Elderly Care? ",
            "", true, true
        ),

        Question(
            "Choose a vaccine you would like to be vaccinated with.",
        ),
    )

    private fun setQuestion() {
        currentQuestion = questions[questionIndex]
    }

    private fun checkAnswer(answer: String) {
        questionIndex++
        showButtons()
        if (questionIndex < questions.size) {
            setQuestion()
            binding.invalidateAll()
        } else {
            getAnswersList()
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

        val yesBtn = binding.questionYesBtn
        val noBtn = binding.questionNoBtn
        val pfizerBtn = binding.questionPfizerBtn
        val modernaBtn = binding.questionModernaBtn
        val astrazeBtn = binding.questionAstrazeBtn
        val sputnikBtn = binding.questionSputnikBtn

        val segmentedProgressBar: SegmentedProgressBar = binding.segmentedProgressbar
        segmentedProgressBar.setSegmentCount(questions.size);
        segmentedProgressBar.setContainerColor(Color.parseColor("#dbdfe2"));
        segmentedProgressBar.setFillColor(Color.parseColor("#0B72C1"));
        segmentedProgressBar.playSegment(500);
        segmentedProgressBar.setCompletedSegments(questionIndex);


        mAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        userId = mAuth.currentUser.uid

        val docRef = firestore.collection("users").document(userId)
        checkUserAge(docRef)

        yesBtn.setOnClickListener {
            choosenAnswersList.add(yesBtn.text.toString())
            //Provjeri da li je tu prepreka,ako jeste,preskoci sve i prikazi da ne ispunjava uslov za vakcinaciju
            if (currentQuestion.eligible == false) {
                if(questionIndex==0){
                    checkAnswer(yesBtn.text.toString())
                    segmentedProgressBar.incrementCompletedSegments();
                }
                else{
                    notEligibleQuestions.add(currentQuestion)
                    badAnswersList.add(currentQuestion.theQuestion)
                    badAnswersList.add("Yes")
                    notEligibleQuestions[0].theQuestion
                    checkAnswer(yesBtn.text.toString())
                    segmentedProgressBar.incrementCompletedSegments();
                }
            }
            else {
                //POSTAVLJANJE PRIORITETA ZA KORISNIKA, DOSTUPNI DATUMI PRIJE ONIH BEZ PRIORITETA, ODP SEDMICA DVIJE
                if (currentQuestion.priority == true) {
                    priorityFlag = true
                }
                checkAnswer(yesBtn.text.toString())
                segmentedProgressBar.incrementCompletedSegments();
            }
        }

        noBtn.setOnClickListener {
            choosenAnswersList.add(noBtn.text.toString())
            if (questionIndex==0) {
                notEligibleQuestions.add(currentQuestion)
                badAnswersList.add(currentQuestion.theQuestion)
                badAnswersList.add("No")
                notEligibleQuestions[0].theQuestion
                checkAnswer(noBtn.text.toString())
                segmentedProgressBar.incrementCompletedSegments();
            } else {
                checkAnswer(noBtn.text.toString())
                segmentedProgressBar.incrementCompletedSegments()
            }

        }

        pfizerBtn.setOnClickListener {
            vaccineName = pfizerBtn.text.toString()
            checkAnswer(pfizerBtn.text.toString())
            segmentedProgressBar.incrementCompletedSegments()
        }

        modernaBtn.setOnClickListener {
            vaccineName = modernaBtn.text.toString()
            checkAnswer(modernaBtn.text.toString())
            segmentedProgressBar.incrementCompletedSegments()
        }

        astrazeBtn.setOnClickListener {
            vaccineName = astrazeBtn.text.toString()
            checkAnswer(astrazeBtn.text.toString())
            segmentedProgressBar.incrementCompletedSegments()
        }
        sputnikBtn.setOnClickListener {
            vaccineName = sputnikBtn.text.toString()
            checkAnswer(sputnikBtn.text.toString())
            segmentedProgressBar.incrementCompletedSegments()
        }
    }

    private fun showButtons() {
        if (questionIndex == questions.size - 1) {
            var pfizer = binding.questionPfizerBtn
            var moderna = binding.questionModernaBtn
            var astraZe = binding.questionAstrazeBtn
            var sputnik = binding.questionSputnikBtn
            var yes = binding.questionYesBtn
            var no = binding.questionNoBtn
            pfizer.visibility = View.VISIBLE
            moderna.visibility = View.VISIBLE
            astraZe.visibility = View.VISIBLE
            sputnik.visibility = View.VISIBLE
            yes.visibility = View.GONE
            no.visibility = View.GONE
        }
    }

    private fun getAnswersList() {

        val actionNE = QuestionFragmentDirections.actionQuestionFragmentToNotEligibleFragment(
            badAnswersList.toTypedArray()
        )
        val actionE = QuestionFragmentDirections.actionQuestionFragmentToEligibleFragment(vaccineName,priorityFlag)
        if (questionIndex >= questions.size - 1) {
            if (badAnswersList.size > 0) {
                findNavController().navigate(actionNE)
            } else {
                findNavController().navigate(actionE)
            }
        }
    }

    private fun checkUserAge(docRef : DocumentReference){
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val userAge = document.getString("age")
                    if (userAge != null) {
                        if (userAge.toInt()<18) {
                            badAnswersList.add("You are under the age of 18")
                            badAnswersList.add("")
                        } else if(userAge.toInt()>=65){
                            priorityFlag = true
                        }
                        else {}
                    }
                }
            }
    }
}

