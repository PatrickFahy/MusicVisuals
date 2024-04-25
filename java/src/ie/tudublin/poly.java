package ie.tudublin;

public abstract class poly {
    ProjectVisual v;

    public poly(ProjectVisual v){
        this.v = v;
    }

    public abstract void render();
}