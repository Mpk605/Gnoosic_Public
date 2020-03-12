package com.kinejou.gnoosic.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kinejou.gnoosic.Database.ArtistDatabase;
import com.kinejou.gnoosic.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    List<String> listItems = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ListView lv = findViewById(R.id.lv_history);

        listItems = ArtistDatabase.getInstance(this).getArtistDao().getSavedArtists();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItems);
        lv.setAdapter(adapter);

        for (int i = 0; i < 100; i++) {
            addElement(String.valueOf(i));
        }
    }

    public void addElement(String name) {
        ClipData.Item item = new ClipData.Item(name);
        //listItems.addItem(item);
    }
}
