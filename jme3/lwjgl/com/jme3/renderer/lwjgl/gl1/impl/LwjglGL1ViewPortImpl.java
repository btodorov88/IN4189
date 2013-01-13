package com.jme3.renderer.lwjgl.gl1.impl;

import com.jme3.renderer.lwjgl.gl1.api.GL11Wrapper;
import com.jme3.renderer.lwjgl.gl1.api.LwjglGL1ViewPort;


public class LwjglGL1ViewPortImpl implements LwjglGL1ViewPort {
	
    private int vpX, vpY, vpW, vpH;
    private GL11Wrapper gl11_Wrapper;
    
    public LwjglGL1ViewPortImpl(GL11Wrapper igl11Wrapper){
		gl11_Wrapper = igl11Wrapper;
    }
    
    /* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.gl1.impl.LwjglGL1ViewPort#setViewPort(int, int, int, int)
	 */
    @Override
	public void setViewPort(int x, int y, int w, int h) {
        if (x != vpX || vpY != y || vpW != w || vpH != h) {
            gl11_Wrapper.glViewport(x, y, w, h);
            vpX = x;
            vpY = y;
            vpW = w;
            vpH = h;
        }
    }
    
}
