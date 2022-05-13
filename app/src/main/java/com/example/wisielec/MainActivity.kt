package com.example.wisielec

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import java.util.*

class MainActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val loginButton = findViewById<Button>(R.id.login_button)
        val registerButton = findViewById<Button>(R.id.register_button)
        val rateAppButton = findViewById<Button>(R.id.rate_app_button)

        val loginInput = findViewById<EditText>(R.id.login_input)
        val passwordInput = findViewById<EditText>(R.id.password_input)
        val ratingInput = findViewById<EditText>(R.id.rating_input)

        val database = DatabaseOrganiser(applicationContext)


        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS), 111)
        }


        loginButton.setOnClickListener()
        {
            var loginData = loginInput.text.toString()
            var passwordData = passwordInput.text.toString()


            if (TextUtils.isEmpty(loginData) || TextUtils.isEmpty(passwordData)) {
                val toast = Toast.makeText(this, "Pole login oraz hasło nie mogą być puste!", Toast.LENGTH_LONG)
                toast.show()
            }
            else
            {
                val dbReturn = database.login(loginData, passwordData)
                if(dbReturn!=-1){
                    val toast = Toast.makeText(this, "Zalogowano pomyślnie!", Toast.LENGTH_LONG)
                    toast.show()
                    val intent = Intent(this, Game::class.java)
                    startActivity(intent)
                }
                else
                {
                    val toast = Toast.makeText(this, "Niepoprawne hasło lub konto nie istnieje!", Toast.LENGTH_LONG)
                    toast.show()
                }

            }
        }

        registerButton.setOnClickListener()
        {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        rateAppButton.setOnClickListener()
        {
            val ratingData = ratingInput.text.toString()
            if (!TextUtils.isEmpty(ratingData))
            {
                if (ratingData.toIntOrNull()!! >= 0 && ratingData.toIntOrNull()!! <= 5)
                {
                    Toast.makeText(this, "Twoja ocena " + ratingData + "/5. Wysłano wiadomość do twórcy!", Toast.LENGTH_LONG).show()

                    sendMsg(ratingData)
                }
                else
                {
                    Toast.makeText(this, "Ocena musi być cyfrą z przedziału 0 - 5!", Toast.LENGTH_LONG).show()
                }
            }
            else
            {
                Toast.makeText(this, "Wypełnij pole z oceną!", Toast.LENGTH_LONG).show()
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
        var messageText = ratingData
        sms.sendTextMessage("1223", "ME", messageText, null, null)
    }



}
