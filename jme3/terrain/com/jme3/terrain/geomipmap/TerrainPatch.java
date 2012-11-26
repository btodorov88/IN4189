/*
 * Copyright (c) 2009-2012 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.jme3.terrain.geomipmap;

import com.jme3.bounding.BoundingBox;
import com.jme3.bounding.BoundingSphere;
import com.jme3.bounding.BoundingVolume;
import com.jme3.collision.Collidable;
import com.jme3.collision.CollisionResults;
import com.jme3.collision.UnsupportedCollisionException;
import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.math.*;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.terrain.geomipmap.TerrainQuad.LocationHeight;
import com.jme3.terrain.geomipmap.lodcalc.util.EntropyComputeUtil;
import com.jme3.util.BufferUtils;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.List;


/**
 * A terrain patch is a leaf in the terrain quad tree. It has a mesh that can change levels of detail (LOD)
 * whenever the view point, or camera, changes. The actual terrain mesh is created by the LODGeomap class.
 * That uses a geo-mipmapping algorithm to change the index buffer of the mesh.
 * The mesh is a triangle strip. In wireframe mode you might notice some strange lines, these are degenerate
 * triangles generated by the geoMipMap algorithm and can be ignored. The video card removes them at almost no cost.
 * 
 * Each patch needs to know its neighbour's LOD so it can seam its edges with them, in case the neighbour has a different
 * LOD. If this doesn't happen, you will see gaps.
 * 
 * The LOD value is most detailed at zero. It gets less detailed the higher the LOD value until you reach maxLod, which
 * is a mathematical limit on the number of times the 'size' of the patch can be divided by two. However there is a -1 to that
 * for now until I add in a custom index buffer calculation for that max level, the current algorithm does not go that far.
 * 
 * You can supply a LodThresholdCalculator for use in determining when the LOD should change. It's API will no doubt change 
 * in the near future. Right now it defaults to just changing LOD every two patch sizes. So if a patch has a size of 65, 
 * then the LOD changes every 130 units away.
 * 
 * @author Brent Owens
 */
public class TerrainPatch extends Geometry {

    protected LODGeomap geomap;
    protected int lod = 0; // this terrain patch's LOD
    private int maxLod = -1;
    protected int previousLod = -1;
    protected int lodLeft, lodTop, lodRight, lodBottom; // it's neighbour's LODs

    protected int size;

    protected int totalSize;

    protected short quadrant = 1;

    // x/z step
    protected Vector3f stepScale;

    // center of the patch in relation to (0,0,0)
    protected Vector2f offset;

    // amount the patch has been shifted.
    protected float offsetAmount;

    //protected LodCalculator lodCalculator;
    //protected LodCalculatorFactory lodCalculatorFactory;

    protected TerrainPatch leftNeighbour, topNeighbour, rightNeighbour, bottomNeighbour;
    protected boolean searchedForNeighboursAlready = false;

    // these two vectors are calculated on the GL thread, but used in the outside LOD thread
    protected Vector3f worldTranslationCached;
    protected Vector3f worldScaleCached;

    protected float[] lodEntropy;

    public TerrainPatch() {
        super("TerrainPatch");
    }
    
    public TerrainPatch(String name) {
        super(name);
    }

    public TerrainPatch(String name, int size) {
        this(name, size, new Vector3f(1,1,1), null, new Vector3f(0,0,0));
    }

    /**
     * Constructor instantiates a new <code>TerrainPatch</code> object. The
     * parameters and heightmap data are then processed to generate a
     * <code>TriMesh</code> object for rendering.
     *
     * @param name
     *			the name of the terrain patch.
     * @param size
     *			the size of the heightmap.
     * @param stepScale
     *			the scale for the axes.
     * @param heightMap
     *			the height data.
     * @param origin
     *			the origin offset of the patch.
     */
    public TerrainPatch(String name, int size, Vector3f stepScale,
                    float[] heightMap, Vector3f origin) {
        this(name, size, stepScale, heightMap, origin, size, new Vector2f(), 0);
    }

