package com.leandromandu.favoriteecar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CalcularActivity: AppCompatActivity() {
    lateinit var preco : EditText
    lateinit var km_percorrido : EditText
    lateinit var btn_calcular : Button
    lateinit var result : TextView
    lateinit var btn_voltar : ImageView
    var historico = mutableListOf("Histórico")
    lateinit var sp_his : Spinner;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calcular)

        setupView()
        setupListeners()
    }
    fun setupView(){
        preco = findViewById(R.id.et_preco_kwh)
        btn_calcular=findViewById(R.id.bt_calcular)
        km_percorrido=findViewById(R.id.et_km_percorrido)
        result =findViewById(R.id.tv_result)
        btn_voltar=findViewById(R.id.ic_back)
        sp_his=findViewById(R.id.sp_history)

        sp_his.adapter=ArrayAdapter(this, android.R.layout.simple_list_item_1, historico)
        var last = getLastResult()
        if(last!=0.0f) {
            result.visibility= View.VISIBLE;
            result.text="Último: "+last
        }

    }

    fun setupListeners(){
        btn_calcular.setOnClickListener {
            var preco = preco.text.toString()
            var km = km_percorrido.text.toString()
            var res = preco.toFloat()/km.toFloat()
            Log.d( "Preco: ",preco)
            Log.d( "Km: ",km)
            Log.d("Resultado: ",res.toString())
            result.text="Res. $res"
            result.visibility= View.VISIBLE;

            historico.add(res.toString())
            saveLastResult(res)
        }
        btn_voltar.setOnClickListener {
            //startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    fun saveLastResult(result:Float){
//        val sharedPref = getPreferences(Context.MODE_PRIVATE)
//        sharedPref.edit().putFloat(getString(R.string.key_last_result), result)
//        sharedPref.edit().apply()
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        with(sharedPref.edit()){
            putFloat(getString(R.string.key_last_result), result)
            apply()
        }
    }

    fun getLastResult():Float{
        val sharedPref=getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getFloat(getString(R.string.key_last_result), 0.0f)
    }
}