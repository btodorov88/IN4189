package com.jme3.material;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.jme3.asset.AssetKey;
import com.jme3.asset.DesktopAssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Matrix4f;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.shader.Shader;
import com.jme3.shader.VarType;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.Type;
import com.jme3.texture.Texture.WrapAxis;
import com.jme3.texture.Texture.WrapMode;

public class MaterialTest {

	Material material;
	MaterialDef materialDef;
	
	@Before
	public void setUp() throws Exception {
		//Setup Material class with mockups for materialdef or actual objects
		materialDef = new MaterialDef(new DesktopAssetManager(), "Phong Lighting");
		materialDef.addMaterialParam(VarType.Int, "Brightness", 20, FixedFuncBinding.MaterialShininess);
		materialDef.addMaterialParam(VarType.Matrix4, "Matrix4", new Matrix4f(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16), null);
		materialDef.addMaterialParam(VarType.Boolean, "Fragile", false, null);
		materialDef.addMaterialParam(VarType.Float, "Coordinates", new Float(55.30), null);
		materialDef.addMaterialParam(VarType.Vector4, "Colour", ColorRGBA.Blue, null);
		materialDef.addMaterialParam(VarType.Vector2, "Smell", new Vector2f(1, 3), null);
		materialDef.addMaterialParam(VarType.Vector3, "Point", new Vector3f(1, 3, 45), null);
		material = new Material(materialDef);
	}

	/*@Test
	public void testMaterialMaterialDef() {
		MaterialDef def = material.getMaterialDef();
		assertEquals(materialDef, def);
	}

	@Test
	public void testMaterialAssetManagerString() {
		//fail("Not yet implemented");
		assert(true);
	}

	@Test
	public void testMaterial() {
		fail("Not yet implemented");
	}
*/
	
	@Test
	public void testGetAssetName() {
		String assetName = material.getAssetName();
		assertEquals(null, assetName);
	}

	
	@Test
	public void testGetName() {
		String wood = "wood";
		material.setName(wood);
		String name = material.getName();
		assertEquals("wood", name);
	}

	
	@Test
	public void testSetName() {
		String wood = "wood";
		material.setName(wood);
		String name = material.getName();
		assertEquals("wood", name);
	}

	
	@Test
	public void testSetKey() {
		AssetKey key = new AssetKey<>("key");
		material.setKey(key);
		assertEquals(key, material.getKey());
	}

	@Test
	public void testGetKey() {
		AssetKey key = material.getKey();
		assertEquals(null, key);
	}

	
	@Test
	public void testGetSortId() {
		int sortId = material.getSortId();
		assertEquals(-1, sortId);
	}

	
	@Test
	public void testClone() {
		Material clone = material.clone();
		assertFalse(material.equals(clone));
	}

	@Test
	public void testContentEquals() {
		Material clone = material.clone();
		assertTrue(material.contentEquals(clone));
	}

	@Test
	public void testContentHashCode() {
		int hashCode = material.contentHashCode();
		material.getAdditionalRenderState();
		int newHashCode = material.contentHashCode();
		assertFalse(hashCode == newHashCode);
	}

	@Test
	public void testGetActiveTechnique() {
		Technique technique = material.getActiveTechnique();
		assertEquals(null, technique);
	}

	@Test
	public void testIsTransparent() {
		boolean isTransparent = material.isTransparent();
		assertFalse(isTransparent);
	}

	@Test
	public void testSetTransparent() {
		material.setTransparent(true);
		assertTrue(material.isTransparent());
	}

	@Test
	public void testIsReceivesShadows() {
		boolean isReceivesShadows = material.isReceivesShadows();
		assertFalse(isReceivesShadows);
	}

	@Test
	public void testSetReceivesShadows() {
		material.setReceivesShadows(true);
		boolean isReceivesShadows = material.isReceivesShadows();
		assertTrue(isReceivesShadows);
	}

