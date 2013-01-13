package com.jme3.renderer.lwjgl.gl1.impl;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.jme3.light.DirectionalLight;
import com.jme3.light.Light;
import com.jme3.light.LightList;
import com.jme3.light.PointLight;
import com.jme3.light.SpotLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Matrix4f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderContext;
import com.jme3.renderer.lwjgl.gl1.api.GL11Wrapper;
import com.jme3.renderer.lwjgl.gl1.api.LwjglGL1LightsRenderer;
import com.jme3.renderer.lwjgl.gl1.utils.MatrixUtils;
import com.jme3.util.BufferUtils;

public class LwjglGL1LightsRendererImpl implements LwjglGL1LightsRenderer {
	
	private GL11Wrapper gl11_Wrapper;
    private ArrayList<Light> lightList = new ArrayList<Light>(8);
    
    private final FloatBuffer fb16 = BufferUtils.createFloatBuffer(16);
    private final RenderContext context;
    
    private ColorRGBA materialAmbientColor = new ColorRGBA();
    private Vector3f tempVec = new Vector3f();
    
    private Matrix4f worldMatrix = new Matrix4f();
    private Matrix4f viewMatrix = new Matrix4f();
    
    private int maxLights = 1;
    
    public LwjglGL1LightsRendererImpl(GL11Wrapper igl11Wrapper, RenderContext renderContext){
		gl11_Wrapper = igl11Wrapper;
		context = renderContext;
    }
    
    /* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.gl1.impl.LwjglGL1LightsRenderer#initialize()
	 */
    @Override
	public void initialize() {
		this.maxLights = gl11_Wrapper.glGetInteger(GL11.GL_MAX_LIGHTS);
	}
    
    /* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.gl1.impl.LwjglGL1LightsRenderer#setWorldMatrix(com.jme3.math.Matrix4f)
	 */
    @Override
	public void setWorldMatrix(Matrix4f worldMatrix) {
		this.worldMatrix = worldMatrix;
	}
    
    /* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.gl1.impl.LwjglGL1LightsRenderer#setViewMatrix(com.jme3.math.Matrix4f)
	 */
    @Override
	public void setViewMatrix(Matrix4f viewMatrix) {
		this.viewMatrix = viewMatrix;
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.gl1.impl.LwjglGL1LightsRenderer#setLighting(com.jme3.light.LightList)
	 */
	@Override
	public void setLighting(LightList list) {
        // XXX: This is abuse of setLighting() to
        // apply fixed function bindings
        // and do other book keeping.
        if (list == null || list.size() == 0){
            gl11_Wrapper.glDisable(GL11.GL_LIGHTING);
            applyFixedFuncBindings(false);
            setModelView(worldMatrix, viewMatrix);
            return;
        }
        
        // Number of lights set previously
        int numLightsSetPrev = lightList.size();
        
        // If more than maxLights are defined, they will be ignored.
        // The GL1 renderer is not permitted to crash due to a 
        // GL1 limitation. It must render anything that the GL2 renderer
        // can render (even incorrectly).
        lightList.clear();
        materialAmbientColor.set(0, 0, 0, 0);
        
        for (int i = 0; i < list.size(); i++){
            Light l = list.get(i);
            if (l.getType() == Light.Type.Ambient){
                // Gather
                materialAmbientColor.addLocal(l.getColor());
            }else{
                // Add to list
                lightList.add(l);
                
                // Once maximum lights reached, exit loop.
                if (lightList.size() >= maxLights){
                    break;
                }
            }
        }
        
        applyFixedFuncBindings(true);
        
        gl11_Wrapper.glEnable(GL11.GL_LIGHTING);
        
        fb16.clear();
        fb16.put(materialAmbientColor.r)
            .put(materialAmbientColor.g)
            .put(materialAmbientColor.b)
            .put(1).flip();
        
        gl11_Wrapper.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, fb16);
        
        if (context.matrixMode != GL11.GL_MODELVIEW) {
            gl11_Wrapper.glMatrixMode(GL11.GL_MODELVIEW);
            context.matrixMode = GL11.GL_MODELVIEW;
        }
        // Lights are already in world space, so just convert
        // them to view space.
        gl11_Wrapper.glLoadMatrix(MatrixUtils.storeMatrix(viewMatrix, fb16));
        
        for (int i = 0; i < lightList.size(); i++){
            int glLightIndex = GL11.GL_LIGHT0 + i;
            Light light = lightList.get(i);
            Light.Type lightType = light.getType();
            ColorRGBA col = light.getColor();
            Vector3f pos;
            
            // Enable the light
            gl11_Wrapper.glEnable(glLightIndex);
            
            setLight(glLightIndex, light, lightType, col);
        }
        
        // Disable lights after the index
        for (int i = lightList.size(); i < numLightsSetPrev; i++){
            gl11_Wrapper.glDisable(GL11.GL_LIGHT0 + i);
        }
        
        // This will set view matrix as well.
        setModelView(worldMatrix, viewMatrix);
    }

