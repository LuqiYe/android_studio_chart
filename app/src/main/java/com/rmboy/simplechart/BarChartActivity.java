package com.rmboy.simplechart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.rmboy.simplechart.anim.Anim;
import com.rmboy.simplechart.data.HistogramData;
import com.rmboy.simplechart.view.Histogram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BarChartActivity extends AppCompatActivity {
    String[] xdata;
    int[] ydata;
    private List<Map<String, Object>> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        data = (List<Map<String, Object>>) bundle.getSerializable("data");
        Log.v("BookReq", data.get(1).get("month").toString());
        initData(data);
        final Histogram histogramChart = (Histogram) findViewById(R.id.histogramchart);
        final HistogramData histogramData = HistogramData.builder()
                .setXdata(xdata)
                .setYdata(ydata)
                .setXpCount(xdata.length)
                .setYpCount(xdata.length)
                .build();
        histogramChart.setChartData(histogramData);
    }

    private void initData(List<Map<String, Object>> data) {
        if (data != null && data.size() > 0) {
            xdata = new String[data.size()];
            ydata = new int[data.size()];
            for (int i = 0; i < data.size(); i++) {
                xdata[i] = (String) data.get(i).get("month");
                ydata[i] = Integer.valueOf((String) data.get(i).get("revenue"));
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