    /**
     * Constructor instantiates a new <code>TerrainPatch</code> object. The
     * parameters and heightmap data are then processed to generate a
     * <code>TriMesh</code> object for renderering.
     *
     * @param name
     *			the name of the terrain patch.
     * @param size
     *			the size of the patch.
     * @param stepScale
     *			the scale for the axes.
     * @param heightMap
     *			the height data.
     * @param origin
     *			the origin offset of the patch.
     * @param totalSize
     *			the total size of the terrain. (Higher if the patch is part of
     *			a <code>TerrainQuad</code> tree.
     * @param offset
     *			the offset for texture coordinates.
     * @param offsetAmount
     *			the total offset amount. Used for texture coordinates.
     */
    public TerrainPatch(String name, int size, Vector3f stepScale,
                    float[] heightMap, Vector3f origin, int totalSize,
                    Vector2f offset, float offsetAmount) {
        super(name);
        this.size = size;
        this.stepScale = stepScale;
        this.totalSize = totalSize;
        this.offsetAmount = offsetAmount;
        this.offset = offset;

        setLocalTranslation(origin);

        geomap = new LODGeomap(size, heightMap);
        Mesh m = geomap.createMesh(stepScale, new Vector2f(1,1), offset, offsetAmount, totalSize, false);
        setMesh(m);

    }

    /**
     * This calculation is slow, so don't use it often.
     */
    public void generateLodEntropies() {
        float[] entropies = new float[getMaxLod()+1];
        for (int i = 0; i <= getMaxLod(); i++){
            int curLod = (int) Math.pow(2, i);
            IntBuffer buf = geomap.writeIndexArrayLodDiff(null, curLod, false, false, false, false);
            entropies[i] = EntropyComputeUtil.computeLodEntropy(mesh, buf);
        }

        lodEntropy = entropies;
    }

    public float[] getLodEntropies(){
        if (lodEntropy == null){
            generateLodEntropies();
        }
        return lodEntropy;
    }

    @Deprecated
    public FloatBuffer getHeightmap() {
        return BufferUtils.createFloatBuffer(geomap.getHeightArray());
    }
    
    public float[] getHeightMap() {
        return geomap.getHeightArray();
    }

    /**
     * The maximum lod supported by this terrain patch.
     * If the patch size is 32 then the returned value would be log2(32)-2 = 3
     * You can then use that value, 3, to see how many times you can divide 32 by 2
     * before the terrain gets too un-detailed (can't stitch it any further).
     * @return the maximum LOD
     */
    public int getMaxLod() {
        if (maxLod < 0)
            maxLod = Math.max(1, (int) (FastMath.log(size-1)/FastMath.log(2)) -1); // -1 forces our minimum of 4 triangles wide

        return maxLod;
    }

    protected void reIndexGeometry(HashMap<String,UpdatedTerrainPatch> updated, boolean useVariableLod) {

        UpdatedTerrainPatch utp = updated.get(getName());

        if (utp != null && utp.isReIndexNeeded() ) {
            int pow = (int) Math.pow(2, utp.getNewLod());
            boolean left = utp.getLeftLod() > utp.getNewLod();
            boolean top = utp.getTopLod() > utp.getNewLod();
            boolean right = utp.getRightLod() > utp.getNewLod();
            boolean bottom = utp.getBottomLod() > utp.getNewLod();

            IntBuffer ib = null;
            if (useVariableLod)
                ib = geomap.writeIndexArrayLodVariable(null, pow, (int) Math.pow(2, utp.getRightLod()), (int) Math.pow(2, utp.getTopLod()), (int) Math.pow(2, utp.getLeftLod()), (int) Math.pow(2, utp.getBottomLod()));
            else
                ib = geomap.writeIndexArrayLodDiff(null, pow, right, top, left, bottom);
            utp.setNewIndexBuffer(ib);
        }

    }


    public Vector2f getTex(float x, float z, Vector2f store) {
        if (x < 0 || z < 0 || x >= size || z >= size) {
            store.set(Vector2f.ZERO);
            return store;
        }
        int idx = (int) (z * size + x);
        return store.set(getMesh().getFloatBuffer(Type.TexCoord).get(idx*2),
                         getMesh().getFloatBuffer(Type.TexCoord).get(idx*2+1) );
    }
    
    public float getHeightmapHeight(float x, float z) {
        if (x < 0 || z < 0 || x >= size || z >= size)
            return 0;
        int idx = (int) (z * size + x);
        return getMesh().getFloatBuffer(Type.Position).get(idx*3+1); // 3 floats per entry (x,y,z), the +1 is to get the Y
    }
    
    /**
     * Get the triangle of this geometry at the specified local coordinate.
     * @param x local to the terrain patch
     * @param z local to the terrain patch
     * @return the triangle in world coordinates, or null if the point does intersect this patch on the XZ axis
     */
    public Triangle getTriangle(float x, float z) {
        return geomap.getTriangleAtPoint(x, z, getWorldScale() , getWorldTranslation());
    }

