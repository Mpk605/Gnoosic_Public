package com.kinejou.gnoosic.Tools;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

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

                                for (FirebaseVisionImageLabel label : firebaseVisionImageLabels)
                                    for (String subLabel : label.getText().split(" "))
                                        predictions.addAll(Arrays.asList(GnoosicHelper.getInstance().getTypeAheadSuggestion(subLabel)));

                                result = predictions.get(ThreadLocalRandom.current().nextInt(predictions.size()));

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
