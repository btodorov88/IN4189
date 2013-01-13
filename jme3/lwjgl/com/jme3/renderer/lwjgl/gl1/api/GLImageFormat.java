package com.jme3.renderer.lwjgl.gl1.api;

public class GLImageFormat {
	public int internalFormat;
	public int format;
	public int dataType;
	public boolean compressed;

	public GLImageFormat(int internalFormat, int format, int dataType,
			boolean compressed) {
		this.internalFormat = internalFormat;
		this.format = format;
		this.dataType = dataType;
		this.compressed = compressed;
	}
}
