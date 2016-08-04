package com.woofer.ui.OpenGl;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by YOURFATHERME on 2016/8/4.
 */
public class GLView  extends GLSurfaceView {
    private final GLRenderer renderer;

    public GLView(Context context) {
        super(context);

        // Uncomment this to turn on error-checking and logging
        //setDebugFlags(DEBUG_CHECK_GL_ERROR | DEBUG_LOG_GL_CALLS);

        renderer = new GLRenderer(context);
        setRenderer(renderer);
    }
}

