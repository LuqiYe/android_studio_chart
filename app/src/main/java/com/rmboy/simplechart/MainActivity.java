package com.rmboy.simplechart;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private ListView ll_data;
    private List<Map<String, String>> data;
    private SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        ll_data = findViewById(R.id.ll_data);
        findViewById(R.id.bn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMsgDialog();
            }
        });
        findViewById(R.id.bn_pie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PieChartActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", (Serializable) data);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        findViewById(R.id.bn_bar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BarChartActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", (Serializable) data);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        data = getData();
        adapter = new SimpleAdapter(this, data,
                R.layout.my_simple_list_item, new String[]{"month",
                "revenue"}, new int[]{R.id.text1,
                R.id.text2});
        ll_data.setAdapter(adapter);
    }

    private List<Map<String, String>> getData() {
        Map<String, String> item = new HashMap<String, String>();
        item.put("month", "January");
        item.put("revenue", "100");
        Map<String, String> item1 = new HashMap<String, String>();
        item1.put("month", "February");
        item1.put("revenue", "105");
        Map<String, String> item2 = new HashMap<String, String>();
        item2.put("month", "March");
        item2.put("revenue", "160");
        Map<String, String> item3 = new HashMap<String, String>();
        item3.put("month", "April");
        item3.put("revenue", "150");
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        data.add(item);
        data.add(item1);
        data.add(item2);
        data.add(item3);
        return data;
    }

    private AlertDialog msgDialog;

    private void showMsgDialog() {
        int size = data.size();
        if (size > 11) {
            Toast.makeText(mContext, "Enough Data", Toast.LENGTH_SHORT).show();
            return;
        }
        String title = "";
        switch (size) {
            case 4:
                title = "May";
                break;
            case 5:
                title = "June";
                break;
            case 6:
                title = "July";
                break;
            case 7:
                title = "August";
                break;
            case 8:
                title = "September";
                break;
            case 9:
                title = "October";
                break;
            case 10:
                title = "November";
                break;
            case 11:
                title = "December";
                break;
            default:
                return;

        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.dialogStyle);
        View view = View.inflate(mContext, R.layout.dialog_input, null);
        TextView tv_title = view.findViewById(R.id.tv_title);
        final EditText et_input = view.findViewById(R.id.et_input);
        Button bn_pie = view.findViewById(R.id.bn_pie);
        tv_title.setText("Input Data For " + title);
        final String finalTitle = title;
        bn_pie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> item3 = new HashMap<String, String>();
                item3.put("month", finalTitle);
                item3.put("revenue", et_input.getText().toString());
                data.add(item3);
                adapter.notifyDataSetChanged();
                if (msgDialog != null) {
                    msgDialog.dismiss();
                }
            }
        });
        msgDialog = builder.create();
        msgDialog.show();
        WindowManager windowManager = getWindowManager();
        Display d = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = msgDialog.getWindow().getAttributes();
        lp.height = (int) (d.getHeight() * 0.5);
        lp.width = (int) (d.getWidth() * 0.95);
        lp.gravity = Gravity.CENTER;
        msgDialog.getWindow().setAttributes(lp);
        msgDialog.getWindow().setContentView(view);
        msgDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
    }
}
