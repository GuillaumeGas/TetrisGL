package fr.univ_orleans.info.tetrisgl;

/**
 * Created by Guillaume on 19/04/2016.
 */

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

public class MyGLRenderer implements GLSurfaceView.Renderer {

    private static final String TAG = "MyGLRenderer";
    private Game mGame;
    private MyGLSurfaceView mView;
    private Square square;

    // Les habituelles matrices Model/View/Projection
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private final float[] mModelMatrix = new float[16];

    public MyGLRenderer(Game g, MyGLSurfaceView v) {
        mGame = g;
        mView = v;
    }

    /* Première méthode équivalente à la fonction init en OpenGLSL */
    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {

        // la couleur du fond d'écran
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        mGame.init(mView.getWidth(), mView.getHeight());
        square = new Square(mGame.getScreen_width(), mGame.getScreen_height());
        square.init();
    }

    /* Deuxième méthode équivalente à la fonction Display */
    @Override
    public void onDrawFrame(GL10 unused) {
        float[] scratch = new float[16]; // pour stocker une matrice

        // glClear rien de nouveau on vide le buffer de couleur et de profondeur */
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        Matrix.setIdentityM(mViewMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        int[][] grid = mGame.getGrid();
        for(int i = 0; i < 20; i++) {
            for(int j = 0; j < 10; j++) {
                if(grid[i][j] != 0) {
                    square.setPosition(j, i);

                    Matrix.setIdentityM(mModelMatrix,0);
                    Matrix.translateM(mModelMatrix, 0, square.getPosGlX(), square.getPosGlY(), 0);
                    Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mModelMatrix, 0);

                    int color = (grid[i][j] < 0 ? 0 : grid[i][j]);
                    square.draw(scratch, color);
                }
            }
        }
    }

    /* équivalent au Reshape en OpenGLSL */
    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        /* ici on aurait pu se passer de cette méthode et déclarer
        la projection qu'à la création de la surface !!
         */
        GLES20.glViewport(0, 0, width, height);
        Matrix.orthoM(mProjectionMatrix, 0, 1.0f, -1.0f, -1.0f, 1.0f, -1.0f, 1.0f);
    }




    /* ### METHODES STATIQUES ### */

    /* La gestion des shaders ... */
    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    /* pour gérer les erreurs ...*/
    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }
}
