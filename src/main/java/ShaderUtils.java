import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static org.lwjgl.opengl.GL20.*;

public class ShaderUtils {
    public static int loadShaders(InputStream vertexStream, InputStream fragmentStream) {
        int vertexShader = compileShader(vertexStream, GL_VERTEX_SHADER);
        int fragmentShader = compileShader(fragmentStream, GL_FRAGMENT_SHADER);

        int program = glCreateProgram();
        glAttachShader(program, vertexShader);
        glAttachShader(program, fragmentShader);
        glLinkProgram(program);

        if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
            System.err.println("Program linking failed: " + glGetProgramInfoLog(program));
            System.exit(1);
        }

        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);

        return program;
    }

    private static int compileShader(InputStream inputStream, int type) {
        try {
            if (inputStream == null) {
                throw new RuntimeException("Shader file not found!");
            }

            // Read InputStream into a String
            String shaderSource = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));

            int shader = glCreateShader(type);
            glShaderSource(shader, shaderSource);
            glCompileShader(shader);

            if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
                System.err.println("Shader compilation failed: " + glGetShaderInfoLog(shader));
                System.exit(1);
            }

            return shader;
        } catch (Exception e) {
            throw new RuntimeException("Error loading shader", e);
        }
    }
}
