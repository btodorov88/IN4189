package com.jme3.renderer.lwjgl.mock;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.jme3.scene.VertexBuffer;

public class VertexBufferColorMock extends VertexBuffer {
	
	public VertexBufferColorMock() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Buffer getData() {
		return ByteBuffer.allocate(48);
	}
	
	@Override
	public Format getFormat() {
		return format.UnsignedByte;
	}
	
	@Override
	public Type getBufferType() {
		return Type.Color;
	}
	
}
