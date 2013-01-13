package com.jme3.renderer.lwjgl.gl1.impl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.opengl.GL11;

import com.jme3.renderer.IStatistics;
import com.jme3.renderer.RenderContext;
import com.jme3.renderer.lwjgl.gl1.api.GL11Wrapper;
import com.jme3.renderer.lwjgl.gl1.api.LwjglGL1MeshRenderer;
import com.jme3.renderer.lwjgl.gl1.utils.VertexUtils;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.Mesh.Mode;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.scene.VertexBuffer.Usage;
import com.jme3.texture.Image;

public class LwjglGL1MeshRendererImpl implements LwjglGL1MeshRenderer {

	private GL11Wrapper gl11_Wrapper;

	private IStatistics statistics;

	private final RenderContext context;

	public LwjglGL1MeshRendererImpl(GL11Wrapper gl11_Wrapper,
			IStatistics statistics, RenderContext context) {
		super();
		this.gl11_Wrapper = gl11_Wrapper;
		this.statistics = statistics;
		this.context = context;
	}

	/* (non-Javadoc)
	 * @see com.jme3.renderer.lwjgl.gl1.impl.LwjglGL1MeshRenderer#renderMesh(com.jme3.scene.Mesh, int, int)
	 */
	@Override
	public void renderMesh(Mesh mesh, int lod, int count) {
		if (mesh.getVertexCount() == 0) {
			return;
		}

		if (context.pointSize != mesh.getPointSize()) {
			gl11_Wrapper.glPointSize(mesh.getPointSize());
			context.pointSize = mesh.getPointSize();
		}
		if (context.lineWidth != mesh.getLineWidth()) {
			gl11_Wrapper.glLineWidth(mesh.getLineWidth());
			context.lineWidth = mesh.getLineWidth();
		}

		boolean dynamic = false;
		if (mesh.getBuffer(Type.InterleavedData) != null) {
			throw new UnsupportedOperationException(
					"Interleaved meshes are not supported");
		}

		if (mesh.getNumLodLevels() == 0) {
			for (VertexBuffer vb : mesh.getBufferList().getArray()) {
				if (vb.getUsage() != VertexBuffer.Usage.Static) {
					dynamic = true;
					break;
				}
			}
		} else {
			dynamic = true;
		}

		statistics.onMeshDrawn(mesh, lod);

		// if (!dynamic) {
		// dealing with a static object, generate display list
		// renderMeshDisplayList(mesh);
		// } else {
		renderMeshDefault(mesh, lod, count);
		// }

	}

	private void renderMeshDefault(Mesh mesh, int lod, int count) {
		VertexBuffer indices = null;

		VertexBuffer interleavedData = mesh.getBuffer(Type.InterleavedData);
		// if (interleavedData != null && interleavedData.isUpdateNeeded()) {
		// updateBufferData(interleavedData);
		// }

		if (mesh.getNumLodLevels() > 0) {
			indices = mesh.getLodLevel(lod);
		} else {
			indices = mesh.getBuffer(Type.Index);
		}
		for (VertexBuffer vb : mesh.getBufferList().getArray()) {
			if (vb.getBufferType() == Type.InterleavedData
					|| vb.getUsage() == Usage.CpuOnly // ignore cpu-only buffers
					|| vb.getBufferType() == Type.Index) {
				continue;
			}

			if (vb.getStride() == 0) {
				// not interleaved
				setVertexAttrib(vb);
			} else {
				// interleaved
				setVertexAttrib(vb, interleavedData);
			}
		}

		if (indices != null) {
			drawTriangleList(indices, mesh, count);
		} else {
			gl11_Wrapper.glDrawArrays(convertElementMode(mesh.getMode()), 0,
					mesh.getVertexCount());
		}

		// TODO: Fix these to use IDList??
		clearVertexAttribs();
		clearTextureUnits();
		resetFixedFuncBindings();
	}

	/**
	 * Reset fixed function bindings to default values.
	 */
	private void resetFixedFuncBindings() {
		context.alphaTestFallOff = 0f; // zero means disable alpha test!
		context.color = null;
		context.ambient = null;
		context.diffuse = null;
		context.specular = null;
		context.shininess = 0;
		context.useVertexColor = false;
	}

	private void drawTriangleList(VertexBuffer indexBuf, Mesh mesh, int count) {
		Mesh.Mode mode = mesh.getMode();

		Buffer indexData = indexBuf.getData();
		indexData.rewind();

		if (mesh.getMode() == Mode.Hybrid) {
			throw new UnsupportedOperationException();
		} else {
			drawElements(convertElementMode(mode),
					VertexUtils.convertVertexFormat(indexBuf.getFormat()),
					indexData);
		}
	}

