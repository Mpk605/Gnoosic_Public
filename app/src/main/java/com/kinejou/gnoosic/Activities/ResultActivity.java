package com.kinejou.gnoosic.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.kinejou.gnoosic.Database.ArtistDatabase;
import com.kinejou.gnoosic.Database.Entities.Artist;
import com.kinejou.gnoosic.R;
import com.kinejou.gnoosic.Tools.Internet.AsyncResponse;
import com.kinejou.gnoosic.Tools.Internet.GnoosicAPI.GetNewBandFromPreviousBand;
import com.kinejou.gnoosic.Tools.Internet.YoutubeAPI;

import java.util.concurrent.ExecutionException;

public class ResultActivity extends YouTubeBaseActivity implements AsyncResponse {
    String suppID;
    String artist;

    // Asynchronous
    GetNewBandFromPreviousBand getNewBandFromPreviousBand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        findViewById(R.id.progress_bar).setVisibility(View.GONE);

        getNewBandFromPreviousBand = new GetNewBandFromPreviousBand();

        artist = getIntent().getStringExtra("band");
        suppID = getIntent().getStringExtra("SuppID");

        TextView artistTextView = findViewById(R.id.artist_text_view);
        artistTextView.setText(artist);

        findViewById(R.id.like_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);

                getNewBandFromPreviousBand.delegate = ResultActivity.this;

                ArtistDatabase.getInstance(view.getContext()).getArtistDao().insert(new Artist(artist));

                getNewBandFromPreviousBand.execute("RateP01", suppID);
            }
        });

        findViewById(R.id.dislike_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);

                getNewBandFromPreviousBand.delegate = ResultActivity.this;

                getNewBandFromPreviousBand.execute("RateN01", suppID);
            }
        });

        findViewById(R.id.idk_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);

                getNewBandFromPreviousBand.delegate = ResultActivity.this;

                getNewBandFromPreviousBand.execute("Rate001", suppID);
            }
        });

        final YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player);
        try {
            playVideo(new YoutubeAPI().execute(artist).get(), youTubePlayerView);
        } catch (ExecutionException | InterruptedException e) {
            playVideo("G_cdKPMcjiQ", youTubePlayerView);
            e.printStackTrace();
        }
    }

    public void playVideo(final String videoId, YouTubePlayerView youTubePlayerView) {
        //initialize youtube player view
        youTubePlayerView.initialize(getResources().getString(R.string.youtube_api_key),
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {
                        youTubePlayer.cueVideo(videoId);
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
    }

    @Override
    public void processFinish(String[] output) {
        Intent resultIntent = new Intent(this, ResultActivity.class);
        resultIntent.putExtra("band", output[0]);
        resultIntent.putExtra("SuppID", output[1]);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        startActivity(resultIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_settings:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent goToFormActivity = new Intent(this, FormActivity.class);
        goToFormActivity.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(goToFormActivity);
    }
}
