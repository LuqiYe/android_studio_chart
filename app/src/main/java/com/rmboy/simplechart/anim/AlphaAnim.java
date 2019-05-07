package com.rmboy.simplechart.anim;

public class AlphaAnim implements IAnimation {

    @Override
    public void refresh(Anim anim) {
        anim.setAlpha(updateAlpha(anim.getAlpha(), (int) anim.getVelocity()));
    }

    @Override
    public boolean isOver(Anim anim) {
        return anim.getAlpha() == 255;
    }

    private int updateAlpha(int alpha, int velocity) {
        alpha += velocity;
        if (alpha >= 255) {
            alpha = 255;
        }
        return alpha;
    }
}
