package com.example.harrypotter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class EasySingle : AppCompatActivity() {
    lateinit var textView : TextView


    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_easy_single)


        database = Firebase.database.reference












        textView = findViewById(R.id.Time1)

        object : CountDownTimer(45000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                textView.setText("seconds remaining: " + millisUntilFinished / 1000)
            }


            override fun onFinish() {
                textView.setText("done!")
            }
        }.start()
    }
}








