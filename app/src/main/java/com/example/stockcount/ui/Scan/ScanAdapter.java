package com.example.stockcount.ui.ScanActivity;

import android.content.DialogInterface;
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
import com.example.stockcount.data.ItemStock;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ScanAdapter extends RecyclerView.Adapter<ScanAdapter.ScanViewHolder>{
    private List<ItemStock> itemStockList;
    ExecutorService executor = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());


    public ScanAdapter(List<ItemStock> itemStocks) {
        this.itemStockList = itemStocks;
    }


    @NonNull
    @Override
    public ScanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ScanViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.scan_item , parent ,
                                false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ScanViewHolder holder, int position) {
        holder.setStockItems(itemStockList.get(position));


        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String barcode = itemStockList.get(position).getBarcode();
                String des = itemStockList.get(position).getDescription();
                double qty = itemStockList.get(position).getQty();

                AlertDialog dialog = new MaterialAlertDialogBuilder(view.getContext(), R.style.AlertDialogTheme).setTitle("are you sure  you want to delete")
                        .setMessage( des)
                        .setPositiveButton("Delete",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        executor.execute( () -> {

                                            StockDatabase.getMarketDatabase(view.getContext()).marketDao().deleteStockItem(barcode);

                                        });
                                     itemStockList.remove(position);
                                     notifyDataSetChanged();

                                    }
                                }).setNegativeButton(R.string.cancelBtn, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();


            }
        });


        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String barcode = itemStockList.get(position).getBarcode();
                String des = itemStockList.get(position).getDescription();
                double qty = itemStockList.get(position).getQty();
                int  sheetId  =itemStockList.get(position).getSheetIdItem();

                LayoutInflater inflater = LayoutInflater.from(view.getContext());
                final View v = inflater.inflate(R.layout.update_qty_dialog, null);
             //  , R.style.AlertDialogStyleWithEditText
                AlertDialog alertDialog = new AlertDialog.Builder(view.getContext()).create();
                alertDialog.setTitle(" set the incerment value");   //"Enter price Update" R.string.enter_price_update
                alertDialog.setIcon(R.drawable.ic_baseline_refresh_24);
                alertDialog.getWindow().setBackgroundDrawableResource(R.color.orange9);
                alertDialog.setCancelable(false);
                // alertDialog.setMessage("Your Message Here");

                 EditText fileNameEt = (EditText) v.findViewById(R.id.updateET);

                //getString(R.string.change_price_btn)
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, " Add" , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e( "Entered updated price " , fileNameEt +"");

                        double updatePrice =  qty + Double.parseDouble(fileNameEt.getText().toString());
                        Log.e( "Entered updated price " , updatePrice +"");


                        executor.execute(() -> {
                            StockDatabase.getMarketDatabase(view.getContext()).marketDao().updateStockItem(barcode , updatePrice , sheetId);


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


                alertDialog.setView(v);
                alertDialog.show();

            }
        });

        holder.subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String barcode = itemStockList.get(position).getBarcode();
                String des = itemStockList.get(position).getDescription();
                double qty = itemStockList.get(position).getQty();
                int sheetId = itemStockList.get(position).getSheetIdItem();

                LayoutInflater inflater = LayoutInflater.from(view.getContext());
                final View v = inflater.inflate(R.layout.update_qty_dialog, null);
                //  , R.style.AlertDialogStyleWithEditText
                AlertDialog alertDialog = new AlertDialog.Builder(view.getContext()).create();
                alertDialog.setTitle(" set the decerment value");   //"Enter price Update" R.string.enter_price_update
                alertDialog.setIcon(R.drawable.ic_baseline_refresh_24);
                alertDialog.getWindow().setBackgroundDrawableResource(R.color.orange9);
                alertDialog.setCancelable(false);
                // alertDialog.setMessage("Your Message Here");

                EditText fileNameEt = (EditText) v.findViewById(R.id.updateET);

                //getString(R.string.change_price_btn)
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, " subtract" , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e( "Entered updated price " , fileNameEt +"");

                        double updatePrice =  qty - Double.parseDouble(fileNameEt.getText().toString());
                        if(updatePrice < 0){
                            Toast.makeText(view.getContext(), "qty will be -ve value ", Toast.LENGTH_LONG).show();
                        }else {

                            Log.e( "Entered updated price " , updatePrice +"");


                            executor.execute(() -> {
                                StockDatabase.getMarketDatabase(view.getContext()).marketDao().updateStockItem(barcode , updatePrice , sheetId);
                            });
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
        return itemStockList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ScanViewHolder extends RecyclerView.ViewHolder {
        TextView   barcode  , des , qty ;
        ImageView addBtn , deleteBtn , subBtn ;


        ScanViewHolder(@NonNull View itemView) {
            super(itemView);
            barcode  = itemView.findViewById(R.id.itemBarcode);
            des  = itemView.findViewById(R.id.itemDes);
            qty = itemView.findViewById(R.id.itemQty);
            addBtn = itemView.findViewById(R.id.addBtn);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
            subBtn = itemView.findViewById(R.id.subBtn);

        }

        void setStockItems(ItemStock itemStock){
            barcode.setText(itemStock.getBarcode());
            des.setText(itemStock.getDescription());
           // qty.setText(Double.toString(itemStock.getQty()));
            qty.setText(String.format("%.2f", itemStock.getQty()));
        }
    }
}

