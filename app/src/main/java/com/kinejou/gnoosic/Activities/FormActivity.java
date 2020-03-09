package com.kinejou.gnoosic.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.kinejou.gnoosic.R;
import com.kinejou.gnoosic.Tools.BuildUI;
import com.kinejou.gnoosic.Tools.Internet.AsyncResponse;
import com.kinejou.gnoosic.Tools.Internet.CookieFetcher;
import com.kinejou.gnoosic.Tools.Internet.GnoosicAPI.GetNewBandFrom3Bands;
import com.kinejou.gnoosic.Tools.Internet.GnoosicAPI.GetNewBandFromPreviousBand;
import com.kinejou.gnoosic.Tools.Internet.GnoosicAPI.GnoosicHelper;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class FormActivity extends AppCompatActivity implements AsyncResponse {
    GnoosicHelper gnoosicHelper;

    // Text inputs
    AutoCompleteTextView fav1, fav2, fav3;

    // Asynchronous
    GetNewBandFrom3Bands getNewBandFrom3Bands = new GetNewBandFrom3Bands();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        gnoosicHelper = GnoosicHelper.getInstance();

        fav1 = findViewById(R.id.fav1);
        fav1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, int i, int i1, int i2) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final String[] suggestions = gnoosicHelper.getTypeAheadSuggestion(String.valueOf(charSequence));

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(FormActivity.this,
                                            android.R.layout.simple_dropdown_item_1line, suggestions);
                                    fav1.setAdapter(adapter);
                                    fav1.showDropDown();
                                }
                            });
                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        fav2 = findViewById(R.id.fav2);
        fav2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, int i, int i1, int i2) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final String[] suggestions = gnoosicHelper.getTypeAheadSuggestion(String.valueOf(charSequence));

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(FormActivity.this,
                                            android.R.layout.simple_dropdown_item_1line, suggestions);
                                    fav2.setAdapter(adapter);
                                    fav2.showDropDown();
                                }
                            });
                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        fav3 = findViewById(R.id.fav3);
        fav3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, int i, int i1, int i2) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final String[] suggestions = gnoosicHelper.getTypeAheadSuggestion(String.valueOf(charSequence));

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(FormActivity.this,
                                            android.R.layout.simple_dropdown_item_1line, suggestions);
                                    fav3.setAdapter(adapter);
                                    fav3.showDropDown();
                                }
                            });
                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        findViewById(R.id.continue_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(FormActivity.this, fav1.getText() + " - " + fav2.getText() + " - " + fav3.getText(), Toast.LENGTH_SHORT).show();
                try {
                    GnoosicHelper gnoosicHelper = GnoosicHelper.getInstance();
                    gnoosicHelper.setCookie(new CookieFetcher().execute().get());

                    getNewBandFrom3Bands.delegate = FormActivity.this;
                    getNewBandFrom3Bands.execute(String.valueOf(fav1.getText()), String.valueOf(fav2.getText()), String.valueOf(fav3.getText()));
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();

                    Toast.makeText(FormActivity.this, "An error occured, please try again later", Toast.LENGTH_SHORT).show();
                }
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
}
