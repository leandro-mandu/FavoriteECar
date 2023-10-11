package com.leandromandu.favoriteecar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.leandromandu.favoriteecar.adapter.CarAdapter
import com.leandromandu.favoriteecar.adapter.TabAdapter
import com.leandromandu.favoriteecar.data.CarroFactory

class MainActivity : AppCompatActivity() {
    lateinit var btn_navegar : Button
    //lateinit var car_list : RecyclerView
    lateinit var tab_car : TabLayout
    lateinit var viewPager2 : ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()
        setupListeners()
    }

    fun setupView(){
        btn_navegar=findViewById(R.id.bt_navegar)
        //car_list=findViewById(R.id.rv_car_list)
        tab_car=findViewById(R.id.tab_carros)
        viewPager2=findViewById(R.id.vp_view_pager)



//        val dados = arrayOf("Car One", "Car Two", "Car Three")
 //       val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, dados)

//        val dados = CarroFactory.lista
//        val adapter = CarAdapter(dados)
        val tabAdapter = TabAdapter(this)
        viewPager2.adapter=tabAdapter
//        val adapter = CustomAdapter(dados)
//        car_list.adapter=adapter
//        car_list.adapter=adapter
    }

    fun setupListeners(){
        btn_navegar.setOnClickListener {
            startActivity(Intent(this,CalcularActivity::class.java))
        }
        tab_car.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let { viewPager2.currentItem = it.position }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tab_car.getTabAt(position)?.select()
            }
        })

    }
}