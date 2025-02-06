package com.jdfunk.game;

import com.jdfunk.engine.Engine;
import com.jdfunk.engine.Window;
import com.jdfunk.engine.utils.Constants;

public class Main {

    private static Window window;
    private static TestGame game;

    public static void main(String[] args) {
        window = new Window(0, 900, Constants.TITLE_PREFIX, false);
        game = new TestGame();
        Engine engine = new Engine();

        try {
            engine.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Window getWindow() {
        return window;
    }

    public static TestGame getGame() {
        return game;
    }
}
