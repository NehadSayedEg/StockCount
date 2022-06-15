package com.example.stockcount.data;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import static androidx.room.ForeignKey.CASCADE;


import java.io.Serializable;

 @Entity(tableName = "stock_item_table")
//@Entity(foreignKeys = @ForeignKey(entity = Sheet.class,
//        parentColumns = "sheet_id",
//        childColumns = "sheet_id_item",
//        onDelete = CASCADE))

public class ItemStock implements Serializable {
     @PrimaryKey(autoGenerate = true)
     @ColumnInfo(name = "itemId")
     private int itemId ;



     @NonNull
   // @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "barcode")
    private String barcode ;


    @ColumnInfo(name = "sheet_id_item")
    private int sheetIdItem ;



    @ColumnInfo(name = "description")
    private String description ;



    @ColumnInfo(name = "qty")
    private double qty ;

    public ItemStock() {
    }

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

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public int getSheetIdItem() {
        return sheetIdItem;
    }

    public void setSheetIdItem(int sheetIdItem) {
        this.sheetIdItem = sheetIdItem;
    }

     public int getItemId() {
         return itemId;
     }

     public void setItemId(int itemId) {
         this.itemId = itemId;
     }

     @Override
    public String toString() {
        return "ItemStock{" +
                "barcode='" + barcode + '\'' +
                ", description='" + description + '\'' +
                ", qty=" + qty +
                '}';
    }
}
