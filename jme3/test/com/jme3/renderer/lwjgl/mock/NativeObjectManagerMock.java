package com.jme3.renderer.lwjgl.mock;

import com.jme3.renderer.lwjgl.StateTracker;
import com.jme3.util.NativeObject;
import com.jme3.util.NativeObjectManager;

public class NativeObjectManagerMock extends NativeObjectManager {
	
	private StateTracker tracker;

	public NativeObjectManagerMock(StateTracker tracker) {
		this.tracker = tracker;
	}
	
	@Override
	public void deleteAllObjects(Object rendererObject) {
		tracker.registerCall(rendererObject);
	}
	
	@Override
	public void deleteUnused(Object rendererObject) {
		tracker.registerCall(rendererObject);
	}
	
	@Override
	public void registerForCleanup(NativeObject obj) {
		tracker.registerCall(obj);
	}
	
	@Override
	public void resetObjects() {
		tracker.registerCall();
	}

}
