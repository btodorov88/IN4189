package com.jme3.renderer.lwjgl.gl1;

import java.util.EnumSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jme3.light.LightList;
import com.jme3.material.FixedFuncBinding;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Matrix4f;
import com.jme3.renderer.Caps;
import com.jme3.renderer.IStatistics;
import com.jme3.renderer.RenderContext;
import com.jme3.renderer.Statistics;
import com.jme3.renderer.lwjgl.LwjglRenderer;
import com.jme3.renderer.lwjgl.gl1.api.GL11Wrapper;
import com.jme3.renderer.lwjgl.gl1.api.LwjglGL1Capabilities;
import com.jme3.renderer.lwjgl.gl1.api.LwjglGL1Clipper;
import com.jme3.renderer.lwjgl.gl1.api.LwjglGL1LightsRenderer;
import com.jme3.renderer.lwjgl.gl1.api.LwjglGL1MeshRenderer;
import com.jme3.renderer.lwjgl.gl1.api.LwjglGL1Projection;
import com.jme3.renderer.lwjgl.gl1.api.LwjglGL1StateManager;
import com.jme3.renderer.lwjgl.gl1.api.LwjglGL1TextureRenderer;
import com.jme3.renderer.lwjgl.gl1.api.LwjglGL1ViewPort;
import com.jme3.renderer.lwjgl.gl1.api.TextureUtil;
import com.jme3.renderer.lwjgl.gl1.impl.GL11WrapperImpl;
import com.jme3.renderer.lwjgl.gl1.impl.LwjglGL1CapabilitiesImpl;
import com.jme3.renderer.lwjgl.gl1.impl.LwjglGL1ClipperImpl;
import com.jme3.renderer.lwjgl.gl1.impl.LwjglGL1LightsRendererImpl;
import com.jme3.renderer.lwjgl.gl1.impl.LwjglGL1MeshRendererImpl;
import com.jme3.renderer.lwjgl.gl1.impl.LwjglGL1ProjectionImpl;
import com.jme3.renderer.lwjgl.gl1.impl.LwjglGL1StateManagerImpl;
import com.jme3.renderer.lwjgl.gl1.impl.LwjglGL1TextureRendererImpl;
import com.jme3.renderer.lwjgl.gl1.impl.LwjglGL1ViewPortImpl;
import com.jme3.renderer.lwjgl.gl1.utils.ContextUtils;
import com.jme3.renderer.lwjgl.gl1.utils.TextureUtilImpl;
import com.jme3.scene.Mesh;
import com.jme3.texture.Image;
import com.jme3.texture.Texture;
import com.jme3.util.NativeObjectManager;

public class LwjglGL1Renderer extends LwjglGL1RendererCommon {

    private static final Logger logger = Logger.getLogger(LwjglRenderer.class.getName());
    
    private final RenderContext context;
    private final NativeObjectManager objManager;
    
    private final GL11Wrapper gl11Wrapper;
    private final IStatistics statistics;
    
    private final LwjglGL1LightsRenderer lighteningRenderer;
	private final LwjglGL1MeshRenderer meshRenderer;
	private final LwjglGL1TextureRenderer textureRenderer;
	private final LwjglGL1StateManager stateManager;
	private final LwjglGL1Clipper clipper;
	private final LwjglGL1ViewPort viewPort;
	private final LwjglGL1Projection projection;
	private final LwjglGL1Capabilities capabilities;
    
    public LwjglGL1Renderer(){
    	context = new RenderContext();
    	objManager = new NativeObjectManager();
    	gl11Wrapper = new GL11WrapperImpl();
    	statistics = new Statistics();
    	lighteningRenderer = new LwjglGL1LightsRendererImpl(gl11Wrapper, context);
    	meshRenderer = new LwjglGL1MeshRendererImpl(gl11Wrapper, statistics, context);
    	textureRenderer = new LwjglGL1TextureRendererImpl(statistics, gl11Wrapper, context, new TextureUtilImpl(), objManager);
    	stateManager = new LwjglGL1StateManagerImpl(gl11Wrapper, context);
    	clipper = new LwjglGL1ClipperImpl(gl11Wrapper, context);
    	viewPort = new LwjglGL1ViewPortImpl(gl11Wrapper);
    	projection = new LwjglGL1ProjectionImpl(gl11Wrapper, context);
    	capabilities = new LwjglGL1CapabilitiesImpl(gl11Wrapper);
    }
    
