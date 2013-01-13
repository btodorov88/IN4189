package com.jme3.renderer.lwjgl.gl1.utils;

import java.nio.FloatBuffer;

import com.jme3.math.Matrix4f;

public class MatrixUtils {
	
    public static FloatBuffer storeMatrix(Matrix4f matrix, FloatBuffer store) {
        store.clear();
        matrix.fillFloatBuffer(store, true);
        store.clear();
        return store;
    }

}
