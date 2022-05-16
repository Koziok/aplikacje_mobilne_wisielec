package com.example.wisielec

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.w3c.dom.Text
import java.net.URL

class Game : AppCompatActivity()
{
    var generatedWord = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)

        val logoutButton = findViewById<Button>(R.id.logout_button)
        val startNewGameButton = findViewById<Button>(R.id.newgame_button)
        val rateAppButton = findViewById<Button>(R.id.rateapp_button)

        val login = intent.getStringExtra("Login").toString()

        val database = DatabaseOrganiser(applicationContext)
        var currentScore = database.getScore(login)
        println(currentScore)

        val randomWord = findViewById<TextView>(R.id.random_word)
        val usedLettersView = findViewById<TextView>(R.id.used_letters)
        val image = findViewById<ImageView>(R.id.hanger_image)

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
        word = word.uppercase()


        println(word.count())

        val wordLength = word.count()

        var guessWord = ""
        for (i in 0..wordLength - 1)
        {
         guessWord += "_"
        }
        randomWord.text = guessWord

        val inputLetter = findViewById<EditText>(R.id.letter_input)
        val okButton = findViewById<Button>(R.id.ok_button)

        var mistakeCount = 0
        var usedLetters =  ""

        okButton.setOnClickListener()
        {

            if (word != guessWord)
            {
                if (!TextUtils.isEmpty(inputLetter.text)) {

                    if (!word.contains(inputLetter.text.toString().toCharArray()[0]) && mistakeCount <= 11)
                    {
                        mistakeCount += 1
                        if (mistakeCount <= 11) {
                            var drawableId = getResources().getIdentifier("mistake_$mistakeCount",
                                "drawable",
                                "com.example.wisielec");
                            image.setImageResource(drawableId)
                        }
                        if (!usedLetters.contains(inputLetter.text.toString()))
                        {
                            usedLetters += inputLetter.text.toString() + " "
                            usedLettersView.text = usedLetters
                        }

                    } else if (word.contains(inputLetter.text.toString().toCharArray()[0]) && mistakeCount <= 11)
                    {
                        for (i in 0 until wordLength) {
                            if (inputLetter.text.toString().toCharArray()[0] == word.toCharArray()[i]) {
                                guessWord = guessWord.substring(0, i) + inputLetter.text.toString()
                                    .toCharArray()[0] + guessWord.substring(i + 1)
                            }
                        }

                        if (word == guessWord)
                        {
                            Toast.makeText(this, "You won! Congrats!", Toast.LENGTH_LONG).show()
                            currentScore += 1
                            database.insertScore(login, currentScore);
                        }

                        randomWord.text = guessWord
                        if (!usedLetters.contains(inputLetter.text.toString()))
                        {
                            usedLetters += inputLetter.text.toString() + " "
                            usedLettersView.text = usedLetters
                        }
                    }
                    else
                    {
                        Toast.makeText(this, "The end! You lost!", Toast.LENGTH_LONG).show()
                        var drawableId =
                            getResources().getIdentifier("the_end", "drawable", "com.example.wisielec");
                        image.setImageResource(drawableId)
                        randomWord.text = word
                    }
                }
                else
                {
                    Toast.makeText(this, "Field cannot be empty!", Toast.LENGTH_LONG).show()
                }
            }
            else
            {
                Toast.makeText(this, "Your result saved to database! Start new game", Toast.LENGTH_LONG).show()
            }

        }

        logoutButton.setOnClickListener()
        {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        startNewGameButton.setOnClickListener()
        {
            val intent = Intent(this, Game::class.java)
            intent.putExtra("Login", login)
            startActivity(intent)
            finish()
        }

        rateAppButton.setOnClickListener()
        {
            if (database.getRating(login) == 0)
            {
                val intent = Intent(this, Rate::class.java)
                intent.putExtra("Login", login)
                startActivity(intent)
                finish()
            }
            else
            {
                Toast.makeText(this, "This user already submitted a rating!", Toast.LENGTH_LONG).show()
            }

        }
    }
}