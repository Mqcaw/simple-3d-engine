package com.jdfunk.game;

import com.jdfunk.engine.ILogic;
import com.jdfunk.engine.ObjectLoader;
import com.jdfunk.engine.Render;
import com.jdfunk.engine.Window;
import com.jdfunk.engine.entity.Model;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class TestGame implements ILogic {

    private int direction = 0;
    private float color = 0.0f;

    private final Render renderer;
    private final ObjectLoader loader;
    private final Window window;

    private Model model;

    public TestGame() {
        renderer = new Render();
        window = Main.getWindow();
        loader = new ObjectLoader();
    }

    @Override
    public void init() throws Exception {
        renderer.init();

        float[] vertices = {
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f,
                -0.5f, 0.5f, 0f
        };

        model = loader.loadModel(vertices);

    }

    @Override
    public void input() {
        if (window.isKeyPressed(GLFW_KEY_UP)) {
            direction = 1;
        } else if (window.isKeyPressed(GLFW_KEY_DOWN)) {
            direction = -1;
        } else {
            direction = 0;
        }
    }

    @Override
    public void update() {
        color += direction * 0.01f;
        if (color > 1) {
            color = 1.0f;
        } else if (color <= 0) {
            color = 0.0f;
        }
    }

    @Override
    public void render() {
        if (window.isResize()) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResize(true);
        }

        window.setClearColor(color, color, color, 0.0f);
        renderer.render(model);
    }

    @Override
    public void cleanUp() {
        renderer.cleanUp();
        loader.cleanUp();
    }
}
