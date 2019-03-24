package com.example.closet;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.example.closet.model.Users;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

public EditText number, Password;
public Button button;
public ProgressDialog bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        button = (Button) findViewById(R.id.submit);
        number = (EditText) findViewById(R.id.login_number);
        Password = (EditText) findViewById(R.id.login_passwrd);
        bar = new ProgressDialog(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();

            }
        });
    }

    private void userLogin() {
        String phone = number.getText().toString();
        String passWord = Password.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Enter your phone number.", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(passWord)) {
            Toast.makeText(this, "Enter a password.", Toast.LENGTH_SHORT).show();
        }
        else{
            bar.setTitle("login account");
            bar.setMessage("Checking the credentials");
            bar.setCanceledOnTouchOutside(false);
            bar.show();
            allow(phone, passWord);
        }
        }

    private void allow(final String phone, final String password) {
        final DatabaseReference rootRef;
        FirebaseApp.initializeApp(this);
        rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Users").child(phone).exists()){
                    Users userData = dataSnapshot.child("Users").child(phone).getValue(Users.class);
                    if(userData.getPhone().equals(phone)){
                        if(userData.getPassword().equals(password)){
                            Toast.makeText(LoginActivity.this, "Logged in successfully.. ", Toast.LENGTH_SHORT).show();
                            bar.dismiss();
                            Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
                            startActivity(intent);
                        }
                        else{
                            bar.dismiss();
                            Toast.makeText(LoginActivity.this, "Please enter a valid password ", Toast.LENGTH_LONG).show();
                        }
                    }

                }
                else {
                    Toast.makeText(LoginActivity.this, "This"+phone+"number doesn't exist, please sign up.", Toast.LENGTH_SHORT).show();
                    bar.dismiss();
                    Toast.makeText(LoginActivity.this, "Use another phone number.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
