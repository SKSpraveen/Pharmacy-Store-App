package com.example.pharmacy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class mainPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        val btn = findViewById<Button>(R.id.start)
        btn.setOnClickListener{
            val Intent = Intent(this,MainActivity::class.java)
            startActivity(Intent)

        }
    }
}