	private int convertElementMode(Mesh.Mode mode) {
		switch (mode) {
		case Points:
			return GL11.GL_POINTS;
		case Lines:
			return GL11.GL_LINES;
		case LineLoop:
			return GL11.GL_LINE_LOOP;
		case LineStrip:
			return GL11.GL_LINE_STRIP;
		case Triangles:
			return GL11.GL_TRIANGLES;
		case TriangleFan:
			return GL11.GL_TRIANGLE_FAN;
		case TriangleStrip:
			return GL11.GL_TRIANGLE_STRIP;
		default:
			throw new UnsupportedOperationException("Unrecognized mesh mode: "
					+ mode);
		}
	}

	private void drawElements(int mode, int format, Buffer data) {
		switch (format) {
		case GL11.GL_UNSIGNED_BYTE:
			gl11_Wrapper.glDrawElements(mode, (ByteBuffer) data);
			break;
		case GL11.GL_UNSIGNED_SHORT:
			gl11_Wrapper.glDrawElements(mode, (ShortBuffer) data);
			break;
		case GL11.GL_UNSIGNED_INT:
			gl11_Wrapper.glDrawElements(mode, (IntBuffer) data);
			break;
		default:
			throw new UnsupportedOperationException();
		}
	}

	private void clearTextureUnits() {
		Image[] textures = context.boundTextures;
		if (textures[0] != null) {
			gl11_Wrapper.glDisable(GL11.GL_TEXTURE_2D);
			textures[0] = null;
		}
	}

	private void clearVertexAttribs() {
		for (int i = 0; i < 16; i++) {
			VertexBuffer vb = context.boundAttribs[i];
			if (vb != null) {
				int arrayType = VertexUtils
						.convertArrayType(vb.getBufferType());
				gl11_Wrapper.glDisableClientState(arrayType);
				context.boundAttribs[vb.getBufferType().ordinal()] = null;
			}
		}
	}

	private void setVertexAttrib(VertexBuffer vb, VertexBuffer idb) {
		if (vb.getBufferType() == VertexBuffer.Type.Color
				&& !context.useVertexColor) {
			// Ignore vertex color buffer if vertex color is disabled.
			return;
		}

		int arrayType = VertexUtils.convertArrayType(vb.getBufferType());
		if (arrayType == -1) {
			return; // unsupported
		}
		gl11_Wrapper.glEnableClientState(arrayType);
		context.boundAttribs[vb.getBufferType().ordinal()] = vb;

		if (vb.getBufferType() == Type.Normal) {
			// normalize if requested
			if (vb.isNormalized() && !context.normalizeEnabled) {
				gl11_Wrapper.glEnable(GL11.GL_NORMALIZE);
				context.normalizeEnabled = true;
			} else if (!vb.isNormalized() && context.normalizeEnabled) {
				gl11_Wrapper.glDisable(GL11.GL_NORMALIZE);
				context.normalizeEnabled = false;
			}
		}

		// NOTE: Use data from interleaved buffer if specified
		Buffer data = idb != null ? idb.getData() : vb.getData();
		int comps = vb.getNumComponents();
		int type = VertexUtils.convertVertexFormat(vb.getFormat());

		data.rewind();

		switch (vb.getBufferType()) {
		case Position:
			if (!(data instanceof FloatBuffer)) {
				throw new UnsupportedOperationException();
			}

			gl11_Wrapper.glVertexPointer(comps, vb.getStride(),
					(FloatBuffer) data);
			break;
		case Normal:
			if (!(data instanceof FloatBuffer)) {
				throw new UnsupportedOperationException();
			}

			gl11_Wrapper.glNormalPointer(vb.getStride(), (FloatBuffer) data);
			break;
		case Color:
			if (data instanceof FloatBuffer) {
				gl11_Wrapper.glColorPointer(comps, vb.getStride(),
						(FloatBuffer) data);
			} else if (data instanceof ByteBuffer) {
				gl11_Wrapper.glColorPointer(comps, true, vb.getStride(),
						(ByteBuffer) data);
			} else {
				throw new UnsupportedOperationException();
			}
			break;
		case TexCoord:
			if (!(data instanceof FloatBuffer)) {
				throw new UnsupportedOperationException();
			}

			gl11_Wrapper.glTexCoordPointer(comps, vb.getStride(),
					(FloatBuffer) data);
			break;
		default:
			// Ignore, this is an unsupported attribute for OpenGL1.
			break;
		}
	}

	private void setVertexAttrib(VertexBuffer vb) {
		setVertexAttrib(vb, null);
	}

}
