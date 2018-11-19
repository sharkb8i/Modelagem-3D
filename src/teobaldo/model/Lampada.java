package teobaldo.model;

/**
 *
 * @author Diedrich_
 */
public class Lampada {
    private Vertice pos;
    private double intensidade;
    
    public Lampada (Vertice pos, double intensidade) {
        this.pos = pos;
        this.intensidade = intensidade;
    }
    
    public Vertice getPos() {
        return pos;
    }
    
    public double getIntensidade() {
        return intensidade;
    }
    
    public void setPos(Vertice pos) {
        this.pos = pos;
    }
    
    public void setIntensidade(double i) {
        this.intensidade = i;
    }
}