    /**
     * Get the triangles at the specified grid point. Probably only 2 triangles
     * @param x local to the terrain patch
     * @param z local to the terrain patch
     * @return the triangles in world coordinates, or null if the point does intersect this patch on the XZ axis
     */
    public Triangle[] getGridTriangles(float x, float z) {
        return geomap.getGridTrianglesAtPoint(x, z, getWorldScale() , getWorldTranslation());
    }

    protected void setHeight(List<LocationHeight> locationHeights, boolean overrideHeight) {
        
        for (LocationHeight lh : locationHeights) {
            if (lh.x < 0 || lh.z < 0 || lh.x >= size || lh.z >= size)
                continue;
            int idx = lh.z * size + lh.x;
            if (overrideHeight) {
                geomap.getHeightArray()[idx] = lh.h;
            } else {
                float h = getMesh().getFloatBuffer(Type.Position).get(idx*3+1);
                geomap.getHeightArray()[idx] = h+lh.h;
            }
            
        }

        FloatBuffer newVertexBuffer = geomap.writeVertexArray(null, stepScale, false);
        getMesh().clearBuffer(Type.Position);
        getMesh().setBuffer(Type.Position, 3, newVertexBuffer);
    }

    /**
     * recalculate all of the normal vectors in this terrain patch
     */
    protected void updateNormals() {
        FloatBuffer newNormalBuffer = geomap.writeNormalArray(null, getWorldScale());
        getMesh().getBuffer(Type.Normal).updateData(newNormalBuffer);
        FloatBuffer newTangentBuffer = null;
        FloatBuffer newBinormalBuffer = null;
        FloatBuffer[] tb = geomap.writeTangentArray(newNormalBuffer, newTangentBuffer, newBinormalBuffer, (FloatBuffer)getMesh().getBuffer(Type.TexCoord).getData(), getWorldScale());
        newTangentBuffer = tb[0];
        newBinormalBuffer = tb[1];
        getMesh().getBuffer(Type.Tangent).updateData(newTangentBuffer);
        getMesh().getBuffer(Type.Binormal).updateData(newBinormalBuffer);
    }

    private void setInBuffer(Mesh mesh, int index, Vector3f normal, Vector3f tangent, Vector3f binormal) {
        VertexBuffer NB = mesh.getBuffer(Type.Normal);
        VertexBuffer TB = mesh.getBuffer(Type.Tangent);
        VertexBuffer BB = mesh.getBuffer(Type.Binormal);
        BufferUtils.setInBuffer(normal, (FloatBuffer)NB.getData(), index);
        BufferUtils.setInBuffer(tangent, (FloatBuffer)TB.getData(), index);
        BufferUtils.setInBuffer(binormal, (FloatBuffer)BB.getData(), index);
        NB.setUpdateNeeded();
        TB.setUpdateNeeded();
        BB.setUpdateNeeded();
    }
    
