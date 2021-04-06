package lyonun.theo.gameproject;

public abstract class Renderable{
    protected Shader shader;

    public void setShader(Shader shader) {
        this.shader = shader;
    }

    public abstract void draw();
}
