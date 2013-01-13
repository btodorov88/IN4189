package com.jme3.renderer.lwjgl.gl1.api;

import com.jme3.light.LightList;
import com.jme3.math.Matrix4f;

public interface LwjglGL1LightsRenderer {

	public abstract void initialize();

	public abstract void setWorldMatrix(Matrix4f worldMatrix);

	public abstract void setViewMatrix(Matrix4f viewMatrix);

	public abstract void setLighting(LightList list);

}