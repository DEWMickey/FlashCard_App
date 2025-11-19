package com.example.flashcard_app

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var count = 0
    private var getValueRep: String = ""
    private val reponse = arrayOf("Mercure", "Océan Pacifique", "Tokyo")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val question = arrayOf(
            "Quelle est la planète la plus proche du Soleil ?",
            "Quel est le plus grand océan du monde ?",
            "Quelle est la capitale du Japon ?")


        val rep1 = findViewById<TextView>(R.id.textViewReponse1)
        val rep2 = findViewById<TextView>(R.id.textViewReponse2)
        val rep3 = findViewById<TextView>(R.id.textViewReponse3)
        val buttonFC = findViewById<Button>(R.id.button)
        val getValueQues = findViewById<TextView>(R.id.questionText)



        getValueQues.text = question[count]

        fun repCheck(valeur: TextView = rep1, qnum: Int , rnum: Int ) {
            return if(question[count] == question[qnum] && getValueRep == reponse[rnum]){
                valeur.setBackgroundResource(R.drawable.bonnereponse)
            } else {
                valeur.setBackgroundResource(R.drawable.mauvaisereponse)
            }
        }

        fun colorResetByClick() {
            rep1.setBackgroundResource(R.drawable.bordure_textview)
            rep2.setBackgroundResource(R.drawable.bordure_textview)
            rep3.setBackgroundResource(R.drawable.bordure_textview)
        }


        // component Actions
        rep1.setOnClickListener {
            getValueRep = rep1.text as String
            repCheck(valeur = rep1, qnum = 0, rnum = 0)
            // Reset background
            rep2.setBackgroundResource(R.drawable.bordure_textview)
            rep3.setBackgroundResource(R.drawable.bordure_textview)
        }

        rep2.setOnClickListener {
            getValueRep = rep2.text as String
            repCheck(valeur = rep2, qnum = 1, rnum = 1)
            // Reset background
            rep1.setBackgroundResource(R.drawable.bordure_textview)
            rep3.setBackgroundResource(R.drawable.bordure_textview)
        }

        rep3.setOnClickListener {
            getValueRep = rep3.text as String
            repCheck(valeur = rep3, qnum = 2, rnum = 2)
            // Reset background
            rep2.setBackgroundResource(R.drawable.bordure_textview)
            rep1.setBackgroundResource(R.drawable.bordure_textview)
        }


        buttonFC.setOnClickListener {
            //Initialisation
            count += 1

            // Reset all Color
            colorResetByClick()

            // Reset count si plus de donnee dispo dans la liste
            if(count == 3) {
                count = 0
            }
            // update question after click
            getValueQues.text = question[count]
        }

    }

}

