package com.itschool.tensorflowlitetest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ListTestTask extends AsyncTask<Void, Float, @NonNull float[]> {
    Handler mHandler;
    private float[] res = new float[100];


    public ListTestTask(Handler mHandler) {
        this.mHandler = mHandler;
    }

    @Override
    protected @NonNull float[] doInBackground(Void... voids) {
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(4);
                res[i] = ((float) ThreadLocalRandom.current().nextInt(88, 98 + 1));
                publishProgress(res[i]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return new float[0];
    }

    @Override
    protected void onPostExecute(@NonNull float[] floats) {
        Message message = new Message();
        Bundle b = new Bundle();
        b.putFloatArray("res", res);
        message.setData(b);
        mHandler.sendMessage(message);

    }

    @Override
    protected void onProgressUpdate(Float... values) {
        super.onProgressUpdate(values);
        Message message = new Message();
        Bundle b = new Bundle();
        b.putFloat("resProgress", values[0]);
        message.setData(b);
        Log.d("TAG", "task: " + values[0]);
        mHandler.sendMessage(message);
    }
}
