package com.example.wisielec

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class TableScore: AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.table_scores)

        val database = DatabaseOrganiser(applicationContext)
        val dataList = database.getData()

        var dataListModified = arrayOf<String>()
        val list: MutableList<String> = dataListModified.toMutableList()
        for (i in 0..(dataList.size - 1) step 3)
            list.add("Place : " + dataList[i] + " - " + dataList[i+1] + " - " + dataList[i+2] + " points.")
        dataListModified = list.toTypedArray()

        val adapter = ArrayAdapter(this,
            R.layout.listview_item, dataListModified)

        val listView: ListView = findViewById(R.id.listview_1)
        listView.setAdapter(adapter)

        val backButton = findViewById<Button>(R.id.backButtonTable)

        backButton.setOnClickListener()
        {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }



    }


}