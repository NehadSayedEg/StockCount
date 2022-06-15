
package com.example.stockcount.ui.HomeActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.stockcount.Database.StockDatabase;
import com.example.stockcount.R;
import com.example.stockcount.data.Sheet;
import com.example.stockcount.databinding.ActivityHomeBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.example.stockcount.ui.HomeActivity.HomeActivity;


public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    HomeViewModel homeViewModel;

    ExecutorService executor = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());
    SheetsAdapter sheetsAdapter ;
    List<Sheet> sheetList  = new ArrayList<>();
    Activity activity ;
    Context context ;
    String searchSheetName ;
    List<Sheet> searchSheetList  = new ArrayList<>();
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   setContentView(R.layout.activity_home);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding.sheetRV.setLayoutManager(new StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL));
        sheetsAdapter = new SheetsAdapter(sheetList );
        binding.sheetRV.setAdapter(sheetsAdapter);
        binding.sheetRV.setHasFixedSize(true);
       // binding.sheetRV.setLayoutManager(layoutManager);
        binding.sheetRV.scrollToPosition(0);
//        scanAdapter = new ScanAdapter(assetslList ,activity  ,context);
//        binding.scanRecyclerView.setAdapter(scanAdapter);

       homeViewModel.getAllSheets().observe(this, new Observer<List<Sheet>>() {
           @Override
           public void onChanged(List<Sheet> sheets) {
            //   Log.e( "no of sheets stored" , sheets.get(0).getSheetName()+"" );
               Log.e( "no of sheets stored" , sheets.size() +"live Data" );

               sheetList.clear();

               for (int i = 0; i < sheets.size(); i++) {
                   String  sheetName = sheets.get(i).getSheetName();
                   String sheetDate = sheets.get(i).getSheetDate();

                       sheetList.add(sheets.get(i));
                       Log.i("Sheets  List" + sheetList.size() , "");

                   }

               //  sheetsAdapter  = new SheetsAdapter(sheetList , this);
               binding.sheetRV.setAdapter(sheetsAdapter);






               }
       });


       binding.searchET.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
           }

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void afterTextChanged(Editable s) {

               if(sheetList.size() != 0){
                   searchSheetName = s.toString();
//                   searchSheet(searchSheetName);
                  // sheetsAdapter.searchSheets(s.toString());
               }

           }
       });





        binding.addFileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog();

            }
        });


    }


    private void alertDialog(){
        LayoutInflater inflater = LayoutInflater.from(this);
        final View view = inflater.inflate(R.layout.dialog_layout, null);
        AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
        alertDialog.setTitle(" Enter file name");   //"Enter price Update" R.string.enter_price_update
        alertDialog.setIcon(R.drawable.file);
        alertDialog.setCancelable(false);
        // alertDialog.setMessage("Your Message Here");


        final EditText fileNameEt = (EditText) view.findViewById(R.id.etComments);
        //getString(R.string.change_price_btn)
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, " Add" , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String fileName = fileNameEt.getText().toString();
                Log.e( "Entered name" , fileName);


                executor.execute(() -> {
//                    String Date = new SimpleDateFormat("EEEE ,dd MMMM yyyy HH: mm a" ,
//                            Locale.getDefault()).format(new Date());

                    Calendar c = Calendar.getInstance();
                    System.out.println("Current time => "+c.getTime());
                    SimpleDateFormat df = new SimpleDateFormat("EEEE ,dd MMMM yyyy HH: mm a");
                    String Date = df.format(c.getTime());

                    Log.e( "Entered date " , Date);

                    Sheet sheet = new Sheet();
                    String name = sheet.setSheetName(fileName);
                    String date = sheet.setSheetDate(Date);

                    StockDatabase.getMarketDatabase(getApplicationContext()).marketDao().insertFile(sheet);

                    //Background work here
                    handler.post(() -> {
                        //UI Thread work here
                    });
                });

            }
        });

// getString(R.string.cancel_btn)
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,  "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });


        alertDialog.setView(view);
        alertDialog.show();
    }

    public void searchSheet( String sheetName){

        executor.execute(() -> {
            searchSheetList.clear();

         searchSheetList.addAll(StockDatabase.getMarketDatabase(getApplicationContext()).marketDao().searchSheetByName(sheetName));
         if(searchSheetList.size() > 0){
             Log.e(" search list ", searchSheetList.size() +"");

             sheetList.clear();
             Log.e(" search list ", sheetList.size() +"");

             sheetList.addAll(StockDatabase.getMarketDatabase(getApplicationContext()).marketDao().searchSheetByName(sheetName));

             Log.e(" search list ll", sheetList.size() +"");

             handler.post(() -> {

                 //UI Thread work here

             });
         }


        });

    }



}