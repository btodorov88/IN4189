package com.jme3.renderer.lwjgl.gl1.impl;

import org.lwjgl.opengl.GL11;

import com.jme3.renderer.RenderContext;
import com.jme3.renderer.lwjgl.gl1.api.GL11Wrapper;
import com.jme3.renderer.lwjgl.gl1.api.LwjglGL1Clipper;

public class LwjglGL1ClipperImpl implements LwjglGL1Clipper {
	
    private int clipX, clipY, clipW, clipH;
	
	private final GL11Wrapper gl11_Wrapper;
	private final RenderContext context;
	
	public LwjglGL1ClipperImpl(GL11Wrapper gl11_Wrapper,
			RenderContext context) {
		super();
		this.gl11_Wrapper = gl11_Wrapper;
		this.context = context;
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.gl1.impl.LwjglGL1Clipper#setClipRect(int, int, int, int)
	 */
	@Override
	public void setClipRect(int x, int y, int width, int height) {
        if (!context.clipRectEnabled) {
            gl11_Wrapper.glEnable(GL11.GL_SCISSOR_TEST);
            context.clipRectEnabled = true;
        }
        if (clipX != x || clipY != y || clipW != width || clipH != height) {
            gl11_Wrapper.glScissor(x, y, width, height);
            clipX = x;
            clipY = y;
            clipW = width;
            clipH = height;
        }
    }

    /* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.gl1.impl.LwjglGL1Clipper#clearClipRect()
	 */
    @Override
	public void clearClipRect() {
        if (context.clipRectEnabled) {
            gl11_Wrapper.glDisable(GL11.GL_SCISSOR_TEST);
            context.clipRectEnabled = false;

            clipX = 0;
            clipY = 0;
            clipW = 0;
            clipH = 0;
        }
    }

}
