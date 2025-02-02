public class Main {
    public static void main(String[] args) {
        Window window = new Window(800, 600, "Engine");
        window.init();
        window.run();
        window.cleanUp();
    }
}
