package com.jme3.renderer.lwjgl.gl1.utils;

import com.jme3.material.FixedFuncBinding;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderContext;

public class ContextUtils {
	
    public static void setFixedFuncBinding(RenderContext context, FixedFuncBinding ffBinding, Object val) {        
        switch (ffBinding) {
            case Color:
                context.color = (ColorRGBA) val;
                break;
            case MaterialAmbient:
                context.ambient = (ColorRGBA) val;
                break;
            case MaterialDiffuse:
                context.diffuse = (ColorRGBA) val;
                break;
            case MaterialSpecular:
                context.specular = (ColorRGBA) val;
                break;
            case MaterialShininess:
                context.shininess = (Float) val;
                break;
            case UseVertexColor:
                context.useVertexColor = (Boolean) val;
                break;
            case AlphaTestFallOff:
                context.alphaTestFallOff = (Float) val;
                break;
        }
    }
}
