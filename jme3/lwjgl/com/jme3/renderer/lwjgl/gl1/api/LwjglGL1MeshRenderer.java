package com.jme3.renderer.lwjgl.gl1.api;

import com.jme3.scene.Mesh;

public interface LwjglGL1MeshRenderer {

	public abstract void renderMesh(Mesh mesh, int lod, int count);

}