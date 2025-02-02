import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import static org.lwjgl.opengl.GL30.*;

public class Renderer {
    private int shaderProgram;
    private int vao, vbo, ebo;
    private int modelLoc, viewLoc, projectionLoc;
    private Matrix4f model, view, projection;

    public void init() {

        InputStream vertexStream = getClass().getClassLoader().getResourceAsStream("Shaders/vertex_shader.vsh");
        InputStream fragmentStream = getClass().getClassLoader().getResourceAsStream("Shaders/fragment_shader.fsh");

        if (vertexStream == null || fragmentStream == null) {
            throw new RuntimeException("Shader files not found in JAR!");
        }

        shaderProgram = ShaderUtils.loadShaders(vertexStream, fragmentStream);

        // Get uniform locations
        modelLoc = glGetUniformLocation(shaderProgram, "model");
        viewLoc = glGetUniformLocation(shaderProgram, "view");
        projectionLoc = glGetUniformLocation(shaderProgram, "projection");

        // Set up matrices
        model = new Matrix4f();
        view = new Matrix4f().lookAt(new Vector3f(0, 0, 3), new Vector3f(0, 0, 0), new Vector3f(0, 1, 0));
        projection = new Matrix4f().perspective((float) Math.toRadians(45.0), 800f / 600f, 0.1f, 100.0f);

        // Create VAO, VBO, and EBO
        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        // VBO - Vertex Buffer Object
        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(Cube.vertices.length);
        vertexBuffer.put(Cube.vertices).flip();
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        // EBO - Element Buffer Object
        ebo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        IntBuffer indexBuffer = BufferUtils.createIntBuffer(Cube.indices.length);
        indexBuffer.put(Cube.indices).flip();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW);

        // Vertex attribute pointer (position)
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    public void render() {
        glUseProgram(shaderProgram);

        // Pass matrices to the shader
        glUniformMatrix4fv(modelLoc, false, model.get(new float[16]));
        glUniformMatrix4fv(viewLoc, false, view.get(new float[16]));
        glUniformMatrix4fv(projectionLoc, false, projection.get(new float[16]));

        // Bind VAO and draw the cube
        glBindVertexArray(vao);
        glDrawElements(GL_TRIANGLES, Cube.indices.length, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
    }
}
