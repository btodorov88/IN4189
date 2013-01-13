package com.jme3.renderer.lwjgl.gl1.api;

import com.jme3.texture.Image;
import com.jme3.texture.Texture;

public interface LwjglGL1TextureRenderer {

	public abstract void initialize();

	public abstract void setTexture(int unit, Texture tex);

	public abstract void deleteImage(Image image);

}