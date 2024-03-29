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

package com.jme3.system.jogl;

import com.jme3.system.JmeCanvasContext;
import com.jogamp.newt.awt.NewtCanvasAWT;
import java.util.logging.Logger;
import javax.media.opengl.GLAutoDrawable;

public class JoglNewtCanvas extends JoglNewtAbstractDisplay implements JmeCanvasContext {
    
    private static final Logger logger = Logger.getLogger(JoglNewtCanvas.class.getName());
    private int width, height;
    
    private NewtCanvasAWT newtAwtCanvas;

    public JoglNewtCanvas(){
        super();
        initGLCanvas();
    }
    
    @Override
    protected final void initGLCanvas() {
        super.initGLCanvas();
        newtAwtCanvas = new NewtCanvasAWT(canvas) {
            @Override
            public void addNotify() {
                super.addNotify();
                onCanvasAdded();
            }

            @Override
            public void removeNotify() {
                onCanvasRemoved();
                super.removeNotify();
            }
        };
    }

    public Type getType() {
        return Type.Canvas;
    }

    public void setTitle(String title) {
    }

    public void restart() {
    }

    public void create(boolean waitFor){
        if (waitFor)
            waitFor(true);
    }

    public void destroy(boolean waitFor){
        if (waitFor)
            waitFor(false);
    }

    @Override
    protected void onCanvasRemoved(){
        super.onCanvasRemoved();
        created.set(false);
        waitFor(false);
    }

    @Override
    protected void onCanvasAdded(){
        startGLCanvas();
    }

    public void init(GLAutoDrawable drawable) {
        canvas.requestFocus();

        super.internalCreate();
        logger.info("Display created.");

        renderer.initialize();
        listener.initialize();
    }

    public void display(GLAutoDrawable glad) {
        if (!created.get() && renderer != null){
            listener.destroy();
            logger.info("Canvas destroyed.");
            super.internalDestroy();
            return;
        }

        int newWidth = Math.max(canvas.getWidth(), 1);
        int newHeight = Math.max(canvas.getHeight(), 1);
        if (width != newWidth || height != newHeight) {
            width = newWidth;
            height = newHeight;
            if (listener != null) {
                listener.reshape(width, height);
            }
        }

        boolean flush = autoFlush.get();
        if (flush && !wasAnimating){
            animator.start();
            wasAnimating = true;
        }else if (!flush && wasAnimating){
            animator.stop();
            wasAnimating = false;
        }
            
        listener.update();
        renderer.onFrame();

    }

    @Override
    public NewtCanvasAWT getCanvas() {
        return newtAwtCanvas;
    }

    @Override
    public void dispose(GLAutoDrawable arg0) {       
    }

}
