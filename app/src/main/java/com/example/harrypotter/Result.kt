package com.example.harrypotter

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_difficulty.*

import kotlinx.android.synthetic.main.activity_result.*
import java.util.*

class Result : AppCompatActivity() {
    lateinit var textView : TextView
    var mediaPlayer : MediaPlayer?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)


        textView = findViewById(R.id.textScore)
        var score=  intent.getStringExtra("score")
        if (intent.getStringExtra("win")!=null){



            mediaPlayer=MediaPlayer.create(this,R.raw.congratulations)
            mediaPlayer!!.isLooping=true
            mediaPlayer!!.start()

        }

        textView.setText("skorunuz:"+score)
        button1.setOnClickListener{


            intent = Intent(applicationContext, Home::class.java)
            startActivity(intent)
        }
    }
    public override fun onStop() {
        super.onStop()

        mediaPlayer!!.stop()
        mediaPlayer!!.reset()

    }

}