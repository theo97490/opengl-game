package lyonun.theo.gameproject;

public class Main {
    public static void main(String[] args) {
        GameEngine engine = new GameEngine("Game", 800, 800, new GameLogic());
        engine.start();
    }
}
