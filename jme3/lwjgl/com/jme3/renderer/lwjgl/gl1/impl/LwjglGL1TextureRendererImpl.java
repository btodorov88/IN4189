package com.jme3.renderer.lwjgl.gl1.impl;

import java.nio.IntBuffer;

import jme3tools.converters.MipMapGenerator;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GLContext;

import com.jme3.math.FastMath;
import com.jme3.renderer.IStatistics;
import com.jme3.renderer.RenderContext;
import com.jme3.renderer.RendererException;
import com.jme3.renderer.lwjgl.gl1.api.LwjglGL1TextureRenderer;
import com.jme3.renderer.lwjgl.gl1.api.TextureUtil;
import com.jme3.renderer.lwjgl.gl1.api.GL11Wrapper;
import com.jme3.texture.Image;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapAxis;
import com.jme3.util.BufferUtils;
import com.jme3.util.NativeObjectManager;

public class LwjglGL1TextureRendererImpl implements LwjglGL1TextureRenderer {
	
	private final IStatistics statistics;
	private final GL11Wrapper gl11_Wrapper;
	private final RenderContext context;
	
    private int maxTexSize = 1;

    private final TextureUtil textureUtil;
    
    private final IntBuffer ib1 = BufferUtils.createIntBuffer(1);
    
