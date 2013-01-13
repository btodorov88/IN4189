package com.jme3.renderer.lwjgl.gl1.api;

import com.jme3.material.RenderState;

public interface LwjglGL1StateManager {

	public abstract void initialize();

	public abstract void invalidateState();

	public abstract void clearBuffers(boolean color, boolean depth,
			boolean stencil);

	public abstract void applyRenderState(RenderState state);

}