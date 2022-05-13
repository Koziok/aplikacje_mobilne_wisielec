package com.example.wisielec

import android.content.Intent
import android.os.Bundle
import android.telephony.SmsManager
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class Register : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_screen)

        var loginInput = findViewById<EditText>(R.id.register_login_input)
        var passwordInput  = findViewById<EditText>(R.id.register_password_input)
        var phoneInput  = findViewById<EditText>(R.id.register_phone_input)
        var emailInput = findViewById<EditText>(R.id.register_email_input)

        var registerBackButton = findViewById<Button>(R.id.register_back_button)
        var registerButton = findViewById<Button>(R.id.register_register_button)

        val database = DatabaseOrganiser(applicationContext)

        registerButton.setOnClickListener() {

            var loginData = loginInput.text.toString()
            var passwordData = passwordInput.text.toString()
            var phoneData = phoneInput.text.toString()
            var emailData = emailInput.text.toString()
            var score = 0

            if (TextUtils.isEmpty(loginData) || TextUtils.isEmpty(passwordData) || TextUtils.isEmpty(phoneData) || TextUtils.isEmpty(emailData)) {
                val toast = Toast.makeText(this, "Żadne pole nie może być puste!", Toast.LENGTH_LONG)
                toast.show()
            }
            else
            {
                val dbReturn = database.register(loginData, passwordData, phoneData, emailData, score)
                if(dbReturn!=-1){
                    val toast = Toast.makeText(this, "Zarejestrowano pomyślnie!", Toast.LENGTH_LONG)
                    toast.show()
                }
                else
                {
                    val toast = Toast.makeText(this, "Konto z takim loginem już istnieje!", Toast.LENGTH_LONG)
                    toast.show()
                }
            }
        }

        registerBackButton.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}