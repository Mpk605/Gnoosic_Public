package com.kinejou.gnoosic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //the 3 artists
                startActivity(new Intent(MainActivity.this, FormActivity.class));
            }
        });

        findViewById(R.id.history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //History of liked artistes
                startActivity(new Intent(MainActivity.this, HistoryActivity.class));
            }
        });
    }
}
