package teobaldo.model;

import java.io.Serializable;

/**
 *
 * @author Diedrich_
 */
public class Luz implements Serializable {
    public Vertice local;
    public double Ir;
    public double Ig;
    public double Ib;
    
    public Luz (Vertice local, double Ir, double Ig, double Ib) {
        this.local = local;
        this.Ir = Ir;
        this.Ig = Ig;
        this.Ib = Ib;
    }
    
    public Luz (Vertice local) {
        this.local = local;
    }
    
    public double getIr() {
        return this.Ir;
    }
    
    public double getIg() {
        return this.Ig;
    }
    
    public double getIb() {
        return this.Ib;
    }
    
    public Vertice getLocal() {
        return this.local;
    }
    
    public void setIr(double Ir) {
        this.Ir = Ir;
    }
    
    public void setIg(double Ig) {
        this.Ig = Ig;
    }
    
    public void setIb(double Ib) {
        this.Ib = Ib;
    }
    
    public void setLocal(Vertice local) {
        this.local = local;
    }
}