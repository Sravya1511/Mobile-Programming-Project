package com.example.guesstheword;

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

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class UserActivity extends AppCompatActivity {
    public Button userName;
    public EditText Name;
    public ProgressDialog bar;
    public Boolean doesntexist;
//    public DatabaseReference rootRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        userName = (Button) findViewById(R.id.submit);
        Name = (EditText) findViewById(R.id.name);
        bar = new ProgressDialog(this);
        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newUser();
                String nam = Name.getText().toString();
                /*Intent intent = new Intent(UserActivity.this, NewGameActivity.class);
                intent.putExtra("Extra_message", nam);
                startActivity(intent);*/
//                finish();

            }
        });
    }

    private void newUser() {
        String name = Name.getText().toString();
        doesntexist = false;
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Enter your name.", Toast.LENGTH_SHORT).show();

        }
        else {
            onValid(name);
            if(true) {
            {
                bar.setTitle("Welcome" + "  " + name + ".");
                bar.setMessage("");
                bar.setCanceledOnTouchOutside(false);
                bar.show();
                Intent intent = new Intent(UserActivity.this, NewGameActivity.class);
                intent.putExtra("Extra_message", name);
                startActivity(intent);
            }
        }
            else{
                Toast.makeText(this, "Name already taken.", Toast.LENGTH_SHORT).show();
            }


    }}
    private void onValid(final String name){
    final DatabaseReference rootRef;
        FirebaseApp.initializeApp(this);
    rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.child("Users").addValueEventListener(new ValueEventListener(){
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(!(dataSnapshot.child("Users").child(name).exists())) {
                doesntexist = true;
            }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
}
    }
