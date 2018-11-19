package teobaldo.model;

import java.io.Serializable;
import java.util.LinkedList;
import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.paint.Color;
import java.awt.Color;

public class Objeto implements Serializable, Cloneable {
    
    private LinkedList<Vertice> vertices;
    private LinkedList<Aresta> arestas;
    private LinkedList<Face> faces;
            
    private String nome;
    private int numSecoes;
    private Vertice centro;
    private double raio;
    private double graus;
    private boolean locked;
    private boolean objeto3D;
    
    protected double KaR;
    protected double KaG;
    protected double KaB;
    protected double KdR;
    protected double KdG;
    protected double KdB;
    protected double KsR;
    protected double KsG;
    protected double KsB;
    protected double n;
    
    protected Color cor;
    private static final Color COR_DEFAULT = new Color(50, 150, 250);
    
    public Objeto() {
        this.vertices = new LinkedList<>();
        this.arestas = new LinkedList<>();
        this.faces = new LinkedList<>();
        this.numSecoes = 0;
        this.locked = false;
        this.objeto3D = false;
        this.n = 1;
        this.cor = COR_DEFAULT;
    }
    
    // Obs.: aqui da pra passar a cor do objeto (OPCIONAL)
    public Objeto(int numSecoes, Vertice centro, double raio) {
        this.numSecoes = numSecoes;
        this.centro = centro;
        this.raio = raio;
        
        this.graus = Math.toRadians(Double.valueOf(360.0) / this.numSecoes);
        this.vertices = new LinkedList<>();
        this.locked = true;
        this.objeto3D = false;
        this.n = 1;
        this.cor = COR_DEFAULT;
    }
    
    @Override
    public String toString() {
        vertices.forEach((vertice) -> {
            System.out.println(vertice);
        });
        
        arestas.forEach((aresta) -> {
            System.out.println(aresta);
        });

        return "Poligonos";
    }
    
    // MANIPULAÇÃO DE PONTOS DA LISTA DE ARESTAS
    public void addPonto(Vertice p) {
        this.vertices.add(p);
    }
    
    public void delPonto(Vertice p) {
        this.vertices.remove(p);
    }
    
    public void addAresta(Aresta a) {
        this.arestas.add(a);
    }
    
    public void addFace(Face f) {
        this.faces.add(f);
    }
    
    // <--------------- GETTERS --------------->
    public String getNome() {
        return nome;
    }
    
    public LinkedList<Vertice> getVertices() {
        return vertices;
    }
    
    public LinkedList<Aresta> getArestas() {
        return arestas;
    }
    
    public LinkedList<Face> getFaces() {
        return faces;
    }
    
    public boolean getObjeto3D() {
        return objeto3D;
    }
    
    public LinkedList<Vertice> getLados() {
        return vertices;
    }
    
    public int getNumSecoes() {
        return numSecoes; 
    }
    
    public void incNumSecoes() {
        this.numSecoes++;
    }
    
    public Vertice getCentro() {
        return this.centro;
    }
    
    public double getRaio() {
        return raio;
    }
    
    public double getGraus() {
        return graus;
    }
    
    public boolean isLocked() {
        return locked;
    }
    
    public double getKaR() {
        return this.KaR;
    }
    
    public double getKaG() {
        return this.KaG;
    }
    
    public double getKaB() {
        return this.KaB;
    }
    
    public double getKdR() {
        return this.KdR;
    }
    
    public double getKdG() {
        return this.KdG;
    }
    
    public double getKdB() {
        return this.KdB;
    }
    
    public double getKsR() {
        return this.KsR;
    }
    
    public double getKsG() {
        return this.KsG;
    }
    
    public double getKsB() {
        return this.KsB;
    }
    
    public double getN() {
        return this.n;
    }
    
    public Color getCor() {
        return this.cor;
    }
    
    // <--------------- SETTERS --------------->
    public void setNome(int incrementaObjeto) {
        this.nome = "Objeto " + incrementaObjeto;
    }
    
    public void setVertices(LinkedList<Vertice> vertices) {
        this.vertices = vertices;
    }
    
    public void setObjeto3D(boolean objeto3D) {
        this.objeto3D = objeto3D;
    }
    
    public void setNumSecoes(int numSecoes) {
        this.numSecoes = numSecoes;
    }
    
    public void setCentro(Vertice centro) {
        this.centro = centro;
    }
    
    public void setRaio(double raio) {
        this.raio = raio;
    }
    
    public void setGraus(double graus) {
        this.graus = graus;
    }
    
    public void setLocked(boolean locked) {
        this.locked = locked;
    }
    
    public void lockObject() {
        this.locked = true;
    }
    
    public void setKaR(double KaR) {
        this.KaR = KaR;
    }
    
