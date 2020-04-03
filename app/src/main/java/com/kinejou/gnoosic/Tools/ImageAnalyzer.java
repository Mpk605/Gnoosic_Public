package com.kinejou.gnoosic.Tools;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.preference.PreferenceManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;
import com.kinejou.gnoosic.Activities.ResultActivity;
import com.kinejou.gnoosic.Tools.Internet.GnoosicAPI.GnoosicHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ImageAnalyzer {
    private String result;

    public void getMoodPredictionBasedOnImage(final Context context, Bitmap bitmap) {
        result = "Stupeflip";

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        try {
            FirebaseVisionImage image;

            image = FirebaseVisionImage.fromBitmap(bitmap);

            final FirebaseVisionImageLabeler labeler = FirebaseVision.getInstance().getOnDeviceImageLabeler();

            labeler.processImage(image)
                    .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
                        @Override
                        public void onSuccess(List<FirebaseVisionImageLabel> firebaseVisionImageLabels) {
                            ArrayList<String> predictionList = new ArrayList<>();

                            for (FirebaseVisionImageLabel label : firebaseVisionImageLabels) {
                                Log.d("ML", label.getText() + " : " + label.getConfidence());
                                predictionList.add(label.getConfidence() + " : " + label.getText());
                            }

                            try {
                                ArrayList<String> predictions = new ArrayList<>();

                                switch (preferences.getString("mood_mode", "eclair")) {
                                    case "eclair":
                                        for (FirebaseVisionImageLabel label : firebaseVisionImageLabels)
                                            for (String subLabel : label.getText().split(" "))
                                                predictions.addAll(Arrays.asList(GnoosicHelper.getInstance().getTypeAheadSuggestion(subLabel)));

                                        result = predictions.get(ThreadLocalRandom.current().nextInt(predictions.size()));
                                        break;
                                    case "gorilla":
                                        FirebaseVisionImageLabel maxLabel = firebaseVisionImageLabels.get(0);

                                        for (FirebaseVisionImageLabel label : firebaseVisionImageLabels)
                                            maxLabel = (label.getConfidence() <= maxLabel.getConfidence()) ? maxLabel : label;

                                        for (String subLabel : maxLabel.getText().split(" "))
                                            predictions.addAll(Arrays.asList(GnoosicHelper.getInstance().getTypeAheadSuggestion(subLabel)));

                                        result = predictions.get(ThreadLocalRandom.current().nextInt(predictions.size()));
                                        break;
                                    case "bold":
                                        FirebaseVisionImageLabel minLabel = firebaseVisionImageLabels.get(0);

                                        for (FirebaseVisionImageLabel label : firebaseVisionImageLabels)
                                            minLabel = (label.getConfidence() >= minLabel.getConfidence()) ? minLabel : label;

                                        for (String subLabel : minLabel.getText().split(" "))
                                            predictions.addAll(Arrays.asList(GnoosicHelper.getInstance().getTypeAheadSuggestion(subLabel)));

                                        result = predictions.get(ThreadLocalRandom.current().nextInt(predictions.size()));
                                        break;
                                }

                                result = (result.isEmpty()) ? "Stupeflip" : result;

                                Intent resultActivity = new Intent(context, ResultActivity.class);
                                resultActivity.putExtra("SuppID", "17135");
                                resultActivity.putExtra("band", result);
                                resultActivity.putStringArrayListExtra("predictions", predictionList);

                                Log.d("The", predictions.toString());

                                context.startActivity(resultActivity);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
