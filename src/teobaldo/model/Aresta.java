package teobaldo.model;

import java.io.Serializable;

public class Aresta implements Serializable{
    
    Vertice v1, v2;
    boolean visivel;
    
    public Aresta() {
        this.v1 = null;
        this.v2 = null;
        this.visivel = false;
    }
    
    public Aresta(Vertice v1, Vertice v2) {
        this.v1 = v1;
        this.v2 = v2;
        this.visivel = false;
    }
    
    public Vertice getV1() {
        return this.v1;
    }
    
    public Vertice getV2() {
        return this.v2;
    }
    
    public boolean getVisivel() {
        return this.visivel;
    }
    
    public void setV1(Vertice v1) {
        this.v1 = v1;
    }
    
    public void setV2(Vertice v2) {
        this.v1 = v2;
    }
    
    public void setVisivel(boolean visivel) {
        this.visivel = visivel;
    }
    
    @Override
    public String toString() {
        return "P1: x = " + this.getV1().getX() + ", y = " + this.getV1().getY() + ", z = " + this.getV1().getZ() + 
                "\nP2: x = " + this.getV2().getX() + ", y = " + this.getV2().getY() + ", z = " + this.getV2().getZ();
    }
}
