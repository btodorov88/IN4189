package com.jme3.renderer.lwjgl.mock;

import com.jme3.renderer.IStatistics;
import com.jme3.renderer.lwjgl.StateTracker;
import com.jme3.scene.Mesh;
import com.jme3.shader.Shader;
import com.jme3.texture.FrameBuffer;
import com.jme3.texture.Image;

public class StatisticsMock implements IStatistics {
	
	private StateTracker tracker;

	public StatisticsMock(StateTracker tracker) {
		this.tracker = tracker;
	}
	@Override
	public String[] getLabels() {
		tracker.registerCall();
		return null;
	}

	@Override
	public void getData(int[] data) {
		tracker.registerCall(data);

	}

	@Override
	public void onMeshDrawn(Mesh mesh, int lod) {
		tracker.registerCall(mesh, lod);

	}

	@Override
	public void onShaderUse(Shader shader, boolean wasSwitched) {
		tracker.registerCall(shader, wasSwitched);

	}

	@Override
	public void onUniformSet() {
		tracker.registerCall();

	}

	@Override
	public void onTextureUse(Image image, boolean wasSwitched) {
		tracker.registerCall(image, wasSwitched);

	}

	@Override
	public void onFrameBufferUse(FrameBuffer fb, boolean wasSwitched) {
		tracker.registerCall(fb, wasSwitched);

	}

	@Override
	public void clearFrame() {
		tracker.registerCall();

	}

	@Override
	public void onNewShader() {
		tracker.registerCall();

	}

	@Override
	public void onNewTexture() {
		tracker.registerCall();

	}

	@Override
	public void onNewFrameBuffer() {
		tracker.registerCall();

	}

	@Override
	public void onDeleteShader() {
		tracker.registerCall();

	}

	@Override
	public void onDeleteTexture() {
		tracker.registerCall();

	}

	@Override
	public void onDeleteFrameBuffer() {
		tracker.registerCall();

	}

	@Override
	public void clearMemory() {
		tracker.registerCall();

	}

}
