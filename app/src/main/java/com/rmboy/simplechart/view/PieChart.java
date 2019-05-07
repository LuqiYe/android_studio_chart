package com.rmboy.simplechart.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import com.rmboy.simplechart.R;
import com.rmboy.simplechart.anim.AlphaAnim;
import com.rmboy.simplechart.anim.Anim;
import com.rmboy.simplechart.anim.TranslateAnim;
import com.rmboy.simplechart.data.PieChartData;

import java.text.DecimalFormat;

public class PieChart extends View {
    private float oX;
    private float oY;
    private float left;
    private float top;
    private float right;
    private float bottom;
    private float pieMax;
    private float surplus = 50;
    private float separationDegree = 2;
    private float[] datas;
    private String[] showXData;
    private int[] colors;
    private Sector[] sectors;
    protected boolean isAnim = true;
    protected Anim[] anims;
    protected long interval = 50;
    private int animType = -2;
    private int currentSel = 0;
    private float pieRadius = 0f;
    private float cCircleNarrow = 4;
    private int textColor = 0;
    private float cTextSize = 0;

    public PieChart(Context context) {
        super(context);
    }

    public PieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        initColors(context, attrs);
    }

    public PieChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initColors(context, attrs);
    }

    private void initColors(Context context, AttributeSet attrs) {
        TypedArray types = context.obtainStyledAttributes(attrs, R.styleable.zqxchart_pie);
        textColor = types.getColor(R.styleable.zqxchart_pie_textColor, Color.WHITE);
        cTextSize = types.getInteger(R.styleable.zqxchart_pie_textSize, 0);
        separationDegree = types.getInteger(R.styleable.zqxchart_pie_separationDegree, 2);
        animType = types.getInteger(R.styleable.zqxchart_pie_panimType, Anim.ANIM_NONE);
        colors = getResources().getIntArray(R.array.pie_colors);
        types.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        initPieProp();
        if (isAnim) {
            initAnims();
            post(animator);
            isAnim = false;
        }
        drawPieChart(canvas);
    }

    private void initPieProp() {
        if (datas == null) {
            throw new NullPointerException("Data Null");
        }
        int width = getWidth();
        int height = getHeight();
        int sur = 0;
        if (width >= height) {
            sur = (width - height) / 2;
            left = getPaddingLeft() + surplus + sur;
            top = getPaddingTop() + surplus;
            right = width - getPaddingRight() - surplus - sur;
            bottom = height - getPaddingBottom() - surplus;
        } else {
            sur = (height - width) / 2;
            left = getPaddingLeft() + surplus;
            top = getPaddingTop() + surplus + sur;
            right = width - getPaddingRight() - surplus;
            bottom = height - getPaddingBottom() - surplus - sur;
        }
        pieRadius = (right - left) / 2;
        oX = left + (right - left) / 2;
        oY = top + (bottom - top) / 2;
        float allsDegree = separationDegree * datas.length;
        float max = 0;
        for (int i = 0; i < datas.length; i++) {
            max += datas[i];
        }
        sectors = new Sector[datas.length];
        float start = 0f;
        for (int j = 0; j < datas.length; j++) {
            float percent = datas[j] / max;
            float finalDegree = percent * (360f - allsDegree);
            Sector sector = new Sector();
            sector.startDegree = start;
            sector.endDegree = start + finalDegree;
            sector.percent = percent;
            sectors[j] = sector;
            start += finalDegree + separationDegree;
        }
        pieMax = max;
    }

    private void initAnims() {
        anims = new Anim[datas.length];
        switch (animType) {
            case Anim.ANIM_TRANSLATE:
                /*for (int i=0;i<anims.length;i++){
                    Anim anim = new Anim(left,top,oX,oY);
                    anim.setAnimation(new TranslateAnim());
                    anim.setVelocity(interval*2);
                    anims[i] = anim;
                }*/
                for (int i = 0; i < anims.length; i++) {
                    Anim anim = new Anim(left, top, left, top);
                    anim.setAnimation(new TranslateAnim());
                    anim.setVelocity(interval);
                    anims[i] = anim;
                }
                break;
            case Anim.ANIM_ALPHA:
                for (int i = 0; i < anims.length; i++) {
                    Anim anim = new Anim(left, top, left, top);
                    anim.setAnimation(new AlphaAnim());
                    anim.setAlpha(0);
                    anim.setVelocity(interval * 4);
                    anims[i] = anim;
                }
                break;
            default:
                for (int i = 0; i < anims.length; i++) {
                    Anim anim = new Anim(left, top, left, top);
                    anim.setAnimation(new TranslateAnim());
                    anim.setVelocity(interval);
                    anims[i] = anim;
                }
                break;
        }
    }

    private void drawPieChart(Canvas canvas) {
        if (datas == null) {
            throw new NullPointerException("Data Null");
        }
        Paint p = new Paint();
        p.setAntiAlias(true);
        RectF rectF;
        DecimalFormat formater = new DecimalFormat("0.0%");
        for (int i = 0; i < datas.length; i++) {
            rectF = new RectF(anims[i].getCurrentX(), anims[i].getCurrentY(), right, bottom);
            p.setColor(colors[i]);
            p.setTextSize(20);
            try {
                p.setColor(colors == null
                        ? getResources().getColor(R.color.colorPrimary)
                        : colors[i]);
            } catch (ArrayIndexOutOfBoundsException e) {
                p.setColor(getResources().getColor(R.color.colorPrimary));
            }
            p.setAlpha(anims[i].getAlpha());
            Sector sector = sectors[i];
            /*if(currentSel == i){
                canvas.drawArc(new RectF(rectF.left+20,rectF.top,rectF.right+20,rectF.bottom),
                        sector.startDegree, sector.endDegree, true, p);
            }else{*/
            canvas.drawArc(rectF, sector.startDegree, sector.endDegree - sector.startDegree, true, p);
            if (i < 3) {
                canvas.drawText(showXData[i], left, i * 25 + bottom + 30, p);
            } else if (3 <= i && i < 6) {
                canvas.drawText(showXData[i], left + oX / 3, (i - 3) * 25 + bottom + 30, p);
            } else if (6 <= i && i < 9) {
                canvas.drawText(showXData[i], left + oX / 3 * 2, (i - 6) * 25 + bottom + 30, p);
            } else {
                canvas.drawText(showXData[i], left + oX + 30, (i - 9) * 25 + bottom + 30, p);
            }
            //}
        }
        /*Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(oX,oY,(right-left)/cCircleNarrow,paint);
        paint.setColor(colors[currentSel]);
        canvas.drawCircle(oX,oY,(right-left)/(cCircleNarrow+1),paint);
        paint.setColor(textColor != 0 ? textColor : Color.WHITE);
        String precent = formater.format(sectors[currentSel].percent);
        paint.setTextSize(cTextSize != 0 ? cTextSize : pieRadius/6);
        int[] textSize = getTextSize(precent,paint);
        canvas.drawText(precent,oX-textSize[0]/2,oY+textSize[1]/2,paint);*/
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSpecSize = measureWidth(heightMeasureSpec);
        setMeasuredDimension(wSpecSize, hSpecSize);
    }

    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = 400;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return findSelSector(event.getX(), event.getY());
            case MotionEvent.ACTION_UP:
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    private boolean findSelSector(float x, float y) {
        double distance = getDistance(x, y, oX, oY);
        if (distance > pieRadius || distance < pieRadius / 2) {
            return false;
        }
        double degree = Math.atan2(x - oX, y - oY);
        degree = -(Math.toDegrees(degree) - 90);
        degree = degree <= 0 ? degree + 360 : degree;
        int index = 0;
        for (Sector sector : sectors) {
            if (degree >= sector.startDegree && degree <= sector.endDegree) {
                currentSel = index;
                return true;
            }
            index++;
        }
        return false;
    }

    private double getDistance(double x1, double y1, double x2, double y2) {
        double d = (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1);
        return Math.sqrt(d);
    }

    protected int[] getTextSize(String str, Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        int w = rect.width();
        int h = rect.height();
        return new int[]{w, h};
    }


    private Runnable animator = new Runnable() {
        @Override
        public void run() {
            for (Anim a : anims) {
                if (!a.isOver()) {
                    a.refresh();
                    postDelayed(this, interval);
                    invalidate();
                    return;
                }
            }
        }
    };


    public void update(PieChartData pieChartData) {
        setChartData(pieChartData);
        isAnim = true;
        invalidate();
    }

    public void setChartData(PieChartData pieChartData) {
        datas = pieChartData.getDatas();
        showXData = pieChartData.getShowXDatas();
        if (datas == null) {
            throw new NullPointerException("Data Error");
        }
        if (showXData == null) {
            throw new NullPointerException("Data Error");
        }
        colors = pieChartData.getColors() == null ? colors : pieChartData.getColors();
        separationDegree = pieChartData.getSeparationDegree() == 2 ? separationDegree
                : pieChartData.getSeparationDegree();
        textColor = pieChartData.getTextColor() == 0 ? textColor : pieChartData.getTextColor();
        cTextSize = pieChartData.getTextSize() == 0 ? cTextSize : pieChartData.getTextSize();
        this.animType = pieChartData.getAnimType() != -2 ? pieChartData.getAnimType()
                : this.animType;
    }

    private class Sector {
        public float startDegree;
        public float endDegree;
        public float percent;
    }
}
