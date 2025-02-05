import org.lwjgl.glfw.GLFWErrorCallback;
import static org.lwjgl.glfw.GLFW.*;

public class EngineManager {

    public static final long NANOSECOND = 1000000000L;
    public static final float FRAMERATE = 1000;

    private static int fps;
    private static float frameTime = 1.0f / FRAMERATE;

    private boolean isRunning;

    private Window window;
    private GLFWErrorCallback errorCallBack;

    private void init() throws Exception {
        glfwSetErrorCallback(errorCallBack = GLFWErrorCallback.createPrint(System.err));
        window = Main.getWindow();
        window.init();
    }

    public void start() throws Exception {
        init();
        if (isRunning) {
            return;
        }
        run();
    }

    public void run() {

    }

    private void stop() {

    }

    private void input() {

    }

    private void render() {

    }

    private void update() {

    }

    private void cleanUp() {
        window.cleanUp();
        errorCallBack.free();
        glfwTerminate();
    }


    public static int getFps() {
        return fps;
    }

    public static void setFps(int fps) {
        EngineManager.fps = fps;
    }
}
