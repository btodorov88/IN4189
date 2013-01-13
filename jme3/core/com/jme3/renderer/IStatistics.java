package com.jme3.renderer;

import com.jme3.scene.Mesh;
import com.jme3.shader.Shader;
import com.jme3.texture.FrameBuffer;
import com.jme3.texture.Image;

public interface IStatistics {

	/**
	 * Returns a list of labels corresponding to each statistic.
	 * 
	 * @return a list of labels corresponding to each statistic.
	 * 
	 * @see #getData(int[]) 
	 */
	public abstract String[] getLabels();

	/**
	 * Retrieves the statistics data into the given array.
	 * The array should be as large as the array given in 
	 * {@link #getLabels() }.
	 * 
	 * @param data The data array to write to
	 */
	public abstract void getData(int[] data);

	/**
	 * Called by the Renderer when a mesh has been drawn.
	 * 
	 */
	public abstract void onMeshDrawn(Mesh mesh, int lod);

	/**
	 * Called by the Renderer when a shader has been utilized.
	 * 
	 * @param shader The shader that was used
	 * @param wasSwitched If true, the shader has required a state switch
	 */
	public abstract void onShaderUse(Shader shader, boolean wasSwitched);

	/**
	 * Called by the Renderer when a uniform was set.
	 */
	public abstract void onUniformSet();

	/**
	 * Called by the Renderer when a texture has been set.
	 * 
	 * @param image The image that was set
	 * @param wasSwitched If true, the texture has required a state switch
	 */
	public abstract void onTextureUse(Image image, boolean wasSwitched);

	/**
	 * Called by the Renderer when a framebuffer has been set.
	 * 
	 * @param fb The framebuffer that was set
	 * @param wasSwitched If true, the framebuffer required a state switch
	 */
	public abstract void onFrameBufferUse(FrameBuffer fb, boolean wasSwitched);

	/**
	 * Clears all frame-specific statistics such as objects used per frame.
	 */
	public abstract void clearFrame();

	/**
	 * Called by the Renderer when it creates a new shader
	 */
	public abstract void onNewShader();

	/**
	 * Called by the Renderer when it creates a new texture
	 */
	public abstract void onNewTexture();

	/**
	 * Called by the Renderer when it creates a new framebuffer
	 */
	public abstract void onNewFrameBuffer();

	/**
	 * Called by the Renderer when it deletes a shader
	 */
	public abstract void onDeleteShader();

	/**
	 * Called by the Renderer when it deletes a texture
	 */
	public abstract void onDeleteTexture();

	/**
	 * Called by the Renderer when it deletes a framebuffer
	 */
	public abstract void onDeleteFrameBuffer();

	/**
	 * Called when video memory is cleared.
	 */
	public abstract void clearMemory();

}