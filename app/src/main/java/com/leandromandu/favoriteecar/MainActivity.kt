package com.leandromandu.favoriteecar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.util.logging.LogManager

class MainActivity : AppCompatActivity() {
    lateinit var btn_navegar : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()
        setupListeners()
    }

    fun setupView(){
        btn_navegar=findViewById(R.id.bt_navegar)
    }

    fun setupListeners(){
        btn_navegar.setOnClickListener {
            startActivity(Intent(this,CalcularActivity::class.java))
        }
    }
}