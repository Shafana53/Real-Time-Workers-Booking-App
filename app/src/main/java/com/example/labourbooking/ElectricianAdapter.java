package com.example.labourbooking;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class ElectricianAdapter extends RecyclerView.Adapter<ElectricianAdapter.MyHolder>  {
    Context context;
    ArrayList<HelperClass> list;

    public ElectricianAdapter(Context context, ArrayList<HelperClass> list) {
        this.context = context;
        this.list = list;
//        layoutInflater=LayoutInflater.from(context);
    }

//    LayoutInflater layoutInflater;
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_file,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        HelperClass user=list.get(position);
        holder.electricianName.setText(user.getName());
        holder.electricianLoc.setText(user.getLocation());

    }

    @Override
    public int getItemCount() {

       return list.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView electricianName,electricianLoc;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            electricianName=itemView.findViewById(R.id.txt1);
            electricianLoc=itemView.findViewById(R.id.txt2);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
          //  int position=getBindingAdapterPosition();
           //Toast.makeText(context,"position",Toast.LENGTH_SHORT).show();
           Intent intent=new Intent(context,ElectricianProfile.class);
           intent.putExtra("name", electricianName.getText().toString()) ;
           intent.putExtra("location", electricianLoc.getText().toString());
           context.startActivity(intent);

        }
    }
}
