package com.example.harrypotter


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_easy_single.*
import java.util.*
import kotlin.collections.ArrayList


class EasySingle : AppCompatActivity() {
    lateinit var textView : TextView
    private lateinit var buttons: List<ImageView>
    private lateinit var harry: List<Mem>
    private var indexOfSingleSelectedCard: Int? = null
    var randomCards = java.util.ArrayList<Int>()
    var homeList = java.util.ArrayList<Int>()
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_easy_single)
        val card_matrix = java.util.ArrayList<java.util.ArrayList<Int>>()
        var row1 = java.util.ArrayList(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11))
        var row2 = java.util.ArrayList(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11))
        var row3 = java.util.ArrayList(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11))
        var row4 = java.util.ArrayList(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11))

        card_matrix.add(row1)
        card_matrix.add(row2)
        card_matrix.add(row3)
        card_matrix.add(row4)

        var homeList = ArrayList<String>()
        var temp = 0
        var random_number = 0

        for (i in 0..1)
        {
            homeList.add((1..4).random().toString())
            random_number = (1..3).random()
        }

        var randomCards = ArrayList<Int>()
        for (i in 0..1)
        {
            temp = (1..card_matrix[homeList[i].toInt()-1].size-1).random()

            randomCards.add(card_matrix[homeList[i].toInt()-1][temp])

            card_matrix[homeList[i].toInt()-1].removeAt(temp)
        }

        buttons= listOf(imageView2,imageView3,imageView4,imageView5)


 /*       harry = buttons.indices.map { index ->
            var temp3= mutableListOf<Int>(card_home[0][index],card_home[1][index])
            Mem(temp3)
        }
*/
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








