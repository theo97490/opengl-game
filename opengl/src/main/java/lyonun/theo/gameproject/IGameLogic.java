package lyonun.theo.gameproject;

public abstract class IGameLogic {
    Renderer renderer;

    public IGameLogic(){
        renderer = new Renderer();
    }

    public abstract void init(Window win);

    public abstract void input(Window win);

    public abstract void update();

    public void render(){
        renderer.render();
    };
}
