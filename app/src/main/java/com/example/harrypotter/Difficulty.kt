package com.example.harrypotter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_difficulty.*

class Difficulty : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_difficulty)
        btn_easy.setOnClickListener{


            intent = Intent(applicationContext, EasySingle::class.java)
            startActivity(intent)
        }
        btn_middle.setOnClickListener{


            intent = Intent(applicationContext, MediumSingle::class.java)
            startActivity(intent)
        }
        btn_Hard.setOnClickListener{


            intent = Intent(applicationContext, HardSingle::class.java)
            startActivity(intent)
        }
    }
}