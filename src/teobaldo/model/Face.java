package teobaldo.model;

import java.util.LinkedList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;

public class Face {
    
    private LinkedList<Aresta> arestas;
    Objeto obj;
    
    private Vertice normal;
    private Vertice centroide;
    private RGB difusa;
    private RGB especular;
    private RGB ambiente;
    private RGB cor;
    
    public double distanciaAtual;
    
    public Face() {
        this.arestas = new LinkedList<>();
        this.difusa = new RGB(0, 0, 0);
        this.especular = new RGB(0, 0, 0);
        this.ambiente = new RGB(0, 0, 0);
        this.cor = new RGB(0, 0, 0);
        this.centroide = new Vertice(0,0,0);
    }
    
    public Face(LinkedList<Aresta> arestas) {
        this.arestas = arestas;
        this.difusa = new RGB(0, 0, 0);
        this.especular = new RGB(0, 0, 0);
        this.ambiente = new RGB(0, 0, 0);
        this.cor = new RGB(0, 0, 0);
        this.centroide = new Vertice(0,0,0);
    }
    
    public void addAresta(Aresta aresta) {
        this.arestas.add(aresta);
    }
    
    public LinkedList<Aresta> getArestas() {
        return this.arestas;
    }
    
    public Objeto getObjeto() {
        return this.obj;
    }
    
    public void setObjeto(Objeto obj) {
        this.obj = obj;
    }
    
    public Vertice getNormal() {
        return normal;
    }
    
    public void CalculaNormal() {
        double a, b, c, d, norma;
        Vertice p1, p2 , p3;
        Vertice n;
        
        p1 = new Vertice(0, 0, 0);
        p2 = new Vertice(0, 0, 0);
        p3 = new Vertice(0, 0, 0);
        
        //DETERMINANDO EQUAÇÃO DE UM PLANO P1 - PEGAR OS VÉRTICES DAS ARESTAS (SENTIDO ANTI-HORÁRIO)
        for(int i = 0; i < this.arestas.size(); i++){
            if(i == 0){
                p1 = this.arestas.get(i).getV1();
                p2 = this.arestas.get(i).getV2();
            }
            if(this.arestas.get(i).getV1() == p2){
                p3 = this.arestas.get(i).getV2();
            }
        }
        
        a = ((p3.getY() - p2.getY()) * (p1.getZ() - p2.getZ())) - ((p1.getY() - p2.getY()) * (p3.getZ() - p2.getZ()));
        b = ((p3.getZ() - p2.getZ()) * (p1.getX() - p2.getX()) - ((p1.getZ() - p2.getZ()) * (p3.getX() - p2.getX())));
        c = ((p3.getX() - p2.getX()) * (p1.getY() - p2.getY())) - ((p1.getX() - p2.getX()) * (p3.getY() - p2.getY()));
        
        n = new Vertice(a, b, c);
        norma = n.norma();
        this.normal = new Vertice((n.getX() / norma), (n.getY() / norma), (n.getZ() / norma));
        
        //PARA DEFINIR POSICAO RELATIVA DO OBSERVADOR (NECESSÁRIO ??)
        d = -(a * p2.getX()) - (b * p2.getY()) - (c * p2.getZ());
    }
    
    public void CalcCentroide() {
        double maxX = -9999, maxY = -9999, maxZ = -9999;
        double minX = 9999, minY = 9999, minZ = 9999;
        
        for (int i = 0; i < arestas.size(); i++) {
            if (arestas.get(i).getV1().getX() > maxX) {
                maxX = arestas.get(i).getV1().getX();
            }
            if(arestas.get(i).getV2().getX() > maxX) {
                maxX = arestas.get(i).getV2().getX();
            }
            if (arestas.get(i).getV1().getY() > maxY) {
                maxY = arestas.get(i).getV1().getY();
            }
            if(arestas.get(i).getV2().getY() > maxY) {
                maxY = arestas.get(i).getV2().getY();
            }
            if (arestas.get(i).getV1().getZ() > maxZ) {
                maxZ = arestas.get(i).getV1().getZ();
            }
            if(arestas.get(i).getV2().getZ() > maxZ) {
                maxZ = arestas.get(i).getV2().getZ();
            }
            
            if (arestas.get(i).getV1().getX() < minX) {
                minX = arestas.get(i).getV1().getX();
            }
            if(arestas.get(i).getV2().getX() < minX) {
                minX = arestas.get(i).getV2().getX();
            }
            if (arestas.get(i).getV1().getY() < minY) {
                minY = arestas.get(i).getV1().getY();
            }
            if(arestas.get(i).getV2().getY() < minY) {
                minY = arestas.get(i).getV2().getY();
            }
            if (arestas.get(i).getV1().getZ() < minZ) {
                minZ = arestas.get(i).getV1().getZ();
            }
            if(arestas.get(i).getV2().getZ() < minZ) {
                minZ = arestas.get(i).getV2().getZ();
            }
        }
        this.centroide.setX((maxX - minX)/2);
        this.centroide.setY((maxY - minY)/2);
        this.centroide.setZ((maxZ - minZ)/2);
    }
    
    public void CalcDistance(Camera cam) {
        this.distanciaAtual = (double)Vertice.getDistancia(centroide, cam.posicao);
    }
    
