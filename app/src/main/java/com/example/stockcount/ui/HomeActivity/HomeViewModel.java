package com.example.stockcount.ui.HomeActivity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.stockcount.Database.StockDao;
import com.example.stockcount.Database.StockDatabase;
import com.example.stockcount.data.Sheet;

import java.util.List;

public class HomeViewModel extends ViewModel {
    StockDao stockDao ;

    LiveData<List<Sheet>> mAllSheets;

    public HomeViewModel(){
        stockDao = StockDatabase.assetsDatabase.marketDao();
        //.getInstance(application).dao();
        mAllSheets = stockDao.getAllSheets();

    }


    LiveData<List<Sheet>> getAllSheets() {
        // mAllDes = assetsDao.getAlllocAndDesList() ;
        return mAllSheets; }



//    public void insertDescription(DescriptionEntity descriptionEntity) { assetsDao.insertDescription(descriptionEntity); }
//    public void insertLocation(LocationEntity locationEntity) { assetsDao.insertLocation(locationEntity); }
//    public void insertDes(AddDescription addDescription) { assetsDao.insertDes(addDescription); }
//
//    public void insertAsset(AssetModel assetModel) { assetsDao.insertAsset(assetModel); }


}


