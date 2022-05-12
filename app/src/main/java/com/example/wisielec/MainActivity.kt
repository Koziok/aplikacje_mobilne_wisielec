package com.example.wisielec

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

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
                val toast = Toast.makeText(this, "Pole login oraz hasło nie mogą być puste!", Toast.LENGTH_LONG)
                toast.show()
            }
            else
            {
                val dbReturn = database.login(loginData, passwordData)
                if(dbReturn!=-1){
                    val toast = Toast.makeText(this, "Zalogowano pomyślnie!", Toast.LENGTH_LONG)
                    toast.show()
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
    }
}