package com.example.labourbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class selectUserType extends AppCompatActivity {
TextView name;
FirebaseAuth auth = FirebaseAuth.getInstance();
FirebaseDatabase database = FirebaseDatabase.getInstance();
Button b1,b2;
Button btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_select_user_type);
      b1=findViewById(R.id.button1);
      b2=findViewById(R.id.button2);
      auth=FirebaseAuth.getInstance();
      btnLogout=findViewById(R.id.btnLogout);

      btnLogout.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              auth.signOut();
              Intent intent=new Intent(selectUserType.this,MainActivity.class);
              startActivity(intent);
              finish();
              Toast.makeText(selectUserType.this,"LOGOUT SUCCESSFUL",Toast.LENGTH_SHORT).show();
          }
      });


      b1.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Log.d("userTypeDebug","worker button clicked");
              database.getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                  @Override
                  public void onDataChange(@NonNull DataSnapshot snapshot) {
                      for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                          HelperClass temp = snapshot1.getValue(HelperClass.class);

                          if(temp.getUid().equals(auth.getUid()))
                          {
                          Log.d("userTypeDebug",auth.getUid());
                              Log.d("userTypeDebug",temp.getUserType());
                              if(!temp.getUserType().equals("Hirer"))
                              {
                                  Intent intent=new Intent(selectUserType.this, workerHome.class);
                                  startActivity(intent);
                                  break;
                              }
                              else
                              {
                                  Toast.makeText(selectUserType.this, "Only Worker has access to this page", Toast.LENGTH_SHORT).show();
                              }
                          }

                      }
                  }

                  @Override
                  public void onCancelled(@NonNull DatabaseError error) {

                  }
              });
//              startActivity(intent);
          }
      });
      b2.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Log.d("userTypeDebug","Hirer button clicked");

              database.getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                  @Override
                  public void onDataChange(@NonNull DataSnapshot snapshot) {
                      for(DataSnapshot snapshot1 : snapshot.getChildren()) {

                          HelperClass temp = snapshot1.getValue(HelperClass.class);

                          if(temp.getUid().equals(auth.getUid()))
                          {
                              Log.d("userTypeDebug",temp.getUserType());
                              if(temp.getUserType().equals("Hirer"))
                              {
                                  Intent intent=new Intent(selectUserType.this,Home.class);
                                  startActivity(intent);
                                  break;
                              }
                              else
                              {
                                  Toast.makeText(selectUserType.this, "Only Hirer has access to this page", Toast.LENGTH_SHORT).show();
                              }
                          }
                      }
                  }

                  @Override
                  public void onCancelled(@NonNull DatabaseError error) {

                  }
              });
//              startActivity(intent);
          }
      });

    }
}