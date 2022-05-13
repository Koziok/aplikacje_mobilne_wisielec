package com.example.wisielec

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import java.net.URL

class Game : AppCompatActivity()
{
    var generatedWord = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)

        val randomWord = findViewById<TextView>(R.id.random_word)

        val sharedWord = this.getSharedPreferences("com.example.wisielec", 0)

        val edit = sharedWord.edit()

        Thread( Runnable {
            run {
                val urlRandomWord = "https://random-word-api.herokuapp.com/word"
                val bodyWord = URL(urlRandomWord).readText()
                if (bodyWord != "") {
                    var jsonArray = JSONArray(bodyWord)
                    generatedWord = jsonArray.toString()

                    }
                }
            runOnUiThread()
            {
                edit.putString("word", generatedWord)
                edit.apply()
            }

        }).start()
        var word = sharedWord.getString("word", "")
        println(word)

        word = word!!.drop(2)
        word = word!!.dropLast(2)
        randomWord.text = word

        println(word.count())

    }
}