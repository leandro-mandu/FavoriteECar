package com.leandromandu.favoriteecar.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.leandromandu.favoriteecar.R
import com.leandromandu.favoriteecar.adapter.CarAdapter
import com.leandromandu.favoriteecar.data.CarroFactory

class CarFragment : Fragment(){
    lateinit var car_list : RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.car_fragment, container, false)
//        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        setupView(view)
    }
    fun setupView(view:View){
        val dados = CarroFactory.lista
        val adapter = CarAdapter(dados)
        car_list=view.findViewById(R.id.rv_car_list)
        car_list.adapter=adapter
    }
}