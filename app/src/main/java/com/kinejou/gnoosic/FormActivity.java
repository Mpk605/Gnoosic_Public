package com.kinejou.gnoosic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.concurrent.ExecutionException;

public class FormActivity extends AppCompatActivity {
    GnoosicHelper gnoosicHelper;

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            final String input = s.toString();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        gnoosicHelper.getTypeAheadSuggestion(input);
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        @Override
        public void afterTextChanged(Editable s) {
            Log.d("afterTextChanged", "text changed to " + s);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        gnoosicHelper = GnoosicHelper.getInstance();

        final TextInputEditText fav1 = findViewById(R.id.fav_band_1);
        fav1.addTextChangedListener(watcher);

        final TextInputEditText fav2 = findViewById(R.id.fav_band_2);
        fav1.addTextChangedListener(watcher);

        final TextInputEditText fav3 = findViewById(R.id.fav_band_3);
        fav1.addTextChangedListener(watcher);

        findViewById(R.id.continue_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FormActivity.this, fav1.getText() + " - " + fav2.getText() + " - " + fav3.getText(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(FormActivity.this, ResultActivity.class));
            }
        });
    }
}
