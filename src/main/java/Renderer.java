import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryStack.stackPush;

public class Renderer {
    private int shaderProgram;
    private int vao, vbo, ebo;
    private int modelLoc, viewLoc, projectionLoc;
    private Matrix4f model, view, projection;
    private long window;

    Cube cube_1 = new Cube();
    Cube cube_2 = new Cube();

    public void init(long window) {

        window = window;
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

        IntBuffer windowWidth;
        IntBuffer windowHeight;

        try (MemoryStack stack = stackPush()) {
            windowWidth = stack.mallocInt(1);
            windowHeight = stack.mallocInt(1);

            glfwGetWindowSize(window, windowWidth, windowHeight);
        }

        cube_1.applyTransformation(1.0f, new Vector3f(0, 0, 0), new Vector3f(0, 1, 0));

        // Set up matrices
        model = new Matrix4f();
        view = new Matrix4f().lookAt(new Vector3f(1, 1, 3), new Vector3f(0, 0, 0), new Vector3f(0, 1, 0));
        projection = new Matrix4f().perspective((float) Math.toRadians(45.0),  (float) windowWidth.get() / windowHeight.get(), 0.1f, 100.0f);

        // Create VAO, VBO, and EBO
        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        // VBO - Vertex Buffer Object
        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        //FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(cube.getVertexBuffer().capacity());
        //vertexBuffer.put(cube.getVertexBuffer()).flip();
        //glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);


        // EBO - Element Buffer Object
        ebo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        //IntBuffer indexBuffer = BufferUtils.createIntBuffer(cube.getIndexBuffer().capacity());
        //indexBuffer.put(cube.getIndexBuffer()).flip();
       // glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW);

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
        //glDrawElements(GL_TRIANGLES, cube.getIndexBuffer().capacity(), GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
    }
}
