package com.example.wisielec

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class Rate : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rate)

        val database = DatabaseOrganiser(applicationContext)

        val login = intent.getStringExtra("Login").toString()

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS), 111)
        }

        val rateAppButton = findViewById<Button>(R.id.rate_app_button)
        val ratingInput = findViewById<EditText>(R.id.rating_input)

        rateAppButton.setOnClickListener()
        {
            val ratingData = ratingInput.text.toString()
            if (!TextUtils.isEmpty(ratingData))
            {
                if (ratingData.toIntOrNull()!! >= 0 && ratingData.toIntOrNull()!! <= 5)
                {


                    sendMsg(ratingData)
                    if (database.setRating(login) == -1)
                    {
                        Toast.makeText(this, "Your rating " + ratingData + "/5 was sent to creator!", Toast.LENGTH_LONG).show()
                    }
                    else
                    {
                        Toast.makeText(this, "Message not sent!", Toast.LENGTH_LONG).show()
                    }
                    val intent = Intent(this, Game::class.java)
                    intent.putExtra("Login", login)
                    startActivity(intent)
                    finish()
                }
                else
                {
                    Toast.makeText(this, "The rating must be between 0 - 5!", Toast.LENGTH_LONG).show()
                }
            }
            else
            {
                Toast.makeText(this, "Fill in the grade field!", Toast.LENGTH_LONG).show()
            }


        }


    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==111 && grantResults[0]==PackageManager.PERMISSION_GRANTED){}


    }
    private fun sendMsg(ratingData : String)
    {
        var sms = SmsManager.getDefault()
        var messageText = "User score : " + ratingData
        sms.sendTextMessage("1223", "ME", messageText, null, null)
    }
}
