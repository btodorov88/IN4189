package com.jme3.renderer.lwjgl.mock;

import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.VertexBuffer.Format;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.scene.VertexBuffer.Usage;
import com.jme3.util.SafeArrayList;

public class MeshMock extends Mesh {
	
	private VertexBuffer vertexBufferMock;
	private Mode mode;
	private int lodLevels = 2;
	
	public MeshMock(Mode mode, VertexBuffer vertexBufferMock) {
		super();
		this.mode = mode;
		this.vertexBufferMock = vertexBufferMock;
	}
	
	public void setLodLevels(int lodLevels) {
		this.lodLevels = lodLevels;
	}

	@Override
	public int getNumLodLevels() {
		return lodLevels ;
	}
	
	@Override
	public VertexBuffer getBuffer(Type type) {
		if(type.equals(Type.InterleavedData)){
			return null;
		}
		
		return new VertexBufferColorMock();
	}
	
	@Override
	public VertexBuffer getLodLevel(int lod) {
		return new VertexBufferColorMock();
	}
	
	@Override
	public Mode getMode() {
		return mode;
	}
	
	@Override
	public SafeArrayList<VertexBuffer> getBufferList() {
		// TODO Auto-generated method stub
		SafeArrayList<VertexBuffer> safeArrayList = new SafeArrayList<VertexBuffer>(VertexBuffer.class);
		safeArrayList.add(0, vertexBufferMock);
		return safeArrayList;
	}
	
	@Override
	public String toString() {
		if(mode != null)
			return "MeshMock." + mode.name();
		else
			return "MeshMock";
	}

}
