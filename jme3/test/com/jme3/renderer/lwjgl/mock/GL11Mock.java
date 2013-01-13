package com.jme3.renderer.lwjgl.mock;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import com.jme3.renderer.lwjgl.StateTracker;
import com.jme3.renderer.lwjgl.gl1.api.GL11Wrapper;


public class GL11Mock implements GL11Wrapper {

	private StateTracker tracker;
	private boolean GL_ARB_texture_non_power_of_two = true;
	private boolean openGL12 = true;

	public GL11Mock(StateTracker tracker) {
		this.tracker = tracker;
	}

	@Override
	public void glMaterial(int arg0, int arg1, FloatBuffer arg2) {
		tracker.registerCall(arg0, arg1, arg2);

	}

	@Override
	public void glViewport(int arg0, int arg1, int arg2, int arg3) {
		tracker.registerCall(arg0, arg1, arg2);

	}

	@Override
	public void glDepthFunc(int arg0) {
		tracker.registerCall(arg0);

	}

	@Override
	public void glLineWidth(float f) {
		tracker.registerCall(f);

	}

	@Override
	public int glGetInteger(int arg0) {
		tracker.registerCall(arg0);
		return 0;
	}

	@Override
	public void glPolygonMode(int arg0, int arg1) {
		tracker.registerCall(arg0, arg1);

	}

	@Override
	public void glColorPointer(int arg0, boolean b, int arg2, ByteBuffer data) {
		tracker.registerCall(arg0, arg2, data);

	}

	@Override
	public void glColorPointer(int arg0, int arg2, FloatBuffer data) {
		tracker.registerCall(arg0, arg2, data);

	}

	@Override
	public void glShadeModel(int arg0) {
		tracker.registerCall(arg0);

	}

	@Override
	public void glDrawArrays(int arg0, int arg1, int arg2) {
		tracker.registerCall(arg0, arg1, arg2);

	}

	@Override
	public void glDisable(int arg0) {
		tracker.registerCall(arg0);

	}

	@Override
	public void glLightf(int arg0, int arg1, int arg2) {
		tracker.registerCall(arg0, arg1, arg2);

	}

	@Override
	public void glPolygonOffset(float arg0, float arg1) {
		tracker.registerCall(arg0, arg1);

	}

	@Override
	public void glTexCoordPointer(int arg0, int arg1, FloatBuffer data) {
		tracker.registerCall(arg0, arg1, data);

	}

	@Override
	public void glNormalPointer(int arg0, FloatBuffer data) {
		tracker.registerCall(arg0, data);

	}

	@Override
	public void glAlphaFunc(int arg0, float alphaTestFallOff) {
		tracker.registerCall(arg0, alphaTestFallOff);

	}

	@Override
	public void glScissor(int arg0, int arg1, int arg2, int arg3) {
		tracker.registerCall(arg0, arg1, arg2);

	}

	@Override
	public void glBlendFunc(int arg0, int arg1) {
		tracker.registerCall(arg0, arg1);

	}

	@Override
	public void glMaterialf(int arg0, int arg1, float shininess) {
		tracker.registerCall(arg0, arg1, shininess);

	}

	@Override
	public void glClearColor(float r, float g, float b, float a) {
		tracker.registerCall(r, g, b, a);

	}

	@Override
	public void glLoadMatrix(FloatBuffer arg0) {
		tracker.registerCall(arg0);

	}

	@Override
	public void glGenTextures(IntBuffer arg0) {
		tracker.registerCall(arg0);

	}

	@Override
	public void glLight(int arg0, int arg1, FloatBuffer arg2) {
		tracker.registerCall(arg0, arg1, arg2);

	}

	@Override
	public void glMultMatrix(FloatBuffer arg0) {
		tracker.registerCall(arg0);

	}

	@Override
	public void glDisableClientState(int arg0) {
		tracker.registerCall(arg0);

	}

	@Override
	public void glColorMask(boolean arg0, boolean arg1, boolean arg2,
			boolean arg3) {
		tracker.registerCall(arg0, arg1, arg2);

	}

	@Override
	public void glEnableClientState(int arg0) {
		tracker.registerCall(arg0);

	}

	@Override
	public void glBindTexture(int arg0, int arg1) {
		tracker.registerCall(arg0, arg1);

	}

	@Override
	public void glPointSize(float f) {
		tracker.registerCall(f);

	}

	@Override
	public void glTexParameteri(int arg0, int arg1, int arg2) {
		tracker.registerCall(arg0, arg1, arg2);

	}

	@Override
	public void glMatrixMode(int arg0) {
		tracker.registerCall(arg0);

	}

	@Override
	public void glClear(int arg0) {
		tracker.registerCall(arg0);

	}

	@Override
	public void glColorMaterial(int arg0, int arg1) {
		tracker.registerCall(arg0, arg1);

	}

	@Override
	public void glDrawElements(int arg0, ByteBuffer arg1) {
		tracker.registerCall(arg0, arg1);

	}

	@Override
	public void glDrawElements(int arg0, IntBuffer arg1) {
		tracker.registerCall(arg0, arg1);

	}

	@Override
	public void glDrawElements(int arg0, ShortBuffer arg1) {
		tracker.registerCall(arg0, arg1);

	}

	@Override
	public void glLightModel(int arg0, FloatBuffer arg1) {
		tracker.registerCall(arg0, arg1);

	}

	@Override
	public void glDepthMask(boolean arg0) {
		tracker.registerCall(arg0);

	}

	@Override
	public void glHint(int arg0, int arg1) {
		tracker.registerCall(arg0, arg1);

	}

	@Override
	public void glDeleteTextures(IntBuffer ib1) {
		tracker.registerCall(ib1);

	}

	@Override
	public void glColor4f(float r, float g, float b, float a) {
		tracker.registerCall(r, g, b, a);

	}

	@Override
	public void glEnable(int arg0) {
		tracker.registerCall(arg0);

	}

	@Override
	public void glDepthRange(float arg0, float arg1) {
		tracker.registerCall(arg0, arg1);

	}

	@Override
	public void glCullFace(int arg0) {
		tracker.registerCall(arg0);

	}

	@Override
	public void glVertexPointer(int arg0, int arg1, FloatBuffer data) {
		tracker.registerCall(arg0, arg1, data);

	}

	@Override
	public void glLightf(int arg0, int arg1, float arg2) {
		tracker.registerCall(arg0, arg1, arg2);

	}
	
	@Override
	public boolean isGL_ARB_texture_non_power_of_two() {
		tracker.registerCall();
		return GL_ARB_texture_non_power_of_two;
	}
	
	public void setGL_ARB_texture_non_power_of_two(boolean b) {
		this.GL_ARB_texture_non_power_of_two = b;
	}
	
	@Override
	public boolean isOpenGL12() {
		tracker.registerCall();
		return openGL12;
	}
	
	public void setOpenGL12(boolean b){
		this.openGL12 = b;
	}
	
}
