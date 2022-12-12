package com.example.harrypotter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView

class MediumMulti : AppCompatActivity() {
    lateinit var textView : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medium_multi)
        textView = findViewById(R.id.Time1)
        // time count down for 30 seconds,
        // with 1 second as countDown interval
        object : CountDownTimer(60000, 1000) {

            // Callback function, fired on regular interval
            override fun onTick(millisUntilFinished: Long) {
                textView.setText("seconds remaining: " + millisUntilFinished / 1000)
            }

            // Callback function, fired
            // when the time is up
            override fun onFinish() {
                textView.setText("done!")
            }
        }.start()
    }
}