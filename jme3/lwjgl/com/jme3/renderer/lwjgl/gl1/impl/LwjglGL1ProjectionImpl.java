package com.jme3.renderer.lwjgl.gl1.impl;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;

import com.jme3.math.Matrix4f;
import com.jme3.renderer.RenderContext;
import com.jme3.renderer.lwjgl.gl1.api.GL11Wrapper;
import com.jme3.renderer.lwjgl.gl1.api.LwjglGL1Projection;
import com.jme3.renderer.lwjgl.gl1.utils.MatrixUtils;
import com.jme3.util.BufferUtils;

public class LwjglGL1ProjectionImpl implements LwjglGL1Projection {
	
	private final GL11Wrapper gl11_Wrapper;
	private final RenderContext context;
	
    private final FloatBuffer fb16 = BufferUtils.createFloatBuffer(16);
	
	public LwjglGL1ProjectionImpl(GL11Wrapper gl11_Wrapper, RenderContext context) {
		super();
		this.gl11_Wrapper = gl11_Wrapper;
		this.context = context;
	}
	
    /* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.gl1.impl.LwjglGL1Projection#setProjection(com.jme3.math.Matrix4f)
	 */
    @Override
	public void setProjection(Matrix4f projMatrix){
        if (context.matrixMode != GL11.GL_PROJECTION) {
        	gl11_Wrapper.glMatrixMode(GL11.GL_PROJECTION);
            context.matrixMode = GL11.GL_PROJECTION;
        }

        gl11_Wrapper.glLoadMatrix(MatrixUtils.storeMatrix(projMatrix, fb16));
    }

}
