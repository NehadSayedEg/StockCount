package com.example.stockcount.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity(tableName = "sheet_table")
public class Sheet implements Serializable {


    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "sheet_id")
    private int sheetId ;


    @ColumnInfo(name = "sheet_name")
    public String sheetName ;



    @ColumnInfo(name = "sheet_date")
    public String sheetDate ;


    public String getSheetName() {
        return sheetName;
    }

    public String setSheetName(String sheetName) {
        this.sheetName = sheetName;
        return sheetName;
    }

    public String getSheetDate() {
        return sheetDate;
    }

    public String setSheetDate(String sheetDate) {
        this.sheetDate = sheetDate;
        return sheetDate;
    }

    public int getSheetId() {
        return sheetId;
    }

    public void setSheetId(int sheetId) {
        this.sheetId = sheetId;
    }




    @Nullable
    @Override
    public String toString() {
        return "Market{" +
                "sheet='" + sheetId + '\'' +
                ", sheet_name='" + sheetName + '\'' +
                ", sheet_date=" + sheetDate +

                '}';
    }
}
