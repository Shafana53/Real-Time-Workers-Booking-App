package com.example.labourbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.checkerframework.checker.lock.qual.EnsuresLockHeld;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    TextView alreadyHaveaccount;
    EditText inputName,inputUserType,inputEmail,inputPhonenumber,inputLocation,inputPassword,inputConfirmPassword;
    Button btnRegister;
    String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    String name;
    String location;
    String phonenumber;
    String password;
    String userType;
    String email;
//    FirebaseUser mUser;

//    FirebaseDatabase database;
//    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        alreadyHaveaccount=findViewById(R.id.alreadyHaveaccount);
        inputName=findViewById(R.id.inputName);
        inputUserType=findViewById(R.id.inputUserType);
        inputEmail=findViewById(R.id.inputEmail);
        inputPhonenumber=findViewById(R.id.inputPhonenumber);
        inputLocation=findViewById(R.id.inputLocation);
        inputPassword=findViewById(R.id.inputPassword);
        inputConfirmPassword=findViewById(R.id.inputConfirmPassword);
        btnRegister=findViewById(R.id.btnRegister);

        progressDialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
//        mUser=mAuth.getCurrentUser();


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);



        alreadyHaveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this,MainActivity.class));
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               name=inputName.getText().toString().trim();
                email=inputEmail.getText().toString().trim();
                phonenumber=inputPhonenumber.getText().toString().trim();
                 location=inputLocation.getText().toString().trim();
                 userType=inputUserType.getText().toString().trim();
                 password=inputPassword.getText().toString().trim();
                String confirmPassword=inputConfirmPassword.getText().toString().trim();

                if(name.isEmpty())
                {
                    inputName.setError("Enter Username");

                }else if(email.isEmpty()) {
                    inputEmail.setError("Enter Email");

                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    inputEmail.setError("Email is not Valid");
                }
                else if(password.isEmpty())
                {
                    inputPassword.setError("Enter Password");
                }
                else if(password.length()<6)
                {
                    Toast.makeText(Register.this,"Password length must be greater than 6",Toast.LENGTH_SHORT).show();
                }
                else if(!password.equals(confirmPassword)){
                    inputConfirmPassword.setError("Password Not Match Both Field");
                }
                else {
                    registerUser(email,password);
                }

//                database=FirebaseDatabase.getInstance();
//                reference=database.getReference("users");
 //               PerForAuth();


            }
        });
    }
//    private void PerForAuth(){
//        String name=inputName.getText().toString().trim();
//        String email=inputEmail.getText().toString().trim();
//        String phonenumber=inputPhonenumber.getText().toString().trim();
//        String location=inputLocation.getText().toString().trim();
//        String userType=inputUserType.getText().toString().trim();
//        String password=inputPassword.getText().toString().trim();
//        String confirmPassword=inputConfirmPassword.getText().toString().trim();
//
//        HelperClass helperClass=new HelperClass(name,userType,email,phonenumber,location,password,mAuth.getUid());
//        reference.child(mAuth.getUid()).setValue(helperClass);
//
//        if (!email.matches(emailPattern))
//        {
//            inputEmail.setError("Enter Context Email");
//        } else if (password.isEmpty() || password.length()<6)
//        {
//            inputPassword.setError("Enter Proper Password");
//        } else if (!password.equals(confirmPassword))
//        {
//            inputConfirmPassword.setError("Password Not Match Both Field");
//        }else
//        {
//            progressDialog.setMessage("Please wait while registration");
//            progressDialog.setTitle("Registration");
//            progressDialog.setCanceledOnTouchOutside(false);
//            progressDialog.show();
//
//            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if (task.isSuccessful()){
//                        progressDialog.dismiss();
//                        sendUserToNextActivity();
//                        Toast.makeText(Register.this,"Registration Successful",Toast.LENGTH_SHORT).show();
//                    }else {
//                        progressDialog.dismiss();
//                        Toast.makeText(Register.this," "+task.getException(),Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }
//    }
//
//    private void sendUserToNextActivity() {
//        Intent intent=new Intent(Register.this,MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//    }
    private void registerUser(String email,String password)
    {
      progressDialog.setMessage("Plese Wait");
      progressDialog.show();
      mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
              if(task.isSuccessful()){
                  progressDialog.dismiss();
                  FirebaseUser mUser=mAuth.getCurrentUser();
                  String email= mUser.getEmail();
                  String uid=mUser.getUid();
//                  HashMap<Object,String> hashMap=new HashMap<>();
//                  hashMap.put("uid",uid);
//                  hashMap.put("email",email);
//                  hashMap.put("name",name);
//                  hashMap.put("location",location);
//                  hashMap.put("userType",userType);
//                  hashMap.put("phonenumber",phonenumber);
//                  hashMap.put("password",password);
                  HelperClass user = new HelperClass(name, userType, email, phonenumber, location, password, uid);


                  FirebaseDatabase database=FirebaseDatabase.getInstance();
                  DatabaseReference reference= database.getReference("users");

                  reference.child(mAuth.getUid()).setValue(user);

                  startActivity(new Intent(Register.this,MainActivity.class));

              }
              else{
                  Toast.makeText(Register.this,"Failed",Toast.LENGTH_SHORT).show();
                  progressDialog.dismiss();
              }

          }
      }).addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e) {
              Toast.makeText(Register.this,""+e,Toast.LENGTH_SHORT).show();
              progressDialog.dismiss();
          }
      });
    }

}