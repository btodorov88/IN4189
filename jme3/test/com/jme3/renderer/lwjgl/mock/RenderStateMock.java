package com.jme3.renderer.lwjgl.mock;

import com.jme3.material.RenderState;

public class RenderStateMock extends RenderState {
	private boolean isStencilTest;

	@Override
	public boolean isStencilTest() {
		return isStencilTest;
	}
	
	public void setStencilTest(boolean isStencilTest) {
		this.isStencilTest = isStencilTest;
	}
	
	
	
}
