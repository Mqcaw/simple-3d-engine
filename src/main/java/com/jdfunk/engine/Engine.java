package com.jdfunk.engine;

import com.jdfunk.game.Main;
import org.lwjgl.glfw.GLFWErrorCallback;
import com.jdfunk.engine.utils.Constants;

import static org.lwjgl.glfw.GLFW.*;

public class Engine {

    public static final long NANOSECOND = 1000000000L;
    public static final float FRAMERATE = 1000;

    private static int fps;
    private static float frameTime = 1.0f / FRAMERATE;

    private boolean isRunning;

    private Window window;
    private GLFWErrorCallback errorCallBack;
    private ILogic gameLogic;

    private void init() throws Exception {
        glfwSetErrorCallback(errorCallBack = GLFWErrorCallback.createPrint(System.err));
        window = Main.getWindow();
        gameLogic = Main.getGame();
        window.init();
        gameLogic.init();
    }

    public void start() throws Exception {
        init();
        if (isRunning) {
            return;
        }
        run();
    }

    public void run() {
        isRunning = true;
        int frames = 0;
        long frameCounter = 0;
        long lastTime = System.nanoTime();
        double unprocessedTime = 0;

        while (isRunning) {
            boolean render = false;
            long startTime = System.nanoTime();
            long passedTime = startTime - lastTime;
            lastTime = startTime;

            unprocessedTime += passedTime / (double) NANOSECOND;
            frameCounter += passedTime;

            input();

            while (unprocessedTime > frameTime) {
                render = true;
                unprocessedTime =+ frameTime;

                if (window.windowShouldClose()) {
                    stop();
                }

                if (frameCounter >= NANOSECOND) {
                    setFps(frames);
                    window.setTitle(Constants.TITLE_PREFIX + " | FPS: " + getFps());
                    frames = 0;
                    frameCounter = 0;
                }
            }

            if (render) {
                update();
                render();
                frames++;
            }
        }
        cleanUp();
    }

    private void stop() {
        if (!isRunning) {
            return;
        }
        isRunning = false;
    }

    private void input() {
        gameLogic.input();
    }

    private void render() {
        gameLogic.render();
        window.update();
    }

    private void update() {
        gameLogic.update();
    }

    private void cleanUp() {
        window.cleanUp();
        gameLogic.cleanUp();
        errorCallBack.free();
        glfwTerminate();
    }


    public static int getFps() {
        return fps;
    }

    public static void setFps(int fps) {
        Engine.fps = fps;
    }
}
