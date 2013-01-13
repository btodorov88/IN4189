package com.jme3.renderer.lwjgl;

import static org.junit.Assert.assertNotNull;

import java.util.EnumSet;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.LightList;
import com.jme3.light.PointLight;
import com.jme3.light.SpotLight;
import com.jme3.material.FixedFuncBinding;
import com.jme3.material.RenderState;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Matrix4f;
import com.jme3.renderer.Caps;
import com.jme3.renderer.IStatistics;
import com.jme3.renderer.RenderContext;
import com.jme3.renderer.lwjgl.gl1.LwjglGL1Renderer;
import com.jme3.renderer.lwjgl.gl1.api.TextureUtil;
import com.jme3.renderer.lwjgl.mock.GL11Mock;
import com.jme3.renderer.lwjgl.mock.ImageMock;
import com.jme3.renderer.lwjgl.mock.MeshMock;
import com.jme3.renderer.lwjgl.mock.NativeObjectManagerMock;
import com.jme3.renderer.lwjgl.mock.RenderStateMock;
import com.jme3.renderer.lwjgl.mock.RendererContextMock;
import com.jme3.renderer.lwjgl.mock.StatisticsMock;
import com.jme3.renderer.lwjgl.mock.TextureMock;
import com.jme3.renderer.lwjgl.mock.TextureUtilMock;
import com.jme3.renderer.lwjgl.mock.VertexBufferColorMock;
import com.jme3.renderer.lwjgl.mock.VertexBufferNormalMock;
import com.jme3.renderer.lwjgl.mock.VertexBufferPositionMock;
import com.jme3.renderer.lwjgl.mock.VertexBufferTexCoordMock;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.Mesh.Mode;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.NativeObjectManager;

/**
 * The class <code>LwjglGL1RendererTest</code> contains tests for the class
 * <code>{@link LwjglGL1Renderer}</code>.
 * 
 * @generatedBy CodePro at 12/17/12 3:36 PM
 * @author btodorov
 * @version $Revision: 1.0 $
 */
public class LwjglGL1RendererTest {

	private LwjglGL1Renderer fixture;
	private StateTracker tracker;
	private IStatistics statistics;
	private GL11Mock gl11Wrapper;
	private RenderContext renderContext;

	@Before
	public void setup() {
		tracker = new StateTracker();

		gl11Wrapper = new GL11Mock(tracker);
		TextureUtil textureUtil = new TextureUtilMock(tracker);
		statistics = new StatisticsMock(tracker);
		renderContext = new RendererContextMock(tracker);
		NativeObjectManager objManager = new NativeObjectManagerMock(tracker);

		fixture = new LwjglGL1Renderer(gl11Wrapper, textureUtil, statistics,
				renderContext, objManager);
	}

	/**
	 * Run the LwjglGL1Renderer(Igl11Wrapper,ITextureUtil,IStatistics)
	 * constructor test.
	 * 
	 * @throws Exception
	 * 
	 * @generatedBy CodePro at 12/17/12 3:36 PM
	 */
	@Test
	public void testLwjglGL1Renderer_2() throws Exception {
		assertNotNull(fixture);
	}
	
	/**
	 * Run the LwjglGL1Renderer(Igl11Wrapper,ITextureUtil,IStatistics)
	 * constructor test.
	 * 
	 * @throws Exception
	 * 
	 * @generatedBy CodePro at 12/17/12 3:36 PM
	 */
	@Test
	public void testLwjglGL1Renderer_1() throws Exception {
		assertNotNull(new LwjglGL1Renderer());
	}

	@Test
	public void testGetStatistics() throws Exception {

		IStatistics getStatistics = fixture.getStatistics();

		Assert.assertSame(getStatistics, statistics);
	}

	@Test
	public void testInitialize_0() throws Exception {

		gl11Wrapper.setGL_ARB_texture_non_power_of_two(true);

		fixture.initialize();

		EnumSet<Caps> caps = fixture.getCaps();

		Assert.assertTrue(caps.contains(Caps.NonPowerOfTwoTextures));
	}

