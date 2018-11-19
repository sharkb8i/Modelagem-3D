package teobaldo.model;

/**
 *
 * @author Diedrich_
 */
public class Camera {
    public Vertice posicao;
    public Vertice pontoFocal;
    public Vertice normal;
    public Vertice n;

    public Camera(Vertice _pos, Vertice pontoFocal) {
        double norma;

        this.posicao = _pos;
        this.pontoFocal = pontoFocal;
        this.n = new Vertice((posicao.getX() - pontoFocal.getX()), 
                             (posicao.getY() - pontoFocal.getY()), 
                             (posicao.getZ() - pontoFocal.getZ()));
        norma = n.norma();
        normal = new Vertice((n.getX() / norma), (n.getY() / norma), (n.getZ() / norma));
    }

    public Vertice getNormal() {
        return this.normal;
    } 
}