    public void setKaG(double KaG) {
        this.KaG = KaG;
    }
    
    public void setKaB(double KaB) {
        this.KaB = KaB;
    }
    
    public void setKdR(double KdR) {
        this.KdR = KdR;
    }
    
    public void setKdG(double KdG) {
        this.KdG = KdG;
    }
    
    public void setKdB(double KdB) {
        this.KdB = KdB;
    }
    
    public void setKsR(double KsR) {
        this.KsR = KsR;
    }
    
    public void setKsG(double KsG) {
        this.KsG = KsG;
    }
    
    public void setKsB(double KsB) {
        this.KsB = KsB;
    }
    
    public void setN(double n) {
        this.n = n;
    }
    
    public void setCor(Color cor) {
        this.cor = cor;
    }
    
    // MANIPULAÇÕES DE LISTA
    private void criarLista(LinkedList<Lista> lista, double max, double min) {
        for (int i = (int) min; i < (int) max; i++) {
            lista.add(new Lista(i));
        }
    }
    
    private void addIndex(LinkedList<Lista> lista, int index, double x) {
        lista.get(index).add(x);
    }
    
    // MÉTODO PARA DESENHO FRONTAL
    public void drawXY(GraphicsContext gc) {
        if(this.objeto3D == true) {
            Color awtColor = new Color(0, 255, 0); // COR VERDE
            int r = awtColor.getRed();
            int g = awtColor.getGreen();
            int b = awtColor.getBlue();
            int a = awtColor.getAlpha();
            double opacity = a / 255.0;
            gc.setStroke(javafx.scene.paint.Color.rgb(r, g, b, opacity));
            
            arestas.forEach((aresta) -> {
                gc.strokeLine(aresta.getV1().getX(), -aresta.getV1().getY(), aresta.getV2().getX(), -aresta.getV2().getY());
            });
        } else {
            Vertice p = null;
            
            Color awtColor = new Color(0, 0, 0); // COR PRETA
            int r = awtColor.getRed();
            int g = awtColor.getGreen();
            int b = awtColor.getBlue();
            int a = awtColor.getAlpha();
            double opacity = a / 255.0;
            gc.setStroke(javafx.scene.paint.Color.rgb(r, g, b, opacity));

            for (Vertice ponto : vertices) {
                if (p == null) {
                    p = ponto;
                    continue;
                }

                gc.strokeLine(p.getX(), -p.getY(), ponto.getX(), -ponto.getY());
                p = ponto;
            }
            if (locked) {
                gc.strokeLine(p.getX(), -p.getY(), this.vertices.getFirst().getX(), -this.vertices.getFirst().getY());
            }
        }
    }
    
    // MÉTODO PARA DESENHO LATERAL
    public void drawYZ(GraphicsContext gc) {
        if(this.objeto3D == true) {
            Color awtColor = new Color(0, 255, 0);  // COR VERDE
            int r = awtColor.getRed();
            int g = awtColor.getGreen();
            int b = awtColor.getBlue();
            int a = awtColor.getAlpha();
            double opacity = a / 255.0;
            gc.setStroke(javafx.scene.paint.Color.rgb(r, g, b, opacity));
            
            arestas.forEach((aresta) -> {
                gc.strokeLine(-aresta.getV1().getZ(), -aresta.getV1().getY(), -aresta.getV2().getZ(), -aresta.getV2().getY());
            });
        } else {
            Vertice p = null;
            
            Color awtColor = new Color(0, 0, 0); // COR PRETA
            int r = awtColor.getRed();
            int g = awtColor.getGreen();
            int b = awtColor.getBlue();
            int a = awtColor.getAlpha();
            double opacity = a / 255.0;
            gc.setStroke(javafx.scene.paint.Color.rgb(r, g, b, opacity));

            for (Vertice ponto : vertices) {
                if (p == null) {
                    p = ponto;
                    continue;
                }

                gc.strokeLine(-p.getZ(), -p.getY(), -ponto.getZ(), -ponto.getY());
                p = ponto;
            }
            if (locked) {
                gc.strokeLine(-p.getZ(), -p.getY(), -this.vertices.getFirst().getZ(), -this.vertices.getFirst().getY());
            }
        }
    }
    
