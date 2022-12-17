package com.example.harrypotter

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Base64
import android.util.Base64.DEFAULT
import android.util.Base64.decode
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_easy_single.*


class EasySingle : AppCompatActivity() {
    lateinit var textView : TextView


    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_easy_single)



        object : CountDownTimer(45000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                textView.setText("süre: " + millisUntilFinished / 1000)
            }
            override fun onFinish() {
                textView.setText("oyun bitti!")
            }
        }.start()
    }


}








