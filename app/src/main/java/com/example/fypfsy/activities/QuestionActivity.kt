package com.example.fypfsy.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fypfsy.Adapter.OptionAdapter
import com.example.fypfsy.R
import com.example.fypfsy.models.Questions
import com.example.fypfsy.models.Quiz
import android.content.Intent
import android.util.Log
import android.view.View
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_question.btnNext
import kotlinx.android.synthetic.main.activity_question.btnPrevious
import kotlinx.android.synthetic.main.activity_question.btnSubmit
import kotlinx.android.synthetic.main.activity_question.description
import kotlinx.android.synthetic.main.activity_question.optionList


class QuestionActivity : AppCompatActivity() {
    var Quizes: MutableList<Quiz>? = null
    var Questions: MutableMap<String, Questions>? = null
    var index = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        setUpFirestore()
        setUpEventListener()
    }

     private fun setUpEventListener() {
         btnPrevious.setOnClickListener {
             index--
             bindViews()
         }

         btnNext.setOnClickListener {
             index++
             bindViews()
         }
         btnSubmit.setOnClickListener {
             Log.d("FINALQUIZ", Questions.toString())

             val intent = Intent(this, ResultActivity::class.java)
             val json = Gson().toJson(Quizes!![0])
             intent.putExtra("QUIZ", json)
             startActivity(intent)
         }
     }


    private fun setUpFirestore() {
        val firestore = FirebaseFirestore.getInstance()
        var date = intent.getStringExtra("DATE")
        if (date != null) {
            firestore.collection("Quizes").whereEqualTo("Title", date)
                .get()
                .addOnSuccessListener {
                    if (it != null && !it.isEmpty) {
                        Log.d("DATA", it.toObjects(Quiz::class.java).toString())
                        Quizes = it.toObjects(Quiz::class.java)
                        Questions = Quizes!![0].questions
                        bindViews()
                    }

                }
        }
    }


    private fun bindViews() {
        btnPrevious.visibility = View.GONE
        btnSubmit.visibility = View.GONE
        btnNext.visibility = View.GONE

        if (index == 1) { //first question
            btnNext.visibility = View.VISIBLE
        } else if (index == Questions!!.size) { // last question
            btnSubmit.visibility = View.VISIBLE
            btnPrevious.visibility = View.VISIBLE
        } else { // Middle
            btnPrevious.visibility = View.VISIBLE
            btnNext.visibility = View.VISIBLE
        }
        val Questions = Questions!!["Questions$index"]
        Questions?.let {
            description.text = it.description
            val optionAdapter = OptionAdapter(this, it)
            optionList.layoutManager = LinearLayoutManager(this)
            optionList.adapter = optionAdapter
            optionList.setHasFixedSize(true)
        }
    }
}
