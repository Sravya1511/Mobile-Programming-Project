package com.example.guesstheword;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LeaderBoard extends AppCompatActivity {
    public ListView list;
    //    Firebase myfiredata;
    private DatabaseReference myfiredata;
    public ArrayList<UserDetails> arraylist = new ArrayList<>();
    public ArrayAdapter<UserDetails> adapter;
    public TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        myfiredata = FirebaseDatabase.getInstance().getReference();
        adapter = new ArrayAdapter<UserDetails>(this, android.R.layout.simple_list_item_1, arraylist);
        list = (ListView) findViewById(R.id.listv);

        myfiredata.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> userdata = dataSnapshot.getChildren();
                for(DataSnapshot child : userdata){
                    UserDetails userinfo = child.getValue(UserDetails.class);
                    adapter.add(userinfo);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        list.setAdapter(adapter);
    }
}
