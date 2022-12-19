package com.example.harrypotter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_difficulty.*

import kotlinx.android.synthetic.main.activity_result.*
import java.util.*

class Result : AppCompatActivity() {
    lateinit var textView : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)


        textView = findViewById(R.id.textScore)
        var score=  intent.getStringExtra("score")

        textView.setText("skorunuz:"+score)
        button1.setOnClickListener{


            intent = Intent(applicationContext, Home::class.java)
            startActivity(intent)
        }
    }

}