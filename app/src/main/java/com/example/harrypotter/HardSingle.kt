package com.example.harrypotter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView

class HardSingle : AppCompatActivity() {
    lateinit var textView : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hard_single)
        textView = findViewById(R.id.Time1)

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