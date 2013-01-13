package com.jme3.renderer.lwjgl.gl1.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.jme3.material.FixedFuncBinding;
import com.jme3.material.RenderState;
import com.jme3.renderer.RenderContext;
import com.jme3.renderer.lwjgl.LwjglRenderer;
import com.jme3.renderer.lwjgl.gl1.api.GL11Wrapper;
import com.jme3.renderer.lwjgl.gl1.api.LwjglGL1StateManager;
import com.jme3.renderer.lwjgl.gl1.utils.ContextUtils;

public class LwjglGL1StateManagerImpl implements LwjglGL1StateManager {

	private static final Logger logger = Logger.getLogger(LwjglRenderer.class
			.getName());

	private final GL11Wrapper gl11_Wrapper;
	private final RenderContext context;

	public LwjglGL1StateManagerImpl(GL11Wrapper gl11_Wrapper, RenderContext context) {
		super();
		this.gl11_Wrapper = gl11_Wrapper;
		this.context = context;
	}
	
	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.gl1.impl.LwjglGL1StateManager#initialize()
	 */
	@Override
	public void initialize(){
		boolean gl12 = false;
        if (gl11_Wrapper.isOpenGL12()){
            gl12 = true;
        }
        
        // Default values for certain GL state.
        gl11_Wrapper.glShadeModel(GL11.GL_SMOOTH);
        gl11_Wrapper.glColorMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_DIFFUSE);
        gl11_Wrapper.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
        