	@Test
	public void testGetAdditionalRenderState() {
		RenderState renderState = material.getAdditionalRenderState();
		RenderState additionalRenderState = RenderState.ADDITIONAL;
		assertEquals(renderState, additionalRenderState);
	}

	@Test
	public void testGetMaterialDef() {
		MaterialDef def = material.getMaterialDef();
		assertEquals(materialDef, def);
	}

	@Test
	public void testGetParam() {
		MatParam param = material.getParam("Brightness");
		assertEquals(20, param.value);
	}

	@Test
	public void testGetTextureParam() {
		MatParamTexture textureParam = material.getTextureParam("wood");
		assertEquals(null, textureParam);
	}

	@Test
	public void testGetParams() {
		Collection<MatParam> params = material.getParams();
		assertFalse(params.isEmpty());
	}

	@Test
	public void testSetParam() {
		material.setParam("Brightness", VarType.Int, 80);
		assertEquals(80, material.getParam("Brightness").value);
	}

	@Test
	public void testClearParam() {
		material.clearParam("Brightness");
		MatParam param = material.getParam("Brightness");
		assertEquals(null, param);
	}

	@Test
	public void testSetTextureParam() {
		//fail("Not yet implemented");
	}

	@Test
	public void testSetTexture() {
		//material.setTexture("Lighting", new Texture() {
	}

	@Test
	public void testSetMatrix4() {
		Matrix4f matrix = new Matrix4f(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
		material.setMatrix4("Matrix4", matrix);
		MatParam matrix4 = material.getParam("Matrix4");
		assertEquals(matrix, matrix4.value);
	}

	@Test
	public void testSetBoolean() {
		material.setBoolean("Fragile", true);
		MatParam fragile = material.getParam("Fragile");
		assertEquals(true, fragile.value);
	}

	@Test
	public void testSetFloat() {
		Float waarde = 22.30f; 
		material.setFloat("Coordinates", waarde);
		MatParam coordinates = material.getParam("Coordinates");
		assertEquals(waarde, coordinates.value);
	}

	@Test
	public void testSetInt() {
		int brightnessValue = 456;
		material.setInt("Brightness", brightnessValue);
		MatParam brightness = material.getParam("Brightness");
		assertEquals(brightnessValue, brightness.value);
		
	}

	@Test
	public void testSetColor() {
		material.setColor("Colour", ColorRGBA.Green);
		MatParam colour = material.getParam("Colour");
		assertEquals(ColorRGBA.Green, colour.value);
	}

	@Test
	public void testSetVector2() {
		material.setVector2("Smell", new Vector2f(42, 24));
		MatParam smell = material.getParam("Smell");
		assertEquals(new Vector2f(42,24), smell.value);
	}

	@Test
	public void testSetVector3() {
		material.setVector3("Point", new Vector3f(1,2,3));
		MatParam point = material.getParam("Point");
		assertEquals(new Vector3f(1,2,3), point.value);
	}

	@Test
	public void testSetVector4() {
		material.setColor("Colour", ColorRGBA.Green);
		MatParam colour = material.getParam("Colour");
		assertEquals(ColorRGBA.Green, colour.value);
	}

	@Test
	public void testUpdateLightListUniforms() {
		Shader shader = new Shader();
		shader.initialize();
		Geometry geometry = new Geometry("Pipe");
		material.updateLightListUniforms(shader, geometry, 5);
	}

	/*
	@Test
	public void testRenderMultipassLighting() {
		fail("Not yet implemented");
	}

	@Test
	public void testSelectTechnique() {
		fail("Not yet implemented");
	}

	@Test
	public void testPreload() {
		fail("Not yet implemented");
	}

	@Test
	public void testRender() {
		fail("Not yet implemented");
	}

	@Test
	public void testWrite() {
		fail("Not yet implemented");
	}

	@Test
	public void testRead() {
		fail("Not yet implemented");
	}*/

}
