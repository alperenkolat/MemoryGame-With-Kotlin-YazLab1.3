package com.example.harrypotter


import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Base64
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_easy_single.*
import java.io.FileOutputStream
import java.util.*

class EasySingle : AppCompatActivity() {
    lateinit var textView : TextView
    lateinit var score:TextView
    private lateinit var buttons: List<ImageView>
    private lateinit var harry: List<Mem>
    private var indexOfSingleSelectedCard: Int? = null
    var randomCards = ArrayList<Int>()
    var homeList = ArrayList<Int>()
    val db = Firebase.firestore
    var mediaPlayer :MediaPlayer?=null
    var mediaPlayer1 :MediaPlayer?=null
    var cardScore = ArrayList(Arrays.asList(0,0,0,0)) //Eklendi
    var userScore = 0
    var secondUntilFinished = 0 // Eklendi
    var matchCount=2
    private lateinit var timer: CountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_easy_single)
        textView = findViewById(R.id.Time1)
        score = findViewById(R.id.Score1)
        timer =object : CountDownTimer(45000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                textView.setText("süre: " + millisUntilFinished / 1000)
                secondUntilFinished = (millisUntilFinished / 1000).toInt() // Eklendi
            }
            override fun onFinish() {
                textView.setText("oyun bitti!")
                if(matchCount!=0){
                intent = Intent(applicationContext, Result::class.java)
                intent.putExtra("score",userScore.toString())
                startActivity(intent)}
            }
        }.start()
        val card_matrix = ArrayList<ArrayList<Int>>()
        var row1 = ArrayList(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11))
        var row2 = ArrayList(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11))
        var row3 = ArrayList(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11))
        var row4 = ArrayList(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11))

        card_matrix.add(row1)
        card_matrix.add(row2)
        card_matrix.add(row3)
        card_matrix.add(row4)

        var card_home = ArrayList<ArrayList<Int>>()
        var temp = 0
        var random_number = 0
        for (i in 0..1)
        {
            homeList.add((1..4).random())
            temp = (1..card_matrix[homeList[i]-1].size-1).random()

            randomCards.add(card_matrix[homeList[i]-1][temp])

            card_matrix[homeList[i].toInt()-1].removeAt(temp)
        }
        randomCards.addAll(randomCards)
        homeList.addAll(homeList)
        card_home.add(randomCards)
        card_home.add(homeList)
        buttons= listOf(imageView2,imageView3,imageView4,imageView5)



        println(randomCards)
        println(homeList)
        println(card_home)

        playsound()

        for (i in 0..1)
        {
            random_number = (0..1).random()

            temp = card_home[0][i]
            card_home[0][i] = card_home[0][random_number]
            card_home[0][random_number] = temp

            temp = card_home[1][i]
            card_home[1][i] = card_home[1][random_number]
            card_home[1][random_number] = temp

        }
        println(card_home)

        harry = buttons.indices.map { index ->
            var temp3= mutableListOf<Int>(card_home[0][index],card_home[1][index])
            Mem(temp3)
        }

        switch4.setOnCheckedChangeListener { compoundButton, b ->
            if (b)//hatalı
                playsound()
            else
                stop()
        }

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {

                updateModels(index)
                updateViews()

            }
        }

        val file:String = "data.txt"
        val fileOutputStream: FileOutputStream
        fileOutputStream = openFileOutput(file, Context.MODE_PRIVATE)
        fileOutputStream.write(card_home.toString().toByteArray())

    }
    private fun updateViews() {
        harry.forEachIndexed { index, card ->
            val button = buttons[index]
            if (card.isMatched) {
                button.alpha = 0.1f
            }
            if (card.isFaceUp) {

                db.collection(homeList[index].toString())// (1..4).random().toString()
                    .whereEqualTo("cardID", randomCards[index])
                    .get()
                    .addOnSuccessListener { result ->
                        for (document in result) {
                            val imageBytes = Base64.decode(document.get("cardImage") as String, Base64.DEFAULT)
                            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                            button.setImageBitmap(decodedImage)

                            if(cardScore[0] == 0)
                            {
                                cardScore[0] = (document.get("cardScore").toString()).toInt()
                                cardScore[1] = homeList[index]
                            }
                            else
                            {
                                cardScore[2] =  (document.get("cardScore").toString()).toInt()
                                cardScore[3] = homeList[index]
                            }
                        }
                    }.addOnFailureListener{
                        println("başarısız")
                    }

                if ( homeList[index]==4&&randomCards[index]==10)playSound(R.raw.nirvana)
            }
            else {
                button.setImageResource(R.drawable.cardback)
            }

        }
    }
    private fun updateModels(position: Int) {
        val card = harry[position]
        // Error checking:
        if (card.isFaceUp) {
            Toast.makeText(this, "HATALI EŞLEŞME!", Toast.LENGTH_SHORT).show()
            return
        }
        // Three cases
        // 0 harry previously flipped over => restore harry + flip over the selected card
        // 1 card previously flipped over => flip over the selected card + check if the images match
        // 2 harry previously flipped over => restore harry + flip over the selected card
        if (indexOfSingleSelectedCard == null) {
            // 0 or 2 selected harry previously
            restoreCards()

            indexOfSingleSelectedCard = position
        } else {
            // exactly 1 card was selected previously
            checkForMatch(indexOfSingleSelectedCard!!, position)
            indexOfSingleSelectedCard = null
        }
        card.isFaceUp = !card.isFaceUp
    }
    private fun restoreCards() {
        for (card in harry) {
            if (!card.isMatched) {
                card.isFaceUp = false
            }
        }
    }

    private fun checkForMatch(position1: Int, position2: Int) {
        var homeScore = 0
        //while(cardScore[2] != 0)


        if(cardScore[1] == 1 || cardScore[1] == 2 )
        { homeScore = 2 }
        else
        { homeScore = 1 }

        if (harry[position1].identifier == harry[position2].identifier) {
            matchCount=matchCount-1
            Toast.makeText(this, "EŞLEŞME!!", Toast.LENGTH_SHORT).show()
            harry[position1].isMatched = true
            harry[position2].isMatched = true
            playSound(R.raw.happy)

            if(secondUntilFinished <10) secondUntilFinished = 10

            userScore += (2*cardScore[0]*homeScore) * (secondUntilFinished/10)
            println("Score" + userScore)
            score.setText("Score:"+userScore)

            if (matchCount==0)
            {
                mediaPlayer1?.stop()
                mediaPlayer1?.reset()


                intent = Intent(applicationContext, Result::class.java)
                intent.putExtra("score",userScore.toString())
                intent.putExtra("win","1")
                startActivity(intent)
            }

        }else
        {
            playSound(R.raw.shocked_sound_effect)

            if(cardScore[1] == cardScore[3] )
            {
                userScore -= (cardScore[0] + cardScore[2]/homeScore) * ((60 - secondUntilFinished)/10)
            }
            else
            {
                if(cardScore[1] == 1 || cardScore[1] == 2 )
                { homeScore = 2 }
                else
                { homeScore = 1 }

                if(cardScore[3] == 1 || cardScore[3] == 2 )
                { homeScore *= 2 }
                else
                { homeScore *= 1 }

                userScore -= (((cardScore[0] + cardScore[2])/2) * homeScore) *  ((60 - secondUntilFinished)/10)
            }
            score.setText("Score:"+userScore)
            println("Score" + userScore)

        }

        cardScore[0] = 0 //Sıfırladık
    }
    fun playSound( soundType: Int){
        if (mediaPlayer1 == null){
        mediaPlayer1= MediaPlayer.create(this,soundType)
        mediaPlayer1!!.isLooping=false
        mediaPlayer1!!.start()
        }

    }
    fun playsound(){
        if (mediaPlayer == null){
            mediaPlayer=MediaPlayer.create(this,R.raw.prologue)
            mediaPlayer!!.isLooping=true
            mediaPlayer!!.start()


        }else{
            mediaPlayer!!.start()
        }
    }
    fun stop(){
        if(mediaPlayer?.isPlaying==true) {

            mediaPlayer1!!.stop()
            mediaPlayer1!!.reset()
            mediaPlayer!!.stop()
            mediaPlayer!!.reset()

        }

    }
    public override fun onStop() {
        super.onStop()
        timer.cancel()
        mediaPlayer1!!.stop()
        mediaPlayer1!!.reset()
        mediaPlayer!!.stop()
        mediaPlayer!!.reset()

    }
}