package com.leandromandu.favoriteecar.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.leandromandu.favoriteecar.R
import com.leandromandu.favoriteecar.adapter.CarAdapter
import com.leandromandu.favoriteecar.data.local.CarRepository

class FavoritesFragment : Fragment(){
    lateinit var rv_fav_list : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorites_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
    }

    override fun onResume() {
        super.onResume()
        setupList()

    }

    fun setupView(view:View){
        rv_fav_list=view.findViewById(R.id.rv_favorite_list)
    }
    fun setupList(){
        var listFavoritos=CarRepository(requireContext()).getAll()
        for (i in 0 until listFavoritos.size){
            if (CarRepository(requireContext()).findById(listFavoritos[i].id) != null)
                listFavoritos[i].isFavorite=true
        }
        val adapter=CarAdapter(listFavoritos)
        rv_fav_list.adapter=adapter
        adapter.carItemListener={
                CarRepository(requireContext()).deleteById(it.id)
                Toast.makeText(context, "Carro ${it.id} removido dos favoritos! :(", Toast.LENGTH_LONG).show()
            setupList()
        }
    }
}