    private final NativeObjectManager objManager;
    
    
    public LwjglGL1TextureRendererImpl(IStatistics statistics,
			GL11Wrapper gl11_Wrapper, RenderContext context,
			TextureUtil textureUtil, NativeObjectManager objManager) {
		super();
		this.statistics = statistics;
		this.gl11_Wrapper = gl11_Wrapper;
		this.context = context;
		this.textureUtil = textureUtil;
		this.objManager = objManager;
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.gl1.impl.LwjglGL1TextureRenderer#initialize()
	 */
	@Override
	public void initialize() {
		this.maxTexSize = gl11_Wrapper.glGetInteger(GL11.GL_MAX_TEXTURE_SIZE);
	}
	
	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.gl1.impl.LwjglGL1TextureRenderer#setTexture(int, com.jme3.texture.Texture)
	 */
	@Override
	public void setTexture(int unit, Texture tex) {
        if (unit != 0 || tex.getType() != Texture.Type.TwoDimensional) {
            //throw new UnsupportedOperationException();
            return;
        }

        Image image = tex.getImage();
        if (image.isUpdateNeeded() || (image.isGeneratedMipmapsRequired() && !image.isMipmapsGenerated()) ) {
            updateTexImageData(image, tex.getType(), unit);
        }

        int texId = image.getId();
        assert texId != -1;

        Image[] textures = context.boundTextures;

        int type = textureUtil.convertTextureType(tex.getType());

        if (textures[unit] != image) {
            gl11_Wrapper.glEnable(type);
            gl11_Wrapper.glBindTexture(type, texId);
            textures[unit] = image;

            statistics.onTextureUse(image, true);
        } else {
            statistics.onTextureUse(image, false);
        }

        setupTextureParams(tex);
    }
	
    /* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.gl1.impl.LwjglGL1TextureRenderer#deleteImage(com.jme3.texture.Image)
	 */
    @Override
	public void deleteImage(Image image) {
        int texId = image.getId();
        if (texId != -1) {
            ib1.put(0, texId);
            ib1.position(0).limit(1);
            gl11_Wrapper.glDeleteTextures(ib1);
            image.resetObject();
        }
    }
	
    private void updateTexImageData(Image img, Texture.Type type, int unit) {
        int texId = img.getId();
        if (texId == -1) {
            // create texture
            gl11_Wrapper.glGenTextures(ib1);
            texId = ib1.get(0);
            img.setId(texId);
            objManager.registerForCleanup(img);

            statistics.onNewTexture();
        }

        // bind texture
        int target = textureUtil.convertTextureType(type);
//        if (context.boundTextureUnit != unit) {
//            gl11_Wrapper.glActiveTexture(GL11.GL_TEXTURE0 + unit);
//            context.boundTextureUnit = unit;
//        }
        if (context.boundTextures[unit] != img) {
            gl11_Wrapper.glEnable(target);
            gl11_Wrapper.glBindTexture(target, texId);
            context.boundTextures[unit] = img;

            statistics.onTextureUse(img, true);
        }

        // Check sizes if graphics card doesn't support NPOT
        if (!gl11_Wrapper.isGL_ARB_texture_non_power_of_two()) {
            if (img.getWidth() != 0 && img.getHeight() != 0) {
                if (!FastMath.isPowerOfTwo(img.getWidth())
                        || !FastMath.isPowerOfTwo(img.getHeight())) {

                    // Resize texture to Power-of-2 size
                    MipMapGenerator.resizeToPowerOf2(img);
                }
            }
        }

        if (!img.hasMipmaps() && img.isGeneratedMipmapsRequired()) {
            // No pregenerated mips available,
            // generate from base level if required

            // Check if hardware mips are supported
            if (GLContext.getCapabilities().OpenGL14) {
                gl11_Wrapper.glTexParameteri(target, GL14.GL_GENERATE_MIPMAP, GL11.GL_TRUE);
            } else {
                MipMapGenerator.generateMipMaps(img);
            }
            img.setMipmapsGenerated(true);
        } else {
        }

        if (img.getWidth() > maxTexSize || img.getHeight() > maxTexSize) {
            throw new RendererException("Cannot upload texture " + img + ". The maximum supported texture resolution is " + maxTexSize);
        }
        
        textureUtil.uploadTexture(img, target, 0, 0);

        img.clearUpdateNeeded();
    }
    
    private void setupTextureParams(Texture tex) {
        int target = textureUtil.convertTextureType(tex.getType());

        // filter things
        int minFilter = convertMinFilter(tex.getMinFilter());
        int magFilter = convertMagFilter(tex.getMagFilter());
        gl11_Wrapper.glTexParameteri(target, GL11.GL_TEXTURE_MIN_FILTER, minFilter);
        gl11_Wrapper.glTexParameteri(target, GL11.GL_TEXTURE_MAG_FILTER, magFilter);

        // repeat modes
        switch (tex.getType()) {
//            case ThreeDimensional:
//            case CubeMap:
//                gl11_Wrapper.glTexParameteri(target, GL11.GL_TEXTURE_WRAP_R, convertWrapMode(tex.getWrap(WrapAxis.R)));
            case TwoDimensional:
                gl11_Wrapper.glTexParameteri(target, GL11.GL_TEXTURE_WRAP_T, convertWrapMode(tex.getWrap(WrapAxis.T)));
                // fall down here is intentional..
//            case OneDimensional:
                gl11_Wrapper.glTexParameteri(target, GL11.GL_TEXTURE_WRAP_S, convertWrapMode(tex.getWrap(WrapAxis.S)));
                break;
            default:
                throw new UnsupportedOperationException("Unknown texture type: " + tex.getType());
        }
    }
    
    private int convertMagFilter(Texture.MagFilter filter) {
        switch (filter) {
            case Bilinear:
                return GL11.GL_LINEAR;
            case Nearest:
                return GL11.GL_NEAREST;
            default:
                throw new UnsupportedOperationException("Unknown mag filter: " + filter);
        }
    }

    private int convertMinFilter(Texture.MinFilter filter) {
        switch (filter) {
            case Trilinear:
                return GL11.GL_LINEAR_MIPMAP_LINEAR;
            case BilinearNearestMipMap:
                return GL11.GL_LINEAR_MIPMAP_NEAREST;
            case NearestLinearMipMap:
                return GL11.GL_NEAREST_MIPMAP_LINEAR;
            case NearestNearestMipMap:
                return GL11.GL_NEAREST_MIPMAP_NEAREST;
            case BilinearNoMipMaps:
                return GL11.GL_LINEAR;
            case NearestNoMipMaps:
                return GL11.GL_NEAREST;
            default:
                throw new UnsupportedOperationException("Unknown min filter: " + filter);
        }
    }

    private int convertWrapMode(Texture.WrapMode mode) {
        switch (mode) {
            case EdgeClamp:
            case Clamp:
            case BorderClamp:
                return GL11.GL_CLAMP;
            case Repeat:
                return GL11.GL_REPEAT;
            default:
                throw new UnsupportedOperationException("Unknown wrap mode: " + mode);
        }
    }
}
