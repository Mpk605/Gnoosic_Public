package com.kinejou.gnoosic.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.kinejou.gnoosic.R;
import com.kinejou.gnoosic.Tools.Internet.AsyncResponse;
import com.kinejou.gnoosic.Tools.Internet.GnoosicAPI.GetNewBandFromPreviousBand;

public class ResultActivity extends AppCompatActivity implements AsyncResponse {
    String suppID;
    String artist;

    // Asynchronous
    GetNewBandFromPreviousBand getNewBandFromPreviousBand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        getNewBandFromPreviousBand = new GetNewBandFromPreviousBand();

        artist = getIntent().getStringExtra("band");
        suppID = getIntent().getStringExtra("SuppID");

        TextView artistTextView = findViewById(R.id.artist_text_view);
        artistTextView.setText(artist);

        findViewById(R.id.like_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNewBandFromPreviousBand.delegate = ResultActivity.this;

                getNewBandFromPreviousBand.execute("RateP01", suppID);
            }
        });

        findViewById(R.id.dislike_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNewBandFromPreviousBand.delegate = ResultActivity.this;

                getNewBandFromPreviousBand.execute("RateN01", suppID);
            }
        });

        findViewById(R.id.idk_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNewBandFromPreviousBand.delegate = ResultActivity.this;

                getNewBandFromPreviousBand.execute("Rate001", suppID);
            }
        });
    }

    @Override
    public void processFinish(String[] output) {
        Intent resultIntent = new Intent(this, ResultActivity.class);
        resultIntent.putExtra("band", output[0]);
        resultIntent.putExtra("SuppID", output[1]);

        startActivity(resultIntent);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, FormActivity.class));
    }
}