    /**
     * Matches the normals along the edge of the patch with the neighbours.
     * Computes the normals for the right, bottom, left, and top edges of the
     * patch, and saves those normals in the neighbour's edges too.
     *
     * Takes 4 points (if has neighbour on that side) for each
     * point on the edge of the patch:
     *              *
     *              |
     *          *---x---*
     *              |
     *              *
     * It works across the right side of the patch, from the top down to 
     * the bottom. Then it works on the bottom side of the patch, from the
     * left to the right.
     */
    protected void fixNormalEdges(TerrainPatch right,
                                TerrainPatch bottom,
                                TerrainPatch top,
                                TerrainPatch left,
                                TerrainPatch bottomRight,
                                TerrainPatch bottomLeft,
                                TerrainPatch topRight,
                                TerrainPatch topLeft)
    {
        Vector3f rootPoint = new Vector3f();
        Vector3f rightPoint = new Vector3f();
        Vector3f leftPoint = new Vector3f();
        Vector3f topPoint = new Vector3f();

        Vector3f bottomPoint = new Vector3f();

        Vector3f tangent = new Vector3f();
        Vector3f binormal = new Vector3f();
        Vector3f normal = new Vector3f();

        
        int s = this.getSize()-1;
        
        if (right != null) { // right side,    works its way down
            for (int i=0; i<s+1; i++) {
                rootPoint.set(0, this.getHeightmapHeight(s,i), 0);
                leftPoint.set(-1, this.getHeightmapHeight(s-1,i), 0);
                rightPoint.set(1, right.getHeightmapHeight(1,i), 0);

                if (i == 0) { // top point
                    bottomPoint.set(0, this.getHeightmapHeight(s,i+1), 1);
                    
                    if (top == null) {
                        averageNormalsTangents(null, rootPoint, leftPoint, bottomPoint, rightPoint,  normal, tangent, binormal);
                        setInBuffer(this.getMesh(), s, normal, tangent, binormal);
                        setInBuffer(right.getMesh(), 0, normal, tangent, binormal);
                    } else {
                        topPoint.set(0, top.getHeightmapHeight(s,s-1), -1);
                        
                        averageNormalsTangents(topPoint, rootPoint, leftPoint, bottomPoint, rightPoint,normal, tangent, binormal);
                        setInBuffer(this.getMesh(), s, normal, tangent, binormal);
                        setInBuffer(right.getMesh(), 0, normal, tangent, binormal);
                        setInBuffer(top.getMesh(), (s+1)*(s+1)-1, normal, tangent, binormal);
                        
                        if (topRight != null) {
                    //        setInBuffer(topRight.getMesh(), (s+1)*s, normal, tangent, binormal);
                        }
                    }
                } else if (i == s) { // bottom point
                    topPoint.set(0, this.getHeightmapHeight(s,s-1), -1);
                    
                    if (bottom == null) {
                        averageNormalsTangents(topPoint, rootPoint, leftPoint, null, rightPoint, normal, tangent, binormal);
                        setInBuffer(this.getMesh(), (s+1)*(s+1)-1, normal, tangent, binormal);
                        setInBuffer(right.getMesh(), (s+1)*(s), normal, tangent, binormal);
                    } else {
                        bottomPoint.set(0, bottom.getHeightmapHeight(s,1), 1);
                        averageNormalsTangents(topPoint, rootPoint, leftPoint, bottomPoint, rightPoint, normal, tangent, binormal);
                        setInBuffer(this.getMesh(), (s+1)*(s+1)-1, normal, tangent, binormal);
                        setInBuffer(right.getMesh(), (s+1)*s, normal, tangent, binormal);
                        setInBuffer(bottom.getMesh(), s, normal, tangent, binormal);
                        
                        if (bottomRight != null) {
                   //         setInBuffer(bottomRight.getMesh(), 0, normal, tangent, binormal);
                        }
                    }
                } else { // all in the middle
                    topPoint.set(0, this.getHeightmapHeight(s,i-1), -1);
                    bottomPoint.set(0, this.getHeightmapHeight(s,i+1), 1);
                    averageNormalsTangents(topPoint, rootPoint, leftPoint, bottomPoint, rightPoint, normal, tangent, binormal);
                    setInBuffer(this.getMesh(), (s+1)*(i+1)-1, normal, tangent, binormal);
                    setInBuffer(right.getMesh(), (s+1)*(i), normal, tangent, binormal);
                }
            }
        }

        if (left != null) { // left side,    works its way down
            for (int i=0; i<s+1; i++) {
                rootPoint.set(0, this.getHeightmapHeight(0,i), 0);
                leftPoint.set(-1, left.getHeightmapHeight(s-1,i), 0);
                rightPoint.set(1, this.getHeightmapHeight(1,i), 0);
                
                if (i == 0) { // top point
                    bottomPoint.set(0, this.getHeightmapHeight(0,i+1), 1);
                    
                    if (top == null) {
                        averageNormalsTangents(null, rootPoint, leftPoint, bottomPoint, rightPoint, normal, tangent, binormal);
                        setInBuffer(this.getMesh(), 0, normal, tangent, binormal);
                        setInBuffer(left.getMesh(), s, normal, tangent, binormal);
                    } else {
                        topPoint.set(0, top.getHeightmapHeight(0,s-1), -1);
                        
                        averageNormalsTangents(topPoint, rootPoint, leftPoint, bottomPoint, rightPoint, normal, tangent, binormal);
                        setInBuffer(this.getMesh(), 0, normal, tangent, binormal);
                        setInBuffer(left.getMesh(), s, normal, tangent, binormal);
                        setInBuffer(top.getMesh(), (s+1)*s, normal, tangent, binormal);
                        
                        if (topLeft != null) {
                     //       setInBuffer(topLeft.getMesh(), (s+1)*(s+1)-1, normal, tangent, binormal);
                        }
                    }
                } else if (i == s) { // bottom point
                    topPoint.set(0, this.getHeightmapHeight(0,i-1), -1);
                    
                    if (bottom == null) {
                        averageNormalsTangents(topPoint, rootPoint, leftPoint, null, rightPoint, normal, tangent, binormal);
                        setInBuffer(this.getMesh(), (s+1)*(s), normal, tangent, binormal);
                        setInBuffer(left.getMesh(), (s+1)*(s+1)-1, normal, tangent, binormal);
                    } else {
                        bottomPoint.set(0, bottom.getHeightmapHeight(0,1), 1);
                        
                        averageNormalsTangents(topPoint, rootPoint, leftPoint, bottomPoint, rightPoint, normal, tangent, binormal);
                        setInBuffer(this.getMesh(), (s+1)*(s), normal, tangent, binormal);
                        setInBuffer(left.getMesh(), (s+1)*(s+1)-1, normal, tangent, binormal);
                        setInBuffer(bottom.getMesh(), 0, normal, tangent, binormal);
                        
                        if (bottomLeft != null) {
                     //       setInBuffer(bottomLeft.getMesh(), s, normal, tangent, binormal);
                        }
                    }
                } else { // all in the middle
                    topPoint.set(0, this.getHeightmapHeight(0,i-1), -1);
                    bottomPoint.set(0, this.getHeightmapHeight(0,i+1), 1);
                    
                    averageNormalsTangents(topPoint, rootPoint, leftPoint, bottomPoint, rightPoint, normal, tangent, binormal);
                    setInBuffer(this.getMesh(), (s+1)*(i), normal, tangent, binormal);
                    setInBuffer(left.getMesh(), (s+1)*(i+1)-1, normal, tangent, binormal);
                }
            }
        }

        if (top != null) { // top side,    works its way right
            for (int i=0; i<s+1; i++) {
                rootPoint.set(0, this.getHeightmapHeight(i,0), 0);
                topPoint.set(0, top.getHeightmapHeight(i,s-1), -1);
                bottomPoint.set(0, this.getHeightmapHeight(i,1), 1);
                
                if (i == 0) { // left corner
                    // handled by left side pass
                    
                } else if (i == s) { // right corner
                    
                    // handled by this patch when it does its right side
                    
                } else { // all in the middle
                    leftPoint.set(-1, this.getHeightmapHeight(i-1,0), 0);
                    rightPoint.set(1, this.getHeightmapHeight(i+1,0), 0);
                    averageNormalsTangents(topPoint, rootPoint, leftPoint, bottomPoint, rightPoint, normal, tangent, binormal);
                    setInBuffer(this.getMesh(), i, normal, tangent, binormal);
                    setInBuffer(top.getMesh(), (s+1)*(s)+i, normal, tangent, binormal);
                }
            }
            
        }
        
        if (bottom != null) { // bottom side,    works its way right
            for (int i=0; i<s+1; i++) {
                rootPoint.set(0, this.getHeightmapHeight(i,s), 0);
                topPoint.set(0, this.getHeightmapHeight(i,s-1), -1);
                bottomPoint.set(0, bottom.getHeightmapHeight(i,1), 1);

                if (i == 0) { // left
                    // handled by the left side pass
                    
                } else if (i == s) { // right
                    
                    // handled by the right side pass
                    
                } else { // all in the middle
                    leftPoint.set(-1, this.getHeightmapHeight(i-1,s), 0);
                    rightPoint.set(1, this.getHeightmapHeight(i+1,s), 0);
                    averageNormalsTangents(topPoint, rootPoint, leftPoint, bottomPoint, rightPoint, normal, tangent, binormal);
                    setInBuffer(this.getMesh(), (s+1)*(s)+i, normal, tangent, binormal);
                    setInBuffer(bottom.getMesh(), i, normal, tangent, binormal);
                }
            }
            
        }
    }

