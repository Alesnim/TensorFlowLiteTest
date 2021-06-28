package com.itschool.tensorflowlitetest;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


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

        AnyChartView anyChartView = findViewById(R.id.chart);
        // anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        Cartesian cartesian = AnyChart.line();

        cartesian.animation(true);

        cartesian.padding(10d, 20d, 5d, 20d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                // TODO ystroke
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        cartesian.title("ROC-кривая");

        cartesian.yAxis(0).title("False Pos");
        cartesian.xAxis(0).title("True Pos");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        List<DataEntry> seriesData = new ArrayList<>();
        seriesData.add(new CustomDataEntry("0.0", 3.6, 2.3, 2.8));
        seriesData.add(new CustomDataEntry("0.01", 7.1, 4.0, 4.1));
        seriesData.add(new CustomDataEntry("0.02", 8.5, 6.2, 5.1));
        seriesData.add(new CustomDataEntry("0.03", 9.2, 11.8, 6.5));
        seriesData.add(new CustomDataEntry("0.04", 10.1, 13.0, 12.5));
        seriesData.add(new CustomDataEntry("0.05", 11.6, 13.9, 18.0));
        seriesData.add(new CustomDataEntry("0.06", 16.4, 18.0, 21.0));
        seriesData.add(new CustomDataEntry("0.06", 18.0, 23.3, 20.3));
        seriesData.add(new CustomDataEntry("0.07", 13.2, 24.7, 19.2));
        seriesData.add(new CustomDataEntry("0.08", 12.0, 18.0, 14.4));
        seriesData.add(new CustomDataEntry("0.09", 3.2, 15.1, 9.2));
        seriesData.add(new CustomDataEntry("0.1", 4.1, 11.3, 5.9));
        seriesData.add(new CustomDataEntry("0.11", 6.3, 14.2, 5.2));
        seriesData.add(new CustomDataEntry("0.12", 9.4, 13.7, 4.7));
        seriesData.add(new CustomDataEntry("0.13", 11.5, 9.9, 4.2));
        seriesData.add(new CustomDataEntry("0.14", 13.5, 12.1, 1.2));
        seriesData.add(new CustomDataEntry("0.15", 14.8, 13.5, 5.4));
        seriesData.add(new CustomDataEntry("0.16", 16.6, 15.1, 6.3));
        seriesData.add(new CustomDataEntry("0.17", 18.1, 17.9, 8.9));
        seriesData.add(new CustomDataEntry("0.18", 17.0, 18.9, 10.1));
        seriesData.add(new CustomDataEntry("0.19", 16.6, 20.3, 11.5));
        seriesData.add(new CustomDataEntry("0.2", 14.1, 20.7, 12.2));
        seriesData.add(new CustomDataEntry("0.21", 15.7, 21.6, 10));
        seriesData.add(new CustomDataEntry("0.22", 12.0, 22.5, 8.9));

        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");
        Mapping series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.name("LA-32");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series2 = cartesian.line(series2Mapping);
        series2.name("LA-quant");
        series2.hovered().markers().enabled(true);
        series2.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series2.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series3 = cartesian.line(series3Mapping);
        series3.name("LA-orig");
        series3.hovered().markers().enabled(true);
        series3.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series3.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

        anyChartView.setChart(cartesian);

        List<String> tests = List.of("01: test val 4/epoch loss: 0.0924", "02: test val 5/epoch loss:0.0932");

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyRecycleViewAdapter adapter = new MyRecycleViewAdapter(this, tests);
        recyclerView.setAdapter(adapter);
    }

    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value, Number value2, Number value3) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
        }

    }
}