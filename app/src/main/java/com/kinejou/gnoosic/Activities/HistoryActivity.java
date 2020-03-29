package com.kinejou.gnoosic.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kinejou.gnoosic.Database.ArtistDatabase;
import com.kinejou.gnoosic.R;
import com.kinejou.gnoosic.Tools.Theme;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    List<String> listItems = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(Theme.getTheme(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ListView lv = findViewById(R.id.lv_history);

        listItems = ArtistDatabase.getInstance(this).getArtistDao().getSavedArtists();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItems);
        lv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(this, SettingsActivity.class));
        Log.d("menu", "settings");
        return super.onOptionsItemSelected(item);
    }
}
