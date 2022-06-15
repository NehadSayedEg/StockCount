package com.example.stockcount.ui.ScanActivity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.stockcount.Database.StockDao;
import com.example.stockcount.Database.StockDatabase;
import com.example.stockcount.data.ItemStock;
import com.example.stockcount.data.Sheet;

import java.util.List;

public class ScanViewModel extends ViewModel {
    StockDao stockDao ;

    LiveData<List<ItemStock>> mAllStockItem;

    public ScanViewModel(){
        stockDao = StockDatabase.assetsDatabase.marketDao();
        //.getInstance(application).dao();
        mAllStockItem = stockDao.getAllStockItems();

    }


    LiveData<List<ItemStock>> getAllStockItems() {
        // mAllDes = assetsDao.getAlllocAndDesList() ;
        return mAllStockItem; }


}

