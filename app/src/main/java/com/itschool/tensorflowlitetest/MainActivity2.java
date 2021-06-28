package com.itschool.tensorflowlitetest;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.SingleValueDataSet;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MainActivity2 extends AppCompatActivity {

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(@NonNull Message msg) {
            float resProgress = msg.getData().getFloat("resProgress");
            res.add(resProgress);
            updateGraph(resProgress);

        }
    };
    private List<Integer> xAxis;
    private List<Entry> seriesData;
    private int curr = 0;
    private Mapping series1Mapping;
    private LineDataSet set1;
    private LineDataSet lineDataSet;

    private void updateGraph(float val) {
        Log.d("TAG", String.valueOf(val));
//        seriesData.add(new Entry(xAxis.get(curr), val));
        lineDataSet.addEntry(new Entry((float) xAxis.get(curr), val));
        lineDataSet.notifyDataSetChanged();
//        set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
//        set1.setValues(seriesData);
//        set1.notifyDataSetChanged();
        chart.getData().notifyDataChanged();
        chart.notifyDataSetChanged();
        chart.invalidate();
//        Set set = Set.instantiate();
//        set.data(seriesData);
//        series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        if (curr < 99) curr++;
    }

    private List<Float> res = new ArrayList<>();
    private Map<String, Float> result;
    private LineChart chart;

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        xAxis = Stream
                .iterate(0, x -> x + 100)
                .limit(100)
                .collect(Collectors.toList());
        ListTestTask listTestTask = new ListTestTask(mHandler);
        listTestTask.execute();
        TextView tw = findViewById(R.id.textView);
        tw.setText("Model: \"sequential_2\"\n" +
                "________________________________________________\n" +
                "Layer (type)       Output Shape        Param #   \n" +
                "=======================================\n" +
                "dense_16 (Dense)   (None, 1, 42)       210       \n" +
                "_________________________________________________\n" +
                "dense_17 (Dense)   (None, 1, 1)        43        \n" +
                "=======================================\n" +
                "Total params: 253\n" +
                "Trainable params: 253\n" +
                "Non-trainable params: 0");


        lineDataSet = new LineDataSet(seriesData, "Точность");
        lineDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        lineDataSet.setFillAlpha(110);
        chart = findViewById(R.id.chart);
        YAxis rightYAxis = chart.getAxisLeft();
        rightYAxis.setLabelCount(7);
        chart.getXAxis().setLabelRotationAngle(90);
        chart.getXAxis().setGranularityEnabled(true);
        chart.getXAxis().setGranularity(100f);
        chart.setData(new LineData(lineDataSet));
//        chart.setVisibleXRangeMaximum(100);
//

        // anyChartView.setProgressBar(findViewById(R.id.progress_bar));
//        seriesData = new ArrayList<>();
//
//        Cartesian cartesian = AnyChart.line();
//
//        cartesian.animation(true);
//
//        cartesian.padding(10d, 20d, 5d, 20d);
//
////        cartesian.crosshair().enabled(true);
////        cartesian.crosshair()
////                .yLabel(true)
////                // TODO ystroke
////                .yStroke((Stroke) null, null, null, (String) null, (String) null);
//
//        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
//
//        cartesian.title("Точность");
//
//        cartesian.yAxis(0).title("Точность, %");
//        cartesian.xAxis(0).title("Семплы");
//        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);
//        cartesian.line(seriesData);
//
//
////
////
////        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value' }");
////        Mapping series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }");
//
////        Line series1 =
//
////        series1.name("LA-32");
////        series1.hovered().markers().enabled(true);
////        series1.hovered().markers()
////                .type(MarkerType.CIRCLE)
////                .size(4d);
////        series1.tooltip()
////                .position("right")
////                .anchor(Anchor.LEFT_CENTER)
////                .offsetX(5d)
////                .offsetY(5d);
//
////        Line series2 = cartesian.line(series2Mapping);
////        series2.name("LA-quant");
////        series2.hovered().markers().enabled(true);
////        series2.hovered().markers()
////                .type(MarkerType.CIRCLE)
////                .size(4d);
////        series2.tooltip()
////                .position("right")
////                .anchor(Anchor.LEFT_CENTER)
////                .offsetX(5d)
////                .offsetY(5d);
//
////        Line series3 = cartesian.line(series3Mapping);
////        series3.name("LA-orig");
////        series3.hovered().markers().enabled(true);
////        series3.hovered().markers()
////                .type(MarkerType.CIRCLE)
////                .size(4d);
//        series3.tooltip()
//                .position("right")
//                .anchor(Anchor.LEFT_CENTER)
//                .offsetX(5d)
//                .offsetY(5d);

//        cartesian.legend().enabled(true);
//        cartesian.legend().fontSize(13d);
//        cartesian.legend().padding(0d, 0d, 10d, 0d);

//        anyChartView.setChart(cartesian);

        @SuppressLint({"NewApi", "LocalSuppress"}) List<String> tests = List.of("01: test val 4/epoch loss: 0.0924", "02: test val 5/epoch loss:0.0932");

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyRecycleViewAdapter adapter = new MyRecycleViewAdapter(this, tests);

        recyclerView.setAdapter(adapter);
    }

    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value) {
            super(x, value);
        }

    }

    Map<String, Float> combineListsIntoOrderedMap(List<String> keys, List<Float> values) {
        if (keys.size() != values.size())
            throw new IllegalArgumentException("Cannot combine lists with dissimilar sizes");
        Map<String, Float> map = new LinkedHashMap<String, Float>();
        for (int i = 0; i < keys.size(); i++) {
            map.put(keys.get(i), values.get(i));
        }
        return map;
    }
}