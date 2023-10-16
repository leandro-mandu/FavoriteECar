package com.leandromandu.favoriteecar.dominio

data class Carro(
    val id : Int,
    val preco : String,
    val bateria : String,
    val potencia : String,
    val urlPhoto : String,
    val recarga : String,
    var isFavorite : Boolean,
) {
}