	@Test
	public void testInitialize_1() throws Exception {

		gl11Wrapper.setGL_ARB_texture_non_power_of_two(false);

		fixture.initialize();

		EnumSet<Caps> caps = fixture.getCaps();

		Assert.assertFalse(caps.contains(Caps.NonPowerOfTwoTextures));
	}

	@Test
	public void testInitialize_2() throws Exception {

		gl11Wrapper.setOpenGL12(true);

		fixture.initialize();

		String state = tracker.asString();
		Assert.assertTrue(States.checkState(state));

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testInitialize_3() throws Exception {

		gl11Wrapper.setOpenGL12(false);

		fixture.initialize();

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testInvalidateState() throws Exception {
		fixture.invalidateState();

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testResetGLObjects() throws Exception {
		fixture.resetGLObjects();

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testCleanup() throws Exception {
		fixture.cleanup();

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testSetDepthRange() throws Exception {
		fixture.setDepthRange(1, 3);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testClearBuffers() throws Exception {
		renderContext.colorWriteEnabled = false;
		renderContext.depthWriteEnabled = false;
		fixture.clearBuffers(true, true, true);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testSetBackgroundColor() throws Exception {

		fixture.setBackgroundColor(new ColorRGBA(1, 2, 3, 4));

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testSetFixedFuncBinding() throws Exception {

		fixture.setFixedFuncBinding(FixedFuncBinding.MaterialShininess, 2f);

		Assert.assertEquals(2f, renderContext.shininess, 0.0000001);
	}

	@Test
	public void testSetViewPort() throws Exception {

		fixture.setViewPort(1, 2, 3, 4);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testOnFrame() throws Exception {

		fixture.onFrame();

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testDeleteImage() throws Exception {

		fixture.deleteImage(new ImageMock(tracker));

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testClearClipRect() throws Exception {

		fixture.clearClipRect();

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testClearClipRect_1() throws Exception {

		renderContext.clipRectEnabled = true;
		fixture.clearClipRect();

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testSetClipRect() throws Exception {

		fixture.setClipRect(1, 2, 3, 4);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testSetTexture() throws Exception {

		fixture.setTexture(1, null);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testSetTexture_1() throws Exception {

		gl11Wrapper.setGL_ARB_texture_non_power_of_two(false);

		fixture.setTexture(0, new TextureMock(tracker));

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testSetWorldMatrix() throws Exception {
		fixture.setWorldMatrix(new Matrix4f());

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testSetLighting() throws Exception {

		fixture.setLighting(new LightList());

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testSetLighting_1() throws Exception {

		LightList lights = new LightList();
		lights.add(new AmbientLight());
		fixture.setLighting(lights);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testSetLighting_2() throws Exception {

		LightList lights = new LightList();
		lights.add(new DirectionalLight());
		fixture.setLighting(lights);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testSetLighting_3() throws Exception {

		LightList lights = new LightList();
		lights.add(new PointLight());
		fixture.setLighting(lights);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testSetLighting_4() throws Exception {

		LightList lights = new LightList();
		lights.add(new SpotLight());
		fixture.setLighting(lights);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testSetViewProjectionMatrices() throws Exception {

		fixture.setViewProjectionMatrices(new Matrix4f(), new Matrix4f());

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testApplyRenderState() throws Exception {

		RenderStateMock renderState = new RenderStateMock();
		renderState.setStencilTest(true);
		fixture.applyRenderState(renderState);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testApplyRenderState_0() throws Exception {

		fixture.applyRenderState(new RenderStateMock());

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testApplyRenderState_1() throws Exception {

		RenderStateMock renderStateMock = new RenderStateMock();
		renderStateMock.setColorWrite(true);
		renderContext.colorWriteEnabled = false;

		fixture.applyRenderState(renderStateMock);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testApplyRenderState_2() throws Exception {

		RenderStateMock renderStateMock = new RenderStateMock();
		renderStateMock.setColorWrite(false);
		renderContext.colorWriteEnabled = true;

		fixture.applyRenderState(renderStateMock);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testApplyRenderState_3() throws Exception {

		RenderStateMock renderStateMock = new RenderStateMock();
		renderStateMock.setDepthWrite(true);
		renderContext.depthWriteEnabled = false;

		fixture.applyRenderState(renderStateMock);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testApplyRenderState_4() throws Exception {

		RenderStateMock renderStateMock = new RenderStateMock();
		renderStateMock.setDepthWrite(false);
		renderContext.depthWriteEnabled = true;

		fixture.applyRenderState(renderStateMock);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testApplyRenderState_5() throws Exception {

		RenderStateMock renderStateMock = new RenderStateMock();
		renderStateMock.setAlphaTest(true);

		fixture.applyRenderState(renderStateMock);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testApplyRenderState_6() throws Exception {

		RenderStateMock renderStateMock = new RenderStateMock();
		renderStateMock.setDepthTest(true);
		renderContext.depthTestEnabled = false;

		fixture.applyRenderState(renderStateMock);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testApplyRenderState_7() throws Exception {

		RenderStateMock renderStateMock = new RenderStateMock();
		renderStateMock.setDepthTest(false);
		renderContext.depthTestEnabled = true;

		fixture.applyRenderState(renderStateMock);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testApplyRenderState_8() throws Exception {

		RenderStateMock renderStateMock = new RenderStateMock();
		renderStateMock.setWireframe(true);
		renderContext.wireframe = false;

		fixture.applyRenderState(renderStateMock);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testApplyRenderState_9() throws Exception {

		RenderStateMock renderStateMock = new RenderStateMock();
		renderStateMock.setWireframe(false);
		renderContext.wireframe = true;

		fixture.applyRenderState(renderStateMock);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testApplyRenderState_10() throws Exception {

		RenderStateMock renderStateMock = new RenderStateMock();
		renderStateMock.setPolyOffset(1f, 1f);

		fixture.applyRenderState(renderStateMock);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testApplyRenderState_11() throws Exception {

		RenderStateMock renderStateMock = new RenderStateMock();
		renderStateMock.setPolyOffset(1f, 1f);
		renderContext.polyOffsetEnabled = true;

		fixture.applyRenderState(renderStateMock);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testApplyRenderState_12() throws Exception {

		RenderStateMock renderStateMock = new RenderStateMock();

		renderContext.polyOffsetEnabled = true;

		fixture.applyRenderState(renderStateMock);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testApplyRenderState_14() throws Exception {

		RenderStateMock renderStateMock = new RenderStateMock();

		renderStateMock.setBlendMode(BlendMode.Off);

		fixture.applyRenderState(renderStateMock);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testApplyRenderState_15() throws Exception {

		RenderStateMock renderStateMock = new RenderStateMock();

		renderStateMock.setBlendMode(BlendMode.Additive);

		fixture.applyRenderState(renderStateMock);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testApplyRenderState_16() throws Exception {

		RenderStateMock renderStateMock = new RenderStateMock();

		renderStateMock.setBlendMode(BlendMode.AlphaAdditive);

		fixture.applyRenderState(renderStateMock);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testApplyRenderState_17() throws Exception {

		RenderStateMock renderStateMock = new RenderStateMock();

		renderStateMock.setBlendMode(BlendMode.Color);

		fixture.applyRenderState(renderStateMock);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testApplyRenderState_18() throws Exception {

		RenderStateMock renderStateMock = new RenderStateMock();

		renderStateMock.setBlendMode(BlendMode.PremultAlpha);

		fixture.applyRenderState(renderStateMock);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testApplyRenderState_19() throws Exception {

		RenderStateMock renderStateMock = new RenderStateMock();

		renderStateMock.setBlendMode(BlendMode.Modulate);

		fixture.applyRenderState(renderStateMock);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testApplyRenderState_20() throws Exception {

		RenderStateMock renderStateMock = new RenderStateMock();

		renderStateMock.setBlendMode(BlendMode.ModulateX2);

		fixture.applyRenderState(renderStateMock);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testRenderMesh() throws Exception {

		Mesh mesh = new MeshMock(Mode.Points, new VertexBufferColorMock());
		renderContext.useVertexColor = true;

		fixture.renderMesh(mesh, 1, 2);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testRenderMesh_0() throws Exception {

		Mesh mesh = new MeshMock(Mode.Points, new VertexBufferColorMock());

		fixture.renderMesh(mesh, 1, 2);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testRenderMesh_1() throws Exception {

		Mesh mesh = new MeshMock(Mode.Points, new VertexBufferPositionMock());

		fixture.renderMesh(mesh, 1, 2);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testRenderMesh_2() throws Exception {

		Mesh mesh = new MeshMock(Mode.Points, new VertexBufferNormalMock());

		fixture.renderMesh(mesh, 1, 2);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testRenderMesh_3() throws Exception {

		Mesh mesh = new MeshMock(Mode.Points, new VertexBufferTexCoordMock());

		fixture.renderMesh(mesh, 1, 2);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testRenderMesh_4() throws Exception {

		Mesh mesh = new MeshMock(Mode.Lines, new VertexBufferTexCoordMock());

		fixture.renderMesh(mesh, 1, 2);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testRenderMesh_5() throws Exception {

		Mesh mesh = new MeshMock(Mode.LineLoop, new VertexBufferTexCoordMock());

		fixture.renderMesh(mesh, 1, 2);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testRenderMesh_51() throws Exception {

		Mesh mesh = new MeshMock(Mode.LineStrip, new VertexBufferTexCoordMock());

		fixture.renderMesh(mesh, 1, 2);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testRenderMesh_6() throws Exception {

		Mesh mesh = new MeshMock(Mode.Triangles, new VertexBufferTexCoordMock());

		fixture.renderMesh(mesh, 1, 2);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testRenderMesh_7() throws Exception {

		Mesh mesh = new MeshMock(Mode.TriangleStrip,
				new VertexBufferTexCoordMock());

		fixture.renderMesh(mesh, 1, 2);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testRenderMesh_8() throws Exception {

		Mesh mesh = new MeshMock(Mode.TriangleFan,
				new VertexBufferTexCoordMock());

		fixture.renderMesh(mesh, 1, 2);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRenderMesh_9() throws Exception {

		Mesh mesh = new MeshMock(Mode.Hybrid, new VertexBufferTexCoordMock());

		fixture.renderMesh(mesh, 1, 2);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}

	@Test
	public void testRenderMesh_10() throws Exception {

		renderContext.pointSize = 12;
		renderContext.lineWidth = 32;
		Mesh mesh = new MeshMock(Mode.TriangleFan,
				new VertexBufferTexCoordMock());

		fixture.renderMesh(mesh, 1, 2);

		String state = tracker.asString();

		Assert.assertTrue(States.checkState(state));
	}
	
	@Test
	public void testRenderMesh_11() throws Exception {

		MeshMock mesh = new MeshMock(Mode.TriangleFan,
				new VertexBufferTexCoordMock());
		mesh.setLodLevels(0);

		fixture.renderMesh(mesh, 1, 2);

		String state = tracker.asString();
		Assert.assertTrue(States.checkState(state));

		Assert.assertTrue(States.checkState(state));
	}

	/**
	 * Perform post-test clean-up.
	 * 
	 * @throws Exception
	 *             if the clean-up fails for some reason
	 * 
	 * @generatedBy CodePro at 12/17/12 3:36 PM
	 */
	@After
	public void tearDown() throws Exception {
		// Add additional tear down code here
	}

	/**
	 * Launch the test.
	 * 
	 * @param args
	 *            the command line arguments
	 * 
	 * @generatedBy CodePro at 12/17/12 3:36 PM
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(LwjglGL1RendererTest.class);
	}
}