	private void setLight(int glLightIndex, Light light, Light.Type lightType,
			ColorRGBA col) {
		Vector3f pos;
		// OGL spec states default value for light ambient is black
		switch (lightType){
		    case Directional:
		        DirectionalLight dLight = (DirectionalLight) light;

			setDirectionalLight(glLightIndex, col, dLight);
		        break;
		    case Point:
		        PointLight pLight = (PointLight) light;
     
			setPointLight(glLightIndex, col, pLight);

		        break;
		    case Spot:
		        SpotLight sLight = (SpotLight) light;

			setSpotLight(glLightIndex, col, sLight);

		        break;
		    default:
		        throw new UnsupportedOperationException(
		                "Unrecognized light type: " + lightType);
		}
	}

	private void setSpotLight(int glLightIndex, ColorRGBA col, SpotLight sLight) {
		Vector3f pos;
		fb16.clear();
		fb16.put(col.r).put(col.g).put(col.b).put(col.a).flip();
		gl11_Wrapper.glLight(glLightIndex, GL11.GL_DIFFUSE, fb16);
		gl11_Wrapper.glLight(glLightIndex, GL11.GL_SPECULAR, fb16);

		pos = sLight.getPosition();
		fb16.clear();
		fb16.put(pos.x).put(pos.y).put(pos.z).put(1.0f).flip();
		gl11_Wrapper.glLight(glLightIndex, GL11.GL_POSITION, fb16);

		Vector3f dir = sLight.getDirection();
		fb16.clear();
		fb16.put(dir.x).put(dir.y).put(dir.z).put(1.0f).flip();
		gl11_Wrapper.glLight(glLightIndex, GL11.GL_SPOT_DIRECTION, fb16);

		float outerAngleRad = sLight.getSpotOuterAngle();
		float innerAngleRad = sLight.getSpotInnerAngle();
		float spotCut = outerAngleRad * FastMath.RAD_TO_DEG;
		float spotExpo = 0.0f;
		if (outerAngleRad > 0) {
		    spotExpo = (1.0f - (innerAngleRad / outerAngleRad)) * 128.0f;
		}

		gl11_Wrapper.glLightf(glLightIndex, GL11.GL_SPOT_CUTOFF, spotCut);
		gl11_Wrapper.glLightf(glLightIndex, GL11.GL_SPOT_EXPONENT, spotExpo);

		if (sLight.getSpotRange() > 0) {
		    gl11_Wrapper.glLightf(glLightIndex, GL11.GL_LINEAR_ATTENUATION, sLight.getInvSpotRange());
		}else{
		    gl11_Wrapper.glLightf(glLightIndex, GL11.GL_LINEAR_ATTENUATION, 0);
		}
	}

	private void setPointLight(int glLightIndex, ColorRGBA col, PointLight pLight) {
		Vector3f pos;
		fb16.clear();
		fb16.put(col.r).put(col.g).put(col.b).put(col.a).flip();
		gl11_Wrapper.glLight(glLightIndex, GL11.GL_DIFFUSE, fb16);
		gl11_Wrapper.glLight(glLightIndex, GL11.GL_SPECULAR, fb16);

		pos = pLight.getPosition();
		fb16.clear();
		fb16.put(pos.x).put(pos.y).put(pos.z).put(1.0f).flip();
		gl11_Wrapper.glLight(glLightIndex, GL11.GL_POSITION, fb16);
		gl11_Wrapper.glLightf(glLightIndex, GL11.GL_SPOT_CUTOFF, 180);

		if (pLight.getRadius() > 0) {
		    // Note: this doesn't follow the same attenuation model
		    // as the one used in the lighting shader.
		    gl11_Wrapper.glLightf(glLightIndex, GL11.GL_CONSTANT_ATTENUATION,  1);
		    gl11_Wrapper.glLightf(glLightIndex, GL11.GL_LINEAR_ATTENUATION,    pLight.getInvRadius() * 2);
		    gl11_Wrapper.glLightf(glLightIndex, GL11.GL_QUADRATIC_ATTENUATION, pLight.getInvRadius() * pLight.getInvRadius()); 
		}else{
		    gl11_Wrapper.glLightf(glLightIndex, GL11.GL_CONSTANT_ATTENUATION,  1);
		    gl11_Wrapper.glLightf(glLightIndex, GL11.GL_LINEAR_ATTENUATION,    0);
		    gl11_Wrapper.glLightf(glLightIndex, GL11.GL_QUADRATIC_ATTENUATION, 0);
		}
	}

