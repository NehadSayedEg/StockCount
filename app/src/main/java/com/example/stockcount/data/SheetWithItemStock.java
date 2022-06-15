package com.example.stockcount.data;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;


public class SheetWithItemStock {
    @Embedded
        public Sheet sheet;
        @Relation(
                parentColumn = "sheet_id",
                entityColumn = "sheet_id_item"

        )
        public List<ItemStock> itemStockList;

        public SheetWithItemStock(Sheet sheet, List<ItemStock> itemStockList) {
            this.sheet = sheet;
            this.itemStockList = itemStockList;
        }

    }
