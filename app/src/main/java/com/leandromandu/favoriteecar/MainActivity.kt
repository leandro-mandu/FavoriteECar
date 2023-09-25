package com.leandromandu.favoriteecar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.leandromandu.favoriteecar.adapter.CarAdapter
import com.leandromandu.favoriteecar.data.CarroFactory

class MainActivity : AppCompatActivity() {
    lateinit var btn_navegar : Button
    lateinit var car_list : RecyclerView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()
        setupListeners()
    }

    fun setupView(){
        btn_navegar=findViewById(R.id.bt_navegar)
        car_list=findViewById(R.id.rv_car_list);

//        val dados = arrayOf("Car One", "Car Two", "Car Three")
 //       val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, dados)

        val dados = CarroFactory.lista
        val adapter = CarAdapter(dados)
//        val adapter = CustomAdapter(dados)
//        car_list.adapter=adapter
        car_list.adapter=adapter
    }

    fun setupListeners(){
        btn_navegar.setOnClickListener {
            startActivity(Intent(this,CalcularActivity::class.java))
        }
    }
}