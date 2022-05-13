package com.example.wisielec

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView

class Welcomescreen : AppCompatActivity() {

    private lateinit var image:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_screen)


            image = findViewById(R.id.wisielec_img);
            animateImage()

        val thread = Thread(){
            run{
                Thread.sleep(5000)

            }
            runOnUiThread(){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        thread.start()
    }

    private fun animateImage()
    {
        val rotate = AnimationUtils.loadAnimation(this, R.anim.rotation)
        image.animation = rotate
    }

}