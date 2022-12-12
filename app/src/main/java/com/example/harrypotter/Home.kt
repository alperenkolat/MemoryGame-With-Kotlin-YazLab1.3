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
    lateinit var textView : TextView
    var mediaPlayer :MediaPlayer?=null
    lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_home)
        textView = findViewById(R.id.Time1)



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


      //  mediaPlayer.start()
        // time count down for 30 seconds,
        // with 1 second as countDown interval
        object : CountDownTimer(45000, 1000) {

            // Callback function, fired on regular interval
            override fun onTick(millisUntilFinished: Long) {
                textView.setText("süre/time " + millisUntilFinished / 1000)
            }

            // Callback function, fired
            // when the time is up
            override fun onFinish() {
                textView.setText("oyun bitti!")
            }
        }.start()
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