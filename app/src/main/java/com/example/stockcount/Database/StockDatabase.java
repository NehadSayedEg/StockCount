package com.example.stockcount.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.stockcount.data.Stock;


@Database(entities = {  Stock.class }, version = 1 , exportSchema = false)
public  abstract  class StockDatabase extends RoomDatabase {
    public static StockDatabase assetsDatabase ;
    public static synchronized StockDatabase getMarketDatabase(Context context){
        if(assetsDatabase == null){
            assetsDatabase = Room.databaseBuilder(context , StockDatabase.class , "stock_db").fallbackToDestructiveMigration()
                    .build();
        }
        return assetsDatabase;
    }

    public abstract StockDao marketDao();
}
