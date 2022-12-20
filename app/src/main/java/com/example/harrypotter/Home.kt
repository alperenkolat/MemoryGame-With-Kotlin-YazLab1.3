package com.example.harrypotter

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView
import com.example.harrypotter.databinding.ActivityHomeBinding
import com.example.harrypotter.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() {
    var mediaPlayer :MediaPlayer?=null
    lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_home)




        btn_single.setOnClickListener{


        intent = Intent(applicationContext, Difficulty::class.java)
        startActivity(intent)
            }
        btn_Multi.setOnClickListener{


            intent = Intent(applicationContext, DifficultyMulti::class.java)
            startActivity(intent)
        }

        switch1.setOnCheckedChangeListener { compoundButton, b ->
            if (b)//hatalı
                playsound()
            else
                stop()
        }
        button2.setOnClickListener() {
            intent = Intent(applicationContext, PasswordChange::class.java)
            startActivity(intent)
        }
    }
    fun playsound(){
        if (mediaPlayer == null){
            mediaPlayer=MediaPlayer.create(this,R.raw.nirvana)
            mediaPlayer!!.isLooping=true
            mediaPlayer!!.start()
        }else{
                mediaPlayer!!.start()}
    }
    fun stop(){
        if(mediaPlayer?.isPlaying==true) {

            mediaPlayer?.pause()
        }

    }


}