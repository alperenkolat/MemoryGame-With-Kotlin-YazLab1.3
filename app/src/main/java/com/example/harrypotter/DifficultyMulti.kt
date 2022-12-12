package com.example.harrypotter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_difficulty.*
import kotlinx.android.synthetic.main.activity_home.*

class DifficultyMulti : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_difficulty_multi)
        btn_easy.setOnClickListener{


            intent = Intent(applicationContext, EasyMulti::class.java)
            startActivity(intent)
        }
        btn_middle.setOnClickListener{


            intent = Intent(applicationContext, MediumMulti::class.java)
            startActivity(intent)
        }
        btn_Hard.setOnClickListener{


            intent = Intent(applicationContext, HardMulti::class.java)
            startActivity(intent)
        }
    }
}