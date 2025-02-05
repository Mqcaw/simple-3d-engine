import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Cube {
    private Vector3f[] vertices;
    private int[] indices;
    private Matrix4f transformMatrix;

    public Cube() {
        vertices = new Vector3f[]{
                new Vector3f(-0.5f, -0.5f, -0.5f),
                new Vector3f( 0.5f, -0.5f, -0.5f),
                new Vector3f( 0.5f,  0.5f, -0.5f),
                new Vector3f(-0.5f,  0.5f, -0.5f),
                new Vector3f(-0.5f, -0.5f,  0.5f),
                new Vector3f( 0.5f, -0.5f,  0.5f),
                new Vector3f( 0.5f,  0.5f,  0.5f),
                new Vector3f(-0.5f,  0.5f,  0.5f)
        };

        indices = new int[]{
                0, 1, 2, 2, 3, 0, // Front
                1, 5, 6, 6, 2, 1, // Right
                5, 4, 7, 7, 6, 5, // Back
                4, 0, 3, 3, 7, 4, // Left
                3, 2, 6, 6, 7, 3, // Top
                4, 5, 1, 1, 0, 4  // Bottom
        };

        transformMatrix = new Matrix4f().identity();
    }

    public void applyTransformation(float scale, Vector3f rotation, Vector3f translation) {
        transformMatrix.identity()
                .translate(translation)
                .rotateXYZ((float) Math.toRadians(rotation.x),
                        (float) Math.toRadians(rotation.y),
                        (float) Math.toRadians(rotation.z))
                .scale(scale);

        // Apply transformation to all vertices
        for (int i = 0; i < vertices.length; i++) {
            transformMatrix.transformPosition(vertices[i]);
        }
    }

    // Convert vertex list to a flat float array for OpenGL
    public float[] getVertexFloatArray() {
        float[] vertexArray = new float[vertices.length * 3]; // Each vertex has x, y, z

        for (int i = 0; i < vertices.length; i++) {
            vertexArray[i * 3] = vertices[i].x;
            vertexArray[i * 3 + 1] = vertices[i].y;
            vertexArray[i * 3 + 2] = vertices[i].z;
        }

        return vertexArray;
    }

    // Convert indices to IntBuffer
    public IntBuffer getIndexBuffer() {
        IntBuffer buffer = BufferUtils.createIntBuffer(indices.length);
        buffer.put(indices).flip();
        return buffer;
    }

    // Convert vertices to FloatBuffer (for OpenGL VBO)
    public FloatBuffer getVertexBuffer() {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length * 3);
        buffer.put(getVertexFloatArray()).flip();
        return buffer;
    }

    public Vector3f[] getVertices() {
        return vertices;
    }

    public int[] getIndices() {
        return indices;
    }


}
