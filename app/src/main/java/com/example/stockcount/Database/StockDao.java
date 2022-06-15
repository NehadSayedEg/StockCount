package com.example.stockcount.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.stockcount.data.ItemStock;
import com.example.stockcount.data.Sheet;
import com.example.stockcount.data.Stock;

import java.util.Collection;
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


    @Query("SELECT * FROM  sheet_table")
    LiveData<List<Sheet>> getAllSheets();



    @Query("SELECT * FROM  sheet_table ORDER BY sheet_id DESC ")
      LiveData < List<Sheet>>getAllFiles();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFile(Sheet sheetFile);

    @Delete
    void deleteFile(Sheet sheetFile);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(ItemStock itemStock);


    @Query("SELECT * FROM  stock_item_table")
    LiveData<List<ItemStock>> getAllStockItems();
    @Query("SELECT * FROM  stock_item_table")
    List<ItemStock> getItems();

    @Query("DELETE  FROM  sheet_table WHERE sheet_id = :sheetId")
    void deleteSheet(int sheetId);


    @Query("DELETE  FROM  stock_item_table WHERE barcode = :barcode")
   void deleteStockItem(String barcode);

    @Query("SELECT * FROM  stock_item_table WHERE barcode = :barcode AND sheet_id_item = :sheetId")
    List<ItemStock> getStockItem(String barcode , int sheetId);

@Query(" UPDATE stock_item_table SET qty =:updateQty WHERE barcode =:barcode AND  sheet_id_item = :sheetID")
    void updateStockItem(String barcode , double updateQty   ,int sheetID );


    @Query("SELECT * FROM  stock_item_table WHERE   sheet_id_item = :sheetId")
    List<ItemStock> getItemsBySheetId(int sheetId);
    @Query("SELECT * FROM  sheet_table WHERE   sheet_name = :sheetName")
    List<Sheet> searchSheetByName(String sheetName);


}
