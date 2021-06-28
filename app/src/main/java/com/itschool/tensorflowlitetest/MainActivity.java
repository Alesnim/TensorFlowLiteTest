package com.itschool.tensorflowlitetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class MainActivity extends AppCompatActivity {
    TextView tw_res;
    SwitchMaterial input;
    SwitchMaterial inputSecond;
    boolean oneIn, twoIn;

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(@NonNull Message msg) {

             float[] res = msg.getData().getFloatArray("res");
            setText(res[0]);

        }
    };

    private void setText(float re) {
        if (re >= 0.5) {
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
                (buttonView, isChecked) -> twoIn = !twoIn);


        Button b = findViewById(R.id.button);
        b.setOnClickListener((v) -> {
            Intent i = new Intent(this, MainActivity2.class);
            startActivity(i);
        });

        Button mnist = findViewById(R.id.bt_mnist);
        mnist.setOnClickListener((view -> {
            Intent i = new Intent(this, Mnist.class);
            startActivity(i);
        }));

    }

    public void startNetwork(View view) {
        new PredictTask(this, mHandler, getAssets(), oneIn, twoIn).execute();

    }
}