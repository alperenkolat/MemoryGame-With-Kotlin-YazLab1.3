package com.example.harrypotter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Base64
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_medium_single.*
import java.util.*

class MediumSingle : AppCompatActivity() {
    lateinit var textView : TextView
    private lateinit var buttons: List<ImageView>
    private lateinit var harry: List<Mem>
    private var indexOfSingleSelectedCard: Int? = null
    var randomCards = ArrayList<Int>()
    var homeList = ArrayList<Int>()
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medium_single)


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
        val screen = ArrayList(Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15))
        for (i in 0..7)
        {
            homeList.add((1..4).random())
            random_number = (0..15).random()
            temp = screen[i]
            screen[i] = screen[random_number]
            screen[random_number] = temp

        }


        for (i in 0..7)
        {
            temp = (1..card_matrix[homeList[i].toInt()-1].size-1).random()

            randomCards.add(card_matrix[homeList[i].toInt()-1][temp])

            card_matrix[homeList[i].toInt()-1].removeAt(temp)
        }
        randomCards.addAll(randomCards)
        homeList.addAll(homeList)
        card_home.add(randomCards)
        card_home.add(homeList)
        buttons= listOf(imageView1,imageView2,imageView3,imageView4,imageView5,
            imageView6,imageView7,imageView8,imageView9,imageView10,imageView11,
            imageView12,imageView13,imageView14,imageView15,imageView16)



        println(randomCards)
        println(homeList)
        println(card_home)
        card_home[0].shuffle()
        println(card_home)


        harry = buttons.indices.map { index ->
            Mem(randomCards[index])
        }



        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {

                updateModels(index)
                updateViews()

            }
        }
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
                        }
                    }.addOnFailureListener{
                        println("başarısız")
                    }


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
            Toast.makeText(this, "Invalid move!", Toast.LENGTH_SHORT).show()
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
        if (harry[position1].identifier == harry[position2].identifier) {
            Toast.makeText(this, "Match found!!", Toast.LENGTH_SHORT).show()
            harry[position1].isMatched = true
            harry[position2].isMatched = true
        }
    }
}