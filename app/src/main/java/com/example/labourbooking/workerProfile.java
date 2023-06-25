package com.example.labourbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class workerProfile extends AppCompatActivity {
EditText t1,t2,t3,t4;

String name,phone,email,location;
private FirebaseAuth authProfile;
ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_profile);

        t1=findViewById(R.id.v1);
        t2=findViewById(R.id.v2);
        t3=findViewById(R.id.v3);
        t4=findViewById(R.id.v4);
        progressBar = new ProgressBar(workerProfile.this);

        authProfile =FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=authProfile.getCurrentUser();
        if(firebaseUser==null)
        {
            Toast.makeText(workerProfile.this,"details are not available",Toast.LENGTH_LONG).show();

        }
        else {
            progressBar.setVisibility(View.VISIBLE);
            showWorkerProfile(firebaseUser);
        }


   }
    private void showWorkerProfile(FirebaseUser firebaseUser){
        String userId=firebaseUser.getUid();
        DatabaseReference referenceProfile= FirebaseDatabase.getInstance().getReference("users");
      referenceProfile.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               HelperClass helperClass=snapshot.getValue(HelperClass.class);
              if(helperClass!=null){

                    email=helperClass.getEmail();
                    phone=helperClass.getPhonenumber();
                    location=helperClass.getLocation();
                  name = helperClass.getName();

                    t1.setText(name);
                    t2.setText(phone);
                    t3.setText(email);
                  t4.setText(location);
                  Log.d("workerProfile",name);
                  Log.d("workerProfile",phone);

               }
               progressBar.setVisibility(View.GONE);

               }


           @Override
            public void onCancelled(@NonNull DatabaseError error) {
Toast.makeText(workerProfile.this,"something went wrong",Toast.LENGTH_LONG).show();
               progressBar.setVisibility(View.GONE);

            }
        });

    }
//private void showWorkerProfile(FirebaseUser firebaseUser) {
//    //String displayName=firebaseUser.getDisplayName();
//    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("users");
//    referenceProfile.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//        @Override
//        public void onDataChange(@NonNull DataSnapshot snapshot) {
//            if (snapshot.exists()) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    HelperClass helperClass = dataSnapshot.getValue(HelperClass.class);
//                    if (helperClass != null) {
//                        phone = helperClass.getPhonenumber();
//                        email = helperClass.getEmail();
//                        location = helperClass.getLocation();
//                        t1.setText(name);
//                        t2.setText(phone);
//                        t3.setText(email);
//                        t4.setText(location);
//                    }
//                }
//            } else {
//                Toast.makeText(workerProfile.this, "Worker profile not found", Toast.LENGTH_LONG).show();
//            }
//        }
//
//        @Override
//        public void onCancelled(@NonNull DatabaseError error) {
//            Toast.makeText(workerProfile.this, "Something went wrong", Toast.LENGTH_LONG).show();
//        }
//    });
//}
//    private void showWorkerProfile(FirebaseUser firebaseUser) {
//        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("users");
//        referenceProfile.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    HelperClass helperClass = snapshot.getValue(HelperClass.class);
//                    if (helperClass != null) {
//                        name = helperClass.getName();
//                        phone = helperClass.getPhonenumber();
//                        email = helperClass.getEmail();
//                        location = helperClass.getLocation();
//                        t1.setText(name);
//                        t2.setText(phone);
//                        t3.setText(email);
//                        t4.setText(location);
//                    }
//                } else {
//                    Toast.makeText(workerProfile.this, "Worker profile not found", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(workerProfile.this, "Something went wrong", Toast.LENGTH_LONG).show();
//            }
//        });
// }



}