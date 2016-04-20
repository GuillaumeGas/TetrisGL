package fr.univ_orleans.info.tetrisgl;

/**
 * Created by Guillaume on 19/04/2016.
 */

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.opengl.GLES20;

//Dessiner un carré

public class Square {
    /* Le vertex shader avec la définition de gl_Position et les variables utiles au fragment shader
        Attention pas de variables in par défaut
        Il faut tout définir par des uniform ou de attribute !!!!
     */
    private final String vertexShaderCode =
            "uniform mat4 uMVPMatrix;" + // La matrice PxVxM transmise par le CPU
                    "attribute vec4 vPosition;" + // La position transmise par le CPU (pas de variable prédéfinie)
                    "attribute vec4 vCouleur;"+ // La couleur
                    "varying vec4 vCouleur2;"+ // Pour transmettre au fragment shader
                    "void main() {" +
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "  vCouleur2 = vCouleur;"+
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" + // pour définir la taille d'un float
                    "varying vec4 vCouleur2;"+ // on récupère la couleur interpolée
                    "void main() {" +
                    "  gl_FragColor = vCouleur2;" +
                    "}";

    /* les déclarations pour l'équivalent des VBO */

    private FloatBuffer vertexBuffer; // Pour le buffer des coordonnées des sommets du carré
    private ShortBuffer indiceBuffer; // Pour le buffer des indices
    private FloatBuffer colorBuffer; // Pour le buffer des couleurs des sommets

    /* les déclarations pour les shaders
    Identifiant du programme et pour les variables attribute ou uniform
     */
    private int IdProgram; // identifiant du programme pour lier les shaders
    private int IdPosition; // idendifiant (location) pour transmettre les coordonnées au vertex shader
    private int IdCouleur; // identifiant (location) pour transmettre les couleurs
    private int IdMVPMatrix; // identifiant (location) pour transmettre la matrice PxVxM

    static final int COORDS_PER_VERTEX = 3; // nombre de coordonnées par vertex
    static final int COULEURS_PER_VERTEX = 4; // nombre de composantes couleur par vertex

    private float posGlX = 0.0f;
    private float posGlY = 0.0f;
    private int posX = 0;
    private int posY = 0;
    private float width;
    private float height;

    /* Attention au repère au niveau écran (x est inversé)
     Le tableau des coordonnées des sommets
     Oui ce n'est pas joli avec 0.2 en dur ....
     */
    static float squareCoords[] = {
            -.2f,   .2f, 0.0f,
            -.2f,  -.2f, 0.0f,
            .2f,  -.2f, 0.0f,
            .2f,   .2f, 0.0f };
    // Le tableau des couleurs
    static float squareColors[] = {
            1.0f,  0.0f, 0.0f, 1.0f,
            1.0f,  1.0f, 1.0f, 1.0f,
            0.0f,  1.0f, 0.0f, 1.0f,
            0.0f,  0.0f, 1.0f, 1.0f };

    // Le carré est dessiné avec 2 triangles
    private final short Indices[] = { 0, 1, 2, 0, 2, 3 };

    private final int vertexStride = COORDS_PER_VERTEX * 4; // le pas entre 2 sommets : 4 bytes per vertex

    private final int couleurStride = COULEURS_PER_VERTEX * 4; // le pas entre 2 couleurs

    public Square(int w, int h) {
        float w_ = w / 20;
        float h_ = h / 44;

        squareCoords[0] = -w_*2/w;
        squareCoords[1] = h_*2/h;
        squareCoords[3] = -w_*2/w;
        squareCoords[4] = -h_*2/h;
        squareCoords[6] = w_*2/w;
        squareCoords[7] = -h_*2/h;
        squareCoords[9] = w_*2/w;
        squareCoords[10] = h_*2/h;

        width = Math.abs(squareCoords[0]) + squareCoords[9];
        height = squareCoords[1] + Math.abs(squareCoords[4]);

        posGlX = 1.0f - (width/2.0f);
        posGlY = 1.0f - (height/2.0f);
    }

    public Square(int w, int h, int x, int y) {
        float w_ = w / 20;
        float h_ = h / 44;

        squareCoords[0] = -w_*2/w;
        squareCoords[1] = h_*2/h;
        squareCoords[3] = -w_*2/w;
        squareCoords[4] = -h_*2/h;
        squareCoords[6] = w_*2/w;
        squareCoords[7] = -h_*2/h;
        squareCoords[9] = w_*2/w;
        squareCoords[10] = h_*2/h;

        width = Math.abs(squareCoords[0]) + squareCoords[9];
        height = squareCoords[1] + Math.abs(squareCoords[4]);

        posGlX = (x+1)*(1.0f - (width/2.0f));
        posGlY = (y+1)*(1.0f - (height/2.0f));
    }

    public void init() {
        // initialisation du buffer pour les vertex (4 bytes par float)
        ByteBuffer bb = ByteBuffer.allocateDirect(squareCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(squareCoords);
        vertexBuffer.position(0);


        // initialisation du buffer pour les couleurs (4 bytes par float)
        ByteBuffer bc = ByteBuffer.allocateDirect(squareColors.length * 4);
        bc.order(ByteOrder.nativeOrder());
        colorBuffer = bc.asFloatBuffer();
        colorBuffer.put(squareColors);
        colorBuffer.position(0);


        // initialisation du buffer des indices
        ByteBuffer dlb = ByteBuffer.allocateDirect(Indices.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        indiceBuffer = dlb.asShortBuffer();
        indiceBuffer.put(Indices);
        indiceBuffer.position(0);

        /* Chargement des shaders */
        int vertexShader = MyGLRenderer.loadShader(
                GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(
                GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        IdProgram = GLES20.glCreateProgram();             // create empty OpenGL Program
        GLES20.glAttachShader(IdProgram, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(IdProgram, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(IdProgram);                  // create OpenGL program executables
    }

    /* La fonction Display */
    public void draw(float[] mvpMatrix) {
        // Add program to OpenGL environment
        GLES20.glUseProgram(IdProgram);

        // get handle to vertex shader's vPosition member et vCouleur member
        IdPosition = GLES20.glGetAttribLocation(IdProgram, "vPosition");
        IdCouleur = GLES20.glGetAttribLocation(IdProgram, "vCouleur");

        /* Activation des Buffers */
        GLES20.glEnableVertexAttribArray(IdPosition);
        GLES20.glEnableVertexAttribArray(IdCouleur);

        /* Lecture des Buffers */
        GLES20.glVertexAttribPointer(
                IdPosition, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        GLES20.glVertexAttribPointer(
                IdCouleur, COULEURS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                couleurStride, colorBuffer);


        // get handle to shape's transformation matrix
        IdMVPMatrix = GLES20.glGetUniformLocation(IdProgram, "uMVPMatrix");
        MyGLRenderer.checkGlError("glGetUniformLocation");

        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(IdMVPMatrix, 1, false, mvpMatrix, 0);
        MyGLRenderer.checkGlError("glUniformMatrix4fv");


        // Draw the square
        GLES20.glDrawElements(
                GLES20.GL_TRIANGLES, Indices.length,
                GLES20.GL_UNSIGNED_SHORT, indiceBuffer);


        // Disable vertex array
        GLES20.glDisableVertexAttribArray(IdPosition);
        GLES20.glDisableVertexAttribArray(IdCouleur);

    }

    public void moveDown() { posGlY -= height; }
    public void moveRight() { posGlX -= width; }
    public void moveLeft() { posGlX += width; }

    public float getPosGlX() { return posGlX; }
    public float getPosGlY() { return posGlY; }
}
