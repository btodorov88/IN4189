package com.jme3.renderer.lwjgl.mock;

import com.jme3.renderer.lwjgl.StateTracker;
import com.jme3.texture.Image;
import com.jme3.texture.Texture;

public class TextureMock extends Texture {
	
	private StateTracker tracker;
	
	public TextureMock(StateTracker tracker) {
		this.tracker = tracker;
	}
	
	@Override
	public Image getImage() {
		return new ImageMock(tracker, -1);
	}
	
	@Override
	public void setWrap(WrapAxis axis, WrapMode mode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setWrap(WrapMode mode) {
		// TODO Auto-generated method stub

	}

	@Override
	public WrapMode getWrap(WrapAxis axis) {
		return WrapMode.BorderClamp;
	}

	@Override
	public Type getType() {
		return Type.TwoDimensional;
	}

	@Override
	@Deprecated
	public Texture createSimpleClone() {
		// TODO Auto-generated method stub
		return null;
	}

}
