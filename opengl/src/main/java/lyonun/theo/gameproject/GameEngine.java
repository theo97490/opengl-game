package lyonun.theo.gameproject;

import org.lwjgl.glfw.GLFW;

public class GameEngine implements Runnable{
    private final Thread gameloopThread;
    private IGameLogic gameLogic;
    private Window window;

    private float awaitTime = 1f / 60;

    public GameEngine(String windowTitle, int width, int height, IGameLogic gameLogic){
        gameloopThread = new Thread(this, "GAME_LOOP_THREAD");
        
        this.window = new Window(windowTitle, width, height);
        this.gameLogic = gameLogic;

    }

    public void start(){
        gameloopThread.start();
    }

    public void init(){
        window.init();
        gameLogic.init();
    }

    public void run(){
        init();
        gameLoop();
    }

    public void gameLoop(){
        double currTime, timeElapsed;
        double prevTime = GLFW.glfwGetTime();

        
        while (!window.shouldTerminate()){

            currTime = GLFW.glfwGetTime();
            timeElapsed = currTime - prevTime;
            prevTime = currTime;


            gameLogic.input(window);

            //La boucle update est utilisé normalement pour update le jeu mais dans notre setup update,
            //On rajoute des objets aux renderer. On aurait du faire plusieurs appels de gamelogic.update
            //par frame pendant un temps fixe qui a un fps différent du render
            gameLogic.update();
            
            gameLogic.render();

            window.update();
            syncFps(currTime);
        }

        window.terminate();
        
    }

    public void syncFps(double currTime){
        double endTime = currTime + awaitTime;

        while (GLFW.glfwGetTime() < endTime){
            try {
                Thread.sleep(1);
            } catch (Exception e) {}

        }
        
    }

    
}