        // Enable rescaling/normaling of normal vectors.
        // Fixes lighting issues with scaled models.
        if (gl12){
        	gl11_Wrapper.glEnable(GL12.GL_RESCALE_NORMAL);
        }else{
        	gl11_Wrapper.glEnable(GL11.GL_NORMALIZE);
        }
	}
	
	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.gl1.impl.LwjglGL1StateManager#invalidateState()
	 */
	@Override
	public void invalidateState() {
        context.reset();
    }

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.gl1.impl.LwjglGL1StateManager#clearBuffers(boolean, boolean, boolean)
	 */
	@Override
	public void clearBuffers(boolean color, boolean depth, boolean stencil) {
		int bits = 0;
		if (color) {
			// See explanations of the depth below, we must enable color write
			// to be able to clear the color buffer
			if (context.colorWriteEnabled == false) {
				gl11_Wrapper.glColorMask(true, true, true, true);
				context.colorWriteEnabled = true;
			}
			bits = GL11.GL_COLOR_BUFFER_BIT;
		}
		if (depth) {

			// gl11_Wrapper.glClear(GL11.GL_DEPTH_BUFFER_BIT) seems to not work
			// when gl11_Wrapper.glDepthMask is false
			// here s some link on openl board
			// http://www.opengl.org/discussion_boards/ubbthreads.php?ubb=showflat&Number=257223
			// if depth clear is requested, we enable the depthMask
			if (context.depthWriteEnabled == false) {
				gl11_Wrapper.glDepthMask(true);
				context.depthWriteEnabled = true;
			}
			bits |= GL11.GL_DEPTH_BUFFER_BIT;
		}
		if (stencil) {
			bits |= GL11.GL_STENCIL_BUFFER_BIT;
		}
		if (bits != 0) {
			gl11_Wrapper.glClear(bits);
		}
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.gl1.impl.LwjglGL1StateManager#applyRenderState(com.jme3.material.RenderState)
	 */
	@Override
	public void applyRenderState(RenderState state) {
		checkWireframe(state);

		checkDepthTest(state);

		checkAlphaTest(state);

		checkDepthWrite(state);

		checkColorWrite(state);

		checkPointSprite(state);

		checkPolyOffset(state);

		checkFaceCullMode(state);

		checkBlendMode(state);

		checkStencilTest(state);

	}

	private void checkStencilTest(RenderState state) {
		if (state.isStencilTest()) {
			throw new UnsupportedOperationException(
					"OpenGL 1.1 doesn't support two sided stencil operations.");
		}
	}

	private void checkPointSprite(RenderState state) {
		if (state.isPointSprite()) {
			logger.log(Level.WARNING, "Point Sprite unsupported!");
		}
	}

	private void checkColorWrite(RenderState state) {
		if (state.isColorWrite() && !context.colorWriteEnabled) {
			gl11_Wrapper.glColorMask(true, true, true, true);
			context.colorWriteEnabled = true;
		} else if (!state.isColorWrite() && context.colorWriteEnabled) {
			gl11_Wrapper.glColorMask(false, false, false, false);
			context.colorWriteEnabled = false;
		}
	}

	private void checkDepthWrite(RenderState state) {
		if (state.isDepthWrite() && !context.depthWriteEnabled) {
			gl11_Wrapper.glDepthMask(true);
			context.depthWriteEnabled = true;
		} else if (!state.isDepthWrite() && context.depthWriteEnabled) {
			gl11_Wrapper.glDepthMask(false);
			context.depthWriteEnabled = false;
		}
	}

	private void checkAlphaTest(RenderState state) {
		if (state.isAlphaTest()) {
			ContextUtils.setFixedFuncBinding(context,
					FixedFuncBinding.AlphaTestFallOff, state.getAlphaFallOff());
		} else {
			ContextUtils.setFixedFuncBinding(context,
					FixedFuncBinding.AlphaTestFallOff, 0f); // disable it
		}
	}

	private void checkDepthTest(RenderState state) {
		if (state.isDepthTest() && !context.depthTestEnabled) {
			gl11_Wrapper.glEnable(GL11.GL_DEPTH_TEST);
			gl11_Wrapper.glDepthFunc(GL11.GL_LEQUAL);
			context.depthTestEnabled = true;
		} else if (!state.isDepthTest() && context.depthTestEnabled) {
			gl11_Wrapper.glDisable(GL11.GL_DEPTH_TEST);
			context.depthTestEnabled = false;
		}
	}

	private void checkWireframe(RenderState state) {
		if (state.isWireframe() && !context.wireframe) {
			gl11_Wrapper.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
			context.wireframe = true;
		} else if (!state.isWireframe() && context.wireframe) {
			gl11_Wrapper.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
			context.wireframe = false;
		}
	}

	private void checkPolyOffset(RenderState state) {
		if (state.isPolyOffset()) {
			if (!context.polyOffsetEnabled) {
				gl11_Wrapper.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
				gl11_Wrapper.glPolygonOffset(state.getPolyOffsetFactor(),
						state.getPolyOffsetUnits());
				context.polyOffsetEnabled = true;
				context.polyOffsetFactor = state.getPolyOffsetFactor();
				context.polyOffsetUnits = state.getPolyOffsetUnits();
			} else {
				if (state.getPolyOffsetFactor() != context.polyOffsetFactor
						|| state.getPolyOffsetUnits() != context.polyOffsetUnits) {
					gl11_Wrapper.glPolygonOffset(state.getPolyOffsetFactor(),
							state.getPolyOffsetUnits());
					context.polyOffsetFactor = state.getPolyOffsetFactor();
					context.polyOffsetUnits = state.getPolyOffsetUnits();
				}
			}
		} else {
			if (context.polyOffsetEnabled) {
				gl11_Wrapper.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
				context.polyOffsetEnabled = false;
				context.polyOffsetFactor = 0;
				context.polyOffsetUnits = 0;
			}
		}
	}

	private void checkFaceCullMode(RenderState state) {
		if (state.getFaceCullMode() != context.cullMode) {
			if (state.getFaceCullMode() == RenderState.FaceCullMode.Off) {
				gl11_Wrapper.glDisable(GL11.GL_CULL_FACE);
			} else {
				gl11_Wrapper.glEnable(GL11.GL_CULL_FACE);
			}

			switch (state.getFaceCullMode()) {
			case Off:
				break;
			case Back:
				gl11_Wrapper.glCullFace(GL11.GL_BACK);
				break;
			case Front:
				gl11_Wrapper.glCullFace(GL11.GL_FRONT);
				break;
			case FrontAndBack:
				gl11_Wrapper.glCullFace(GL11.GL_FRONT_AND_BACK);
				break;
			default:
				throw new UnsupportedOperationException(
						"Unrecognized face cull mode: "
								+ state.getFaceCullMode());
			}

			context.cullMode = state.getFaceCullMode();
		}
	}

	private void checkBlendMode(RenderState state) {
		if (state.getBlendMode() != context.blendMode) {
			if (state.getBlendMode() == RenderState.BlendMode.Off) {
				gl11_Wrapper.glDisable(GL11.GL_BLEND);
			} else {
				gl11_Wrapper.glEnable(GL11.GL_BLEND);
				switch (state.getBlendMode()) {
				case Off:
					break;
				case Additive:
					gl11_Wrapper.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
					break;
				case AlphaAdditive:
					gl11_Wrapper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
					break;
				case Color:
					gl11_Wrapper.glBlendFunc(GL11.GL_ONE,
							GL11.GL_ONE_MINUS_SRC_COLOR);
					break;
				case Alpha:
					gl11_Wrapper.glBlendFunc(GL11.GL_SRC_ALPHA,
							GL11.GL_ONE_MINUS_SRC_ALPHA);
					break;
				case PremultAlpha:
					gl11_Wrapper.glBlendFunc(GL11.GL_ONE,
							GL11.GL_ONE_MINUS_SRC_ALPHA);
					break;
				case Modulate:
					gl11_Wrapper.glBlendFunc(GL11.GL_DST_COLOR, GL11.GL_ZERO);
					break;
				case ModulateX2:
					gl11_Wrapper.glBlendFunc(GL11.GL_DST_COLOR,
							GL11.GL_SRC_COLOR);
					break;
				default:
					throw new UnsupportedOperationException(
							"Unrecognized blend mode: " + state.getBlendMode());
				}
			}

			context.blendMode = state.getBlendMode();
		}
	}

}
