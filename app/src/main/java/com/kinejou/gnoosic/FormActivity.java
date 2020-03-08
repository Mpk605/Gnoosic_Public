package com.kinejou.gnoosic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.kinejou.gnoosic.Tools.BuildUI;

import org.w3c.dom.Text;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class FormActivity extends AppCompatActivity {
    GnoosicHelper gnoosicHelper;

    // Text inputs
    TextInputEditText fav1, fav2, fav3;

    // Text inputs containers
    LinearLayout fav1Container, fav2Container, fav3Container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        gnoosicHelper = GnoosicHelper.getInstance();

        fav1 = findViewById(R.id.fav_band_1);
        fav1Container = findViewById(R.id.band1_container);
        fav1.addTextChangedListener(new TextWatcher() {
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
                            final String[] typeAheadResult = gnoosicHelper.getTypeAheadSuggestion(input);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    fav1Container.removeViews(1, fav1Container.getChildCount() - 1);

                                    Log.d("View", fav1Container.getChildCount() + "");

                                    if (typeAheadResult.length > 0) {
                                        MaterialCardView suggestionsCardView = BuildUI.buildMaterialCardView(fav1Container.getContext(),
                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                                0,
                                                new int[]{0, 0, 0, 0});

                                        fav1Container.addView(suggestionsCardView);

                                        LinearLayout suggestionsContainer = BuildUI.buildLinearLayout(fav1Container.getContext(),
                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                                LinearLayout.VERTICAL);

                                        suggestionsCardView.addView(suggestionsContainer);

                                        for (String suggestion : typeAheadResult) {
                                            final TextView textView = BuildUI.buildTextView(fav1Container.getContext(),
                                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                                    suggestion,
                                                    15,
                                                    new int[]{16, 4, 16, 4},
                                                    Color.BLACK);

                                            textView.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    fav1.setText(textView.getText());
                                                }
                                            });
                                            suggestionsContainer.addView(textView);

                                            suggestionsContainer.addView(BuildUI.buildDivider(fav1Container.getContext(),
                                                    new int[]{0, 0, 0, 0}));
                                        }
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
            public void afterTextChanged(Editable s) {
                Log.d("afterTextChanged", "text changed to " + s);
            }
        });

        fav2 = findViewById(R.id.fav_band_2);
        fav2Container = findViewById(R.id.band2_container);
        fav2.addTextChangedListener(new TextWatcher() {
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
                            final String[] typeAheadResult = gnoosicHelper.getTypeAheadSuggestion(input);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    fav2Container.removeViews(1, fav2Container.getChildCount() - 1);

                                    if (typeAheadResult.length > 0) {
                                        MaterialCardView suggestionsCardView = BuildUI.buildMaterialCardView(fav2Container.getContext(),
                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                                0,
                                                new int[]{0, 0, 0, 0});

                                        fav2Container.addView(suggestionsCardView);

                                        LinearLayout suggestionsContainer = BuildUI.buildLinearLayout(fav2Container.getContext(),
                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                                LinearLayout.VERTICAL);

                                        suggestionsCardView.addView(suggestionsContainer);

                                        for (String suggestion : typeAheadResult) {
                                            final TextView textView = BuildUI.buildTextView(fav2Container.getContext(),
                                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                                    suggestion,
                                                    15,
                                                    new int[]{16, 4, 16, 4},
                                                    Color.BLACK);

                                            textView.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    fav2.setText(textView.getText());
                                                }
                                            });
                                            suggestionsContainer.addView(textView);

                                            suggestionsContainer.addView(BuildUI.buildDivider(fav2Container.getContext(),
                                                    new int[]{0, 0, 0, 0}));
                                        }
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
            public void afterTextChanged(Editable s) {
                Log.d("afterTextChanged", "text changed to " + s);
            }
        });

        fav3 = findViewById(R.id.fav_band_3);
        fav3Container = findViewById(R.id.band3_container);
        fav3.addTextChangedListener(new TextWatcher() {
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
                            final String[] typeAheadResult = gnoosicHelper.getTypeAheadSuggestion(input);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    fav3Container.removeViews(1, fav3Container.getChildCount() - 1);

                                    if (typeAheadResult.length > 0) {
                                        MaterialCardView suggestionsCardView = BuildUI.buildMaterialCardView(fav3Container.getContext(),
                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                                0,
                                                new int[]{0, 0, 0, 0});

                                        fav3Container.addView(suggestionsCardView);

                                        LinearLayout suggestionsContainer = BuildUI.buildLinearLayout(fav3Container.getContext(),
                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                                LinearLayout.VERTICAL);

                                        suggestionsCardView.addView(suggestionsContainer);

                                        for (String suggestion : typeAheadResult) {
                                            final TextView textView = BuildUI.buildTextView(fav3Container.getContext(),
                                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                                    suggestion,
                                                    15,
                                                    new int[]{16, 4, 16, 4},
                                                    Color.BLACK);

                                            textView.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    fav3.setText(textView.getText());
                                                }
                                            });
                                            suggestionsContainer.addView(textView);

                                            suggestionsContainer.addView(BuildUI.buildDivider(fav3Container.getContext(),
                                                    new int[]{0, 0, 0, 0}));
                                        }
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
            public void afterTextChanged(Editable s) {
                Log.d("afterTextChanged", "text changed to " + s);
            }
        });

        findViewById(R.id.continue_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FormActivity.this, fav1.getText() + " - " + fav2.getText() + " - " + fav3.getText(), Toast.LENGTH_SHORT).show();

                if (Objects.requireNonNull(fav1.getText()).toString().equals("")) {
                    fav1.setError("Field required");
                    return;
                }

                if (Objects.requireNonNull(fav2.getText()).toString().equals("")) {
                    fav2.setError("Field required");
                    return;
                }

                if (Objects.requireNonNull(fav3.getText()).toString().equals("")) {
                    fav3.setError("Field required");
                    return;
                }

                try {
                    gnoosicHelper.getNewBandFrom3Bands(fav1.getText().toString(), fav2.getText().toString(), fav3.getText().toString());
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }

//                startActivity(new Intent(FormActivity.this, ResultActivity.class));
            }
        });
    }
}
