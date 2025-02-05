public class Main {

    private static Window window;

    public static void main(String[] args) {
        Window window = new Window(0, 900, "Engine", false);

        while (!window.windowShouldClose()) {
            window.update();
        }

        window.cleanUp();
    }

    public static Window getWindow() {
        return window;
    }
}
