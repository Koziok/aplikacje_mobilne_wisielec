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

        val loginInput = findViewById<EditText>(R.id.login_input)
        val passwordInput = findViewById<EditText>(R.id.password_input)

        val database = DatabaseOrganiser(applicationContext)


        loginButton.setOnClickListener()
        {
            var loginData = loginInput.text.toString()
            var passwordData = passwordInput.text.toString()


            if (TextUtils.isEmpty(loginData) || TextUtils.isEmpty(passwordData)) {
                val toast = Toast.makeText(this, "Login and password fields cannot be empty!", Toast.LENGTH_LONG)
                toast.show()
            }
            else
            {
                val dbReturn = database.login(loginData, passwordData)
                if(dbReturn!=-1){
                    val toast = Toast.makeText(this, "Logged in successfully!", Toast.LENGTH_LONG)
                    toast.show()
                    val intent = Intent(this, Game::class.java)
                    startActivity(intent)
                }
                else
                {
                    val toast = Toast.makeText(this, "Wrong password or account does not exist!", Toast.LENGTH_LONG)
                    toast.show()
                }

            }
        }

        registerButton.setOnClickListener()
        {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

    }




}
