package com.example.stockcount.ui.ScanActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.example.stockcount.Database.StockDatabase;
import com.example.stockcount.R;
import com.example.stockcount.data.ItemStock;
import com.example.stockcount.data.Stock;
import com.example.stockcount.databinding.ActivityScanBinding;
import com.example.stockcount.ui.ExportSheetsActivity.ExportSheetsActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScanActivity extends AppCompatActivity {
    private ActivityScanBinding binding;
    ScanViewModel scanViewModel;
    Context context ;
    String barcode  , description;
    String barCount ;
    int itemSheetIdCount;
    double qtyCount;
    int sheetId ;
    private List<ItemStock> itemStockList = new ArrayList<>();
    private List<Stock> stockList = new ArrayList<>();
    private List<ItemStock> itemCountList = new ArrayList<>();

    ExecutorService executor = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());

    private final Handler handlerTextWacher = new Handler();
    ScanAdapter scanAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_scan);
        binding = ActivityScanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        scanViewModel = new ViewModelProvider(this).get(ScanViewModel.class);



         sheetId  =  getIntent().getExtras().getInt("sheetId");
            Log.e(" Scan Activity" , sheetId +"");


        // Recyclerview & Adapter
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        binding.scanRV.setHasFixedSize(true);
        binding.scanRV.setLayoutManager(layoutManager);
        binding.scanRV.scrollToPosition(0);

        scanViewModel.getAllStockItems().observe(this, new Observer<List<ItemStock>>() {
            @Override
            public void onChanged(List<ItemStock> itemStocks) {
                Log.e( "no of sheets stored" , itemStocks.size() +"live Data" );
                itemStockList.clear();

                for (int i = 0; i < itemStocks.size(); i++) {
                    if(itemStocks.get(i).getSheetIdItem() ==sheetId){
                        itemStockList.add(itemStocks.get(i));
                        Log.i("Sheet id " + itemStocks.get(i).getSheetIdItem(), "");
                        Log.i("SheetId from activty" + sheetId , "");

                    }
                    String  bar = itemStocks.get(i).getBarcode();
                    String des = itemStocks.get(i).getDescription();



                     scanAdapter  = new ScanAdapter(itemStockList);
                     binding.scanRV.setAdapter(scanAdapter);

                    Log.i("bar :" + bar , "");
                    Log.i("des :" + des , "");

                }
            }
        });


        //set the edit text focus
        binding.barcodeEt.requestFocus();
        binding.barcodeEt.setFocusable(true);
        binding.barcodeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(! s.toString().isEmpty()){
                    Log.v("on text change  Text  ", s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(! s.toString().isEmpty()){

                    handlerTextWacher.removeCallbacksAndMessages(null);
                    handlerTextWacher.postDelayed(() -> {
                        Log.e("" , s.toString());
                       barcode = s.toString();
                        Log.e("barcode" ,  barcode);
                        CheckIncludeDatabase(barcode);
                    }, 200);

                }
            }
        });


        binding.exportReportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ScanActivity.this, ExportSheetsActivity.class);
                intent.putExtra("sheetId", sheetId);
                startActivity(intent);
            }
        });

    }

    private void CheckIncludeDatabase( String barcode) {
        executor.execute( () -> {

            //Background work here
            stockList.clear();
            Log.e("stock list size" , stockList.size()+"");
            stockList.addAll( StockDatabase.getMarketDatabase(getApplicationContext()).marketDao().searchMarketByBarcode(barcode));


            handler.post( () ->{
                Log.e("stock list size" , stockList.size()+"");

                if (stockList.size() == 0){
                    Log.e("barcode " , " not include data");
                    alertDialog();

                }

            });



            for (int i = 0 ; i< stockList.size() ; i++ ) {

                String  barcodeTxt = stockList.get(i).getBarcode() ;
                description = stockList.get(i).getDescription() ;
                if (stockList.size() == 0){
                    Log.e("barcode " , " not include data");

                }else {
                    Log.e("barcode " , " include data");

                    insertORCount(barcodeTxt , sheetId);

                }


            }




            handler.post( () ->{




            });

        });

    }



    private void alertDialog(){


        AlertDialog dialog = new MaterialAlertDialogBuilder(ScanActivity.this, R.style.AlertDialogTheme).setTitle(R.string.deleteTitle)
                .setMessage("This Barcode not include your inserted data")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setNegativeButton(R.string.cancelBtn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();

    }

    private void insertORCount(String barcode ,  int sheetId){

        Log.e(" enter ", "insertORCount");

        executor.execute( () -> {

            //Background work here
            itemCountList.clear();
            itemCountList.addAll( StockDatabase.getMarketDatabase(getApplicationContext()).marketDao().getStockItem(barcode ,sheetId));
            Log.e(" itemCount List", itemCountList.size()+ "");

            if ( itemCountList.size() == 0){
                Log.e("  contion true ", itemCountList.size()+ "");
                Log.e(" will insert  ", description);
                ItemStock itemStock = new ItemStock();
                itemStock.setBarcode(barcode);
                itemStock.setDescription(description);
                itemStock.setQty(1);
                itemStock.setSheetIdItem(sheetId);
                StockDatabase.getMarketDatabase(getApplicationContext()).marketDao().insertItem(itemStock);
            }
            else {

                for (int i = 0 ; i< itemCountList.size() ; i++ ) {


                    String  barcodeTxt = itemCountList.get(i).getBarcode() ;
                    String  desTxt = itemCountList.get(i).getDescription() ;
                    int sheetId1 = itemCountList.get(i).getSheetIdItem();
                    double qty = itemCountList.get(i).getQty();
                    double updateQTY = qty + 1;

                    Log.e(" barcodeTxt ", barcodeTxt);
                    Log.e(" desTxt ", desTxt);
                    Log.e(" sheetId1 ", sheetId1 + "");
                    Log.e(" Qty ", qty + "");

                    Log.e("updateQTY before update", updateQTY + "");


                    StockDatabase.getMarketDatabase(getApplicationContext()).marketDao().updateStockItem(barcodeTxt ,updateQTY, sheetId);

                    handler.post( () ->{
                        scanAdapter.notifyDataSetChanged();
                    });
                }

            }




        });


        handler.post( () -> {
            binding.barcodeEt.setText("");
            binding.barcodeEt.requestFocus();
            binding.barcodeEt.setFocusable(true);


        });

    }






}