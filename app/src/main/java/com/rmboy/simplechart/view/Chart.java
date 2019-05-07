package com.rmboy.simplechart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.rmboy.simplechart.anim.AlphaAnim;
import com.rmboy.simplechart.anim.Anim;
import com.rmboy.simplechart.anim.TranslateAnim;
import com.rmboy.simplechart.data.ChartData;

import java.text.DecimalFormat;

public class Chart extends View {

    protected final int xSurplus = 50;
    protected final int ySurplus = 40;
    protected final int xTextSurplus = 100;
    protected final int yTextSurplus = 100;
    protected float oX;
    protected float oY;
    protected int xWidth;
    protected int yHeight;
    protected int xSpacing;
    protected int ySpacing;
    protected int xpCount = 7;
    protected int ypCount = 5;
    protected int[] xCoordinates;
    protected int[] yCoordinates;
    protected float yMax = 0f;
    protected String[] xData;
    protected int[] yData;
    protected int coordinates_color;
    protected int x_text_size;
    protected int y_text_size;
    protected int x_text_color;
    protected int y_text_color;
    protected boolean isAnim = true;
    protected Anim[] anims;
    protected long interval = 100;
    protected int animType = -2;

    public Chart(Context context) {
        super(context);
    }

    public Chart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Chart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        initChartProp();
        initCoordinateSystem(canvas);
        if (isAnim){
            initAnims();
            post(animator);
            isAnim = false;
        }
    }

    private void initChartProp() {
        xWidth = getWidth() - getPaddingLeft() - getPaddingRight() - yTextSurplus;
        yHeight = getHeight() - getPaddingTop() - getPaddingBottom() - xTextSurplus;
        xSpacing = (xWidth-xSurplus-xWidth%xpCount)/xpCount;
        ySpacing = (yHeight-ySurplus-yHeight%ypCount)/ypCount;
        oX = getPaddingLeft()+yTextSurplus;
        oY = getPaddingTop()+yHeight+xTextSurplus/2;
        xCoordinates = new int[xpCount];
        yCoordinates = new int[ypCount];
        for (int i=0; i<xpCount; i++){
            xCoordinates[i] = (i+1) * xSpacing;
        }
        for (int j=0; j<ypCount; j++){
            yCoordinates[j] = (j+1) * ySpacing;
        }
    }

    private void initCoordinateSystem(Canvas canvas){
        Paint mainPaint = new Paint();
        mainPaint.setColor(coordinates_color);
        mainPaint.setAntiAlias(true);
        canvas.drawLine(oX,oY, oX,oY-yHeight,mainPaint);
        canvas.drawLine(oX,oY,oX+xWidth,oY,mainPaint);
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        for (int i=0; i<xCoordinates.length; i++){
            textPaint.setTextSize(x_text_size);
            textPaint.setColor(x_text_color);
            canvas.drawLine(oX+xCoordinates[i],oY,oX+xCoordinates[i],oY-5,mainPaint);
            int[] textSize = getTextSize(xData[i],textPaint);
            int textX = textSize[0]/2;
            int textY = xTextSurplus/2 - textSize[1]/2;
            canvas.drawText(xData[i],oX+xCoordinates[i]-textX,oY+textY,textPaint);
        }
        if(yMax == 0f){
            yMax = getYMax();
        }
        for (int j=0; j<ypCount; j++){
            textPaint.setTextSize(y_text_size);
            textPaint.setColor(y_text_color);
            canvas.drawLine(oX,oY-yCoordinates[j],oX+5,oY-yCoordinates[j],mainPaint);
            int iYMax = (int)yMax;
            String datay;
            if (yMax - iYMax == 0){
                datay = (int)(yMax/ypCount*(j+1))+"";
            }else {
                datay = new DecimalFormat("0.000").format(yMax/ypCount*(j+1));
            }
            int[] textSize = getTextSize(datay,textPaint);
            int textX = yTextSurplus/2 + textSize[0]/2;
            int textY = textSize[1]/2;
            canvas.drawText(datay,oX-textX,oY-yCoordinates[j]+textY,textPaint);
        }
    }

    private void initAnims() {
        anims = new Anim[xpCount];
        switch (animType){
            case Anim.ANIM_TRANSLATE:
                for (int i=0;i<xpCount;i++){
                    float dataX = oX+xCoordinates[i];
                    float dataY = oY-yData[i]/yMax*yCoordinates[yCoordinates.length-1];
                    Anim anim = new Anim(dataX,dataY,dataX,oY);
                    anim.setAnimation(new TranslateAnim());
                    anim.setVelocity(interval*2);
                    anims[i] = anim;
                }
                break;
            case Anim.ANIM_ALPHA:
                for (int i=0;i<xpCount;i++){
                    float dataX = oX+xCoordinates[i];
                    float dataY = oY-yData[i]/yMax*yCoordinates[yCoordinates.length-1];
                    Anim anim = new Anim(dataX,dataY,dataX,dataY);
                    anim.setAnimation(new AlphaAnim());
                    anim.setAlpha(0);
                    anim.setVelocity(interval * 3/2);
                    anims[i] = anim;
                }
                break;
            default:
                for (int i=0;i<xpCount;i++){
                    float dataX = oX+xCoordinates[i];
                    float dataY = oY-yData[i]/yMax*yCoordinates[yCoordinates.length-1];
                    Anim anim = new Anim(dataX,dataY,dataX,dataY);
                    anim.setAnimation(new TranslateAnim());
                    anim.setVelocity(interval);
                    anims[i] = anim;
                }
                break;
        }
    }

    protected float getYMax(){
        if (yData == null || yData.length == 0){
            yData = new int[ypCount];
        }
        float max = 1f;
        for (int i=0; i<yData.length; i++){
            if (max < yData[i]){
                max = yData[i];
            }
        }
        return max;
    }

    protected int[] getTextSize(String str, Paint paint){
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        int w = rect.width();
        int h = rect.height();
        return new int[]{w,h};
    }

    public void setChartData(ChartData chartData) {
        if (chartData == null){
            throw new NullPointerException("Data ERROR");
        }
        this.xData = chartData.getXdata();
        this.yData = chartData.getYdata();
        this.xpCount = getFinalValue(this.xpCount,chartData.getXpCount());
        this.ypCount = getFinalValue(this.ypCount,chartData.getYpCount());
        this.coordinates_color = getFinalValue(this.coordinates_color,chartData.getCoordinatesColor());
        this.x_text_size = getFinalValue(this.x_text_size,chartData.getxTextSize());
        this.y_text_size = getFinalValue(this.y_text_size,chartData.getyTextSize());
        this.x_text_color = getFinalValue(this.x_text_color,chartData.getxTextColor());
        this.y_text_color = getFinalValue(this.y_text_color,chartData.getyTextColor());
        this.interval = getFinalValue((int) this.interval,chartData.getInterval());
        this.yMax = chartData.getyMax() != 0f ? chartData.getyMax() : this.yMax;
    }

    protected int getFinalValue(int old,int news){
        old = news != 0 ? news : old;
        return old;
    }

    private Runnable animator = new Runnable() {
        @Override
        public void run() {
            for(Anim a : anims){
                if(!a.isOver()){
                    a.refresh();
                    postDelayed(this, interval);
                    invalidate();
                    return;
                }
            }
        }
    };
}
