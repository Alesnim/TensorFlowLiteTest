package com.itschool.tensorflowlitetest;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.itschool.tensorflowlitetest.classifier.Classifier;
import com.itschool.tensorflowlitetest.classifier.Device;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.units.qual.C;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class PredictTask extends AsyncTask<Void, Void, @NonNull float[]> {
    Handler mHandler;
    static Context context;
    Interpreter interpreter;
    byte inOne, inTwo;

    int[] result;

    public PredictTask(Context context, Handler handler, AssetManager assetManager) {
        this.context = context;
        mHandler = handler;

        interpreter = new Interpreter(createInter(assetManager));
    }

    public PredictTask(Context co, Handler mHandler, AssetManager assets, boolean oneIn, boolean twoIn) {
        this(co, mHandler, assets);
        inOne = (byte) ((oneIn) ? 1 : 0);
        inTwo = (byte) ((twoIn) ? 1 : 0);

    }

    public MappedByteBuffer createInter(AssetManager assetManager) {
        AssetFileDescriptor fileDescriptor = null;
        try {
            fileDescriptor = assetManager.openFd("converted_model2.tflite");
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        try {
            return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected @NonNull float[] doInBackground(Void... voids) {
        ByteBuffer temp = ByteBuffer.allocateDirect(2); // аллоцируем два байта под входные значения
        temp.put(inOne);
        temp.put(inTwo);

        // создаем тензор с входными данными
        TensorBuffer tensorBuffer = TensorBuffer.createFixedSize(new int[]{1, 1, 2},
                interpreter.getInputTensor(0).dataType());
        tensorBuffer.loadBuffer(temp);
        Classifier classifier = new Classifier(context, Device.CPU, 4, "converted_model2.tflite");
        return new float[]{0,0,0};
//        ConvertedModel2 model = null;
//
//
//        try {
//            model = ConvertedModel2.newInstance(context);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        ConvertedModel2.Outputs outputs = model.process(tensorBuffer);
//
//        TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
//
//        return outputFeature0.getFloatArray();
    }


    @Override
    protected void onPostExecute(@NonNull float[] objects) {
        Message message = new Message();
        Bundle b = new Bundle();
        b.putFloatArray("res", objects);
        message.setData(b);
        mHandler.sendMessage(message);
    }


}