    public LwjglGL1Renderer(GL11Wrapper gl11_Wrapper, TextureUtil textureUtil, IStatistics statistics, RenderContext context, NativeObjectManager objManager ){
    	this.context = context;
    	this.objManager = objManager;
    	this.gl11Wrapper = gl11_Wrapper;
		this.statistics = statistics;
		this.lighteningRenderer = new LwjglGL1LightsRendererImpl(gl11_Wrapper, context);
		this.meshRenderer = new LwjglGL1MeshRendererImpl(gl11_Wrapper, statistics, context);
		this.textureRenderer = new LwjglGL1TextureRendererImpl(statistics, gl11_Wrapper, context, textureUtil, objManager);
		this.stateManager = new LwjglGL1StateManagerImpl(gl11_Wrapper, context);
		this.clipper = new LwjglGL1ClipperImpl(gl11_Wrapper, context);
		this.viewPort = new LwjglGL1ViewPortImpl(gl11_Wrapper);
		this.projection = new LwjglGL1ProjectionImpl(gl11_Wrapper, context);
		this.capabilities = new LwjglGL1CapabilitiesImpl(gl11_Wrapper);
    }

    public IStatistics getStatistics() {
        return statistics;
    }

    public EnumSet<Caps> getCaps() {
        return capabilities.getCaps();
    }

    public void initialize() {
    	stateManager.initialize();
        capabilities.initialize();
        lighteningRenderer.initialize();
        textureRenderer.initialize();
    }
    
    public void invalidateState() {
        stateManager.invalidateState();
    }

    public void resetGLObjects() {
        logger.log(Level.INFO, "Reseting objects and invalidating state");
        objManager.resetObjects();
        statistics.clearMemory();
        stateManager.invalidateState();
    }

    public void cleanup() {
        logger.log(Level.INFO, "Deleting objects and invalidating state");
        objManager.deleteAllObjects(this);
        statistics.clearMemory();
        stateManager.invalidateState();
    }

    public void setDepthRange(float start, float end) {
        gl11Wrapper.glDepthRange(start, end);
    }

    public void clearBuffers(boolean color, boolean depth, boolean stencil) {
        stateManager.clearBuffers(color, depth, stencil);
    }

    public void setBackgroundColor(ColorRGBA color) {
        gl11Wrapper.glClearColor(color.r, color.g, color.b, color.a);
    }

    public void setFixedFuncBinding(FixedFuncBinding ffBinding, Object val) {        
    	ContextUtils.setFixedFuncBinding(context, ffBinding, val);
    }
    
    public void setViewPort(int x, int y, int w, int h) {
    	viewPort.setViewPort(x, y, w, h);
    }

    public void onFrame() {
        objManager.deleteUnused(this);
    }

    public void setWorldMatrix(Matrix4f worldMatrix) {
        this.lighteningRenderer.setWorldMatrix(worldMatrix);
    }

    public void setViewProjectionMatrices(Matrix4f viewMatrix, Matrix4f projMatrix) {
        lighteningRenderer.setViewMatrix(viewMatrix);
        projection.setProjection(projMatrix);
    }
    
    public void deleteImage(Image image) {
       textureRenderer.deleteImage(image);
    }
    
    @Override
    public void clearClipRect() {
    	clipper.clearClipRect();
    }
    
    @Override
    public void setClipRect(int x, int y, int width, int height) {
    	clipper.setClipRect(x, y, width, height);
    }
    
    @Override
    public void applyRenderState(RenderState state) {
    	stateManager.applyRenderState(state);
    }
    
    @Override
    public void setTexture(int unit, Texture tex) {
    	textureRenderer.setTexture(unit, tex);
    }
    
    @Override
    public void renderMesh(Mesh mesh, int lod, int count) {
    	meshRenderer.renderMesh(mesh, lod, count);
    }

    @Override
    public void setLighting(LightList lights) {
    	lighteningRenderer.setLighting(lights);
    }

    @Override
    public String toString() {
    	return "LwjglGL1Renderer";
    }
}
