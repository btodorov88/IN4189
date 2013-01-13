package com.jme3.renderer.lwjgl.gl1.impl;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import com.jme3.renderer.lwjgl.gl1.api.GL11Wrapper;

public class GL11WrapperImpl implements GL11Wrapper {

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glMaterial(int, int, java.nio.FloatBuffer)
	 */
	@Override
	public void glMaterial(int arg0, int arg1, FloatBuffer arg2) {
		GL11.glMaterial(arg0, arg1, arg2);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glViewport(int, int, int, int)
	 */
	@Override
	public void glViewport(int arg0, int arg1, int arg2, int arg3) {
		GL11.glViewport(arg0, arg1, arg2, arg3);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glDepthFunc(int)
	 */
	@Override
	public void glDepthFunc(int arg0) {
		GL11.glDepthFunc(arg0);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glLineWidth(float)
	 */
	@Override
	public void glLineWidth(float f) {
		GL11.glLineWidth(f);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glGetInteger(int)
	 */
	@Override
	public int glGetInteger(int arg0) {
		return GL11.glGetInteger(arg0);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glPolygonMode(int, int)
	 */
	@Override
	public void glPolygonMode(int arg0, int arg1) {
		GL11.glPolygonMode(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glColorPointer(int, boolean, int, java.nio.ByteBuffer)
	 */
	@Override
	public void glColorPointer(int arg0, boolean b, int arg2, ByteBuffer data) {
		GL11.glColorPointer(arg0, b, arg2, data);
	}
	
	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glColorPointer(int, int, java.nio.FloatBuffer)
	 */
	@Override
	public void glColorPointer(int arg0, int arg2, FloatBuffer data) {
		GL11.glColorPointer(arg0, arg2, data);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glShadeModel(int)
	 */
	@Override
	public void glShadeModel(int arg0) {
		GL11.glShadeModel(arg0);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glDrawArrays(int, int, int)
	 */
	@Override
	public void glDrawArrays(int arg0, int arg1, int arg2) {
		GL11.glDrawArrays(arg0, arg1, arg2);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glDisable(int)
	 */
	@Override
	public void glDisable(int arg0) {
		GL11.glDisable(arg0);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glLightf(int, int, int)
	 */
	@Override
	public void glLightf(int arg0, int arg1, int arg2) {
		GL11.glLightf(arg0, arg1, arg2);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glPolygonOffset(float, float)
	 */
	@Override
	public void glPolygonOffset(float arg0, float arg1) {
		GL11.glPolygonOffset(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glTexCoordPointer(int, int, java.nio.FloatBuffer)
	 */
	@Override
	public void glTexCoordPointer(int arg0, int arg1, FloatBuffer data) {
		GL11.glTexCoordPointer(arg0, arg1, data);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glNormalPointer(int, java.nio.FloatBuffer)
	 */
	@Override
	public void glNormalPointer(int arg0, FloatBuffer data) {
		GL11.glNormalPointer(arg0, data);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glAlphaFunc(int, float)
	 */
	@Override
	public void glAlphaFunc(int arg0, float alphaTestFallOff) {
		GL11.glAlphaFunc(arg0, alphaTestFallOff);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glScissor(int, int, int, int)
	 */
	@Override
	public void glScissor(int arg0, int arg1, int arg2, int arg3) {
		GL11.glScissor(arg0, arg1, arg2, arg3);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glBlendFunc(int, int)
	 */
	@Override
	public void glBlendFunc(int arg0, int arg1) {
		GL11.glBlendFunc(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glMaterialf(int, int, float)
	 */
	@Override
	public void glMaterialf(int arg0, int arg1, float shininess) {
		GL11.glMaterialf(arg0, arg1, shininess);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glClearColor(float, float, float, float)
	 */
	@Override
	public void glClearColor(float r, float g, float b, float a) {
		GL11.glClearColor(r, g, b, a);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glLoadMatrix(java.nio.FloatBuffer)
	 */
	@Override
	public void glLoadMatrix(FloatBuffer arg0) {
		GL11.glLoadMatrix(arg0);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glGenTextures(java.nio.IntBuffer)
	 */
	@Override
	public void glGenTextures(IntBuffer arg0) {
		GL11.glGenTextures(arg0);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glLight(int, int, java.nio.FloatBuffer)
	 */
	@Override
	public void glLight(int arg0, int arg1, FloatBuffer arg2) {
		GL11.glLight(arg0, arg1, arg2);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glMultMatrix(java.nio.FloatBuffer)
	 */
	@Override
	public void glMultMatrix(FloatBuffer arg0) {
		GL11.glMultMatrix(arg0);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glDisableClientState(int)
	 */
	@Override
	public void glDisableClientState(int arg0) {
		GL11.glDisableClientState(arg0);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glColorMask(boolean, boolean, boolean, boolean)
	 */
	@Override
	public void glColorMask(boolean arg0, boolean arg1, boolean arg2, boolean arg3) {
		GL11.glColorMask(arg0, arg1, arg2, arg3);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glEnableClientState(int)
	 */
	@Override
	public void glEnableClientState(int arg0) {
		GL11.glEnableClientState(arg0);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glBindTexture(int, int)
	 */
	@Override
	public void glBindTexture(int arg0, int arg1) {
		GL11.glBindTexture(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glPointSize(float)
	 */
	@Override
	public void glPointSize(float f) {
		GL11.glPointSize(f);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glTexParameteri(int, int, int)
	 */
	@Override
	public void glTexParameteri(int arg0, int arg1, int arg2) {
		GL11.glTexParameteri(arg0, arg1, arg2);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glMatrixMode(int)
	 */
	@Override
	public void glMatrixMode(int arg0) {
		GL11.glMatrixMode(arg0);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glClear(int)
	 */
	@Override
	public void glClear(int arg0) {
		GL11.glClear(arg0);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glColorMaterial(int, int)
	 */
	@Override
	public void glColorMaterial(int arg0, int arg1) {
		GL11.glColorMaterial(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glDrawElements(int, java.nio.ByteBuffer)
	 */
	@Override
	public void glDrawElements(int arg0, ByteBuffer arg1) {
		GL11.glDrawElements(arg0, arg1);
	}
	
	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glDrawElements(int, java.nio.IntBuffer)
	 */
	@Override
	public void glDrawElements(int arg0, IntBuffer arg1) {
		GL11.glDrawElements(arg0, arg1);
	}
	
	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glDrawElements(int, java.nio.ShortBuffer)
	 */
	@Override
	public void glDrawElements(int arg0, ShortBuffer arg1) {
		GL11.glDrawElements(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glLightModel(int, java.nio.FloatBuffer)
	 */
	@Override
	public void glLightModel(int arg0, FloatBuffer arg1) {
		GL11.glLightModel(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glDepthMask(boolean)
	 */
	@Override
	public void glDepthMask(boolean arg0) {
		GL11.glDepthMask(arg0);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glHint(int, int)
	 */
	@Override
	public void glHint(int arg0, int arg1) {
		GL11.glHint(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glDeleteTextures(java.nio.IntBuffer)
	 */
	@Override
	public void glDeleteTextures(IntBuffer ib1) {
		GL11.glDeleteTextures(ib1);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glColor4f(float, float, float, float)
	 */
	@Override
	public void glColor4f(float r, float g, float b, float a) {
		GL11.glColor4f(r, g, b, a);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glEnable(int)
	 */
	@Override
	public void glEnable(int arg0) {
		GL11.glEnable(arg0);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glDepthRange(float, float)
	 */
	@Override
	public void glDepthRange(float arg0, float arg1) {
		GL11.glDepthRange(arg0, arg1);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glCullFace(int)
	 */
	@Override
	public void glCullFace(int arg0) {
		GL11.glCullFace(arg0);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glVertexPointer(int, int, java.nio.FloatBuffer)
	 */
	@Override
	public void glVertexPointer(int arg0, int arg1, FloatBuffer data) {
		GL11.glVertexPointer(arg0, arg1, data);
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.Igl11Wrapper#glLightf(int, int, float)
	 */
	@Override
	public void glLightf(int arg0, int arg1, float arg2) {
		GL11.glLightf(arg0, arg1, arg2);
	}
	
	@Override
	public boolean isGL_ARB_texture_non_power_of_two() {
		return GLContext.getCapabilities().GL_ARB_texture_non_power_of_two;
	}
	
	@Override
	public boolean isOpenGL12() {
		return GLContext.getCapabilities().OpenGL12;
	}

}
