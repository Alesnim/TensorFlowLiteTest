package com.itschool.tensorflowlitetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class MainActivity extends AppCompatActivity {
    TextView tw_res;
    SwitchMaterial input;
    SwitchMaterial inputSecond;
    boolean oneIn, twoIn;


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            float[] res = msg.getData().getFloatArray("res");
            setText(res[0]);

        }
    };

    private void setText(float re) {
        if (re >= 0.8) {
            tw_res.setTextColor(Color.GREEN);
            tw_res.setText("T");
        } else {
            tw_res.setTextColor(Color.RED);
            tw_res.setText("F");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tw_res = findViewById(R.id.tv_output);

        input = findViewById(R.id.first_input);
        input.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked)
                -> {
            oneIn = !oneIn;
        });
        inputSecond = findViewById(R.id.second_input);
        inputSecond.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        twoIn = !twoIn;
                    }
                });
    }

    public void startNetwork(View view) {
        new PredictTask(this, mHandler, getAssets(), oneIn, twoIn).execute();

    }
}