package com.rmboy.simplechart.data;

public class ChartData {
    protected String[] xdata;
    protected int[] ydata;
    protected int xpCount;
    protected int ypCount;
    protected int coordinatesColor;
    protected int xTextSize;
    protected int yTextSize;
    protected int xTextColor;
    protected int yTextColor;
    protected int yMax;
    protected int interval;
    protected int animType = -2;

    protected ChartData(){}

    public String[] getXdata() {
        return xdata;
    }

    public void setXdata(String[] xdata) {
        this.xdata = xdata;
    }

    public int[] getYdata() {
        return ydata;
    }

    public void setYdata(int[] ydata) {
        this.ydata = ydata;
    }

    public int getXpCount() {
        return xpCount;
    }

    public void setXpCount(int xpCount) {
        this.xpCount = xpCount;
    }

    public int getYpCount() {
        return ypCount;
    }

    public void setYpCount(int ypCount) {
        this.ypCount = ypCount;
    }

    public int getCoordinatesColor() {
        return coordinatesColor;
    }

    public void setCoordinatesColor(int coordinatesColor) {
        this.coordinatesColor = coordinatesColor;
    }

    public int getxTextSize() {
        return xTextSize;
    }

    public void setxTextSize(int xTextSize) {
        this.xTextSize = xTextSize;
    }

    public int getyTextSize() {
        return yTextSize;
    }

    public void setyTextSize(int yTextSize) {
        this.yTextSize = yTextSize;
    }

    public int getxTextColor() {
        return xTextColor;
    }

    public void setxTextColor(int xTextColor) {
        this.xTextColor = xTextColor;
    }

    public int getyTextColor() {
        return yTextColor;
    }

    public void setyTextColor(int yTextColor) {
        this.yTextColor = yTextColor;
    }

    public int getyMax() {
        return yMax;
    }

    public void setyMax(int yMax) {
        this.yMax = yMax;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getAnimType() {
        return animType;
    }

    public void setAnimType(int animType) {
        this.animType = animType;
    }
}
