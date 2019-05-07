package com.rmboy.simplechart.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;


import com.rmboy.simplechart.R;
import com.rmboy.simplechart.data.ChartData;
import com.rmboy.simplechart.data.HistogramData;

import java.text.DecimalFormat;


public class Histogram extends Chart {
    private int rect_text_color;
    private int rect_text_size;

    public Histogram(Context context) {
        super(context);
    }

    public Histogram(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initStyle(context, attrs);
    }

    public Histogram(Context context, AttributeSet attrs) {
        super(context, attrs);
        initStyle(context, attrs);
    }

    private void initStyle(Context context, AttributeSet attrs) {
        TypedArray types = context.obtainStyledAttributes(attrs, R.styleable.zqxchart_histogram);
        coordinates_color = types.getColor(R.styleable.zqxchart_histogram_hCoordinatesColor, Color.RED);
        rect_text_color = types.getColor(R.styleable.zqxchart_histogram_rectTextColor, Color.BLACK);
        rect_text_size = types.getInteger(R.styleable.zqxchart_histogram_rectTextSize, 22);
        x_text_color = types.getColor(R.styleable.zqxchart_histogram_hxTextColor, Color.BLACK);
        y_text_color = types.getColor(R.styleable.zqxchart_histogram_hyTextColor, Color.BLACK);
        x_text_size = types.getInteger(R.styleable.zqxchart_histogram_hxTextSize, 14);
        y_text_size = types.getInteger(R.styleable.zqxchart_histogram_hyTextSize, 20);
        xpCount = types.getInteger(R.styleable.zqxchart_histogram_hxPointCount, 7);
        ypCount = types.getInteger(R.styleable.zqxchart_histogram_hyPointCount, 5);
        types.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawHistogram(canvas);
    }

    private void drawHistogram(Canvas canvas) {
        if (xData == null) {
            throw new NullPointerException("x Data Null!");
        }
        if (yData == null) {
            throw new NullPointerException("y Data Null!");
        }
        Paint histogramPaint = new Paint();
        histogramPaint.setAntiAlias(true);
        histogramPaint.setStyle(Paint.Style.FILL);
        histogramPaint.setStrokeWidth((float) 5.0);
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(rect_text_size);
        textPaint.setColor(rect_text_color);
        DecimalFormat formater = new DecimalFormat("0");
        for (int i = 0; i < xpCount; i++) {
            try {
                histogramPaint.setColor(getResources().getColor(R.color.color_bar));
            } catch (ArrayIndexOutOfBoundsException e) {
                histogramPaint.setColor(getResources().getColor(R.color.colorPrimary));
            }
            int alpha = anims[i].getAlpha();
            textPaint.setAlpha(alpha);
            histogramPaint.setAlpha(alpha);
            float top = anims[i].getCurrentY();
            float left = oX + xCoordinates[i] - xSpacing / 3;
            float right = oX + xCoordinates[i] + xSpacing / 3;
            String ydata = formater.format(yData[i]);
            int[] textSize = getTextSize(xData[i], textPaint);
            float textY = top - textSize[1] / 2;
            canvas.drawText(ydata, left, textY, textPaint);
            canvas.drawRect(left, top, right, oY, histogramPaint);
        }
    }

    @Override
    public void setChartData(ChartData chartData) {
        super.setChartData(chartData);
        HistogramData histogramData = (HistogramData) chartData;
        this.rect_text_size = getFinalValue(this.rect_text_size, histogramData.getRectTextSize());
        this.rect_text_color = getFinalValue(this.rect_text_color, histogramData.getRectTextColor());
    }
}
