package com.example.labourbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;
import com.google.rpc.Help;

import java.util.ArrayList;

public class Electrician extends AppCompatActivity {
    SearchView searchView;
    RecyclerView recyclerView;
    //DatabaseReference reference;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference reference=database.getReference().child("users");
//    Query checkUserDatabase=reference.orderByChild("userType").equalTo("Electrician");

    ElectricianAdapter electricianAdapter;

    ArrayList<HelperClass> list;
    //    String[] electricianList=new String[]{"Richard","Anna","Leo","Ganesh","Suresh","Mahesh"};
//    String[] electricianLoc=new String[]{"Bangalore","Mangalore","Kundapura","Mysore","Hasan","Udupi"};
    ArrayList<HelperClass> searchList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electrician);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
//        reference= FirebaseDatabase.getInstance().getReference("users");

        searchView=findViewById(R.id.searchView);


//        for(int i=0;i<electricianList.length;i++){
//            ModelClass modelClass=new ModelClass();
//            modelClass.setElectricianName(electricianList[i]);
//            modelClass.setElectricianLoc(electricianLoc[i]);
//            arrayList.add(modelClass);
//        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        electricianAdapter = new ElectricianAdapter(this, list);
        // Toast.makeText(Electrician.this,list,Toast.LENGTH_SHORT).show();
        recyclerView.setAdapter(electricianAdapter);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list.clear();
                for (DataSnapshot childSnapshot : datasnapshot.getChildren()) {
                    HelperClass user = childSnapshot.getValue(HelperClass.class);

                    if(user.getUserType().equals("Electrician")) {
                        list.add(user);
                    }
//                    list.add(user);
                    Log.d("listDebug",user.getUserType());
                }
//                if (!list.isEmpty()) {
//                    electricianAdapter.notifyDataSetChanged();
//                }
                Log.d("listDebug","" + list.get(0));
                electricianAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchList=new ArrayList<>();
                if(query.length()>0){
                    for(int i=0;i<list.size();i++){
                        if(list.get(i).getLocation().toUpperCase().contains(query.toUpperCase())){
                            HelperClass helperClass=new HelperClass();
                            helperClass.setName(list.get(i).getName());
                            helperClass.setLocation(list.get(i).getLocation());
                            searchList.add(helperClass);
                        }
                    }
                    RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(Electrician.this);
                    recyclerView.setLayoutManager(layoutManager);
                    ElectricianAdapter electricianAdapter=new ElectricianAdapter(Electrician.this,searchList);
                    recyclerView.setAdapter(electricianAdapter);
                }
                else {
                    RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(Electrician.this);
                    recyclerView.setLayoutManager(layoutManager);
                    ElectricianAdapter electricianAdapter=new ElectricianAdapter(Electrician.this,list);
                    recyclerView.setAdapter(electricianAdapter);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList=new ArrayList<>();
                if(newText.length()>0){
                    for(int i=0;i<list.size();i++){
                        if(list.get(i).getLocation().toUpperCase().contains(newText.toUpperCase())){
                            HelperClass helperClass =new HelperClass();
                            helperClass.setName(list.get(i).getName());
                            helperClass.setLocation(list.get(i).getLocation());
                            searchList.add(helperClass);
                        }
                    }
                    RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(Electrician.this);
                    recyclerView.setLayoutManager(layoutManager);

                    ElectricianAdapter electricianAdapter=new ElectricianAdapter(Electrician.this,searchList);
                    recyclerView.setAdapter(electricianAdapter);

                }
                else {
                    RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(Electrician.this);
                    recyclerView.setLayoutManager(layoutManager);
                    ElectricianAdapter electricianAdapter=new ElectricianAdapter(Electrician.this,list);
                    recyclerView.setAdapter(electricianAdapter);
                }
                return false;
            }
        });
    }


}