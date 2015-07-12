package com.footbits.sava.oglspiritleveldisplay;

import static android.opengl.GLES20.glViewport;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.concubicycle.util.TextResourceReader;
import com.footbits.oglwrapper.Camera;
import com.footbits.oglwrapper.FragmentShader;
import com.footbits.oglwrapper.GlslProgram;
import com.footbits.oglwrapper.VertexShader;
import com.texample2.GLText;
import com.texample2.RenderedString;


import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class AngleGLRenderer implements GLSurfaceView.Renderer {
	private Context context;
	private ArrayList<RenderedString> strings;
	private GLText glText;
	private Camera camera;

	private GlslProgram simpleGlslProgram;

    private float[] projectionViewMatrix;


	public AngleGLRenderer(Context context) {
		this.context = context;
		this.strings = new ArrayList<>();
		this.camera = new Camera(context, Camera.Projection.Orthographic);
        this.projectionViewMatrix = new float[16];

		// camera is looking down negative z axis
		camera.lookAt(0, 0, 3, //position
				0, 0, 0, //look at point
				0, 1, 0); //up
	}

	public void onSurfaceCreated(GL10 unused, EGLConfig config) {
		// Set the background frame color
		GLES20.glClearColor(0.2f, 0.0f, 0.3f, 1.0f);

		// enable texture + alpha blending
		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA);

		simpleGlslProgram = new GlslProgram(
				TextResourceReader.readTextFileFromResource(context, R.raw.simple_vert),
				TextResourceReader.readTextFileFromResource(context, R.raw.simple_frag));

		simpleGlslProgram.compile();


		initGLText();
	}

	public void onDrawFrame(GL10 unused) {
		// Redraw background color
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        renderStrings();
	}

	public void onSurfaceChanged(GL10 unused, int width, int height) {
		// Set the OpenGL viewport to fill the entire surface.
		glViewport(0, 0, width, height);
		camera.setScreenDimensions(width, height);
	}

	public ArrayList<RenderedString> getStrings() {
		return strings;
	}


	private void initGLText() {
		glText = new GLText(context.getAssets());

		// Load the font from file (set size + padding), creates the texture
		// NOTE: after a successful call to this the font is ready for rendering!
		glText.load("Roboto-Regular.ttf", 40, 1, 1);  // Create Font (Height: 14 Pixels / X+Y Padding 2 Pixels)
	}

    private void renderStrings() {
        camera.getProjectionViewnMatrix(projectionViewMatrix);
        glText.begin(projectionViewMatrix);
        for (RenderedString str : strings) {
            glText.draw(str.getText(), str.getX(), str.getY());
        }
        glText.end();
    }
}