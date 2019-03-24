package com.example.guesstheword;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class NewGameActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String SCORE_SHARE_HASHTAG = " #guess";
    private TextView level;
    private TextView GivenWord, usName;
    private int i = 1;
    private EditText Answer;
    public String wordToFind;
    public TextView Hints, ANSs;
    public Button next;
    public Button val;
    String username;
    public Button hintButton, Ansbutton;
    private TextView Score;
    int s = 10;
    public ProgressDialog bar;
    public DatabaseReference rootRef;


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        level = (TextView) findViewById(R.id.Level);
        Score = (TextView) findViewById(R.id.Score);
        GivenWord = (TextView) findViewById(R.id.Question);
        Answer = (EditText) findViewById(R.id.YourAnswer);
        Hints = (TextView) findViewById(R.id.Hint);
        usName = (TextView) findViewById(R.id.Name);
        ANSs = (TextView) findViewById(R.id.Hint);
        hintButton = (Button) findViewById(R.id.HintButton);
        Ansbutton = (Button) findViewById(R.id.ansbutton);
        val = (Button) findViewById(R.id.Validate);
        next = (Button) findViewById(R.id.Next);

        next.setOnClickListener(this);
        next();


    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.commonmenus, menu);
//        MenuItem menuItem = menu.findItem(R.id.mnuShare);
//        menuItem.setIntent(createShareScoreIntent());
//        return true;
//    }

    private Intent createShareScoreIntent() {
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText(Score + SCORE_SHARE_HASHTAG)
                .getIntent();
        return shareIntent;
    }

    @Override
    public void onClick(View v) {
        if(v == next) {
            next();
        }
    }

    public void next() {
        Intent intent = getIntent();


        username = intent.getStringExtra("Extra_message");
        usName.setText(username);
        String N = usName.getText().toString();
        String num = Integer.toString(i);
        level.setText("Level " + num);
        String sc = Integer.toString(s);
        Score.setText("Score: " + sc);

        i++;
        wordToFind = WordClass.randomWord(i);
        String wordShuffled = WordClass.shuffleWords(wordToFind);
        GivenWord.setText(wordShuffled);
        Answer.setEnabled(true);
        val.setEnabled(true);
        hintButton.setVisibility(View.VISIBLE);
        next.setVisibility(View.INVISIBLE);

        Answer.setText("");
//        if(i==7){
//
//            onValid(N, sc, num);
//        }


    }


    public void Hint(View view) {
        String hi = "The first letter is ";
        String h = hi + wordToFind.substring(0, 1);
        Hints.setText(h);
        Hints.setVisibility(View.VISIBLE);
        s = s-5;
        Toast toast = Toast.makeText(this, "Opps..Taking hint reduced 5 points", Toast.LENGTH_LONG);
        toast.show();
        String sc = Integer.toString(s);
        Score.setText("Score: " + sc);
        hintButton.setVisibility(View.INVISIBLE);
    }
    public void Ans(View view) {
        String hi = "The answer is ";
        String h = hi + wordToFind;
        ANSs.setText(h);
        ANSs.setVisibility(View.VISIBLE);
        s = s-10;
        Toast toast = Toast.makeText(this, "Opps..Taking Answer reduced 10 points", Toast.LENGTH_LONG);
        toast.show();
        String sc = Integer.toString(s);
        Score.setText("Score: " + sc);
    }

    public void Validate(View view) {
        String ans = Answer.getText().toString();
        String N = usName.getText().toString();
        String num = Integer.toString(i);
        level.setText("Level " + num);
        String sc = Integer.toString(s);
        Score.setText("Score: " + sc);
        Log.d("valida", "Validate: ");
        if(wordToFind.equals(ans)){
            s = s+10;
            Toast toast = Toast.makeText(this, "You did it!...You earned 10 points", Toast.LENGTH_LONG);
            toast.show();
            Answer.setEnabled(false);
            next.setVisibility(View.VISIBLE);
            if(i >= 7){
                next.setVisibility(View.INVISIBLE);
            }
            val.setEnabled(false);
            hintButton.setVisibility(View.INVISIBLE);
            Hints.setVisibility(View.INVISIBLE);
//            if(i == 7) {
//                onValid(N, sc, num);
//            }

        }
        else {
            Toast toast = Toast.makeText(this, "Try again!", Toast.LENGTH_LONG);
            toast.show();

        }
    }

    public void endGame(View view) {
        String sc = Integer.toString(s);
        Score.setText("Score: " + sc);
        String num = Integer.toString(i);
        level.setText("Level " + num);
        String N = usName.getText().toString();
        onValid(N, sc, num);
        String nam = N +"_" + sc +"_" + num;
        Intent intent = new Intent(this, ScoreActivity.class);
        intent.putExtra("Score_report", nam);
        startActivity(intent);


    }
    private void onValid(final String name, final String score, final String levl) {

        FirebaseApp.initializeApp(NewGameActivity.this);
        rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(true){
                    HashMap<String, Object> userdata = new HashMap<>();
                    userdata.put("name", name);
                    userdata.put("Score", score);
                    userdata.put("Level", levl);
                    rootRef.child("Users").child(name).updateChildren(userdata)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(NewGameActivity.this, "Game Ended", Toast.LENGTH_LONG).show();
//                                        bar.dismiss();
//                                        Intent intent = new Intent(NewGameActivity.this, ScoreActivity.class);
//                                        startActivity(intent);
                                    }
                                    else{
                                         Toast.makeText(NewGameActivity.this, "Error Occured", Toast.LENGTH_SHORT).show();
                                        bar.dismiss();

                                    }
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

}
