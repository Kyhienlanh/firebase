package com.example.firebase

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var button=findViewById<Button>(R.id.button)
        var button2=findViewById<Button>(R.id.button2)
        button.setOnClickListener(){
            var intent = Intent(this,MainActivity2::class.java)
            startActivity(intent)
        }
        button2.setOnClickListener(){
            var intent = Intent(this,FetchingActivity::class.java)
            startActivity(intent)
        }
    }
}