package com.example.stockcount.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.stockcount.data.Stock;

import java.util.List;
@Dao
public interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMarketList(Stock stock);

    @Query("SELECT * FROM  stock_table")
    List<Stock> getAllMarket();


    @Query("SELECT * from stock_table WHERE barcode = :barcode ")
    List<Stock> searchMarketByBarcode(String barcode );


//    @Query("UPDATE stock_table  SET  price =:price WHERE  barcode  = :barcode  ")
//    void  updatePrice(String barcode  , double price);
//
//    @Query("UPDATE market_table  SET  updateprice =:salePrice WHERE  barcode  = :barcode  ")
//    void  updateSalePrice(String barcode  , double salePrice);


    @Query("DELETE  FROM  stock_table")
    public void DeleteAllMarket();



    @Query("SELECT * FROM  stock_table")
    LiveData<List<Stock> > getAllMarketPrices();



}
