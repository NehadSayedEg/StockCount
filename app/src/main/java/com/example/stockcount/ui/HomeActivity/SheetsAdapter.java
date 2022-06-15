package com.example.stockcount.ui.HomeActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stockcount.Database.StockDatabase;
import com.example.stockcount.R;
import com.example.stockcount.data.Sheet;
import com.example.stockcount.ui.ScanActivity.ScanActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SheetsAdapter extends RecyclerView.Adapter<SheetsAdapter.SheetsViewHolder>{
    private List<Sheet> sheetList;
    ExecutorService executor = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());


    public SheetsAdapter(List<Sheet> sheetList) {

        this.sheetList = sheetList;

    }


    @NonNull
    @Override
    public SheetsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new  SheetsViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.sheet_item , parent ,
                                false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SheetsViewHolder holder, int position) {
        holder.setSheets(sheetList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String shett = sheetList.get(position).sheetName;
                int sheetId = sheetList.get(position).getSheetId();

                Intent intent = new Intent(view.getContext() , ScanActivity.class);
                intent.putExtra("sheetId", sheetId);
                Log.e(" Item Clicked"+ sheetId , "");
                view.getContext().startActivity(intent);
          //    int pos =  holder.getLayoutPosition();
                //    Log.e(" getLayoutPosition" , pos + "");
               // sheetsAdapterListener.onSheetClicked(sheetList.get(position) ,position);
            }
        });
        holder.sheetDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {

                String name = sheetList.get(position).getSheetName();
                String date = sheetList.get(position).getSheetDate();
                int  sheetId = sheetList.get(position).getSheetId();

                LayoutInflater inflater = LayoutInflater.from(view.getContext());
                final View v = inflater.inflate(R.layout.admin_dialog, null);
                //  , R.style.AlertDialogStyleWithEditText
                AlertDialog alertDialog = new AlertDialog.Builder(view.getContext()).create();
                alertDialog.setTitle("Please enter  your userName & password");   //"Enter price Update" R.string.enter_price_update
                alertDialog.setIcon(R.drawable.person);
                alertDialog.getWindow().setBackgroundDrawableResource(R.color.orange9);
                alertDialog.setCancelable(false);
                // alertDialog.setMessage("Your Message Here");

                EditText userNameEt = (EditText) v.findViewById(R.id.userNameEt);
                EditText passwordEt = (EditText) v.findViewById(R.id.passEt);


                //getString(R.string.change_price_btn)
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, " submit" , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                     String userName = userNameEt.getText().toString();
                     String password = passwordEt.getText().toString();
                        Log.e( "userNameEt  " , userName);
                        Log.e( "passwordEt " , password);
                        if (userName.equals("admin") && password.equals("winadmin")){
                            executor.execute(() -> {
                                StockDatabase.getMarketDatabase(view.getContext()).marketDao().deleteSheet(sheetId);


                            });
                        }else {
                            Toast.makeText(v.getContext(), " please Enter Correct UserName & password", Toast.LENGTH_LONG).show();
                        }




                    }
                });

// getString(R.string.cancel_btn)
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,  "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });


                alertDialog.setView(v);
                alertDialog.show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return sheetList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class SheetsViewHolder extends RecyclerView.ViewHolder {
        TextView sheetName , sheetDate ;
        ImageView sheetDeleteBtn ;


         SheetsViewHolder(@NonNull View itemView) {
            super(itemView);
            sheetName  = itemView.findViewById(R.id.sheetName);
             sheetDate  = itemView.findViewById(R.id.sheetDate);
             sheetDeleteBtn = itemView.findViewById(R.id.deleteSheetBtn);

         }

         void setSheets(Sheet sheet){
             sheetName.setText(sheet.getSheetName());
             sheetDate.setText(sheet.getSheetDate());

         }
    }

//    public void searchSheets(final String searchKeyWord){
//        timer =new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                if(searchKeyWord.trim().isEmpty()){
//
//                    sheetList = searchSheetList;
//                }else {
//                    ArrayList<Sheet> temp =  new ArrayList<>();
//                    for (Sheet sheet : searchSheetList){
//                        if( sheet.getSheetName().toLowerCase().contains(searchKeyWord.toLowerCase())
//                        ||   sheet.getSheetName().toLowerCase().contains(searchKeyWord.toLowerCase())
//                        ){
//                            temp.add(sheet);
//                        }
//                    }
//                    searchSheetList = temp ;
//                }
//                new Handler(Looper.getMainLooper()).post(new Runnable() {
//                    @Override
//                    public void run() {
//                        notifyDataSetChanged();
//                    }
//                });
//
//            }
//        } ,500);
//
//    }
//
//    public void cancelTimer(){
//        if(timer != null){
//            timer.cancel();
//        }
//    }
}
