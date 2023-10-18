package com.leandromandu.favoriteecar.data.local

import android.provider.BaseColumns

object CarContract {
    object CarEntry: BaseColumns{
        const val TABLE_NAME ="carro"
        const val COLUMN_NAME_CAR_ID ="car_id"
        const val COLUMN_NAME_PRECO ="preco"
        const val COLUMN_NAME_BATERIA="bateria"
        const val COLUMN_NAME_POTENCIA="potencia"
        const val COLUMN_NAME_URL_PHOTO="urlPhoto"
        const val COLUMN_NAME_RECARGA="recarga"
    }
    const val TABLE_CAR="CREATE TABLE IF NOT EXISTS ${CarEntry.TABLE_NAME} ("+
            "${BaseColumns._ID} INTEGER PRIMARY KEY,"+
            "${CarEntry.COLUMN_NAME_CAR_ID} INTEGER,"+
            "${CarEntry.COLUMN_NAME_PRECO} TEXT,"+
            "${CarEntry.COLUMN_NAME_BATERIA} TEXT,"+
            "${CarEntry.COLUMN_NAME_POTENCIA} TEXT,"+
            "${CarEntry.COLUMN_NAME_URL_PHOTO} TEXT,"+
            "${CarEntry.COLUMN_NAME_RECARGA} TEXT)"

    const val TABLE_CAR_DELETE="DROP TABLE IF EXISTS ${CarEntry.TABLE_NAME}"
}