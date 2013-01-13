package com.jme3.renderer.lwjgl.mock;

import org.lwjgl.opengl.GL11;

import com.jme3.renderer.lwjgl.StateTracker;
import com.jme3.renderer.lwjgl.gl1.api.GLImageFormat;
import com.jme3.renderer.lwjgl.gl1.api.TextureUtil;
import com.jme3.texture.Image;
import com.jme3.texture.Image.Format;
import com.jme3.texture.Texture;

public class TextureUtilMock implements TextureUtil {
	
	private StateTracker tracker;

	public TextureUtilMock(StateTracker tracker) {
		this.tracker = tracker;
	}

	@Override
	public void uploadTexture(Image image, int target, int index, int border) {
		tracker.registerCall(image, target, index, border);

	}
	
	@Override
	public GLImageFormat getImageFormatWithError(Format format) {
		tracker.registerCall(format);
		return null;
	}
	
    public int convertTextureType(Texture.Type type) {
        switch (type) {
            case TwoDimensional:
                return GL11.GL_TEXTURE_2D;
//            case ThreeDimensional:
//                return GL11.GL_TEXTURE_3D;
//            case CubeMap:
//                return GL11.GL_TEXTURE_CUBE_MAP;
            default:
                throw new UnsupportedOperationException("Unknown texture type: " + type);
        }
    }

}
