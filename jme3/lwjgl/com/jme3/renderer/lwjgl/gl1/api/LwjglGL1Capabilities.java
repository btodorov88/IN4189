package com.jme3.renderer.lwjgl.gl1.api;

import java.util.EnumSet;

import com.jme3.renderer.Caps;

public interface LwjglGL1Capabilities {

	public abstract void initialize();

	public abstract EnumSet<Caps> getCaps();

}