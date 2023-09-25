package com.leandromandu.favoriteecar.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.leandromandu.favoriteecar.R

/*
        ESTRUTURA BÁSICA ADAPTER:

        class CarAdapter(dados) : ViewHolder
            onCreate -> ViewHolder(view)
            onBind
            getItemCount
            class ViewHolder
 */

class CarAdapter(private val carros : Array<String>) : RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_car, parent, false)
        Log.d("adapter", "onCreate")

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text=carros[position]
        Log.d("adapter", "onBind")

    }
    override fun getItemCount() :Int {
        Log.d("adapter", "itemCount")
        return carros.size
    }
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val textView : TextView
        init {
            textView=view.findViewById(R.id.tv_price_value)
            Log.d("adapter", "init")
        }
    }
}
