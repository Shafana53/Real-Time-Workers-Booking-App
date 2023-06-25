package com.example.labourbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.labourbooking.databinding.ActivityCarpenterBinding;
import com.example.labourbooking.databinding.ActivityElectricianBinding;
import com.example.labourbooking.databinding.ActivityElectricianProfileBinding;
import com.example.labourbooking.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class ElectricianProfile extends AppCompatActivity {
    ActivityElectricianProfileBinding binding;
    TextView name,location;

    Button b1,b2;

    TextView phoneNumber,availability;

    FirebaseDatabase database=FirebaseDatabase.getInstance();

    DatabaseReference reference;

    ElectricianAdapter electricianAdapter;
    private FirebaseAuth authProfile;

    FirebaseUser firebaseUser;
    private DatePickerDialog datePickerDialog;
    private Button datePickerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityElectricianProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initDatePicker();

        String username =getIntent().getStringExtra("name");
        String userlocation=getIntent().getStringExtra("location");
        binding.name.setText(username);
        binding.location.setText(userlocation);

        binding.datePickeBtn.setText(getTodaysDate());

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(!username.isEmpty()){
//                    electricianProfile(username);
//                }
//                else {
//                    Toast.makeText(ElectricianProfile.this, "no data found", Toast.LENGTH_SHORT).show();
//                }
                Log.d("ElectricianProfile","Reached");
                reference=FirebaseDatabase.getInstance().getReference("users");
                reference.orderByChild("name").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot usersnapshot : dataSnapshot.getChildren()){
                            String uid=dataSnapshot.getKey();
                            Log.d("UID",uid);
                            //String name= usersnapshot.child("name").getValue(String.class);
                            //Log.d("ElectricianProfileName",name);
                            String pno= usersnapshot.child("phonenumber").getValue(String.class);

                            binding.phonenumber.setText(pno);
                            }
                        }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        binding.booknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(binding.phonenumber.getText().toString(), null, "YOU ARE BOOKED", null, null);
            }
        });



    }

    private String getTodaysDate() {
        Calendar cal=Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        month=month+1;
        int day=cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day,month,year);

    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month=month+1;
                String date=makeDateString(day,month,year);
                binding.datePickeBtn.setText(date);

            }
        };
        Calendar cal=Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DAY_OF_MONTH);

        int style= AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog=new DatePickerDialog(this,style,dateSetListener,year,month,day);


    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day +" "+ year;

    }

    @SuppressLint("SuspiciousIndentation")
    private String getMonthFormat(int month) {
        if(month==1)
            return "JAN";
        if(month==2)
            return "FEB";
        if(month==3)
            return "MAR";
        if(month==4)
            return "APR";
        if(month==5)
            return "MAY";
        if(month==6)
            return "JUN";
        if(month==7)
            return "JUL";
        if(month==8)
            return "AUG";
        if(month==9)
            return "SEP";
        if(month==10)
            return "OCT";
        if(month==11)
            return "NOV";
        if(month==12)
            return "DEC";

           return "JAN";


    }

    private void electricianProfile(String username) {
        //firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        reference=FirebaseDatabase.getInstance().getReference("users");
         reference.orderByChild("name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
       // reference.child(firebaseUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            //reference.orderByChild("name").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        DataSnapshot dataSnapshot=task.getResult();
                        String phonenumber=String.valueOf(dataSnapshot.child("phonenumber").getValue());
                        binding.phonenumber.setText(phonenumber);
                    }
                    else {
                        Toast.makeText(ElectricianProfile.this, "user does not exist", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(ElectricianProfile.this, "Failed to read", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }
}
