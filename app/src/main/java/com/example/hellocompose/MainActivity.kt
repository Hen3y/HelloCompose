package com.example.hellocompose

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_layout)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content, HomeFragment())
        transaction.commit()
    }
}
