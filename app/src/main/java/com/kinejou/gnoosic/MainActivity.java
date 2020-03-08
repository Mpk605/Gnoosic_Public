package com.kinejou.gnoosic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

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

                try {
                    GnoosicHelper gnoosicHelper = GnoosicHelper.getInstance();
                    gnoosicHelper.execute("Nicki Minaj", "The Beatles", "Led Zeppelin");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
