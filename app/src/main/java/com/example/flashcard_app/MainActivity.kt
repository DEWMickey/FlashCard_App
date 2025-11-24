package com.example.flashcard_app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*


data class Flashcard(
    val question: String,
    val correctAnswer: String,
    val allAnswers: List<String>
)
class MainActivity : AppCompatActivity() {

    // Liste des questions et reponses de l'application
    private var flashcards = mutableListOf( // mutableListOf pour pouvoir ajouter des cartes !
        Flashcard(
            question = "Quelle est la planète la plus proche du Soleil ?",
            correctAnswer = "Mercure",
            allAnswers = listOf("Vénus", "Mercure", "Mars")
        )
    )
    // valeur de l'iteration initiale
    private var count = 0

    // --- LE LANCEUR QUI VA ÉCOUTER LE RÉSULTAT DE AddCard ---
    private val addCardLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val question = data?.getStringExtra("question")
            val reponse1 = data?.getStringExtra("reponse1")
            val reponse2 = data?.getStringExtra("reponse2")
            val reponse3 = data?.getStringExtra("reponse3")

            // On vérifie ici que les données ne sont pas nulles avant de créer la carte
            if (!question.isNullOrBlank() && !reponse1.isNullOrBlank()) {
                val nouvelleCarte = Flashcard(
                    question = question,
                    correctAnswer = reponse1,
                    allAnswers = listOf(reponse1, reponse2 ?: "", reponse3 ?: "")
                )

                flashcards.add(nouvelleCarte)
                Log.d("MainActivity", "Carte ajoutée. Taille de la liste : ${flashcards.size}")

                // Mettre à jour l'UI pour montrer la nouvelle carte
                count = flashcards.size - 1
                updatedIterationVariables()

                findViewById<TextView>(R.id.textViewCongrats).text = "Vous avez ajoute cette nouvelle carte"
                lifecycleScope.launch {
                    delay(4000)
                    findViewById<TextView>(R.id.textViewCongrats).text = ""
                }

            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val rep1 = findViewById<TextView>(R.id.textViewReponse1)
        val rep2 = findViewById<TextView>(R.id.textViewReponse2)
        val rep3 = findViewById<TextView>(R.id.textViewReponse3)

        // Action Button Main To AddCard Activity
        findViewById<Button>(R.id.buttonAjout).setOnClickListener {
            val intent = Intent(this, AddCard::class.java)
            addCardLauncher.launch(intent)
        }

        // Appel fontion pour affichage des questions et reponses de la liste flashcards
        updatedIterationVariables()



        rep1.setOnClickListener {
            checkReponse(rep1)
            rep2.setBackgroundResource(R.drawable.bordure_textview)
            rep3.setBackgroundResource(R.drawable.bordure_textview)
        }

        rep2.setOnClickListener {
            checkReponse(rep2)
            rep1.setBackgroundResource(R.drawable.bordure_textview)
            rep3.setBackgroundResource(R.drawable.bordure_textview)
        }

        rep3.setOnClickListener {
            checkReponse(rep3)
            rep1.setBackgroundResource(R.drawable.bordure_textview)
            rep2.setBackgroundResource(R.drawable.bordure_textview)
        }



        findViewById<Button>(R.id.buttonNext).setOnClickListener {
            if (flashcards.isNotEmpty()) {
                count = (count + 1) % flashcards.size
                colorResetByClick()
                updatedIterationVariables()
                findViewById<TextView>(R.id.textViewCongrats).text = ""
            }
        }
    }

    private fun updatedIterationVariables(){
        findViewById<TextView>(R.id.questionText).text = flashcards[count].question
        val newMelangeList = flashcards[count].allAnswers.shuffled()
        findViewById<TextView>(R.id.textViewReponse1).text = newMelangeList[0]
        findViewById<TextView>(R.id.textViewReponse2).text = newMelangeList[1]
        findViewById<TextView>(R.id.textViewReponse3).text = newMelangeList[2]
    }

    private fun colorResetByClick() {
        findViewById<TextView>(R.id.textViewReponse1).setBackgroundResource(R.drawable.bordure_textview)
        findViewById<TextView>(R.id.textViewReponse2).setBackgroundResource(R.drawable.bordure_textview)
        findViewById<TextView>(R.id.textViewReponse3).setBackgroundResource(R.drawable.bordure_textview)
    }

    private fun checkReponse(reponse: TextView) {
        val info = "Vous avez reussi, Felicitations !!!"
        if(reponse.text.toString() != flashcards[count].correctAnswer){
            reponse.setBackgroundResource(R.drawable.mauvaisereponse)
            findViewById<TextView>(R.id.textViewCongrats).text = ""
        } else {
            reponse.setBackgroundResource(R.drawable.bonnereponse)
            findViewById<TextView>(R.id.textViewCongrats).text = info

        }
    }

}

