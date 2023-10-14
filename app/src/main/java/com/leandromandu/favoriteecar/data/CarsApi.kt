package com.leandromandu.favoriteecar.data

import com.leandromandu.favoriteecar.dominio.Carro
import retrofit2.Call
import retrofit2.http.GET

//for retrofit use
interface CarsApi {
    @GET("cars.json")
    fun getAllCars(): Call<List<Carro>>
}