    protected void averageNormalsTangents(
            Vector3f topPoint,
            Vector3f rootPoint,
            Vector3f leftPoint, 
            Vector3f bottomPoint, 
            Vector3f rightPoint,
            Vector3f normal,
            Vector3f tangent,
            Vector3f binormal)
    {
        Vector3f scale = getWorldScale();
        
        Vector3f n1 = new Vector3f(0,0,0);
        if (topPoint != null && leftPoint != null) {
            n1.set(calculateNormal(topPoint.mult(scale), rootPoint.mult(scale), leftPoint.mult(scale)));
        }
        Vector3f n2 = new Vector3f(0,0,0);
        if (leftPoint != null && bottomPoint != null) {
            n2.set(calculateNormal(leftPoint.mult(scale), rootPoint.mult(scale), bottomPoint.mult(scale)));
        }
        Vector3f n3 = new Vector3f(0,0,0);
        if (rightPoint != null && bottomPoint != null) {
            n3.set(calculateNormal(bottomPoint.mult(scale), rootPoint.mult(scale), rightPoint.mult(scale)));
        }
        Vector3f n4 = new Vector3f(0,0,0);
        if (rightPoint != null && topPoint != null) {
            n4.set(calculateNormal(rightPoint.mult(scale), rootPoint.mult(scale), topPoint.mult(scale)));
        }
        
        //if (bottomPoint != null && rightPoint != null && rootTex != null && rightTex != null && bottomTex != null)
        //    LODGeomap.calculateTangent(new Vector3f[]{rootPoint.mult(scale),rightPoint.mult(scale),bottomPoint.mult(scale)}, new Vector2f[]{rootTex,rightTex,bottomTex}, tangent, binormal);

        normal.set(n1.add(n2).add(n3).add(n4).normalize());
        
        tangent.set(normal.cross(new Vector3f(0,0,1)).normalize());
        binormal.set(new Vector3f(1,0,0).cross(normal).normalize());
    }

