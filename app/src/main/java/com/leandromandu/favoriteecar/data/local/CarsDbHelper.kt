package com.leandromandu.favoriteecar.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.leandromandu.favoriteecar.data.local.CarContract.TABLE_CAR
import com.leandromandu.favoriteecar.data.local.CarContract.TABLE_CAR_DELETE

class CarsDbHelper(context:Context):SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(TABLE_CAR)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL(TABLE_CAR_DELETE)
        onCreate(db)
    }
    companion object{
        const val DATABASE_NAME="DbCar.db"
        const val DATABASE_VERSION=1
    }
}