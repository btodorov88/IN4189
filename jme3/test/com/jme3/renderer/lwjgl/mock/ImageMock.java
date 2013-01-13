package com.jme3.renderer.lwjgl.mock;

import com.jme3.renderer.lwjgl.StateTracker;
import com.jme3.texture.Image;

public class ImageMock extends Image {
	
	private StateTracker tracker;
	
	private int id = 1;

	public ImageMock(StateTracker tracker) {
		this.tracker = tracker;
	}
	
	public ImageMock(StateTracker tracker, int id) {
		this.tracker = tracker;
		this.id = id;
	}
	
	@Override
	public int getId() {
		return id;
	}
	
	@Override
	public void resetObject() {
		tracker.registerCall();
	}
	
	@Override
	public boolean isUpdateNeeded() {
		return true;
	}
	
	@Override
	public String toString() {
		return String.valueOf(id);
	}
	
}
