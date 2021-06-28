package com.itschool.tensorflowlitetest;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.itschool.tensorflowlitetest.classifier.Classifier;
import com.itschool.tensorflowlitetest.classifier.Device;
import com.itschool.tensorflowlitetest.classifier.Recognition;
import com.nex3z.fingerpaintview.FingerPaintView;

public class Mnist extends AppCompatActivity {

    private static final String LOG_TAG = "Tensorflow test - MNIST";

    Button detect, clear;
    private Classifier classifier;
    FingerPaintView fingerPaintView;
    TextView predText, probability, timeCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mnist);
        detect = findViewById(R.id.btn_detect);
        clear = findViewById(R.id.btn_clear);
        fingerPaintView = findViewById(R.id.fpv_paint);
        predText = findViewById(R.id.tv_prediction);
        probability = findViewById(R.id.tv_probability);
        timeCost = findViewById(R.id.tv_timecost);
        initClassifier();
        initView();
    }

    private void initView() {
        detect.setOnClickListener((view -> {
            if (classifier == null) {
                Log.e(LOG_TAG, "onDetectClick(): Classifier is not initialized");
            } else if (fingerPaintView.isEmpty()) {
                Toast.makeText(this, R.string.please_write_a_digit, Toast.LENGTH_SHORT).show();
            }


            Bitmap bitmap = fingerPaintView.exportToBitmap(classifier.getInputShape().getWidth(),
                    classifier.getInputShape().getHeight());

            Recognition r = classifier.classify(bitmap);
            renderRes(r);
        }));


        clear.setOnClickListener((view -> {
            clearRes();
        }));
    }

    @SuppressLint("DefaultLocale")
    private void renderRes(Recognition r) {
        predText.setText(String.format("%d", r.getLabel()));
        probability.setText(String.format("%s", r.getConfidence()));
        timeCost.setText(String.format(getString(R.string.timecost_value), r.getTimeCost()));
    }



    private void clearRes(){
        fingerPaintView.clear();
        predText.setText(R.string.empty);
        probability.setText(R.string.empty);
        timeCost.setText(R.string.empty);
    }

    private void initClassifier() {
        try {
            classifier = new Classifier(this, Device.CPU, 4);
            Log.v(LOG_TAG, "Classifier initialized");
        } catch (Exception e) {
            Toast.makeText(this, R.string.failed_to_create_classifier, Toast.LENGTH_LONG).show();
            Log.e(LOG_TAG, "init(): Failed to create Classifier", e);
        }

    }
}