    private Vector3f calculateNormal(Vector3f firstPoint, Vector3f rootPoint, Vector3f secondPoint) {
        Vector3f normal = new Vector3f();
        normal.set(firstPoint).subtractLocal(rootPoint)
                  .crossLocal(secondPoint.subtract(rootPoint)).normalizeLocal();
        return normal;
    }
    
    protected Vector3f getMeshNormal(int x, int z) {
        if (x >= size || z >= size)
            return null; // out of range
        
        int index = (z*size+x)*3;
        FloatBuffer nb = (FloatBuffer)this.getMesh().getBuffer(Type.Normal).getData();
        Vector3f normal = new Vector3f();
        normal.x = nb.get(index);
        normal.y = nb.get(index+1);
        normal.z = nb.get(index+2);
        return normal;
    }

    protected float getHeight(int x, int z, float xm, float zm) {
        return geomap.getHeight(x,z,xm,zm);
    }
    
    /**
     * Locks the mesh (sets it static) to improve performance.
     * But it it not editable then. Set unlock to make it editable.
     */
    public void lockMesh() {
        getMesh().setStatic();
    }

    /**
     * Unlocks the mesh (sets it dynamic) to make it editable.
     * It will be editable but performance will be reduced.
     * Call lockMesh to improve performance.
     */
    public void unlockMesh() {
        getMesh().setDynamic();
    }
	
    /**
     * Returns the offset amount this terrain patch uses for textures.
     *
     * @return The current offset amount.
     */
    public float getOffsetAmount() {
        return offsetAmount;
    }

    /**
     * Returns the step scale that stretches the height map.
     *
     * @return The current step scale.
     */
    public Vector3f getStepScale() {
        return stepScale;
    }

    /**
     * Returns the total size of the terrain.
     *
     * @return The terrain's total size.
     */
    public int getTotalSize() {
        return totalSize;
    }

    /**
     * Returns the size of this terrain patch.
     *
     * @return The current patch size.
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns the current offset amount. This is used when building texture
     * coordinates.
     *
     * @return The current offset amount.
     */
    public Vector2f getOffset() {
        return offset;
    }

    /**
     * Sets the value for the current offset amount to use when building texture
     * coordinates. Note that this does <b>NOT </b> rebuild the terrain at all.
     * This is mostly used for outside constructors of terrain patches.
     *
     * @param offset
     *			The new texture offset.
     */
    public void setOffset(Vector2f offset) {
        this.offset = offset;
    }

    /**
     * Sets the size of this terrain patch. Note that this does <b>NOT </b>
     * rebuild the terrain at all. This is mostly used for outside constructors
     * of terrain patches.
     *
     * @param size
     *			The new size.
     */
    public void setSize(int size) {
        this.size = size;

        maxLod = -1; // reset it
    }

    /**
     * Sets the total size of the terrain . Note that this does <b>NOT </b>
     * rebuild the terrain at all. This is mostly used for outside constructors
     * of terrain patches.
     *
     * @param totalSize
     *			The new total size.
     */
    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    /**
     * Sets the step scale of this terrain patch's height map. Note that this
     * does <b>NOT </b> rebuild the terrain at all. This is mostly used for
     * outside constructors of terrain patches.
     *
     * @param stepScale
     *			The new step scale.
     */
    public void setStepScale(Vector3f stepScale) {
        this.stepScale = stepScale;
    }

    /**
     * Sets the offset of this terrain texture map. Note that this does <b>NOT
     * </b> rebuild the terrain at all. This is mostly used for outside
     * constructors of terrain patches.
     *
     * @param offsetAmount
     *			The new texture offset.
     */
    public void setOffsetAmount(float offsetAmount) {
        this.offsetAmount = offsetAmount;
    }

    /**
     * @return Returns the quadrant.
     */
    public short getQuadrant() {
        return quadrant;
    }

