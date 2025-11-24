package com.example.flashcard_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.app.Activity // << IMPORT pour passing donnees

class AddCard : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_card)


        // Save and Back to MainActivity
        findViewById<Button>(R.id.buttonSave).setOnClickListener {
            saveData()
        }

    }

    private fun saveData() {
        val question = findViewById<EditText>(R.id.editQuestionText).text.toString()
        val answer1 = findViewById<EditText>(R.id.editSimpleReponseText).text.toString()
        val answer2 = findViewById<EditText>(R.id.editSimpleReponseText2).text.toString()
        val answer3 =  findViewById<EditText>(R.id.editSimpleReponseText3).text.toString()

        if (question.isBlank() || answer1.isBlank()) {
            setResult(Activity.RESULT_CANCELED) // On annule si les champs sont vides
            finish() // On ferme l'activité
            return
        }

        // On prépare un Intent vide qui ne sert qu'à contenir les données de retour
        val resultIntent = Intent()
        resultIntent.putExtra("question", question)
        resultIntent.putExtra("reponse1", answer1)
        resultIntent.putExtra("reponse2", answer2)
        resultIntent.putExtra("reponse3", answer3)


        setResult(Activity.RESULT_OK, resultIntent) // On dit que c'est un succès et on attache les données
        finish() // On ferme AddCard et on retourne à MainActivity

    }

}