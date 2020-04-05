package com.kinejou.gnoosic.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.kinejou.gnoosic.Database.ArtistDatabase;
import com.kinejou.gnoosic.Database.Entities.Artist;
import com.kinejou.gnoosic.R;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG;

public class HistoryActivity extends AppCompatActivity {

    List<String> listItems = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    View thisView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setOrientation();

        ListView lv = findViewById(R.id.lv_history);

        listItems = ArtistDatabase.getInstance(this).getArtistDao().getSavedArtists();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItems);
        lv.setAdapter(adapter);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), "id = "+ id + " position = " + position + " name = " + listItems.get(position), Toast.LENGTH_LONG).show();
                String name = listItems.get(position);
                Artist deleted = ArtistDatabase.getInstance(HistoryActivity.this).getArtistDao().getArtist(name);

                confirmDeleteOne(deleted, name, view);
                return false;
            }
        });

        findViewById(R.id.clear_history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDeleteAll();
            }
        });
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

    private void setOrientation() {
        Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        int orientation = display.getRotation();

        if (orientation == Surface.ROTATION_90 || orientation == Surface.ROTATION_270) {
            setContentView(R.layout.activity_history_horizontal);
        } else {
            setContentView(R.layout.activity_history);
        }
    }

    private void confirmDeleteAll() {
        AlertDialog alertDialog = new MaterialAlertDialogBuilder(this).setTitle(R.string.Warning)
                .setMessage(R.string.confirm_delete)
                .setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "You've chosen to delete all records", Toast.LENGTH_SHORT).show();
                        ArtistDatabase.getInstance(HistoryActivity.this).getArtistDao().clearAll();
                        refresh();
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), R.string.cancel, Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }

    private void confirmDeleteOne(final Artist delete, final String name, final View view) {
        AlertDialog alertDialog = new MaterialAlertDialogBuilder(this).setTitle("Do you want to delete " + name + " ?")
            .setMessage("You cannot cancel this operation")
            .setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ArtistDatabase.getInstance(HistoryActivity.this).getArtistDao().deleteArtist(delete);
                    //deletingSnackBar(deletedArtist, nameA, view);
                    Toast.makeText(getApplicationContext(), "deleting " + name, Toast.LENGTH_SHORT).show();
                    refresh();
                }
            }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), R.string.cancel, Toast.LENGTH_SHORT).show();
                }
            }).show();
    }

    private void refresh() {
        finish();
        overridePendingTransition( 0, 0);
        startActivity(getIntent());
        overridePendingTransition( 0, 0);
    }

    //not working
    private void deletingSnackBar(Artist artist, String name, View view) {
        final Artist addedArtist = artist;
        Snackbar mySnackbar = Snackbar.make(view,
                "deleting " + name + "...", Snackbar.LENGTH_SHORT);
        mySnackbar.setAction("UNDO", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArtistDatabase.getInstance(HistoryActivity.this).getArtistDao().insert(addedArtist);
            }
        });
        mySnackbar.show();
    }
}
