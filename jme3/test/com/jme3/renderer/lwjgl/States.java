package com.jme3.renderer.lwjgl;

public class States {

	private static String testInitialize_2 = "[{com.jme3.renderer.lwjgl.mock.GL11Mock.isOpenGL12 []}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glShadeModel [7425]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glColorMaterial [1032, 4609]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glHint [3152, 4354]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [32826]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.isGL_ARB_texture_non_power_of_two []}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glGetInteger [3377]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glGetInteger [3379]}]";
	private static String testInitialize_3 = "[{com.jme3.renderer.lwjgl.mock.GL11Mock.isOpenGL12 []}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glShadeModel [7425]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glColorMaterial [1032, 4609]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glHint [3152, 4354]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2977]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.isGL_ARB_texture_non_power_of_two []}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glGetInteger [3377]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glGetInteger [3379]}]";
	private static String testInvalidateState = "[{com.jme3.renderer.lwjgl.mock.RendererContextMock.reset []}]";
	private static String testResetGLObjects = "[{com.jme3.renderer.lwjgl.mock.NativeObjectManagerMock.resetObjects []}, {com.jme3.renderer.lwjgl.mock.StatisticsMock.clearMemory []}, {com.jme3.renderer.lwjgl.mock.RendererContextMock.reset []}]";
	private static String testCleanup = "[{com.jme3.renderer.lwjgl.mock.NativeObjectManagerMock.deleteAllObjects [LwjglGL1Renderer]}, {com.jme3.renderer.lwjgl.mock.StatisticsMock.clearMemory []}, {com.jme3.renderer.lwjgl.mock.RendererContextMock.reset []}]";
	private static String testSetDepthRange = "[{com.jme3.renderer.lwjgl.mock.GL11Mock.glDepthRange [1.0, 3.0]}]";
	private static String testClearBuffers = "[{com.jme3.renderer.lwjgl.mock.GL11Mock.glColorMask [true, true, true]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDepthMask [true]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glClear [17664]}]";
	private static String testSetBackgroundColor = "[{com.jme3.renderer.lwjgl.mock.GL11Mock.glClearColor [1.0, 2.0, 3.0, 4.0]}]";
	private static String testSetViewPort = "[{com.jme3.renderer.lwjgl.mock.GL11Mock.glViewport [1, 2, 3]}]";
	private static String testOnFrame = "[{com.jme3.renderer.lwjgl.mock.NativeObjectManagerMock.deleteUnused [LwjglGL1Renderer]}]";
	private static String testDeleteImage = "[{com.jme3.renderer.lwjgl.mock.GL11Mock.glDeleteTextures [java.nio.DirectIntBufferU[pos=0 lim=1 cap=1]]}, {com.jme3.renderer.lwjgl.mock.ImageMock.resetObject []}]";
	private static String testClearClipRect = "[]";
	private static String testClearClipRect_1 = "[{com.jme3.renderer.lwjgl.mock.GL11Mock.glDisable [3089]}]";
	private static String testSetClipRect = "[{com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [3089]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glScissor [1, 2, 3]}]";
	private static String testSetTexture = "[]";
	private static String testSetTexture_1 = "[{com.jme3.renderer.lwjgl.mock.GL11Mock.glGenTextures [java.nio.DirectIntBufferU[pos=0 lim=1 cap=1]]}, {com.jme3.renderer.lwjgl.mock.NativeObjectManagerMock.registerForCleanup [-1]}, {com.jme3.renderer.lwjgl.mock.StatisticsMock.onNewTexture []}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [3553]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glBindTexture [3553, 0]}, {com.jme3.renderer.lwjgl.mock.StatisticsMock.onTextureUse [-1, true]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.isGL_ARB_texture_non_power_of_two []}, {com.jme3.renderer.lwjgl.mock.TextureUtilMock.uploadTexture [-1, 3553, 0, 0]}, {com.jme3.renderer.lwjgl.mock.StatisticsMock.onTextureUse [-1, false]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glTexParameteri [3553, 10241, 9729]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glTexParameteri [3553, 10240, 9729]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glTexParameteri [3553, 10243, 10496]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glTexParameteri [3553, 10242, 10496]}]";
	private static String testSetWorldMatrix = "[]";
	private static String testSetLighting = "[{com.jme3.renderer.lwjgl.mock.GL11Mock.glDisable [2896]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glColor4f [1.0, 1.0, 1.0, 1.0]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDisable [3008]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glMatrixMode [5888]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glLoadMatrix [java.nio.DirectFloatBufferU[pos=0 lim=16 cap=16]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glMultMatrix [java.nio.DirectFloatBufferU[pos=0 lim=16 cap=16]]}]";
	private static String testSetLighting_1 = "[{com.jme3.renderer.lwjgl.mock.GL11Mock.glMaterialf [1032, 5633, 0.0]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glMaterial [1032, 4608, java.nio.DirectFloatBufferU[pos=0 lim=16 cap=16]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glMaterial [1032, 4609, java.nio.DirectFloatBufferU[pos=0 lim=16 cap=16]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glMaterial [1032, 4610, java.nio.DirectFloatBufferU[pos=0 lim=16 cap=16]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDisable [2903]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDisable [3008]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2896]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glLightModel [2899, java.nio.DirectFloatBufferU[pos=0 lim=16 cap=16]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glMatrixMode [5888]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glLoadMatrix [java.nio.DirectFloatBufferU[pos=0 lim=16 cap=16]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glLoadMatrix [java.nio.DirectFloatBufferU[pos=0 lim=16 cap=16]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glMultMatrix [java.nio.DirectFloatBufferU[pos=0 lim=16 cap=16]]}]";
	private static String testSetLighting_2 = "[{com.jme3.renderer.lwjgl.mock.GL11Mock.glMaterialf [1032, 5633, 0.0]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glMaterial [1032, 4608, java.nio.DirectFloatBufferU[pos=0 lim=16 cap=16]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glMaterial [1032, 4609, java.nio.DirectFloatBufferU[pos=0 lim=16 cap=16]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glMaterial [1032, 4610, java.nio.DirectFloatBufferU[pos=0 lim=16 cap=16]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDisable [2903]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDisable [3008]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2896]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glLightModel [2899, java.nio.DirectFloatBufferU[pos=0 lim=16 cap=16]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glMatrixMode [5888]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glLoadMatrix [java.nio.DirectFloatBufferU[pos=0 lim=16 cap=16]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [16384]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glLight [16384, 4609, java.nio.DirectFloatBufferU[pos=0 lim=16 cap=16]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glLight [16384, 4610, java.nio.DirectFloatBufferU[pos=0 lim=16 cap=16]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glLight [16384, 4611, java.nio.DirectFloatBufferU[pos=0 lim=16 cap=16]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glLightf [16384, 4614, 180]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glLoadMatrix [java.nio.DirectFloatBufferU[pos=0 lim=16 cap=16]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glMultMatrix [java.nio.DirectFloatBufferU[pos=0 lim=16 cap=16]]}]";
	private static String testSetLighting_3 = "[{com.jme3.renderer.lwjgl.mock.GL11Mock.glMaterialf [1032, 5633, 0.0]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glMaterial [1032, 4608, java.nio.DirectFloatBufferU[pos=0 lim=16 cap=16]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glMaterial [1032, 4609, java.nio.DirectFloatBufferU[pos=0 lim=16 cap=16]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glMaterial [1032, 4610, java.nio.DirectFloatBufferU[pos=0 lim=16 cap=16]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDisable [2903]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDisable [3008]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2896]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glLightModel [2899, java.nio.DirectFloatBufferU[pos=0 lim=16 cap=16]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glMatrixMode [5888]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glLoadMatrix [java.nio.DirectFloatBufferU[pos=0 lim=16 cap=16]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [16384]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glLight [16384, 4609, java.nio.DirectFloatBufferU[pos=0 lim=16 cap=16]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glLight [16384, 4610, java.nio.DirectFloatBufferU[pos=0 lim=16 cap=16]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glLight [16384, 4611, java.nio.DirectFloatBufferU[pos=0 lim=16 cap=16]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glLightf [16384, 4614, 180]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glLightf [16384, 4615, 1]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glLightf [16384, 4616, 0]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glLightf [16384, 4617, 0]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glLoadMatrix [java.nio.DirectFloatBufferU[pos=0 lim=16 cap=16]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glMultMatrix [java.nio.DirectFloatBufferU[pos=0 lim=16 cap=16]]}]";
	private static String testSetLighting_4 = "[{com.jme3.renderer.lwjgl.mock.GL11Mock.glMaterialf [1032, 5633, 0.0]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glMaterial [1032, 4608, java.nio.DirectFloatBufferU[pos=0 lim=16 cap=16]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glMaterial [1032, 4609, java.nio.DirectFloatBufferU[pos=0 lim=16 cap=16]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glMaterial [1032, 4610, java.nio.DirectFloatBufferU[pos=0 lim=16 cap=16]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDisable [2903]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDisable [3008]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2896]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glLightModel [2899, java.nio.DirectFloatBufferU[pos=0 lim=16 cap=16]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glMatrixMode [5888]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glLoadMatrix [java.nio.DirectFloatBufferU[pos=0 lim=16 cap=16]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [16384]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glLight [16384, 4609, java.nio.DirectFloatBufferU[pos=0 lim=16 cap=16]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glLight [16384, 4610, java.nio.DirectFloatBufferU[pos=0 lim=16 cap=16]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glLight [16384, 4611, java.nio.DirectFloatBufferU[pos=0 lim=16 cap=16]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glLight [16384, 4612, java.nio.DirectFloatBufferU[pos=0 lim=16 cap=16]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glLightf [16384, 4614, 7.5]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glLightf [16384, 4613, 32.0]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glLightf [16384, 4616, 0.0]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glLoadMatrix [java.nio.DirectFloatBufferU[pos=0 lim=16 cap=16]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glMultMatrix [java.nio.DirectFloatBufferU[pos=0 lim=16 cap=16]]}]";
	private static String testSetViewProjectionMatrices = "[{com.jme3.renderer.lwjgl.mock.GL11Mock.glMatrixMode [5889]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glLoadMatrix [java.nio.DirectFloatBufferU[pos=0 lim=16 cap=16]]}]";
	private static String testApplyRenderState_0 = "[{com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2929]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDepthFunc [515]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2884]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glCullFace [1029]}]";
	private static String testApplyRenderState_1 = "[{com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2929]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDepthFunc [515]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glColorMask [true, true, true]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2884]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glCullFace [1029]}]";
	private static String testApplyRenderState_2 = "[{com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2929]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDepthFunc [515]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glColorMask [false, false, false]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2884]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glCullFace [1029]}]";
	private static String testApplyRenderState_3 = "[{com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2929]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDepthFunc [515]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDepthMask [true]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2884]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glCullFace [1029]}]";
	private static String testApplyRenderState_4 = "[{com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2929]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDepthFunc [515]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDepthMask [false]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2884]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glCullFace [1029]}]";
	private static String testApplyRenderState_5 = "[{com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2929]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDepthFunc [515]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2884]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glCullFace [1029]}]";
	private static String testApplyRenderState_6 = "[{com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2929]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDepthFunc [515]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2884]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glCullFace [1029]}]";
	private static String testApplyRenderState_7 = "[{com.jme3.renderer.lwjgl.mock.GL11Mock.glDisable [2929]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2884]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glCullFace [1029]}]";
	private static String testApplyRenderState_8 = "[{com.jme3.renderer.lwjgl.mock.GL11Mock.glPolygonMode [1032, 6913]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2929]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDepthFunc [515]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2884]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glCullFace [1029]}]";
	private static String testApplyRenderState_9 = "[{com.jme3.renderer.lwjgl.mock.GL11Mock.glPolygonMode [1032, 6914]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2929]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDepthFunc [515]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2884]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glCullFace [1029]}]";
	private static String testApplyRenderState_10 = "[{com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2929]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDepthFunc [515]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [32823]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glPolygonOffset [1.0, 1.0]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2884]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glCullFace [1029]}]";
	private static String testApplyRenderState_11 = "[{com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2929]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDepthFunc [515]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glPolygonOffset [1.0, 1.0]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2884]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glCullFace [1029]}]";
	private static String testApplyRenderState_12 = "[{com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2929]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDepthFunc [515]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDisable [32823]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2884]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glCullFace [1029]}]";
	private static String testApplyRenderState_14 = "[{com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2929]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDepthFunc [515]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2884]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glCullFace [1029]}]";
	private static String testApplyRenderState_15 = "[{com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2929]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDepthFunc [515]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2884]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glCullFace [1029]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [3042]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glBlendFunc [1, 1]}]";
	private static String testApplyRenderState_16 = "[{com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2929]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDepthFunc [515]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2884]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glCullFace [1029]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [3042]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glBlendFunc [770, 1]}]";
	private static String testApplyRenderState_17 = "[{com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2929]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDepthFunc [515]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2884]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glCullFace [1029]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [3042]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glBlendFunc [1, 769]}]";
	private static String testApplyRenderState_18 = "[{com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2929]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDepthFunc [515]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2884]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glCullFace [1029]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [3042]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glBlendFunc [1, 771]}]";
	private static String testApplyRenderState_19 = "[{com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2929]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDepthFunc [515]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2884]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glCullFace [1029]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [3042]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glBlendFunc [774, 0]}]";
	private static String testApplyRenderState_20 = "[{com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2929]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDepthFunc [515]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [2884]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glCullFace [1029]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnable [3042]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glBlendFunc [774, 768]}]";
	private static String testRenderMesh = "[{com.jme3.renderer.lwjgl.mock.StatisticsMock.onMeshDrawn [MeshMock.Points, 1]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnableClientState [32886]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glColorPointer [0, 0, java.nio.HeapByteBuffer[pos=0 lim=48 cap=48]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDrawElements [0, java.nio.HeapByteBuffer[pos=0 lim=48 cap=48]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDisableClientState [32886]}]";
	private static String testRenderMesh_0 = "[{com.jme3.renderer.lwjgl.mock.StatisticsMock.onMeshDrawn [MeshMock.Points, 1]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDrawElements [0, java.nio.HeapByteBuffer[pos=0 lim=48 cap=48]]}]";
	private static String testRenderMesh_1 = "[{com.jme3.renderer.lwjgl.mock.StatisticsMock.onMeshDrawn [MeshMock.Points, 1]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnableClientState [32884]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glVertexPointer [0, 0, java.nio.HeapFloatBuffer[pos=0 lim=48 cap=48]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDrawElements [0, java.nio.HeapByteBuffer[pos=0 lim=48 cap=48]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDisableClientState [32884]}]";
	private static String testRenderMesh_2 = "[{com.jme3.renderer.lwjgl.mock.StatisticsMock.onMeshDrawn [MeshMock.Points, 1]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnableClientState [32885]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glNormalPointer [0, java.nio.HeapFloatBuffer[pos=0 lim=48 cap=48]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDrawElements [0, java.nio.HeapByteBuffer[pos=0 lim=48 cap=48]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDisableClientState [32885]}]";
	private static String testRenderMesh_3 = "[{com.jme3.renderer.lwjgl.mock.StatisticsMock.onMeshDrawn [MeshMock.Points, 1]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnableClientState [32888]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glTexCoordPointer [0, 0, java.nio.HeapFloatBuffer[pos=0 lim=48 cap=48]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDrawElements [0, java.nio.HeapByteBuffer[pos=0 lim=48 cap=48]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDisableClientState [32888]}]";
	private static String testRenderMesh_4 = "[{com.jme3.renderer.lwjgl.mock.StatisticsMock.onMeshDrawn [MeshMock.Lines, 1]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnableClientState [32888]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glTexCoordPointer [0, 0, java.nio.HeapFloatBuffer[pos=0 lim=48 cap=48]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDrawElements [1, java.nio.HeapByteBuffer[pos=0 lim=48 cap=48]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDisableClientState [32888]}]";
	private static String testRenderMesh_5 = "[{com.jme3.renderer.lwjgl.mock.StatisticsMock.onMeshDrawn [MeshMock.LineLoop, 1]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnableClientState [32888]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glTexCoordPointer [0, 0, java.nio.HeapFloatBuffer[pos=0 lim=48 cap=48]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDrawElements [2, java.nio.HeapByteBuffer[pos=0 lim=48 cap=48]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDisableClientState [32888]}]";
	private static String testRenderMesh_51 = "[{com.jme3.renderer.lwjgl.mock.StatisticsMock.onMeshDrawn [MeshMock.LineStrip, 1]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnableClientState [32888]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glTexCoordPointer [0, 0, java.nio.HeapFloatBuffer[pos=0 lim=48 cap=48]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDrawElements [3, java.nio.HeapByteBuffer[pos=0 lim=48 cap=48]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDisableClientState [32888]}]";
	private static String testRenderMesh_6 = "[{com.jme3.renderer.lwjgl.mock.StatisticsMock.onMeshDrawn [MeshMock.Triangles, 1]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnableClientState [32888]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glTexCoordPointer [0, 0, java.nio.HeapFloatBuffer[pos=0 lim=48 cap=48]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDrawElements [4, java.nio.HeapByteBuffer[pos=0 lim=48 cap=48]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDisableClientState [32888]}]";
	private static String testRenderMesh_7 = "[{com.jme3.renderer.lwjgl.mock.StatisticsMock.onMeshDrawn [MeshMock.TriangleStrip, 1]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnableClientState [32888]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glTexCoordPointer [0, 0, java.nio.HeapFloatBuffer[pos=0 lim=48 cap=48]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDrawElements [5, java.nio.HeapByteBuffer[pos=0 lim=48 cap=48]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDisableClientState [32888]}]";
	private static String testRenderMesh_8 = "[{com.jme3.renderer.lwjgl.mock.StatisticsMock.onMeshDrawn [MeshMock.TriangleFan, 1]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnableClientState [32888]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glTexCoordPointer [0, 0, java.nio.HeapFloatBuffer[pos=0 lim=48 cap=48]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDrawElements [6, java.nio.HeapByteBuffer[pos=0 lim=48 cap=48]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDisableClientState [32888]}]";
	private static String testRenderMesh_10 = "[{com.jme3.renderer.lwjgl.mock.GL11Mock.glPointSize [1.0]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glLineWidth [1.0]}, {com.jme3.renderer.lwjgl.mock.StatisticsMock.onMeshDrawn [MeshMock.TriangleFan, 1]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnableClientState [32888]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glTexCoordPointer [0, 0, java.nio.HeapFloatBuffer[pos=0 lim=48 cap=48]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDrawElements [6, java.nio.HeapByteBuffer[pos=0 lim=48 cap=48]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDisableClientState [32888]}]";
	private static String testRenderMesh_11 = "[{com.jme3.renderer.lwjgl.mock.StatisticsMock.onMeshDrawn [MeshMock.TriangleFan, 1]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glEnableClientState [32888]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glTexCoordPointer [0, 0, java.nio.HeapFloatBuffer[pos=0 lim=48 cap=48]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDrawElements [6, java.nio.HeapByteBuffer[pos=0 lim=48 cap=48]]}, {com.jme3.renderer.lwjgl.mock.GL11Mock.glDisableClientState [32888]}]";

	public static boolean checkState(String state){
		try {
			return States.class.getDeclaredField(getCallingMethod()).get(String.class).equals(state);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}  
	}
	
	private static String getCallingMethod() {
		StackTraceElement[] stackTraceElements = Thread.currentThread()
				.getStackTrace();

		return stackTraceElements[3].getMethodName();
	}
}
