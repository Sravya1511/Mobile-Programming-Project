package com.example.closet;

import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {
    public Button createAccount;
    public EditText userName, phoneNo, password;
    public ProgressDialog bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        createAccount = (Button) findViewById(R.id.submit);
        userName = (EditText) findViewById(R.id.login_name);
        phoneNo = (EditText) findViewById(R.id.login_number);
        password = (EditText) findViewById(R.id.login_passwrd);
        bar = new ProgressDialog(this);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAcc();

            }
        });
    }

    private void onAcc() {
        String name = userName.getText().toString();
        String phone = phoneNo.getText().toString();
        String passWord = password.getText().toString();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Enter your name.", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Enter your phone number.", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(passWord)) {
            Toast.makeText(this, "Enter a password.", Toast.LENGTH_SHORT).show();

        } else {
            bar.setTitle("create account");
            bar.setMessage("Checking the credentials");
            bar.setCanceledOnTouchOutside(false);
            bar.show();
            onValid(name, phone, passWord);


        }
    }


    private void onValid(final String name, final String phone, final String passWord) {
        final DatabaseReference rootRef;
        FirebaseApp.initializeApp(this);
        rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Users").child(phone).exists())){
                    HashMap<String, Object> userdata = new HashMap<>();
                    userdata.put("name", name);
                    userdata.put("phone", phone);
                    userdata.put("password", passWord);
                    rootRef.child("Users").child(phone).updateChildren(userdata)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SignupActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                bar.dismiss();
                                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                            else{
                                bar.dismiss();
                                Toast.makeText(SignupActivity.this, "Loading error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(SignupActivity.this, "This"+ phone + "already exist.", Toast.LENGTH_SHORT).show();
                    bar.dismiss();
                    Toast.makeText(SignupActivity.this, "Use another phone number.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
