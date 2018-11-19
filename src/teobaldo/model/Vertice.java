package teobaldo.model;

import java.io.Serializable;

public class Vertice implements Serializable {
    
    private double x;
    private double y;
    private double z;

    public Vertice(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Vertice(double x, double y) {
        this.x = x;
        this.y = y;
        this.z = 0;
    }
    
    public Vertice(double z) {
        this.z = z;
    }
    
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }
    
    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
    
    public double norma() {  // enezinho com chapéu
        return (double)(Math.sqrt((x * x) + (y * y) + (z * z)));
    }
    
    public static double getDistancia(Vertice v1, Vertice v2) {
        return Math.sqrt((Math.pow((v2.x - v1.x), 2) + Math.pow((v2.y - v1.y), 2) + Math.pow((v2.z - v1.z), 2)));
    }
    
    @Override
    public String toString() {
        return "x = " + x + ", y = " + y + ", z = " + z;
    }
}