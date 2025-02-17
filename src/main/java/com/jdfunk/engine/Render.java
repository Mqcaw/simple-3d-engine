package com.jdfunk.engine;

import com.jdfunk.engine.entity.Model;
import com.jdfunk.engine.utils.Utils;
import com.jdfunk.game.Main;

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
        shader = new ShaderManager();
        shader.createVertexShader(Utils.loadResource("/shaders/vertex.vsh"));
        shader.createFragmentShader(Utils.loadResource("/shaders/fragment.fsh"));
        shader.link();
    }

    public void render(Model model) {
        clear();
        shader.bind();
        glBindVertexArray(model.getId());
        glEnableVertexAttribArray(0);
        glDrawArrays(GL_TRIANGLES, 0, model.getVertexCount());
        glDisableVertexAttribArray(0);
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
