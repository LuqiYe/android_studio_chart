package com.rmboy.simplechart.anim;

public class Anim {
    public static final int ANIM_NONE = -1;
    public static final int ANIM_TRANSLATE = 0;
    public static final int ANIM_ALPHA = 1;
    private float finalX;
    private float finalY;
    private float currentX;
    private float currentY;
    private float velocity = 100;
    private int alpha = 255;
    private IAnimation animation;

    public Anim(float finalX, float finalY, float currentX, float currentY) {
        this.finalX = finalX;
        this.finalY = finalY;
        this.currentX = currentX;
        this.currentY = currentY;
    }

    public void refresh(){
        if (animation != null){
            animation.refresh(this);
        }
    }

    public boolean isOver(){
       return animation.isOver(this);
    }

    public void setAnimation(IAnimation animation) {
        this.animation = animation;
    }

    public float getFinalX() {
        return finalX;
    }

    public void setFinalX(float finalX) {
        this.finalX = finalX;
    }

    public float getFinalY() {
        return finalY;
    }

    public void setFinalY(float finalY) {
        this.finalY = finalY;
    }

    public float getCurrentX() {
        return currentX;
    }

    public void setCurrentX(float currentX) {
        this.currentX = currentX;
    }

    public float getCurrentY() {
        return currentY;
    }

    public void setCurrentY(float currentY) {
        this.currentY = currentY;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }
}