    // MÉTODO PARA DESENHO TOPO
    public void drawXZ(GraphicsContext gc) {
        if(this.objeto3D == true) {
            Color awtColor = new Color(0, 255, 0);  // COR VERDE
            int r = awtColor.getRed();
            int g = awtColor.getGreen();
            int b = awtColor.getBlue();
            int a = awtColor.getAlpha();
            double opacity = a / 255.0;
            gc.setStroke(javafx.scene.paint.Color.rgb(r, g, b, opacity));
            
            arestas.forEach((aresta) -> {
                gc.strokeLine(aresta.getV1().getX(), aresta.getV1().getZ(), aresta.getV2().getX(), aresta.getV2().getZ());
            });
        } else {
            Vertice p = null;
            
            Color awtColor = new Color(0, 0, 0); // COR PRETA
            int r = awtColor.getRed();
            int g = awtColor.getGreen();
            int b = awtColor.getBlue();
            int a = awtColor.getAlpha();
            double opacity = a / 255.0;
            gc.setStroke(javafx.scene.paint.Color.rgb(r, g, b, opacity));

            for (Vertice ponto : vertices) {
                if (p == null) {
                    p = ponto;
                    continue;
                }

                gc.strokeLine(p.getX(), p.getZ(), ponto.getX(), ponto.getZ());
                p = ponto;
            }
            if (locked) {
                gc.strokeLine(p.getX(), p.getZ(), this.vertices.getFirst().getX(), this.vertices.getFirst().getZ());
            }
        }
    }
    
    // MÉTODO DE DESENHOS PARA SELEÇÕES
    public void drawSelectXY(GraphicsContext gc) {
        Color awtColor = new Color(255, 0, 0); // COR VERMELHA
        int r = awtColor.getRed();
        int g = awtColor.getGreen();
        int b = awtColor.getBlue();
        int a = awtColor.getAlpha();
        double opacity = a / 255.0;
        
        if(this.objeto3D == true) {
            gc.setStroke(javafx.scene.paint.Color.rgb(r, g, b, opacity));
            
            arestas.forEach((aresta) -> {
                gc.strokeLine(aresta.getV1().getX(), -aresta.getV1().getY(), aresta.getV2().getX(), -aresta.getV2().getY());
            });
        } else {
            Vertice p = null;
            
            gc.setStroke(javafx.scene.paint.Color.rgb(r, g, b, opacity));

            for (Vertice ponto : vertices) {
                if (p == null) {
                    p = ponto;
                    continue;
                }

                gc.strokeLine(p.getX(), -p.getY(), ponto.getX(), -ponto.getY());
                p = ponto;
            }
            if (locked) {
                gc.strokeLine(p.getX(), -p.getY(), this.vertices.getFirst().getX(), -this.vertices.getFirst().getY());
            }
        }
    }

    public void drawSelectYZ(GraphicsContext gc) {
        Color awtColor = new Color(255, 0, 0); // COR VERMELHA
        int r = awtColor.getRed();
        int g = awtColor.getGreen();
        int b = awtColor.getBlue();
        int a = awtColor.getAlpha();
        double opacity = a / 255.0;
        
        if(this.objeto3D == true) {
            gc.setStroke(javafx.scene.paint.Color.rgb(r, g, b, opacity));
            
            arestas.forEach((aresta) -> {
                gc.strokeLine(-aresta.getV1().getZ(), -aresta.getV1().getY(), -aresta.getV2().getZ(), -aresta.getV2().getY());
            });
        } else {
            Vertice p = null;
            gc.setStroke(javafx.scene.paint.Color.rgb(r, g, b, opacity));

            for (Vertice ponto : vertices) {
                if (p == null) {
                    p = ponto;
                    continue;
                }

                gc.strokeLine(-p.getZ(), -p.getY(), -ponto.getZ(), -ponto.getY());
                p = ponto;
            }
            if (locked) {
                gc.strokeLine(-p.getZ(), -p.getY(), -this.vertices.getFirst().getZ(), -this.vertices.getFirst().getY());
            }
        }
    }

    public void drawSelectXZ(GraphicsContext gc) {
        Color awtColor = new Color(255, 0, 0); // COR VERMELHA
        int r = awtColor.getRed();
        int g = awtColor.getGreen();
        int b = awtColor.getBlue();
        int a = awtColor.getAlpha();
        double opacity = a / 255.0;
        
        if(this.objeto3D == true) {
            gc.setStroke(javafx.scene.paint.Color.rgb(r, g, b, opacity));
            
            arestas.forEach((aresta) -> {
                gc.strokeLine(aresta.getV1().getX(), aresta.getV1().getZ(), aresta.getV2().getX(), aresta.getV2().getZ());
            });
        } else {
            Vertice p = null;
            gc.setStroke(javafx.scene.paint.Color.rgb(r, g, b, opacity));

            for (Vertice ponto : vertices) {
                if (p == null) {
                    p = ponto;
                    continue;
                }

                gc.strokeLine(p.getX(), p.getZ(), ponto.getX(), ponto.getZ());
                p = ponto;
            }
            if (locked) {
                gc.strokeLine(p.getX(), p.getZ(), this.vertices.getFirst().getX(), this.vertices.getFirst().getZ());
            }
        }
    }
    
    // FAZER O CÁLCULO DO CENTRO
    
}
