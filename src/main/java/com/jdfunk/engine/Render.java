package com.jdfunk.engine;

import com.jdfunk.engine.entity.Model;
import com.jdfunk.engine.utils.Utils;
import com.jdfunk.game.Main;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL30.*;

public class Render {
    private final Window window;
    private ShaderManager shader;

    public Render() {
        window = Main.getWindow();
    }

    public void init() throws Exception {
        System.out.println("OpenGl Renderer: " + GL11.glGetString(GL_RENDERER));

        shader = new ShaderManager();
        shader.createVertexShader(Utils.loadResource("/shaders/vertex.vsh"));
        System.out.println(Utils.loadResource("/shaders/vertex.vsh"));
        shader.createFragmentShader(Utils.loadResource("/shaders/fragment.fsh"));
        shader.link();

        shader.createUniform("textureSampler");
    }

    public void render(Model model) {
        clear();
        shader.bind();
        shader.setUniform("textureSampler", 0);
        glBindVertexArray(model.getId());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, model.getTexture().getId());
        glDrawElements(GL_TRIANGLES, model.getVertexCount(), GL_UNSIGNED_INT, 0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
        shader.unbind();
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    }

    public void cleanUp() {
        shader.cleanUp();
    }

}
