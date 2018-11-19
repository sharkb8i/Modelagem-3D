package teobaldo.model;

/**
 *
 * @author Diedrich_
 */
public class RGB {
    private double r;
    private double g;
    private double b;
    
    public RGB(double r, double g, double b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }
    
    public double getR () {
        return r;
    }
    
    public double getG () {
        return g;
    }
    
    public double getB () {
        return b;
    }
    
    public void setR (double r) {
        this.r = r;
    }
    
    public void setG (double g) {
        this.g = g;
    }
    
    public void setB (double b) {
        this.b = b;
    }
}