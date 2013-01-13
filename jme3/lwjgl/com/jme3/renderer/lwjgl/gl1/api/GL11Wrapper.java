package com.jme3.renderer.lwjgl.gl1.api;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public interface GL11Wrapper {

	public abstract void glMaterial(int arg0, int arg1, FloatBuffer arg2);

	public abstract void glViewport(int arg0, int arg1, int arg2, int arg3);

	public abstract void glDepthFunc(int arg0);

	public abstract void glLineWidth(float f);

	public abstract int glGetInteger(int arg0);

	public abstract void glPolygonMode(int arg0, int arg1);

	public abstract void glColorPointer(int arg0, boolean b, int arg2,
			ByteBuffer data);

	public abstract void glColorPointer(int arg0, int arg2, FloatBuffer data);

	public abstract void glShadeModel(int arg0);

	public abstract void glDrawArrays(int arg0, int arg1, int arg2);

	public abstract void glDisable(int arg0);

	public abstract void glLightf(int arg0, int arg1, int arg2);

	public abstract void glPolygonOffset(float arg0, float arg1);

	public abstract void glTexCoordPointer(int arg0, int arg1, FloatBuffer data);

	public abstract void glNormalPointer(int arg0, FloatBuffer data);

	public abstract void glAlphaFunc(int arg0, float alphaTestFallOff);

	public abstract void glScissor(int arg0, int arg1, int arg2, int arg3);

	public abstract void glBlendFunc(int arg0, int arg1);

	public abstract void glMaterialf(int arg0, int arg1, float shininess);

	public abstract void glClearColor(float r, float g, float b, float a);

	public abstract void glLoadMatrix(FloatBuffer arg0);

	public abstract void glGenTextures(IntBuffer arg0);

	public abstract void glLight(int arg0, int arg1, FloatBuffer arg2);

	public abstract void glMultMatrix(FloatBuffer arg0);

	public abstract void glDisableClientState(int arg0);

	public abstract void glColorMask(boolean arg0, boolean arg1, boolean arg2,
			boolean arg3);

	public abstract void glEnableClientState(int arg0);

	public abstract void glBindTexture(int arg0, int arg1);

	public abstract void glPointSize(float f);

	public abstract void glTexParameteri(int arg0, int arg1, int arg2);

	public abstract void glMatrixMode(int arg0);

	public abstract void glClear(int arg0);

	public abstract void glColorMaterial(int arg0, int arg1);

	public abstract void glDrawElements(int arg0, ByteBuffer arg1);

	public abstract void glDrawElements(int arg0, IntBuffer arg1);

	public abstract void glDrawElements(int arg0, ShortBuffer arg1);

	public abstract void glLightModel(int arg0, FloatBuffer arg1);

	public abstract void glDepthMask(boolean arg0);

	public abstract void glHint(int arg0, int arg1);

	public abstract void glDeleteTextures(IntBuffer ib1);

	public abstract void glColor4f(float r, float g, float b, float a);

	public abstract void glEnable(int arg0);

	public abstract void glDepthRange(float arg0, float arg1);

	public abstract void glCullFace(int arg0);

	public abstract void glVertexPointer(int arg0, int arg1, FloatBuffer data);

	public abstract void glLightf(int arg0, int arg1, float arg2);

	public abstract boolean isGL_ARB_texture_non_power_of_two();

	public abstract boolean isOpenGL12();

}