package com.rmboy.simplechart.data;

public class PieChartData {
    private float[] datas;
    private String[] showXDatas;
    private int[] colors;
    private float separationDegree = 2;
    private int textColor = 0;
    private int textSize = 0;
    private int animType = -2;

    private PieChartData(Builder builder) {
        setDatas(builder.datas);
        setShowXDatas(builder.showXDatas);
        setColors(builder.colors);
        setTextColor(builder.textColor);
        setTextSize(builder.textSize);
        setSeparationDegree(builder.separationDegree);
        setAnimType(builder.animType);
    }

    public static Builder builder() {
        return new Builder();
    }

    public float[] getDatas() {
        return datas;
    }

    public String[] getShowXDatas() {
        return showXDatas;
    }


    public int[] getColors() {
        return colors;
    }

    public float getSeparationDegree() {
        return separationDegree;
    }

    public int getTextColor() {
        return textColor;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setDatas(float[] datas) {
        this.datas = datas;
    }

    public void setShowXDatas(String[] datas) {
        this.showXDatas = datas;
    }

    public void setColors(int[] colors) {
        this.colors = colors;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setSeparationDegree(float separationDegree) {
        this.separationDegree = separationDegree;
    }

    public int getAnimType() {
        return animType;
    }

    public void setAnimType(int animType) {
        this.animType = animType;
    }


    public static final class Builder {
        private float[] datas;
        private String[] showXDatas;
        private int[] colors;
        private int textColor = 0;
        private int textSize = 0;
        private float separationDegree = 2;
        private int animType = -2;

        public Builder setDatas(float[] datas) {
            this.datas = datas;
            return this;
        }

        public Builder setShowXDatas(String[] datas) {
            this.showXDatas = datas;
            return this;
        }

        public Builder setColors(int[] colors) {
            this.colors = colors;
            return this;
        }

        public Builder setSeparationDegree(float separationDegree) {
            this.separationDegree = separationDegree;
            return this;
        }

        public Builder setTextColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        public Builder setTextSize(int textSize) {
            this.textSize = textSize;
            return this;
        }

        public Builder setAnimType(int animType) {
            this.animType = animType;
            return this;
        }

        public PieChartData build() {
            return new PieChartData(this);
        }
    }
}