    /**
     * @param quadrant
     *			The quadrant to set.
     */
    public void setQuadrant(short quadrant) {
        this.quadrant = quadrant;
    }

    public int getLod() {
        return lod;
    }

    public void setLod(int lod) {
        this.lod = lod;
    }

    public int getPreviousLod() {
        return previousLod;
    }

    public void setPreviousLod(int previousLod) {
        this.previousLod = previousLod;
    }

    protected int getLodLeft() {
        return lodLeft;
    }

    protected void setLodLeft(int lodLeft) {
        this.lodLeft = lodLeft;
    }

    protected int getLodTop() {
        return lodTop;
    }

    protected void setLodTop(int lodTop) {
        this.lodTop = lodTop;
    }

    protected int getLodRight() {
        return lodRight;
    }

    protected void setLodRight(int lodRight) {
        this.lodRight = lodRight;
    }

    protected int getLodBottom() {
        return lodBottom;
    }

    protected void setLodBottom(int lodBottom) {
        this.lodBottom = lodBottom;
    }
    
    /*public void setLodCalculator(LodCalculatorFactory lodCalculatorFactory) {
        this.lodCalculatorFactory = lodCalculatorFactory;
        setLodCalculator(lodCalculatorFactory.createCalculator(this));
    }*/

    @Override
    public int collideWith(Collidable other, CollisionResults results) throws UnsupportedCollisionException {
        if (refreshFlags != 0)
            throw new IllegalStateException("Scene graph must be updated" +
                                            " before checking collision");

        if (other instanceof BoundingVolume)
            if (!getWorldBound().intersects((BoundingVolume)other))
                return 0;
        
        if(other instanceof Ray)
            return collideWithRay((Ray)other, results);
        else if (other instanceof BoundingVolume)
            return collideWithBoundingVolume((BoundingVolume)other, results);
        else {
            throw new UnsupportedCollisionException("TerrainPatch cannnot collide with "+other.getClass().getName());
        }
    }


    private int collideWithRay(Ray ray, CollisionResults results) {
        // This should be handled in the root terrain quad
        return 0;
    }

    private int collideWithBoundingVolume(BoundingVolume boundingVolume, CollisionResults results) {
        if (boundingVolume instanceof BoundingBox)
            return collideWithBoundingBox((BoundingBox)boundingVolume, results);
        else if(boundingVolume instanceof BoundingSphere) {
            BoundingSphere sphere = (BoundingSphere) boundingVolume;
            BoundingBox bbox = new BoundingBox(boundingVolume.getCenter().clone(), sphere.getRadius(),
                                                           sphere.getRadius(),
                                                           sphere.getRadius());
            return collideWithBoundingBox(bbox, results);
        }
        return 0;
    }

    protected Vector3f worldCoordinateToLocal(Vector3f loc) {
        Vector3f translated = new Vector3f();
        translated.x = loc.x/getWorldScale().x - getWorldTranslation().x;
        translated.y = loc.y/getWorldScale().y - getWorldTranslation().y;
        translated.z = loc.z/getWorldScale().z - getWorldTranslation().z;
        return translated;
    }

    /**
     * This most definitely is not optimized.
     */
    private int collideWithBoundingBox(BoundingBox bbox, CollisionResults results) {
        
        // test the four corners, for cases where the bbox dimensions are less than the terrain grid size, which is probably most of the time
        Vector3f topLeft = worldCoordinateToLocal(new Vector3f(bbox.getCenter().x-bbox.getXExtent(), 0, bbox.getCenter().z-bbox.getZExtent()));
        Vector3f topRight = worldCoordinateToLocal(new Vector3f(bbox.getCenter().x+bbox.getXExtent(), 0, bbox.getCenter().z-bbox.getZExtent()));
        Vector3f bottomLeft = worldCoordinateToLocal(new Vector3f(bbox.getCenter().x-bbox.getXExtent(), 0, bbox.getCenter().z+bbox.getZExtent()));
        Vector3f bottomRight = worldCoordinateToLocal(new Vector3f(bbox.getCenter().x+bbox.getXExtent(), 0, bbox.getCenter().z+bbox.getZExtent()));

        Triangle t = getTriangle(topLeft.x, topLeft.z);
        if (t != null && bbox.collideWith(t, results) > 0)
            return 1;
        t = getTriangle(topRight.x, topRight.z);
        if (t != null && bbox.collideWith(t, results) > 0)
            return 1;
        t = getTriangle(bottomLeft.x, bottomLeft.z);
        if (t != null && bbox.collideWith(t, results) > 0)
            return 1;
        t = getTriangle(bottomRight.x, bottomRight.z);
        if (t != null && bbox.collideWith(t, results) > 0)
            return 1;
        
        // box is larger than the points on the terrain, so test against the points
        for (float z=topLeft.z; z<bottomLeft.z; z+=1) {
            for (float x=topLeft.x; x<topRight.x; x+=1) {
                
                if (x < 0 || z < 0 || x >= size || z >= size)
                    continue;
                t = getTriangle(x,z);
                if (t != null && bbox.collideWith(t, results) > 0)
                    return 1;
            }
        }

        return 0;
    }


