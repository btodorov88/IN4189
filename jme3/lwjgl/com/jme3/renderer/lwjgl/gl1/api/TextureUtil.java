package com.jme3.renderer.lwjgl.gl1.api;

import com.jme3.texture.Image;
import com.jme3.texture.Texture.Type;

public interface TextureUtil {

	public void uploadTexture(Image image, int target, int index,
			int border);

	public GLImageFormat getImageFormatWithError(
			com.jme3.texture.Image.Format format);

	int convertTextureType(Type type);

}