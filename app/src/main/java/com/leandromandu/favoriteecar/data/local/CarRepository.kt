package com.leandromandu.favoriteecar.data.local

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import android.util.Log
import com.leandromandu.favoriteecar.data.local.CarContract.CarEntry.COLUMN_NAME_BATERIA
import com.leandromandu.favoriteecar.data.local.CarContract.CarEntry.COLUMN_NAME_CAR_ID
import com.leandromandu.favoriteecar.data.local.CarContract.CarEntry.COLUMN_NAME_POTENCIA
import com.leandromandu.favoriteecar.data.local.CarContract.CarEntry.COLUMN_NAME_PRECO
import com.leandromandu.favoriteecar.data.local.CarContract.CarEntry.COLUMN_NAME_RECARGA
import com.leandromandu.favoriteecar.data.local.CarContract.CarEntry.COLUMN_NAME_URL_PHOTO
import com.leandromandu.favoriteecar.dominio.Carro

class CarRepository(private val context: Context) {
    fun save(carro: Carro):Long?{
        try {
            val dbHelper=CarsDbHelper(context)
            val db = dbHelper.writableDatabase
            val values=ContentValues().apply {
                put(CarContract.CarEntry.COLUMN_NAME_CAR_ID, carro.id)
                put(CarContract.CarEntry.COLUMN_NAME_BATERIA, carro.bateria)
                put(CarContract.CarEntry.COLUMN_NAME_POTENCIA, carro.potencia)
                put(CarContract.CarEntry.COLUMN_NAME_PRECO, carro.preco)
                put(CarContract.CarEntry.COLUMN_NAME_RECARGA, carro.recarga)
                put(CarContract.CarEntry.COLUMN_NAME_URL_PHOTO, carro.urlPhoto)
            }
            val result=db.insert(CarContract.CarEntry.TABLE_NAME, null, values)
            return result
        }catch (e:Exception){
            Log.e("Erro no insert:",e.toString())
            return null
        }
    }
    @SuppressLint("Range")
    fun findById(id:Int) : Carro?{
        val dbHelper=CarsDbHelper(context)
        val db=dbHelper.readableDatabase
        val columns= arrayOf(
            BaseColumns._ID,
            COLUMN_NAME_CAR_ID,
            COLUMN_NAME_PRECO,
            COLUMN_NAME_BATERIA,
            COLUMN_NAME_POTENCIA,
            COLUMN_NAME_RECARGA,
            COLUMN_NAME_URL_PHOTO
        )
        val filter="$COLUMN_NAME_CAR_ID = ?"
        val filterValues= arrayOf(id.toString())
        val cursor=db.query(CarContract.CarEntry.TABLE_NAME,
            columns,
            filter,
            filterValues,
            null,
            null,
            null)
        var qtd=cursor.count
        if(qtd==0){
            cursor.close()
            return null
        }
        var carro:Carro
        with(cursor){
            moveToFirst()
            carro = Carro(
                id = getInt( getColumnIndex(COLUMN_NAME_CAR_ID) ) ,
                preco = getString(getColumnIndex(COLUMN_NAME_PRECO)),
                bateria = getString(getColumnIndex(COLUMN_NAME_BATERIA)),
                potencia = getString(getColumnIndex(COLUMN_NAME_POTENCIA)),
                recarga = getString(getColumnIndex(COLUMN_NAME_RECARGA)),
                urlPhoto = getString(getColumnIndex(COLUMN_NAME_URL_PHOTO)),
                isFavorite = true
            )

//            Log.e("preco",getColumnIndexOrThrow(COLUMN_NAME_PRECO).toString())
//            Log.e("preco",getString(2))
            cursor.close()
        }
        return carro
    }
    fun getAll():List<Carro>{

        val dbHelper=CarsDbHelper(context)
        val db=dbHelper.readableDatabase
        val columns= arrayOf(
            BaseColumns._ID,
            COLUMN_NAME_CAR_ID,
            COLUMN_NAME_PRECO,
            COLUMN_NAME_BATERIA,
            COLUMN_NAME_POTENCIA,
            COLUMN_NAME_RECARGA,
            COLUMN_NAME_URL_PHOTO
        )
        val cursor=db.query(CarContract.CarEntry.TABLE_NAME, columns,null,null,
            null,null,null)
        var lista = mutableListOf<Carro>()

        with(cursor){
            while (moveToNext()) {
                var carro = Carro(
                    id = getInt(getColumnIndexOrThrow(COLUMN_NAME_CAR_ID)),
                    preco = getString(getColumnIndexOrThrow(COLUMN_NAME_PRECO)),
                    bateria = getString(getColumnIndexOrThrow(COLUMN_NAME_BATERIA)),
                    potencia = getString(getColumnIndexOrThrow(COLUMN_NAME_POTENCIA)),
                    recarga = getString(getColumnIndexOrThrow(COLUMN_NAME_RECARGA)),
                    urlPhoto = getString(getColumnIndexOrThrow(COLUMN_NAME_URL_PHOTO)),
                    isFavorite = true
                )
                lista.add(carro)
            }
        }
        cursor.close()
        return lista.toList()
    }
    fun deleteById(id: Int){
        val dbHelper=CarsDbHelper(context)
        val db=dbHelper.writableDatabase
        val filter="$COLUMN_NAME_CAR_ID = ?"
        val filterValues= arrayOf(id.toString())
        db.delete(CarContract.CarEntry.TABLE_NAME,filter,filterValues)
    }
}