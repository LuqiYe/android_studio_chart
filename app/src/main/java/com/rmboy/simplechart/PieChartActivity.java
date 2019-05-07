package com.rmboy.simplechart;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.rmboy.simplechart.data.PieChartData;
import com.rmboy.simplechart.view.PieChart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PieChartActivity extends AppCompatActivity {
    float[] datas;
    String[] showDxdata;
    private List<Map<String, Object>> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        data = (List<Map<String, Object>>) bundle.getSerializable("data");
        Log.v("BookReq", data.get(1).get("month").toString());
        initData(data);
        final PieChart pieChart = (PieChart) findViewById(R.id.piechart);
        final int[] colors = new int[]{Color.RED, Color.BLACK, Color.BLUE, Color.GREEN, Color.GRAY,
                Color.YELLOW, Color.LTGRAY, Color.CYAN, Color.MAGENTA};
        final PieChartData pieChartData = PieChartData.builder()
                .setDatas(datas)
                .setShowXDatas(showDxdata)
                .build();
        pieChart.setChartData(pieChartData);
    }

    private void initData(List<Map<String, Object>> data) {
        if (data != null && data.size() > 0) {
            datas = new float[data.size()];
            showDxdata = new String[data.size()];
            for (int i = 0; i < data.size(); i++) {
                datas[i] = Float.valueOf((String) data.get(i).get("revenue"));
                showDxdata[i] = (String) data.get(i).get("month");
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
