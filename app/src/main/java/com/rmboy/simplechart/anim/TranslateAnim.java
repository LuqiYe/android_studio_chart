package com.rmboy.simplechart.anim;


public class TranslateAnim implements IAnimation {

    @Override
    public void refresh(Anim anim) {
        anim.setCurrentX(getCurrent(anim.getCurrentX(),anim.getFinalX(),anim.getVelocity()));
        anim.setCurrentY(getCurrent(anim.getCurrentY(),anim.getFinalY(),anim.getVelocity()));
    }

    @Override
    public boolean isOver(Anim anim) {
        return anim.getCurrentX() == anim.getFinalX() && anim.getCurrentY() == anim.getFinalY();
    }


    private float getCurrent(float curr, float finals, float velocity) {
        if(curr < finals) {
            curr += velocity;
        } else if(curr > finals){
            curr-= velocity;
        }
        if(Math.abs(finals-curr) < velocity){
            curr = finals;
        }
        return curr;
    }
}
