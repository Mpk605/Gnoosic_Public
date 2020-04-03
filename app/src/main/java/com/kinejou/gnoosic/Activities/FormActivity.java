package com.kinejou.gnoosic.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.kinejou.gnoosic.R;
import com.kinejou.gnoosic.Tools.Internet.AsyncResponse;
import com.kinejou.gnoosic.Tools.Internet.CookieFetcher;
import com.kinejou.gnoosic.Tools.Internet.GnoosicAPI.GetNewBandFrom3Bands;
import com.kinejou.gnoosic.Tools.Internet.GnoosicAPI.GnoosicHelper;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class FormActivity extends AppCompatActivity implements AsyncResponse {
    GnoosicHelper gnoosicHelper;

    // Text inputs
    AutoCompleteTextView fav1, fav2, fav3;

    // Asynchronous
    GetNewBandFrom3Bands getNewBandFrom3Bands = new GetNewBandFrom3Bands();

    class BandTextWatcher implements TextWatcher {
        private AutoCompleteTextView textView;

        BandTextWatcher(AutoCompleteTextView textView) {
            this.textView = textView;
        }

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
                                if (textView.isFocused()) {
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(FormActivity.this,
                                            android.R.layout.simple_dropdown_item_1line, suggestions);

                                    textView.setAdapter(adapter);
                                    textView.showDropDown();
                                } else {
                                    textView.setAdapter(null);
                                }
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        gnoosicHelper = GnoosicHelper.getInstance();

        fav1 = findViewById(R.id.fav1);
        fav1.addTextChangedListener(new BandTextWatcher(fav1));
        fav1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(FormActivity.this, fav1.getText(), Toast.LENGTH_SHORT).show();
                fav1.dismissDropDown();
                fav1.clearFocus();
                fav2.requestFocus();
            }
        });

        fav2 = findViewById(R.id.fav2);
        fav2.addTextChangedListener(new BandTextWatcher(fav2));
        fav2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(FormActivity.this, fav2.getText(), Toast.LENGTH_SHORT).show();
                fav2.dismissDropDown();
                fav3.requestFocus();
            }
        });

        fav3 = findViewById(R.id.fav3);
        fav3.addTextChangedListener(new BandTextWatcher(fav3));
        fav3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(FormActivity.this, fav3.getText(), Toast.LENGTH_SHORT).show();
                fav3.dismissDropDown();
                fav3.clearFocus();
                // Dismiss keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                Objects.requireNonNull(imm).hideSoftInputFromWindow(fav3.getWindowToken(), 0);
            }
        });

        findViewById(R.id.continue_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);

                    GnoosicHelper gnoosicHelper = GnoosicHelper.getInstance();
                    gnoosicHelper.setCookie(new CookieFetcher().execute().get());

                    getNewBandFrom3Bands.delegate = FormActivity.this;
                    getNewBandFrom3Bands.execute(String.valueOf(fav1.getText()), String.valueOf(fav2.getText()), String.valueOf(fav3.getText()));
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();

                    findViewById(R.id.progress_bar).setVisibility(View.GONE);

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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
