package com.kinejou.gnoosic.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kinejou.gnoosic.R;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    ArrayList<String> listItems=new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ListView lv = findViewById(R.id.lv_history);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        lv.setAdapter(adapter);

        for (int i = 0; i < 100; i ++) {
            addElement(i+"");
        }
    }

    public void addElement(String name) {
        ClipData.Item item = new ClipData.Item(name);
        //listItems.addItem(item);
    }
}
