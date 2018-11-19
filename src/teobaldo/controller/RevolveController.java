package teobaldo.controller;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import teobaldo.model.Aresta;
import teobaldo.model.Face;
import teobaldo.model.Objeto;
import teobaldo.model.Vertice;

public class RevolveController implements Initializable {
    
    private Stage dialogStage;
    Canvas canvasXY, canvasYZ, canvasXZ, canvasPers;
    
    ObjetoController objControl;
    Face face;
    Objeto obj;
    
    ObservableList eixos;
    
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextField secoesTxtField, grausTxtField;

    @FXML
    private Button cancelButton, okButton;

    @FXML
    private ChoiceBox<String> eixoChoiceBox;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Setando controller de objetos
        objControl = new ObjetoController();
        
        // Setando lista de eixos no ChoiceBox
        eixoChoiceBox.setItems(FXCollections.observableArrayList("x", "y", "z"));
    }
    
    @FXML
    public void cancelButtonClicked() {
        dialogStage.close();
    }
    
    @FXML
    public void okButtonClicked() {
        int numSecoes = 0;
        double graus = 0;
        String eixo = " ";
        
        // Pegando as variáveis dos campos respectivos
        if(this.getSecoesTxtField() != null) {
            numSecoes = Integer.parseInt(this.getSecoesTxtField().getText());
        }
        graus = Integer.parseInt(this.getGrausTxtField().getText());
        eixo = this.getEixoChoiceBox().getValue();
        
        // Decidir qual é o respectivo objeto
        for(int i = 0; i < this.objControl.getObjetos().size(); i++) {
            if(this.objControl.getObjetos().get(i) == obj) {
                revolucionar(numSecoes, graus, eixo, this.objControl.getObjetos().get(i));
            }
        }
        
        draw3D();
    }
    
    public void draw3D() {
       if(this.obj.getObjeto3D() == true) {
            
            canvasXY.getGraphicsContext2D().setStroke(Color.GREEN);
            canvasYZ.getGraphicsContext2D().setStroke(Color.GREEN);
            canvasXZ.getGraphicsContext2D().setStroke(Color.GREEN);
            //canvasPers.getGraphicsContext2D().setStroke(Color.GREEN);
            
            this.obj.getArestas().forEach((aresta) -> {
                canvasXY.getGraphicsContext2D().strokeLine(aresta.getV1().getX(), -aresta.getV1().getY(), aresta.getV2().getX(), -aresta.getV2().getY());
                canvasYZ.getGraphicsContext2D().strokeLine(-aresta.getV1().getZ(), -aresta.getV1().getY(), -aresta.getV2().getZ(), -aresta.getV2().getY());
                canvasXZ.getGraphicsContext2D().strokeLine(aresta.getV1().getX(), aresta.getV1().getZ(), aresta.getV2().getX(), aresta.getV2().getZ());
                //canvasPers.getGraphicsContext2D().strokeLine(aresta.getV1().getX(), -aresta.getV1().getY(), aresta.getV2().getX(), -aresta.getV2().getY());
            });
        }
    }
    
    public void revolucionar(int numSecoes, double graus, String eixo, Objeto obj) {
        dialogStage.close();
        
        double angulo = (graus / numSecoes);
        
        int size = this.obj.getVertices().size();
        int sizeInicial = this.obj.getVertices().size();
        int incS = 0, auxFaces = 0, controlFaces = 0, aux360 = 0;
        int full360Control = 0;
        
        boolean firstA = true;
        
        for(int j = 0; j < numSecoes; j++) {
            // Contador responsável pelo quantidade de Vértices Padrão
            System.out.println(size);
            for(int i = incS; i < size; i++) {
                double x = 0;
                double y = 0;
                double z = 0;
                
                Face f = new Face();
                
                Vertice novoVertice = new Vertice(this.obj.getVertices().get(i).getX(),
                                                  this.obj.getVertices().get(i).getY(),
                                                  this.obj.getVertices().get(i).getZ());
                
                if(graus == 360){
                    if(j == (numSecoes-1)) {
                        //obj.addAresta(new Aresta(obj.getVertices().get(i), obj.getVertices().get(i-(incS))));
                        full360Control = 1;
                    }
                }else if(graus != 360 && j == 0){
                    if(i == 0 && obj.isLocked()){
                        Face first = new Face();
                        System.out.println("TESTAO QUE DROGA::::::::::::::::::::::::::::::::");
                        for(int k = 0; k < obj.getArestas().size(); k++){
                            first.addAresta(obj.getArestas().get(k));
                        }
                        obj.addFace(first);
                        auxFaces++;
                        controlFaces++;
                    }
                }
                
                
                // Controle de Sessão
                if(j == 0) // Primeira Sessão
                    f.addAresta(obj.getArestas().get(i));
                else {     // Próximas Sessões
                    
                    // Pega sempre as últimas arestas de cada face para fazer elas, as primeiras da próxima sessão.
                    f.addAresta(obj.getFaces().get(auxFaces).getArestas().getLast());
                    auxFaces++;
                }
                
                obj.addFace(f);
                
                //FALTA AJUSTAR PRA NAO REPETIR OS PONTOS
                if(full360Control == 1){
                    
                    novoVertice = obj.getVertices().get(aux360);

                    obj.addAresta(new Aresta(obj.getVertices().get(aux360), obj.getVertices().get(i+(sizeInicial))));
                    obj.getFaces().get(controlFaces).addAresta(obj.getArestas().getLast());
                    
                    if(firstA == true) {
                        firstA = false;
                    } else {
                        obj.getFaces().get(controlFaces-1).addAresta(obj.getArestas().getLast());
                        obj.addAresta(new Aresta(obj.getVertices().get(i+sizeInicial-1), obj.getVertices().get(i+sizeInicial)));
                        obj.getFaces().get(controlFaces-1).addAresta(obj.getArestas().getLast());
                        
                        if(obj.isLocked() && (i == (size-1))) {
                            obj.addAresta(new Aresta(obj.getVertices().get(i+sizeInicial), obj.getVertices().get(size)));
                            obj.getFaces().get(controlFaces).addAresta(obj.getFaces().get(controlFaces-(sizeInicial-1)).getArestas().get(1)); // A4
                            obj.getFaces().get(controlFaces).addAresta(obj.getArestas().getLast()); // A11
                        }
                    }
                    
                    aux360++;
                }
                
                if(full360Control != 1){
                    // Rotação Canônica
                    if("x".equals(eixo)) {
                        y = ((novoVertice.getY()*(Math.cos(Math.PI*angulo / 180))) - (novoVertice.getZ()*Math.sin(Math.PI*angulo/180)));
                        z = ((novoVertice.getY()*Math.sin(Math.PI*angulo/180)) + (novoVertice.getZ()*(Math.cos(Math.PI*angulo / 180))));
                        novoVertice.setY(y);
                        novoVertice.setZ(z);
                    }

                    if("y".equals(eixo)) {
                        x = ((novoVertice.getZ()*Math.sin(Math.PI*angulo/180)) + (novoVertice.getX()*(Math.cos(Math.PI*angulo / 180))));
                        z = ((novoVertice.getZ()*(Math.cos(Math.PI*angulo / 180))) - (novoVertice.getX()*Math.sin(Math.PI*angulo/180)));
                        novoVertice.setX(x);
                        novoVertice.setZ(z);
                    }

                    if("z".equals(eixo)) {
                        x = ((novoVertice.getX()*(Math.cos(Math.PI*angulo / 180))) - (novoVertice.getY()*Math.sin(Math.PI*angulo/180)));
                        y = ((novoVertice.getX()*Math.sin(Math.PI*angulo/180)) + (novoVertice.getY()*(Math.cos(Math.PI*angulo / 180))));
                        novoVertice.setX(x);
                        novoVertice.setY(y);
                    }

                    obj.addPonto(novoVertice);
                    obj.addAresta(new Aresta(obj.getVertices().get(i), obj.getVertices().get(i+sizeInicial)));
                    obj.getFaces().get(controlFaces).addAresta(obj.getArestas().getLast());
                    
                    if(firstA == true) {
                        firstA = false;
                    } else {
                        obj.getFaces().get(controlFaces-1).addAresta(obj.getArestas().getLast());
                        obj.addAresta(new Aresta(obj.getVertices().get(i+sizeInicial-1), obj.getVertices().get(i+sizeInicial)));
                        obj.getFaces().get(controlFaces-1).addAresta(obj.getArestas().getLast());
                        
                        if(obj.isLocked() && (i == (size-1))) {
                            obj.addAresta(new Aresta(obj.getVertices().get(i+sizeInicial), obj.getVertices().get(size)));
                            obj.getFaces().get(controlFaces).addAresta(obj.getFaces().get(controlFaces-(sizeInicial-1)).getArestas().get(1)); // A4
                            obj.getFaces().get(controlFaces).addAresta(obj.getArestas().getLast()); // A11
                        }
                    }
                }
                
                if(j == (numSecoes - 1) && graus != 360){
                    if(i == (size-1) && obj.isLocked()){
                        LinkedList<Aresta> arestasTemp1 = new LinkedList<>();
                        for(int k = obj.getFaces().size()-sizeInicial; k < obj.getFaces().size(); k++) {
                            arestasTemp1.add(obj.getFaces().get(k).getArestas().getLast());
                        }
                        obj.addFace(new Face(arestasTemp1));
                        auxFaces++;
                    }
                    
                }
                controlFaces++;
                
                //f.CalcCor(lampAmbiente, eixos, cam);
            }
            firstA = true;
            incS = size;
            size = obj.getVertices().size();
        }
        
        System.out.println("TESTE");
        for(int teste = 0; teste < obj.getFaces().size(); teste++){
            System.out.println(obj.getFaces().get(teste).getArestas());
            System.out.println("******************************");
        }
        System.out.println("FIM TESTE");
        obj.setObjeto3D(true);
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setObjeto(Objeto obj) {
        this.obj = obj;
    }
    
    public void setControlador(ObjetoController objControl, Objeto obj) {
        this.objControl = objControl;
        this.setObjeto(obj);
    }
    
    public void setCanvas(Canvas canvasXY, Canvas canvasYZ, Canvas canvasXZ, Canvas canvaPers) {
        this.canvasXY = canvasXY;
        this.canvasYZ = canvasYZ;
        this.canvasXZ = canvasXZ;
        this.canvasPers = canvasPers;
    }
    
    public Objeto getObjeto() {
        return obj;
    }
    
    public TextField getSecoesTxtField() {
        return secoesTxtField;
    }

    public TextField getGrausTxtField() {
        return grausTxtField;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public Button getOkButton() {
        return okButton;
    }

    public ChoiceBox<String> getEixoChoiceBox() {
        return eixoChoiceBox;
    }
}