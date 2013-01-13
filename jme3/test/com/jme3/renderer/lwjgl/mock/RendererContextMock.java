package com.jme3.renderer.lwjgl.mock;

import com.jme3.renderer.RenderContext;
import com.jme3.renderer.lwjgl.StateTracker;

public class RendererContextMock extends RenderContext{
	
	private StateTracker tracker;

	public RendererContextMock(StateTracker tracker) {
		this.tracker = tracker;
	}
	
	@Override
	public void reset() {
		tracker.registerCall();
	}

}