    @Override
    public void write(JmeExporter ex) throws IOException {
        // the mesh is removed, and reloaded when read() is called
        // this reduces the save size to 10% by not saving the mesh
        Mesh temp = getMesh();
        mesh = null;
        
        super.write(ex);
        OutputCapsule oc = ex.getCapsule(this);
        oc.write(size, "size", 16);
        oc.write(totalSize, "totalSize", 16);
        oc.write(quadrant, "quadrant", (short)0);
        oc.write(stepScale, "stepScale", Vector3f.UNIT_XYZ);
        oc.write(offset, "offset", Vector3f.UNIT_XYZ);
        oc.write(offsetAmount, "offsetAmount", 0);
        //oc.write(lodCalculator, "lodCalculator", null);
        //oc.write(lodCalculatorFactory, "lodCalculatorFactory", null);
        oc.write(lodEntropy, "lodEntropy", null);
        oc.write(geomap, "geomap", null);
        
        setMesh(temp);
    }

    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        InputCapsule ic = im.getCapsule(this);
        size = ic.readInt("size", 16);
        totalSize = ic.readInt("totalSize", 16);
        quadrant = ic.readShort("quadrant", (short)0);
        stepScale = (Vector3f) ic.readSavable("stepScale", Vector3f.UNIT_XYZ);
        offset = (Vector2f) ic.readSavable("offset", Vector3f.UNIT_XYZ);
        offsetAmount = ic.readFloat("offsetAmount", 0);
        //lodCalculator = (LodCalculator) ic.readSavable("lodCalculator", new DistanceLodCalculator());
        //lodCalculator.setTerrainPatch(this);
        //lodCalculatorFactory = (LodCalculatorFactory) ic.readSavable("lodCalculatorFactory", null);
        lodEntropy = ic.readFloatArray("lodEntropy", null);
        geomap = (LODGeomap) ic.readSavable("geomap", null);
        
        Mesh regen = geomap.createMesh(stepScale, new Vector2f(1,1), offset, offsetAmount, totalSize, false);
        setMesh(regen);
        //TangentBinormalGenerator.generate(this); // note that this will be removed
        ensurePositiveVolumeBBox();
    }

    @Override
    public TerrainPatch clone() {
        TerrainPatch clone = new TerrainPatch();
        clone.name = name.toString();
        clone.size = size;
        clone.totalSize = totalSize;
        clone.quadrant = quadrant;
        clone.stepScale = stepScale.clone();
        clone.offset = offset.clone();
        clone.offsetAmount = offsetAmount;
        //clone.lodCalculator = lodCalculator.clone();
        //clone.lodCalculator.setTerrainPatch(clone);
        //clone.setLodCalculator(lodCalculatorFactory.clone());
        clone.geomap = new LODGeomap(size, geomap.getHeightArray());
        clone.setLocalTranslation(getLocalTranslation().clone());
        Mesh m = clone.geomap.createMesh(clone.stepScale, Vector2f.UNIT_XY, clone.offset, clone.offsetAmount, clone.totalSize, false);
        clone.setMesh(m);
        clone.setMaterial(material.clone());
        return clone;
    }

    protected void ensurePositiveVolumeBBox() {
        if (getModelBound() instanceof BoundingBox) {
            if (((BoundingBox)getModelBound()).getYExtent() < 0.001f) {
                // a correction so the box always has a volume
                ((BoundingBox)getModelBound()).setYExtent(0.001f);
                updateWorldBound();
            }
        }
    }

    /**
     * Caches the transforms (except rotation) so the LOD calculator,
     * which runs on a separate thread, can access them safely.
     */
    protected void cacheTerrainTransforms() {
        this.worldScaleCached = getWorldScale().clone();
        this.worldTranslationCached = getWorldTranslation().clone();
    }

    public Vector3f getWorldScaleCached() {
        return worldScaleCached;
    }

    public Vector3f getWorldTranslationCached() {
        return worldTranslationCached;
    }


}
