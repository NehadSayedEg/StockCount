package com.example.stockcount.data;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "stock_table")
public class Stock implements Serializable {


    @NonNull
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "barcode")
    private String barcode ;


    @ColumnInfo(name = "description")
    private String description ;





//    @ColumnInfo(name = "qty")
//    private double upqty ;



    @ColumnInfo(name = "qty")
    private double qty ;


    @ColumnInfo(name = "status")
    private boolean status ;


    @NonNull
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(@NonNull String barcode) {
        this.barcode = barcode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }




    @Nullable
    @Override
    public String toString() {
        return "Market{" +
                "barcode='" + barcode + '\'' +
                ", location='" + description + '\'' +
                ", status=" + status +
                ", qty=" + qty +

                '}';
    }
}
