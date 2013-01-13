package com.jme3.renderer.lwjgl.gl1.api;

public interface LwjglGL1Clipper {

	public abstract void setClipRect(int x, int y, int width, int height);

	public abstract void clearClipRect();

}