    public RGB CalcAmbiente(Lampada lampAmbiente) {
        RGB cor = new RGB(lampAmbiente.getIntensidade() * obj.getKaR(), 
                          lampAmbiente.getIntensidade() * obj.getKaG(),
                          lampAmbiente.getIntensidade() * obj.getKaB());
        return cor;
    }

    public RGB CalcDifusa(Lampada lamp) {
        Vertice normalLuz = new Vertice((lamp.getPos().getX() - centroide.getX()), 
                                        (lamp.getPos().getY() - centroide.getY()), 
                                        (lamp.getPos().getZ() - centroide.getZ()));
        double norma = normalLuz.norma();
        
        normalLuz.setX(normalLuz.getX() / norma);
        normalLuz.setY(normalLuz.getY() / norma);
        normalLuz.setZ(normalLuz.getZ() / norma);
        
        double cosAng = ((normal.getX() * normalLuz.getX()) 
                       + (normal.getY() * normalLuz.getY())
                       + (normal.getZ() * normalLuz.getZ()));
        
        
        RGB cor = new RGB((lamp.getIntensidade() * obj.getKdR() * cosAng),
                          (lamp.getIntensidade() * obj.getKdG() * cosAng),
                          (lamp.getIntensidade() * obj.getKdB() * cosAng));
        return cor;
    }

    public RGB CalcEspecular(Lampada lamp, Camera cam) {
        Vertice normalLuz = new Vertice((lamp.getPos().getX() - centroide.getX()), 
                                        (lamp.getPos().getY() - centroide.getY()), 
                                        (lamp.getPos().getZ() - centroide.getZ()));
        
        double norma = normalLuz.norma();
        
        normalLuz.setX(normalLuz.getX() / norma);
        normalLuz.setY(normalLuz.getY() / norma);
        normalLuz.setZ(normalLuz.getZ() / norma);
        
        Vertice S = new Vertice(cam.posicao.getX() - centroide.getX(), 
                                cam.posicao.getY() - centroide.getY(), 
                                cam.posicao.getZ() - centroide.getZ());
        
        double normaS = S.norma();
        S.setX(S.getX() / normaS);
        S.setY(S.getY() / normaS);
        S.setZ(S.getZ() / normaS);
        
        double LN2 = ((2*(normalLuz.getX() * normal.getX())) + (2*(normalLuz.getY() * normal.getY())) + (2*(normalLuz.getZ() * normal.getZ())));
        
        Vertice R = new Vertice((LN2 * normal.getX()) - normalLuz.getX(),
                                (LN2 * normal.getY()) - normalLuz.getY(),
                                (LN2 * normal.getZ()) - normalLuz.getZ());
        
        double angle = ((R.getX() * S.getX()) + (R.getY() * S.getY()) + (R.getZ() * S.getZ()));

        if(angle < 0) {
            return new RGB(0, 0, 0);
        }

        RGB cor = new RGB((lamp.getIntensidade() * obj.getKsR() * ((double)Math.pow(angle, obj.getN()))),
                          (lamp.getIntensidade() * obj.getKsG() * ((double)Math.pow(angle, obj.getN()))),
                          (lamp.getIntensidade() * obj.getKsB() * ((double)Math.pow(angle, obj.getN()))));
        return cor;
    }

    public void CalcCor(Lampada lampAmbiente, List<Lampada> lamps, Camera cam) {
        this.difusa.setR(0);
        this.difusa.setG(0);
        this.difusa.setB(0);
        this.especular.setR(0);
        this.especular.setG(0);
        this.especular.setB(0);

        this.ambiente = CalcAmbiente(lampAmbiente);
        
        for (Lampada l : lamps) {
            this.difusa.setR(this.difusa.getR() + CalcDifusa(l).getR());
            this.difusa.setG(this.difusa.getG() + CalcDifusa(l).getG());
            this.difusa.setB(this.difusa.getB() + CalcDifusa(l).getB());
            
            this.especular.setR(this.especular.getR() + CalcEspecular(l, cam).getR());
            this.especular.setG(this.especular.getG() + CalcEspecular(l, cam).getG());
            this.especular.setB(this.especular.getB() + CalcEspecular(l, cam).getB());
        }
        
        this.cor.setR(this.ambiente.getR() + this.difusa.getR() + this.especular.getR());
        this.cor.setG(this.ambiente.getG() + this.difusa.getG() + this.especular.getG());
        this.cor.setB(this.ambiente.getB() + this.difusa.getB() + this.especular.getB());
        
        if (this.cor.getR() < 0)
            this.cor.setR(0);
        if (this.cor.getR() > 255)
            this.cor.setR(255);

        if (this.cor.getG() < 0)
            this.cor.setG(0);
        if (this.cor.getG() > 255)
            this.cor.setG(255);

        if (this.cor.getB() < 0)
            this.cor.setB(0);
        if (this.cor.getB() > 255)
            this.cor.setB(255);
    }
    
    public void pintarFace(GraphicsContext gc) {
        for(Aresta a : arestas) {
            gc.fillRect((int) a.getV1().getX(), (int) a.getV1().getY(), (int) a.getV2().getX(), (int) a.getV2().getY());
        }
    }
    
    @Override
    public String toString() {
        return "Face \n Aresta: " + this.getArestas();
    }
}
