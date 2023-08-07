package com.example.fypfsy.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.fypfsy.R
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fypfsy.Adapter.QuizAdapter
import com.example.fypfsy.models.Quiz
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.appBar


class MainActivity : AppCompatActivity() {

    private val videoList = listOf(
        VideoItem("7-7-23", "https://www.youtube.com/shorts/KtDvk5Jwais.mp4"),
        VideoItem("8-7-23", "https://www.youtube.com/shorts/UEFTPOIZurU.mp4"),
        VideoItem("9-7-23", "https://www.youtube.com/shorts/ME68kmrrVuM.mp4"),
        VideoItem("10-7-23", "https://www.youtube.com/shorts/rWgMpnPTScU.mp4"),
        VideoItem("11-7-23", "https://www.youtube.com/shorts/YuODnfP2N8M.mp4"),
        // Add more videos here
    )

    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var adapter: QuizAdapter
    private var quizList = mutableListOf<Quiz>()
    private lateinit var quizRecyclerView: QuizRecylerview
    lateinit var firestore: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpViews()


    }

    fun setUpViews() {
        setupFirestore()
        setUpDrawerLayout()
        setupRecyclerview()
        setUpDatePicker()
        setUpVideoItem()
    }


    private fun setUpDatePicker() {
        btnDatePicker.setOnClickListener {
            val datePicker: MaterialDatePicker<Long> =
                MaterialDatePicker.Builder.datePicker().build()
            datePicker.show(supportFragmentManager, "DatePicker")
            datePicker.addOnPositiveButtonClickListener {
                Log.d("DATEPICKER", datePicker.headerText)
                val dateFormatter = SimpleDateFormat("dd-mm-yyyy")
                val date = dateFormatter.format(Date(it))
                val intent = Intent(this, QuestionActivity::class.java)
                intent.putExtra("DATE", date)
                startActivity(intent)
            }
            datePicker.addOnNegativeButtonClickListener {
                Log.d("DATEPICKER", datePicker.headerText)
            }
            datePicker.addOnCancelListener {
                Log.d("DATE-PICKER", "Date Picker cancelled")
            }
        }
    }

    private fun setUpVideoItem() {
        val recyclerView: RecyclerView = findViewById(R.id.QuizRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = VideoAdapter(videoList)
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener { videoItem ->
            val intent = Intent(this, VideoViews::class.java)
            intent.putExtra("videoTitle", videoItem.title)
            startActivity(intent)
        }
    }


    private fun setupFirestore() {
        firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection("Quizes")
        collectionReference.addSnapshotListener { value, error ->
            if (value == null || error != null) {
                Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }

            Log.d("DATA", value.toObjects(Quiz::class.java).toString())
            quizList.clear()
            quizList.addAll(value.toObjects(Quiz::class.java))
            adapter.notifyDataSetChanged()
        }
    }

    private fun setupRecyclerview() {
        adapter = QuizAdapter(this, quizList)
        quizRecyclerView.layoutManager = GridLayoutManager(this, 2)
        quizRecyclerView.adapter = adapter

    }

    fun setUpDrawerLayout() {
        setSupportActionBar(appBar)
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, mainDrawer, R.string.app_name, R.string.app_name)
        actionBarDrawerToggle.syncState()
        navigationView.setNavigationItemSelectedListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            mainDrawer.closeDrawers()
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    class QuizRecylerview(var layoutManager: GridLayoutManager, var adapter: QuizAdapter) {
        lateinit var QuizAdapter: QuizAdapter
        lateinit var LauoutManager: GridLayoutManager

    }
}





