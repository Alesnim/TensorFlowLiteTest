package com.itschool.tensorflowlitetest;

import android.os.AsyncTask;

import org.checkerframework.checker.nullness.qual.NonNull;

public class ListTestTask extends AsyncTask<Void, Void, @NonNull float[]> {
    @Override
    protected @NonNull float[] doInBackground(Void... voids) {
        return new float[0];
    }
}
