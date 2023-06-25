package com.example.labourbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.labourbooking.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    //ActivityMainBinding binding;
    TextView createNewAccount,forgotPassword;
    EditText inputEmail,inputPassword;
    Button btnLogin;
    String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
        setContentView(R.layout.activity_main);
        createNewAccount=findViewById(R.id.createNewAccount);
        inputEmail=findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);
        btnLogin=findViewById(R.id.btnLogin);
        forgotPassword=findViewById(R.id.forgotPassword);
        progressDialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        createNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Register.class));
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performLogin();
                String useremail=inputEmail.getText().toString().trim();
                String userpassword=inputPassword.getText().toString().trim();

                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users");
                Query checkUserDatabase=reference.orderByChild("useremail").equalTo(useremail);

                //HelperClass helperClass=new HelperClass(name, String userType, String email,String phonenumber,String location, String password)

                checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            inputEmail.setError(null);
                            String passwordFromDB=snapshot.child(useremail).child("password").getValue(String.class);

                            if(!Objects.equals(passwordFromDB,userpassword)){
                                inputPassword.setError(null);
                                Intent intent=new Intent(MainActivity.this,Home.class);
                                startActivity(intent);
                            }else{
                                inputPassword.setError("Invalid Credentials");
                                inputPassword.requestFocus();
                            }
                        }else{
                            inputEmail.setError("User does not exist");
                            inputEmail.requestFocus();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        if(mAuth.getCurrentUser()!=null)
        {
            Intent intent = new Intent(MainActivity.this, selectUserType.class);
            startActivity(intent);
        }
    }

    private void performLogin() {

        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();


        if (!email.matches(emailPattern)) {
            inputEmail.setError("Enter Context Email");
        } else if (password.isEmpty() || password.length() < 6) {
            inputPassword.setError("Enter Proper Password");
        } else {
            progressDialog.setMessage("Please wait while login");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this," "+task.getException(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    //  public void checkUser()
//  {
//      String useremail=inputEmail.getText().toString().trim();
//      String userpassword=inputPassword.getText().toString().trim();
//
//      DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users");
//      Query checkUserDatabase=reference.orderByChild("useremail").equalTo(useremail);
//
//      checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//          @Override
//          public void onDataChange(@NonNull DataSnapshot snapshot) {
//              if (snapshot.exists()){
//                  inputEmail.setError(null);
//                  String passwordFromDB=snapshot.child(useremail).child("password").getValue(String.class);
//
//                  if(!Objects.equals(passwordFromDB,userpassword)){
//                      inputEmail.setError(null);
//                      Intent intent=new Intent(MainActivity.this,Home.class);
//                      startActivity(intent);
//                  }else{
//                      inputPassword.setError("Invalid Credentials");
//                      inputPassword.requestFocus();
//                  }
//              }else{
//                  inputEmail.setError("User does not exist");
//                  inputEmail.requestFocus();
//              }
//          }
//
//          @Override
//          public void onCancelled(@NonNull DatabaseError error) {
//
//          }
//      });
//  }
    private void sendUserToNextActivity() {
        Intent intent=new Intent(MainActivity.this, selectUserType.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}