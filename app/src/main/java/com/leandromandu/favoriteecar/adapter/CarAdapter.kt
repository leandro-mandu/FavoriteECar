package com.leandromandu.favoriteecar.adapter

import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.leandromandu.favoriteecar.R
import com.leandromandu.favoriteecar.dominio.Carro
import java.net.URI
import java.net.URL




/*
        ESTRUTURA BÁSICA ADAPTER:

        class CarAdapter(dados) : ViewHolder
            onCreate -> ViewHolder(view)
            onBind
            getItemCount
            class ViewHolder
 */

class CarAdapter(private val carros : List<Carro>) : RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_car, parent, false)
        Log.d("adapter", "onCreate")

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.preco.text=carros[position].preco
        holder.bateria.text=carros[position].bateria
        holder.potencia.text=carros[position].potencia
        holder.recarga.text=carros[position].recarga
//        val newurl = Uri.(carros[position].img_url)
//        //val img = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
//        holder.imagemUrl.setImageURI(newurl)

//        holder.imagemUrl.setImageURI()

        Log.d("adapter", "onBind")

    }
    override fun getItemCount() :Int {
        Log.d("adapter", "itemCount")
        return carros.size
    }
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val preco : TextView
        val bateria : TextView
        val potencia : TextView
        val imagemUrl : ImageView
        val recarga : TextView
        init {
            preco=view.findViewById(R.id.tv_price_value)
            bateria=view.findViewById(R.id.tv_bat_value)
            potencia=view.findViewById(R.id.tv_horsepower_value)
            imagemUrl=view.findViewById(R.id.img_car)
            recarga=view.findViewById(R.id.tv_charge_value)
            Log.d("adapter", "init")
        }
    }
}
