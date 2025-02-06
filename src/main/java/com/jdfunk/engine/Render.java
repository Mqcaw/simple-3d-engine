package com.jdfunk.engine;

import com.jdfunk.engine.entity.Model;
import com.jdfunk.game.Main;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL20.*;

public class Render {
    private final Window window;

    public Render() {
        window = Main.getWindow();
    }

    public void init() throws Exception {

    }

    public void render(Model model) {
        clear();
        glBindVertexArray(model.getId());
        glEnableVertexAttribArray(0);
        glDrawArrays(GL_TRIANGLES, 0, model.getVertexCount());
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    }

    public void cleanUp() {

    }

}
