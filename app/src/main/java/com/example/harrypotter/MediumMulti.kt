package com.example.harrypotter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_hard_multi.*
import kotlinx.android.synthetic.main.activity_register2.view.*

class MediumMulti : AppCompatActivity() {
    lateinit var textView : TextView
    private lateinit var  imageViews: List<ImageView>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medium_multi)


        textView = findViewById(R.id.Time1)
        object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                textView.setText("süre: " + millisUntilFinished / 1000)
            }
            override fun onFinish() {
                textView.setText("oyun bitti!")
            }
        }.start()
    }
}