package com.jme3.renderer.lwjgl.gl1.impl;

import java.util.EnumSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jme3.renderer.Caps;
import com.jme3.renderer.RenderContext;
import com.jme3.renderer.lwjgl.LwjglRenderer;
import com.jme3.renderer.lwjgl.gl1.api.GL11Wrapper;
import com.jme3.renderer.lwjgl.gl1.api.LwjglGL1Capabilities;

public class LwjglGL1CapabilitiesImpl implements LwjglGL1Capabilities {
	
	private static final Logger logger = Logger.getLogger(LwjglRenderer.class.getName());
	
    private final EnumSet<Caps> caps = EnumSet.noneOf(Caps.class);

	private GL11Wrapper gl11Wrapper;
    
    public LwjglGL1CapabilitiesImpl(GL11Wrapper igl11Wrapper){
		this.gl11Wrapper = igl11Wrapper;
    }

    /* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.gl1.impl.LwjglGL1Capabilities#initialize()
	 */
    @Override
	public void initialize() {
    	
        if (gl11Wrapper.isGL_ARB_texture_non_power_of_two()) {
            caps.add(Caps.NonPowerOfTwoTextures);
        } else {
            logger.log(Level.WARNING, "Your graphics card does not "
                    + "support non-power-of-2 textures. "
                    + "Some features might not work.");
        }
        
    }
    
    /* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.gl1.impl.LwjglGL1Capabilities#getCaps()
	 */
    @Override
	public EnumSet<Caps> getCaps() {
        return caps;
    }
}
