package com.example.guesstheword;

import android.content.Intent;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {
    private static final String SCORE_SHARE_HASHTAG = "Play Guess The Word";
    private TextView title, name, score, level;
    private Button home;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        title = (TextView) findViewById(R.id.Title);
        name = (TextView) findViewById(R.id.Name);
        score = (TextView) findViewById(R.id.Score);
        level = (TextView) findViewById(R.id.Level);
        home = (Button) findViewById(R.id.button2);
        Intent intent = getIntent();
        str = intent.getStringExtra("Score_report");
        String[] sty = str.split("_");
            name.setText("Name: " + sty[0]);
            score.setText("Score: " + sty[1]);
            level.setText("Level: " + sty[2]);
            title.setText("Congratulations: "+ sty[0]);
        }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScoreActivity.this, UserActivity.class);
                startActivity(intent);
            }
        });
        getMenuInflater().inflate(R.menu.commonmenus, menu);
        MenuItem menuItem = menu.findItem(R.id.mnuShare);
        menuItem.setIntent(createShareScoreIntent());
        return true;
    }

    private Intent createShareScoreIntent() {
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText(name + " " + "scored" + " " +score + SCORE_SHARE_HASHTAG)
                .getIntent();
        return shareIntent;
    }
    }