	private void setDirectionalLight(int glLightIndex, ColorRGBA col,
			DirectionalLight dLight) {
		Vector3f pos;
		fb16.clear();
		fb16.put(col.r).put(col.g).put(col.b).put(col.a).flip();
		gl11_Wrapper.glLight(glLightIndex, GL11.GL_DIFFUSE, fb16);
		gl11_Wrapper.glLight(glLightIndex, GL11.GL_SPECULAR, fb16);

		pos = tempVec.set(dLight.getDirection()).negateLocal().normalizeLocal();
		fb16.clear();
		fb16.put(pos.x).put(pos.y).put(pos.z).put(0.0f).flip();
		gl11_Wrapper.glLight(glLightIndex, GL11.GL_POSITION, fb16);
		gl11_Wrapper.glLightf(glLightIndex, GL11.GL_SPOT_CUTOFF, 180);
	}
	
    private void setModelView(Matrix4f modelMatrix, Matrix4f viewMatrix){
        if (context.matrixMode != GL11.GL_MODELVIEW) {
            gl11_Wrapper.glMatrixMode(GL11.GL_MODELVIEW);
            context.matrixMode = GL11.GL_MODELVIEW;
        }

        gl11_Wrapper.glLoadMatrix(MatrixUtils.storeMatrix(viewMatrix, fb16));
        gl11_Wrapper.glMultMatrix(MatrixUtils.storeMatrix(modelMatrix, fb16));
    }
    
    /**
     * Applies fixed function bindings from the context to OpenGL
     */
    private void applyFixedFuncBindings(boolean forLighting){
        if (forLighting) {
            gl11_Wrapper.glMaterialf(GL11.GL_FRONT_AND_BACK, GL11.GL_SHININESS, context.shininess);
            setMaterialColor(GL11.GL_AMBIENT, context.ambient, ColorRGBA.DarkGray);
            setMaterialColor(GL11.GL_DIFFUSE, context.diffuse, ColorRGBA.White);
            setMaterialColor(GL11.GL_SPECULAR, context.specular, ColorRGBA.Black);

            if (context.useVertexColor) {
                gl11_Wrapper.glEnable(GL11.GL_COLOR_MATERIAL);
            } else {
                gl11_Wrapper.glDisable(GL11.GL_COLOR_MATERIAL);
            }
        } else {
            // Ignore other values as they have no effect when 
            // GL11.GL_LIGHTING is disabled.
            ColorRGBA color = context.color;
            if (color != null) {
                gl11_Wrapper.glColor4f(color.r, color.g, color.b, color.a);
            } else {
                gl11_Wrapper.glColor4f(1, 1, 1, 1);
            }
        }
        if (context.alphaTestFallOff > 0f) {
            gl11_Wrapper.glEnable(GL11.GL_ALPHA_TEST);
            gl11_Wrapper.glAlphaFunc(GL11.GL_GREATER, context.alphaTestFallOff);
        } else {
            gl11_Wrapper.glDisable(GL11.GL_ALPHA_TEST);
        }
    }
    
    private void setMaterialColor(int type, ColorRGBA color, ColorRGBA defaultColor) {
        if (color != null){
            fb16.put(color.r).put(color.g).put(color.b).put(color.a).flip();
        }else{
            fb16.put(defaultColor.r).put(defaultColor.g).put(defaultColor.b).put(defaultColor.a).flip();
        }
        gl11_Wrapper.glMaterial(GL11.GL_FRONT_AND_BACK, type, fb16);
    }
    
  
}
