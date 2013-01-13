package com.jme3.renderer.lwjgl.gl1.utils;

import org.lwjgl.opengl.GL11;

import com.jme3.scene.VertexBuffer;

public class VertexUtils {
	
   public static int convertArrayType(VertexBuffer.Type type) {
        switch (type) {
            case Position:
                return GL11.GL_VERTEX_ARRAY;
            case Normal:
                return GL11.GL_NORMAL_ARRAY;
            case TexCoord:
                return GL11.GL_TEXTURE_COORD_ARRAY;
            case Color:
                return GL11.GL_COLOR_ARRAY;
            default:
                return -1; // unsupported
        }
    }
    
   public static int convertVertexFormat(VertexBuffer.Format fmt) {
        switch (fmt) {
            case Byte:
                return GL11.GL_BYTE;
            case Float:
                return GL11.GL_FLOAT;
            case Int:
                return GL11.GL_INT;
            case Short:
                return GL11.GL_SHORT;
            case UnsignedByte:
                return GL11.GL_UNSIGNED_BYTE;
            case UnsignedInt:
                return GL11.GL_UNSIGNED_INT;
            case UnsignedShort:
                return GL11.GL_UNSIGNED_SHORT;
            default:
                throw new UnsupportedOperationException("Unrecognized vertex format: " + fmt);
        }
    }

}
