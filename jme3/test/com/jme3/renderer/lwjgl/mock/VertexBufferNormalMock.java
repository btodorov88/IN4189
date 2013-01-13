package com.jme3.renderer.lwjgl.mock;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.jme3.scene.VertexBuffer;

public class VertexBufferNormalMock extends VertexBuffer {
	
	public VertexBufferNormalMock() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Buffer getData() {
		return FloatBuffer.allocate(48);
	}
	
	@Override
	public Format getFormat() {
		return format.Float;
	}
	
	@Override
	public Type getBufferType() {
		return Type.Normal;
	}
	
}
