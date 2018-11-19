package teobaldo.controller;

import java.io.IOException;
import teobaldo.model.Vertice;
import teobaldo.model.Objeto;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javax.swing.JColorChooser;
import teobaldo.model.Aresta;
import teobaldo.model.Lampada;
import java.awt.Color;

public class InterfaceController implements Initializable {
    GraphicsContext gcXY, gcYZ, gcXZ, gcPers;
    
    private ObjetoController objControl;
    private ArquivoController arqControl;
    // private CameraConfigsController cameraControl;
    
    private Objeto selectObj;
    private Lampada lamp;
    
    private int opcao, opcaoTrans; 
    private double controlMouseAxisX, controlMouseAxisY;
    
    private int indexObj, indexV;
    double desX, desY;
    
    boolean click;
    
    @FXML
    AnchorPane anchorPane;
    
    @FXML
    GridPane gridCanvas;
    
    @FXML
    private Canvas canvasXY, canvasYZ, canvasXZ, canvasPers;
    
    @FXML
    private Button selecionarButton, modelarButton;
    
    @FXML
    private ComboBox transformacoesComboBox;
    
    @FXML
    private RadioButton formaConfigRadioButton;
    
    @FXML
    private ImageView imgXY, imgYZ, imgXZ, imgPers;
    
    @FXML
    private TextField luzAmbienteR, luzAmbienteG, luzAmbienteB, fonteLuzX, fonteLuzY, fonteLuzZ, fonteLuzR, fonteLuzG, fonteLuzB;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Iniciando e setando o controlador de objetos
        this.objControl = new ObjetoController();
        
        // Iniciando e setando o controlador de arquivos
        this.arqControl = new ArquivoController();
        
        // Inicializando e Setando os TextFields
        luzAmbienteR = new TextField(Double.toString(this.objControl.getLuzAmbiente().getIr()));
        luzAmbienteG = new TextField(Double.toString(this.objControl.getLuzAmbiente().getIg()));
        luzAmbienteB = new TextField(Double.toString(this.objControl.getLuzAmbiente().getIb()));
        fonteLuzX = new TextField(Double.toString(this.objControl.getFonteLuz().getLocal().getX()));
        fonteLuzY = new TextField(Double.toString(this.objControl.getFonteLuz().getLocal().getY()));
        fonteLuzZ = new TextField(Double.toString(this.objControl.getFonteLuz().getLocal().getZ()));
        fonteLuzR = new TextField(Double.toString(this.objControl.getFonteLuz().getIr()));
        fonteLuzG = new TextField(Double.toString(this.objControl.getFonteLuz().getIg()));
        fonteLuzB = new TextField(Double.toString(this.objControl.getFonteLuz().getIb()));
        
        // Atualizando proporções
        desX = (canvasXY.getWidth() / 2);
        desY = (canvasXY.getHeight() / 2);
        
        // Ligando os GraphicsContext aos seus respectivos Canvas
        gcXY = this.canvasXY.getGraphicsContext2D();
        gcYZ = this.canvasYZ.getGraphicsContext2D();
        gcXZ = this.canvasXZ.getGraphicsContext2D();
        gcPers = this.canvasPers.getGraphicsContext2D();
        
        gcXY.translate(desX, desY);
        gcXZ.translate(desX, desY);
        gcYZ.translate(desX, desY);
        gcPers.translate(desX, desY);
        
        // Setando a cor da linha padrão para preto
        Color awtColor = new Color(0, 0, 0); // COR PRETA
        int r = awtColor.getRed();
        int g = awtColor.getGreen();
        int b = awtColor.getBlue();
        int a = awtColor.getAlpha();
        double opacity = a / 255.0;
        gcXY.setStroke(javafx.scene.paint.Color.rgb(r, g, b, opacity));
        gcXZ.setStroke(javafx.scene.paint.Color.rgb(r, g, b, opacity));
        gcYZ.setStroke(javafx.scene.paint.Color.rgb(r, g, b, opacity));
        gcPers.setStroke(javafx.scene.paint.Color.rgb(r, g, b, opacity));
        
        // Setando o controle de cliques
        click = true;
        
        // Setando o controle de objetos
        indexObj = 0;
        
        // Setando o controle de vertices para a lista de arestas
        indexV = 0;
        
        // Setando o menu do ComboBox de Transformações
        transformacoesComboBox.getItems().addAll("Transformações", "Translação", "Rotação", "Escala");
        
        // Setando as imagens dos planos
        imgXY.setVisible(true);
        imgYZ.setVisible(true);
        imgXZ.setVisible(true);
        imgPers.setVisible(true);
        
        // Se for ter GRID, fazer aqui!!!
        //drawGrid();
    }
    
    private void ArrumaAmbiente() {
        luzAmbienteR = new TextField(Double.toString(this.objControl.getLuzAmbiente().getIr()));
        luzAmbienteG = new TextField(Double.toString(this.objControl.getLuzAmbiente().getIg()));
        luzAmbienteB = new TextField(Double.toString(this.objControl.getLuzAmbiente().getIb()));
        fonteLuzX = new TextField(Double.toString(this.objControl.getFonteLuz().getLocal().getX()));
        fonteLuzY = new TextField(Double.toString(this.objControl.getFonteLuz().getLocal().getY()));
        fonteLuzZ = new TextField(Double.toString(this.objControl.getFonteLuz().getLocal().getZ()));
        fonteLuzR = new TextField(Double.toString(this.objControl.getFonteLuz().getIr()));
        fonteLuzG = new TextField(Double.toString(this.objControl.getFonteLuz().getIg()));
        fonteLuzB = new TextField(Double.toString(this.objControl.getFonteLuz().getIb()));
    }
    
    private void buttonCorObjto() {
        Color c = JColorChooser.showDialog(null, "Cor do Objeto", this.objControl.getColor(indexObj));
        if (c != null) {
            this.objControl.getObjetos().get(indexObj).setCor(c);
            drawCanvas();
        }
    }
    
    public void selOpcao(int op) {
        this.opcao = op;
    }
    
    @FXML
    public void newFile(ActionEvent e) {
        selOpcao(4);
        clearAll();
        for(int i = this.objControl.getObjetos().size()-1; i >= 0 ; i--){
            this.objControl.getObjetos().remove(i);
        }
        drawCanvas();
    }
    
    @FXML
    public void openFile() throws IOException {
        selOpcao(4);
        clearAll();
        this.arqControl.load(objControl);
        drawCanvas();
    }
    
    @FXML
    public void saveFile() throws IOException {
        this.arqControl.save(objControl.getObjetos());
    }
    
    @FXML
    public void close(ActionEvent e) {
        System.exit(0);
    }
    
    /*
    @FXML
    public void lightConfigsClicked(ActionEvent e) throws IOException {
        selOpcao(7);
        
        // Criando novo loader, pane para referência e setando local
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(LightConfigsController.class.getResource("/teobaldo/view/LightConfigs.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Configuração de Luz");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Setando o Controller.
        LightConfigsController lController = loader.getController();
        lController.setDialogStage(dialogStage);
        
        dialogStage.show();
    }
    */
    
    @FXML
    public void cameraConfigsClicked(ActionEvent e) throws IOException {
        
    }
    
    @FXML
    public void selecionarButtonClicked() {
        selOpcao(1);
        System.out.println(this.objControl.getObjetos());
        
        canvasXY.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent event) -> {
            this.canvasXY.setOnMouseClicked(this::selecionarObjeto2D);
        });
        canvasYZ.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent event) -> {
            this.canvasYZ.setOnMouseClicked(this::selecionarObjeto2D);
        });
        canvasXZ.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent event) -> {
            this.canvasXZ.setOnMouseClicked(this::selecionarObjeto2D);
        });
        
        canvasXY.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent event) -> {
            System.out.println("Canvas 1 Exited");
        });
        canvasYZ.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent event) -> {
            System.out.println("Canvas 2 Exited");
        });
        canvasXZ.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent event) -> {
            System.out.println("Canvas 3 Exited");
        });
    }
    
    private void selecionarObjeto2D(MouseEvent e) {
        if(opcao == 1) {
            this.selectObj = new Objeto();
                    
            if(e.getSource() == canvasXY) {
                this.selectObj = this.objControl.selecaoXY(new Vertice((e.getX() - desX), (desY - e.getY())));
            }
            
            if(e.getSource() == canvasYZ) {
                this.selectObj = this.objControl.selecaoYZ(new Vertice((desX - e.getX()), (desY - e.getY())));
            }
            
            if(e.getSource() == canvasXZ) {
                this.selectObj = this.objControl.selecaoXZ(new Vertice((e.getX() - desX), (e.getY() - desY)));
            }
            
            indexObj = objControl.getObjetos().indexOf(selectObj);
            
            gcXY.clearRect(-(canvasXY.getWidth()), -(canvasXY.getHeight()), canvasXY.getWidth() + desX, canvasXY.getHeight() + desY);
            gcXZ.clearRect(-(canvasXY.getWidth()), -(canvasXY.getHeight()), canvasXY.getWidth() + desX, canvasXY.getHeight() + desY);
            gcYZ.clearRect(-(canvasXY.getWidth()), -(canvasXY.getHeight()), canvasXY.getWidth() + desX, canvasXY.getHeight() + desY);
            gcPers.clearRect(-(canvasXY.getWidth()), -(canvasXY.getHeight()), canvasXY.getWidth() + desX, canvasXY.getHeight() + desY);

            drawCanvas();
        }
    }
    
    public void drawCanvas() {
        if (selectObj == null) {
            for (Objeto objeto : objControl.getObjetos()) {
                objeto.drawXY(gcXY);
                objeto.drawYZ(gcYZ);
                objeto.drawXZ(gcXZ);
            }
        } else {
            for (int i = 0; i < objControl.getObjetos().size(); i++) {
                if (i != indexObj) {
                    objControl.getObjetos().get(i).drawXY(gcXY);
                    objControl.getObjetos().get(i).drawYZ(gcYZ);
                    objControl.getObjetos().get(i).drawXZ(gcXZ);
                } else {
                    selectObj.drawSelectXY(gcXY);
                    selectObj.drawSelectXZ(gcXZ);
                    selectObj.drawSelectYZ(gcYZ);
                }
            }
        }
    }
    
    public void drawGrid() {
        Color awtColor = new Color(255, 0, 0); // COR VERMELHA
        int r = awtColor.getRed();
        int g = awtColor.getGreen();
        int b = awtColor.getBlue();
        int a = awtColor.getAlpha();
        double opacity = a / 255.0;
        gcXY.setStroke(javafx.scene.paint.Color.rgb(r, g, b, opacity));
        gcXY.strokeLine(desX, desY, canvasXY.getWidth(), canvasXY.getHeight());
    }
    
    @FXML
    public void modelarButtonClicked() {
        selOpcao(3);
        this.objControl.startLine();
        
        this.canvasXY.addEventFilter(MouseEvent.MOUSE_ENTERED, (MouseEvent event) -> {
            System.out.println("Canvas 1 Entered");
            objControl.setCanvas(this.canvasXY, this.canvasYZ, this.canvasXZ, this.canvasPers);
            
            this.canvasXY.setOnMouseClicked((e) -> {
                if (opcao == 3) {
                    if (e.getButton() == MouseButton.PRIMARY)
                        if(click == true) {
                            this.objControl.addPonto(new Vertice((e.getX() - desX), (desY - e.getY()), 0));
                            this.click = false;
                        } else {
                            this.objControl.addPonto(new Vertice((e.getX() - desX), (desY - e.getY()), 0));
                            this.objControl.addAresta(new Aresta(this.objControl.getPontos().get(indexV), this.objControl.getPontos().get(indexV+1)));
                            indexV++;
                        }
                    
                    if (e.getButton() == MouseButton.SECONDARY) {
                        if(formaConfigRadioButton.isSelected()) { // Se for verdade, fecha a forma
                            System.out.println("Forma Fechada! - Canvas 1");
                            this.objControl.addAresta(new Aresta(this.objControl.getPontos().get(indexV), this.objControl.getPontos().getFirst()));
                            this.objControl.locked = true;
                        }
                        // Se não, forma aberta
                        this.objControl.endLine();
                        this.click = true;
                        indexV = 0;
                        this.objControl.startLine();
                    }
                }
            });
        });
        canvasYZ.addEventFilter(MouseEvent.MOUSE_ENTERED, (MouseEvent event) -> {
            System.out.println("Canvas 2 Entered");
            this.objControl.setCanvas(this.canvasYZ, this.canvasXY, this.canvasXZ, this.canvasPers);
            
            this.canvasYZ.setOnMouseClicked(e -> {
                if (opcao == 3) {
                    if (e.getButton() == MouseButton.PRIMARY)
                        if(click == true) {
                            this.objControl.addPonto(new Vertice(0, (desY - e.getY()), (desX - e.getX())));
                            this.click = false;
                        } else {
                            this.objControl.addPonto(new Vertice(0, (desY - e.getY()), (desX - e.getX())));
                            this.objControl.addAresta(new Aresta(this.objControl.getPontos().get(indexV), this.objControl.getPontos().get(indexV+1)));
                            indexV++;
                        }

                    if (e.getButton() == MouseButton.SECONDARY) {
                        if(formaConfigRadioButton.isSelected()) { // Se for verdade, fecha a forma
                            System.out.println("Forma Fechada! - Canvas 2");
                            this.objControl.addAresta(new Aresta(this.objControl.getPontos().get(indexV), this.objControl.getPontos().getFirst()));
                            this.objControl.locked = true;
                        }
                        // Se não, forma aberta
                        this.objControl.endLine();
                        this.click = true;
                        indexV = 0;
                        this.objControl.startLine();
                    }
                }
            });
        });
        canvasXZ.addEventFilter(MouseEvent.MOUSE_ENTERED, (MouseEvent event) -> {
            System.out.println("Canvas 3 Entered");
            this.objControl.setCanvas(this.canvasXZ, this.canvasYZ, this.canvasXY, this.canvasPers);
            
            this.canvasXZ.setOnMouseClicked(e -> {
                if (opcao == 3) {
                    if (e.getButton() == MouseButton.PRIMARY)
                        if(click == true) {
                            this.objControl.addPonto(new Vertice((e.getX() - desX), 0, (e.getY() - desY)));
                            this.click = false;
                        } else {
                            this.objControl.addPonto(new Vertice((e.getX() - desX), 0, (e.getY() - desY)));
                            this.objControl.addAresta(new Aresta(this.objControl.getPontos().get(indexV), this.objControl.getPontos().get(indexV+1)));
                            indexV++;
                        }

                    if (e.getButton() == MouseButton.SECONDARY) {
                        if(formaConfigRadioButton.isSelected()) { // Se for verdade, fecha a forma
                            System.out.println("Forma Fechada! - Canvas 2");
                            this.objControl.addAresta(new Aresta(this.objControl.getPontos().get(indexV), this.objControl.getPontos().getFirst()));
                            this.objControl.locked = true;
                        }
                        // Se não, forma aberta
                        this.objControl.endLine();
                        this.click = true;
                        indexV = 0;
                        this.objControl.startLine();
                    }
                }
            });
        });
        this.canvasXY.setOnMouseClicked(null);
        this.canvasYZ.setOnMouseClicked(null);
        this.canvasXZ.setOnMouseClicked(null);
    }
    
    @FXML
    public void limparButtonClicked() {
        selOpcao(4);
        clearAll();
    }
    
    public void clearAll() {
        gcXY.clearRect(-(canvasXY.getWidth()), -(canvasXY.getHeight()), canvasXY.getWidth() + desX, canvasXY.getHeight() + desY);
        gcXZ.clearRect(-(canvasXY.getWidth()), -(canvasXY.getHeight()), canvasXY.getWidth() + desX, canvasXY.getHeight() + desY);
        gcYZ.clearRect(-(canvasXY.getWidth()), -(canvasXY.getHeight()), canvasXY.getWidth() + desX, canvasXY.getHeight() + desY);
        gcPers.clearRect(-(canvasXY.getWidth()), -(canvasXY.getHeight()), canvasXY.getWidth() + desX, canvasXY.getHeight() + desY);

        if(opcao == 4) {
            this.objControl.setObj(new Objeto());
            this.objControl.getObjetos().clear();
        }
    }
    
    @FXML
    public void transformacoes(ActionEvent e) {
        selOpcao(5);
        if(opcao == 5) {
            if((this.selectObj != null) && (this.selectObj.getObjeto3D() == true)) {
                canvasXY.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent event) -> {
                    if(transformacoesComboBox.getValue() == "Transformações") {
                        this.selectObj = null;
                    }
                    if(transformacoesComboBox.getValue() == "Translação") {
                        this.canvasXY.setOnMousePressed(this::transladar);
                        this.canvasXY.setOnMouseClicked(c -> {
                            opcaoTrans = 1;
                            controlMouseAxisX = event.getX();
                            controlMouseAxisY = event.getY();
                        });
                    }
                    if(transformacoesComboBox.getValue() == "Rotação") {
                        
                        this.canvasXY.setOnMousePressed(this::rotacionar);
                        this.canvasXY.setOnMouseClicked(c -> {
                            opcaoTrans = 2;
                            controlMouseAxisX = event.getX();
                            controlMouseAxisY = event.getY();
                        });
                        
                        //Modelo Rotacao 2
                        /*controlRotacao = 0;
                        
                        this.canvasXY.setOnMouseClicked(c -> {
                            opcaoTrans = 2;
                            if((desY - c.getY() < 0) && (c.getX() - desX > 0)){
                                //Rotaciona no eixo X Sentido: Horario
                                controlRotacao = 1;
                            }
                            if((desY - c.getY() < 0) && (c.getX() - desX < 0)){
                                //Rotaciona no eixo X Sentido: Anti-Horario
                                controlRotacao = 2;
                            }
                            if((desY - c.getY() > 0) && (c.getX() - desX > 0)){
                                //Rotaciona no eixo Y Sentido: Horario
                                controlRotacao = 3;
                            }
                            if((desY - c.getY() > 0) && (c.getX() - desX < 0)){
                                //Rotaciona no eixo Y Sentido: Anti-Horario
                                controlRotacao = 4;
                            }
                        });*/
                    }
                    if(transformacoesComboBox.getValue() == "Escala") {
                        this.canvasXY.setOnMousePressed(this::escalar);
                        this.canvasXY.setOnMouseClicked(c -> {
                            opcaoTrans = 3;
                            controlMouseAxisX = event.getX();
                        });
                    }
                });
                
                canvasYZ.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent event) -> {
                    if(transformacoesComboBox.getValue() == "Translação") {
                        this.canvasYZ.setOnMousePressed(this::transladar);
                        this.canvasYZ.setOnMouseClicked(c -> {
                            opcaoTrans = 1;
                            controlMouseAxisX = event.getX();
                            controlMouseAxisY = event.getY();
                        });
                    }
                    if(transformacoesComboBox.getValue() == "Rotação") {
                        this.canvasYZ.setOnMousePressed(this::rotacionar);
                        this.canvasYZ.setOnMouseClicked(c -> {
                            opcaoTrans = 2;
                            controlMouseAxisX = event.getX();
                            controlMouseAxisY = event.getY();
                        });
                        
                        //Modelo de Rotacao 2
                        /*
                        controlRotacao = 0;
                        
                        this.canvasYZ.setOnMouseClicked(c -> {
                            opcaoTrans = 2;
                            if((desY - c.getY() < 0) && (c.getX() - desX > 0)){
                                //Rotaciona no eixo X Sentido: Horario
                                controlRotacao = 1;
                            }
                            if((desY - c.getY() < 0) && (c.getX() - desX < 0)){
                                //Rotaciona no eixo X Sentido: Anti-Horario
                                controlRotacao = 2;
                            }
                            if((desY - c.getY() > 0) && (c.getX() - desX > 0)){
                                //Rotaciona no eixo Y Sentido: Horario
                                controlRotacao = 3;
                            }
                            if((desY - c.getY() > 0) && (c.getX() - desX < 0)){
                                //Rotaciona no eixo Y Sentido: Anti-Horario
                                controlRotacao = 4;
                            }
                        });*/
                    }
                    if(transformacoesComboBox.getValue() == "Escala") {
                        this.canvasYZ.setOnMousePressed(this::escalar);
                        this.canvasYZ.setOnMouseClicked(c -> {
                            opcaoTrans = 3;
                            controlMouseAxisX = event.getX();
                        });
                    }
                });
                canvasXZ.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent event) -> {
                    if(transformacoesComboBox.getValue() == "Translação") {
                        this.canvasXZ.setOnMousePressed(this::transladar);
                        this.canvasXZ.setOnMouseClicked(c -> {
                            opcaoTrans = 1;
                            controlMouseAxisX = event.getX();
                            controlMouseAxisY = event.getY();
                        });
                    }
                    if(transformacoesComboBox.getValue() == "Rotação") {
                        this.canvasXZ.setOnMousePressed(this::rotacionar);
                        this.canvasXZ.setOnMouseClicked(c -> {
                            opcaoTrans = 2;
                            controlMouseAxisX = event.getX();
                            controlMouseAxisY = event.getY();
                        });
                        
                        //Modelo de Rotação 2
                        /*
                        controlRotacao = 0;
                        
                        this.canvasXZ.setOnMouseClicked(c -> {
                            opcaoTrans = 2;
                            if((desY - c.getY() < 0) && (c.getX() - desX > 0)){
                                //Rotaciona no eixo X Sentido: Horario
                                controlRotacao = 1;
                            }
                            if((desY - c.getY() < 0) && (c.getX() - desX < 0)){
                                //Rotaciona no eixo X Sentido: Anti-Horario
                                controlRotacao = 2;
                            }
                            if((desY - c.getY() > 0) && (c.getX() - desX > 0)){
                                //Rotaciona no eixo Y Sentido: Horario
                                controlRotacao = 3;
                            }
                            if((desY - c.getY() > 0) && (c.getX() - desX < 0)){
                                //Rotaciona no eixo Y Sentido: Anti-Horario
                                controlRotacao = 4;
                            }
                        });*/
                    }
                    if(transformacoesComboBox.getValue() == "Escala") {
                       this.canvasXZ.setOnMousePressed(this::escalar);
                        this.canvasXZ.setOnMouseClicked(c -> {
                            opcaoTrans = 3;
                            controlMouseAxisX = event.getX();
                        });
                    }
                });
            } else {
                System.out.println("Objeto não foi selecionado!");
            }
        }
    }
    
    private void transladar(MouseEvent e) {
        if(e.getSource() == canvasXY && opcaoTrans == 1) {
            System.out.println("CANVAS X Y");
            
            this.canvasXY.setOnMouseDragged(event -> {
                if(controlMouseAxisX < event.getX()){
                    for (int i = 0; i < this.selectObj.getVertices().size(); i++) {
                        double x = this.selectObj.getVertices().get(i).getX() + 1;
                        this.selectObj.getVertices().get(i).setX(x);
                        controlMouseAxisX = event.getX();
                    }
                }
                clearAll();
                drawCanvas();
                if(controlMouseAxisX > event.getX()){
                    for (int i = 0; i < this.selectObj.getVertices().size(); i++) {
                        double x = this.selectObj.getVertices().get(i).getX() - 1;
                        this.selectObj.getVertices().get(i).setX(x);
                        controlMouseAxisX = event.getX();
                    }
                }
                if(controlMouseAxisY < event.getY()){
                    for (int i = 0; i < this.selectObj.getVertices().size(); i++) {
                        double y = this.selectObj.getVertices().get(i).getY() - 1;
                        this.selectObj.getVertices().get(i).setY(y);
                        controlMouseAxisY = event.getY();
                    }
                }
                clearAll();
                drawCanvas();
                if(controlMouseAxisY > event.getY()){
                    for (int i = 0; i < this.selectObj.getVertices().size(); i++) {
                        double y = this.selectObj.getVertices().get(i).getY() + 1;
                        this.selectObj.getVertices().get(i).setY(y);
                        controlMouseAxisY = event.getY();
                    }
                }
                clearAll();
                drawCanvas();
            });
        }
        if(e.getSource() == canvasYZ && opcaoTrans == 1) {
            System.out.println("CANVAS Y Z");
            
            this.canvasYZ.setOnMouseDragged(event -> {
                if(controlMouseAxisX < event.getX()){
                    for (int i = 0; i < this.selectObj.getVertices().size(); i++) {
                        double z = this.selectObj.getVertices().get(i).getZ() - 1;
                        this.selectObj.getVertices().get(i).setZ(z);
                        controlMouseAxisX = event.getX();
                    }
                }
                clearAll();
                drawCanvas();
                if(controlMouseAxisX > event.getX()){
                    for (int i = 0; i < this.selectObj.getVertices().size(); i++) {
                        double z = this.selectObj.getVertices().get(i).getZ() + 1;
                        this.selectObj.getVertices().get(i).setZ(z);
                        controlMouseAxisX = event.getX();
                    }
                }
                if(controlMouseAxisY < event.getY()){
                    for (int i = 0; i < this.selectObj.getVertices().size(); i++) {
                        double y = this.selectObj.getVertices().get(i).getY() - 1;
                        this.selectObj.getVertices().get(i).setY(y);
                        controlMouseAxisY = event.getY();
                    }
                }
                clearAll();
                drawCanvas();
                if(controlMouseAxisY > event.getY()){
                    for (int i = 0; i < this.selectObj.getVertices().size(); i++) {
                        double y = this.selectObj.getVertices().get(i).getY() + 1;
                        this.selectObj.getVertices().get(i).setY(y);
                        controlMouseAxisY = event.getY();
                    }
                }
                clearAll();
                drawCanvas();
            });
        }
        if(e.getSource() == canvasXZ) {
            System.out.println("CANVAS X Z");
            
            this.canvasXZ.setOnMouseDragged(event -> {
                if(controlMouseAxisX < event.getX()){
                    for (int i = 0; i < this.selectObj.getVertices().size(); i++) {
                        double x = this.selectObj.getVertices().get(i).getX() + 1;
                        this.selectObj.getVertices().get(i).setX(x);
                        controlMouseAxisX = event.getX();
                    }
                }
                clearAll();
                drawCanvas();
                if(controlMouseAxisX > event.getX()){
                    for (int i = 0; i < this.selectObj.getVertices().size(); i++) {
                        double x = this.selectObj.getVertices().get(i).getX() - 1;
                        this.selectObj.getVertices().get(i).setX(x);
                        controlMouseAxisX = event.getX();
                    }
                }
                if(controlMouseAxisY < event.getY()){
                    for (int i = 0; i < this.selectObj.getVertices().size(); i++) {
                        double z = this.selectObj.getVertices().get(i).getZ() + 1;
                        this.selectObj.getVertices().get(i).setZ(z);
                        controlMouseAxisY = event.getY();
                    }
                }
                clearAll();
                drawCanvas();
                if(controlMouseAxisY > event.getY()){
                    for (int i = 0; i < this.selectObj.getVertices().size(); i++) {
                        double z = this.selectObj.getVertices().get(i).getZ() - 1;
                        this.selectObj.getVertices().get(i).setZ(z);
                        controlMouseAxisY = event.getY();
                    }
                }
                clearAll();
                drawCanvas();
            });
        }
    }
    
    private void rotacionar(MouseEvent e) {
        if(e.getSource() == canvasXY && opcaoTrans == 2) {
            System.out.println("CANVAS X Y");
            
            this.canvasXY.setOnMouseDragged(event -> {
                if(controlMouseAxisX < event.getX()){
                    for (int i = 0; i < this.selectObj.getVertices().size(); i++) {
                       double x = ((this.selectObj.getVertices().get(i).getZ()*(Math.sin(Math.PI*1 / 180))) + (this.selectObj.getVertices().get(i).getX()*Math.cos(Math.PI*1/180)));
                       double z = ((this.selectObj.getVertices().get(i).getZ()*Math.cos(Math.PI*1/180)) - (this.selectObj.getVertices().get(i).getX()*(Math.sin(Math.PI*1 / 180))));
                        
                       this.selectObj.getVertices().get(i).setX(x);
                       this.selectObj.getVertices().get(i).setZ(z);
                       controlMouseAxisX = event.getX();
                    }
                }
                clearAll();
                drawCanvas();
                if(controlMouseAxisX > event.getX()){
                    for (int i = 0; i < this.selectObj.getVertices().size(); i++) {
                        double x = ((this.selectObj.getVertices().get(i).getZ()*(Math.sin(Math.PI*(-1) / 180))) + (this.selectObj.getVertices().get(i).getX()*Math.cos(Math.PI*(-1)/180)));
                        double z = ((this.selectObj.getVertices().get(i).getZ()*Math.cos(Math.PI*(-1)/180)) - (this.selectObj.getVertices().get(i).getX()*(Math.sin(Math.PI*(-1) / 180))));
                        
                        this.selectObj.getVertices().get(i).setX(x);
                        this.selectObj.getVertices().get(i).setZ(z);
                        controlMouseAxisX = event.getX();
                    }
                }
                if(controlMouseAxisY < event.getY()){
                    for (int i = 0; i < this.selectObj.getVertices().size(); i++) {
                       double y = ((this.selectObj.getVertices().get(i).getY()*(Math.cos(Math.PI*1 / 180))) - (this.selectObj.getVertices().get(i).getZ()*Math.sin(Math.PI*1/180)));
                       double z = ((this.selectObj.getVertices().get(i).getY()*Math.sin(Math.PI*1/180)) + (this.selectObj.getVertices().get(i).getZ()*(Math.cos(Math.PI*1 / 180))));
                        
                       this.selectObj.getVertices().get(i).setY(y);
                       this.selectObj.getVertices().get(i).setZ(z);
                       controlMouseAxisY = event.getY();
                    }
                }
                clearAll();
                drawCanvas();
                if(controlMouseAxisY > event.getY()){
                    for (int i = 0; i < this.selectObj.getVertices().size(); i++) {
                        double y = ((this.selectObj.getVertices().get(i).getY()*(Math.cos(Math.PI*(-1) / 180))) - (this.selectObj.getVertices().get(i).getZ()*Math.sin(Math.PI*(-1)/180)));
                        double z = ((this.selectObj.getVertices().get(i).getY()*Math.sin(Math.PI*(-1)/180)) + (this.selectObj.getVertices().get(i).getZ()*(Math.cos(Math.PI*(-1) / 180))));
                        
                        this.selectObj.getVertices().get(i).setY(y);
                        this.selectObj.getVertices().get(i).setZ(z);
                        controlMouseAxisY = event.getY();
                    }
                }
                clearAll();
                drawCanvas();
            });
            //Modelo de rotacao 2
            /*this.canvasXY.setOnMouseDragged(event -> {
                
                for (int i = 0; i < this.selectObj.getVertices().size(); i++) {
                    System.out.println(this.selectObj.getVertices().get(i).getX());
                    System.out.println(event.getX());
                    if(controlRotacao == 1){
                        
                        double y = ((this.selectObj.getVertices().get(i).getY()*(Math.cos(Math.PI*1 / 180))) - (this.selectObj.getVertices().get(i).getZ()*Math.sin(Math.PI*1/180)));
                        double z = ((this.selectObj.getVertices().get(i).getY()*Math.sin(Math.PI*1/180)) + (this.selectObj.getVertices().get(i).getZ()*(Math.cos(Math.PI*1 / 180))));
                        
                        this.selectObj.getVertices().get(i).setY(y);
                        this.selectObj.getVertices().get(i).setZ(z);
                    }
                    if(controlRotacao == 2){
                        
                        double y = ((this.selectObj.getVertices().get(i).getY()*(Math.cos(Math.PI*(-1) / 180))) - (this.selectObj.getVertices().get(i).getZ()*Math.sin(Math.PI*(-1)/180)));
                        double z = ((this.selectObj.getVertices().get(i).getY()*Math.sin(Math.PI*(-1)/180)) + (this.selectObj.getVertices().get(i).getZ()*(Math.cos(Math.PI*(-1) / 180))));
                        
                        this.selectObj.getVertices().get(i).setY(y);
                        this.selectObj.getVertices().get(i).setZ(z);
                    }
                    if(controlRotacao == 3){
                        
                       double x = ((this.selectObj.getVertices().get(i).getZ()*(Math.sin(Math.PI*1 / 180))) + (this.selectObj.getVertices().get(i).getX()*Math.cos(Math.PI*1/180)));
                       double z = ((this.selectObj.getVertices().get(i).getZ()*Math.cos(Math.PI*1/180)) - (this.selectObj.getVertices().get(i).getX()*(Math.sin(Math.PI*1 / 180))));
                        
                       this.selectObj.getVertices().get(i).setX(x);
                       this.selectObj.getVertices().get(i).setZ(z);
                    }
                    if(controlRotacao == 4){
                        
                        double x = ((this.selectObj.getVertices().get(i).getZ()*(Math.sin(Math.PI*(-1) / 180))) + (this.selectObj.getVertices().get(i).getX()*Math.cos(Math.PI*(-1)/180)));
                        double z = ((this.selectObj.getVertices().get(i).getZ()*Math.cos(Math.PI*(-1)/180)) - (this.selectObj.getVertices().get(i).getX()*(Math.sin(Math.PI*(-1) / 180))));
                        
                        this.selectObj.getVertices().get(i).setX(x);
                        this.selectObj.getVertices().get(i).setZ(z);
                    }
                }
                clearAll();
                drawCanvas();
            });*/
        }
        if(e.getSource() == canvasYZ && opcaoTrans == 2) {
            System.out.println("CANVAS Y Z");
            
            this.canvasYZ.setOnMouseDragged(event -> {
                if(controlMouseAxisX < event.getX()){
                    for (int i = 0; i < this.selectObj.getVertices().size(); i++) {
                       double x = ((this.selectObj.getVertices().get(i).getZ()*(Math.sin(Math.PI*1 / 180))) + (this.selectObj.getVertices().get(i).getX()*Math.cos(Math.PI*1/180)));
                       double z = ((this.selectObj.getVertices().get(i).getZ()*Math.cos(Math.PI*1/180)) - (this.selectObj.getVertices().get(i).getX()*(Math.sin(Math.PI*1 / 180))));
                        
                       this.selectObj.getVertices().get(i).setX(x);
                       this.selectObj.getVertices().get(i).setZ(z);
                       controlMouseAxisX = event.getX();
                    }
                }
                clearAll();
                drawCanvas();
                if(controlMouseAxisX > event.getX()){
                    for (int i = 0; i < this.selectObj.getVertices().size(); i++) {
                        double x = ((this.selectObj.getVertices().get(i).getZ()*(Math.sin(Math.PI*(-1) / 180))) + (this.selectObj.getVertices().get(i).getX()*Math.cos(Math.PI*(-1)/180)));
                        double z = ((this.selectObj.getVertices().get(i).getZ()*Math.cos(Math.PI*(-1)/180)) - (this.selectObj.getVertices().get(i).getX()*(Math.sin(Math.PI*(-1) / 180))));
                        
                        this.selectObj.getVertices().get(i).setX(x);
                        this.selectObj.getVertices().get(i).setZ(z);
                        controlMouseAxisX = event.getX();
                    }
                }
                if(controlMouseAxisY < event.getY()){
                    for (int i = 0; i < this.selectObj.getVertices().size(); i++) {
                       double x = ((this.selectObj.getVertices().get(i).getX()*(Math.cos(Math.PI*1 / 180))) - (this.selectObj.getVertices().get(i).getY()*Math.sin(Math.PI*1/180)));
                       double y = ((this.selectObj.getVertices().get(i).getX()*Math.sin(Math.PI*1/180)) + (this.selectObj.getVertices().get(i).getY()*(Math.cos(Math.PI*1 / 180))));
                        
                       this.selectObj.getVertices().get(i).setX(x);
                       this.selectObj.getVertices().get(i).setY(y);
                       controlMouseAxisY = event.getY();
                    }
                }
                clearAll();
                drawCanvas();
                if(controlMouseAxisY > event.getY()){
                    for (int i = 0; i < this.selectObj.getVertices().size(); i++) {
                       double x = ((this.selectObj.getVertices().get(i).getX()*(Math.cos(Math.PI*(-1) / 180))) - (this.selectObj.getVertices().get(i).getY()*Math.sin(Math.PI*(-1)/180)));
                        double y = ((this.selectObj.getVertices().get(i).getX()*Math.sin(Math.PI*(-1)/180)) + (this.selectObj.getVertices().get(i).getY()*(Math.cos(Math.PI*(-1) / 180))));
                        
                        this.selectObj.getVertices().get(i).setX(x);
                        this.selectObj.getVertices().get(i).setY(y);
                        controlMouseAxisY = event.getY();
                    }
                }
                clearAll();
                drawCanvas();
            });
            
            //Modelo de Rotacao 2
            /*
            this.canvasYZ.setOnMouseDragged(event -> {
                
                for (int i = 0; i < this.selectObj.getVertices().size(); i++) {
                    System.out.println(this.selectObj.getVertices().get(i).getX());
                    
                    if(controlRotacao == 1){
                        
                        double x = ((this.selectObj.getVertices().get(i).getX()*(Math.cos(Math.PI*1 / 180))) - (this.selectObj.getVertices().get(i).getY()*Math.sin(Math.PI*1/180)));
                        double y = ((this.selectObj.getVertices().get(i).getX()*Math.sin(Math.PI*1/180)) + (this.selectObj.getVertices().get(i).getY()*(Math.cos(Math.PI*1 / 180))));
                        
                        this.selectObj.getVertices().get(i).setX(x);
                        this.selectObj.getVertices().get(i).setY(y);
                    }
                    if(controlRotacao == 2){
                        
                        double x = ((this.selectObj.getVertices().get(i).getX()*(Math.cos(Math.PI*(-1) / 180))) - (this.selectObj.getVertices().get(i).getY()*Math.sin(Math.PI*(-1)/180)));
                        double y = ((this.selectObj.getVertices().get(i).getX()*Math.sin(Math.PI*(-1)/180)) + (this.selectObj.getVertices().get(i).getY()*(Math.cos(Math.PI*(-1) / 180))));
                        
                        this.selectObj.getVertices().get(i).setX(x);
                        this.selectObj.getVertices().get(i).setY(y);
                    }
                    if(controlRotacao == 3){
                        
                       double x = ((this.selectObj.getVertices().get(i).getZ()*(Math.sin(Math.PI*1 / 180))) + (this.selectObj.getVertices().get(i).getX()*Math.cos(Math.PI*1/180)));
                       double z = ((this.selectObj.getVertices().get(i).getZ()*Math.cos(Math.PI*1/180)) - (this.selectObj.getVertices().get(i).getX()*(Math.sin(Math.PI*1 / 180))));
                        
                       this.selectObj.getVertices().get(i).setX(x);
                       this.selectObj.getVertices().get(i).setZ(z);
                    }
                    if(controlRotacao == 4){
                        
                        double x = ((this.selectObj.getVertices().get(i).getZ()*(Math.sin(Math.PI*(-1) / 180))) + (this.selectObj.getVertices().get(i).getX()*Math.cos(Math.PI*(-1)/180)));
                        double z = ((this.selectObj.getVertices().get(i).getZ()*Math.cos(Math.PI*(-1)/180)) - (this.selectObj.getVertices().get(i).getX()*(Math.sin(Math.PI*(-1) / 180))));
                        
                        this.selectObj.getVertices().get(i).setX(x);
                        this.selectObj.getVertices().get(i).setZ(z);
                    }
                }
                clearAll();
                drawCanvas();
            });*/
        }

        if(e.getSource() == canvasXZ && opcaoTrans == 2) {
            //this.selectObj = this.objControl.selecaoXZ(new Vertice((e.getX() - desX), (e.getY() - desY)));
            System.out.println("CANVAS X Z");
            
            this.canvasXZ.setOnMouseDragged(event -> {
                if(controlMouseAxisX < event.getX()){
                    for (int i = 0; i < this.selectObj.getVertices().size(); i++) {
                       double x = ((this.selectObj.getVertices().get(i).getX()*(Math.cos(Math.PI*(-1) / 180))) - (this.selectObj.getVertices().get(i).getY()*Math.sin(Math.PI*(-1)/180)));
                       double y = ((this.selectObj.getVertices().get(i).getX()*Math.sin(Math.PI*(-1)/180)) + (this.selectObj.getVertices().get(i).getY()*(Math.cos(Math.PI*(-1) / 180))));
                        
                       this.selectObj.getVertices().get(i).setX(x);
                       this.selectObj.getVertices().get(i).setY(y);
                       controlMouseAxisX = event.getX();
                    }
                }
                clearAll();
                drawCanvas();
                if(controlMouseAxisX > event.getX()){
                    for (int i = 0; i < this.selectObj.getVertices().size(); i++) {
                       double x = ((this.selectObj.getVertices().get(i).getX()*(Math.cos(Math.PI*1 / 180))) - (this.selectObj.getVertices().get(i).getY()*Math.sin(Math.PI*1/180)));
                       double y = ((this.selectObj.getVertices().get(i).getX()*Math.sin(Math.PI*1/180)) + (this.selectObj.getVertices().get(i).getY()*(Math.cos(Math.PI*1 / 180))));
                        
                       this.selectObj.getVertices().get(i).setX(x);
                       this.selectObj.getVertices().get(i).setY(y);
                       controlMouseAxisX = event.getX();
                    }
                }
                if(controlMouseAxisY < event.getY()){
                    for (int i = 0; i < this.selectObj.getVertices().size(); i++) {
                       double y = ((this.selectObj.getVertices().get(i).getY()*(Math.cos(Math.PI*(-1) / 180))) - (this.selectObj.getVertices().get(i).getZ()*Math.sin(Math.PI*(-1)/180)));
                       double z = ((this.selectObj.getVertices().get(i).getY()*Math.sin(Math.PI*(-1)/180)) + (this.selectObj.getVertices().get(i).getZ()*(Math.cos(Math.PI*(-1) / 180))));
                        
                       this.selectObj.getVertices().get(i).setY(y);
                       this.selectObj.getVertices().get(i).setZ(z);
                       controlMouseAxisY = event.getY();
                    }
                }
                clearAll();
                drawCanvas();
                if(controlMouseAxisY > event.getY()){
                    for (int i = 0; i < this.selectObj.getVertices().size(); i++) {
                       double y = ((this.selectObj.getVertices().get(i).getY()*(Math.cos(Math.PI*1 / 180))) - (this.selectObj.getVertices().get(i).getZ()*Math.sin(Math.PI*1/180)));
                       double z = ((this.selectObj.getVertices().get(i).getY()*Math.sin(Math.PI*1/180)) + (this.selectObj.getVertices().get(i).getZ()*(Math.cos(Math.PI*1 / 180))));
                        
                       this.selectObj.getVertices().get(i).setY(y);
                       this.selectObj.getVertices().get(i).setZ(z);
                       controlMouseAxisY = event.getY();
                    }
                }
                clearAll();
                drawCanvas();
            });
            
            this.canvasPers.setOnMouseDragged(event -> {
                if(controlMouseAxisX < event.getX()){
                    for (int i = 0; i < this.selectObj.getVertices().size(); i++) {
                       double x = ((this.selectObj.getVertices().get(i).getX()*(Math.cos(Math.PI*(-1) / 180))) - (this.selectObj.getVertices().get(i).getY()*Math.sin(Math.PI*(-1)/180)));
                       double y = ((this.selectObj.getVertices().get(i).getX()*Math.sin(Math.PI*(-1)/180)) + (this.selectObj.getVertices().get(i).getY()*(Math.cos(Math.PI*(-1) / 180))));
                        
                       this.selectObj.getVertices().get(i).setX(x);
                       this.selectObj.getVertices().get(i).setY(y);
                       controlMouseAxisX = event.getX();
                    }
                }
                clearAll();
                drawCanvas();
                if(controlMouseAxisX > event.getX()){
                    for (int i = 0; i < this.selectObj.getVertices().size(); i++) {
                       double x = ((this.selectObj.getVertices().get(i).getX()*(Math.cos(Math.PI*1 / 180))) - (this.selectObj.getVertices().get(i).getY()*Math.sin(Math.PI*1/180)));
                       double y = ((this.selectObj.getVertices().get(i).getX()*Math.sin(Math.PI*1/180)) + (this.selectObj.getVertices().get(i).getY()*(Math.cos(Math.PI*1 / 180))));
                        
                       this.selectObj.getVertices().get(i).setX(x);
                       this.selectObj.getVertices().get(i).setY(y);
                       controlMouseAxisX = event.getX();
                    }
                }
                if(controlMouseAxisY < event.getY()){
                    for (int i = 0; i < this.selectObj.getVertices().size(); i++) {
                       double y = ((this.selectObj.getVertices().get(i).getY()*(Math.cos(Math.PI*(-1) / 180))) - (this.selectObj.getVertices().get(i).getZ()*Math.sin(Math.PI*(-1)/180)));
                       double z = ((this.selectObj.getVertices().get(i).getY()*Math.sin(Math.PI*(-1)/180)) + (this.selectObj.getVertices().get(i).getZ()*(Math.cos(Math.PI*(-1) / 180))));
                        
                       this.selectObj.getVertices().get(i).setY(y);
                       this.selectObj.getVertices().get(i).setZ(z);
                       controlMouseAxisY = event.getY();
                    }
                }
                clearAll();
                drawCanvas();
                if(controlMouseAxisY > event.getY()){
                    for (int i = 0; i < this.selectObj.getVertices().size(); i++) {
                       double y = ((this.selectObj.getVertices().get(i).getY()*(Math.cos(Math.PI*1 / 180))) - (this.selectObj.getVertices().get(i).getZ()*Math.sin(Math.PI*1/180)));
                       double z = ((this.selectObj.getVertices().get(i).getY()*Math.sin(Math.PI*1/180)) + (this.selectObj.getVertices().get(i).getZ()*(Math.cos(Math.PI*1 / 180))));
                        
                       this.selectObj.getVertices().get(i).setY(y);
                       this.selectObj.getVertices().get(i).setZ(z);
                       controlMouseAxisY = event.getY();
                    }
                }
                clearAll();
                drawCanvas();
            });
            
            //Modelo Rotacao 2
            /*
            this.canvasXZ.setOnMouseDragged(event -> {
                
                for (int i = 0; i < this.selectObj.getVertices().size(); i++) {
                    System.out.println(this.selectObj.getVertices().get(i).getX());
                    
                    if(controlRotacao == 1){
                        
                        double y = ((this.selectObj.getVertices().get(i).getY()*(Math.cos(Math.PI*1 / 180))) - (this.selectObj.getVertices().get(i).getZ()*Math.sin(Math.PI*1/180)));
                        double z = ((this.selectObj.getVertices().get(i).getY()*Math.sin(Math.PI*1/180)) + (this.selectObj.getVertices().get(i).getZ()*(Math.cos(Math.PI*1 / 180))));
                        
                        this.selectObj.getVertices().get(i).setY(y);
                        this.selectObj.getVertices().get(i).setZ(z);
                    }
                    if(controlRotacao == 2){
                        
                        double y = ((this.selectObj.getVertices().get(i).getY()*(Math.cos(Math.PI*(-1) / 180))) - (this.selectObj.getVertices().get(i).getZ()*Math.sin(Math.PI*(-1)/180)));
                        double z = ((this.selectObj.getVertices().get(i).getY()*Math.sin(Math.PI*(-1)/180)) + (this.selectObj.getVertices().get(i).getZ()*(Math.cos(Math.PI*(-1) / 180))));
                        
                        this.selectObj.getVertices().get(i).setY(y);
                        this.selectObj.getVertices().get(i).setZ(z);
                    }
                    if(controlRotacao == 3){
                        
                        double x = ((this.selectObj.getVertices().get(i).getX()*(Math.cos(Math.PI*1 / 180))) - (this.selectObj.getVertices().get(i).getY()*Math.sin(Math.PI*1/180)));
                        double y = ((this.selectObj.getVertices().get(i).getX()*Math.sin(Math.PI*1/180)) + (this.selectObj.getVertices().get(i).getY()*(Math.cos(Math.PI*1 / 180))));
                        
                        this.selectObj.getVertices().get(i).setX(x);
                        this.selectObj.getVertices().get(i).setY(y);
                    }
                    if(controlRotacao == 4){
                        
                        double x = ((this.selectObj.getVertices().get(i).getX()*(Math.cos(Math.PI*(-1) / 180))) - (this.selectObj.getVertices().get(i).getY()*Math.sin(Math.PI*(-1)/180)));
                        double y = ((this.selectObj.getVertices().get(i).getX()*Math.sin(Math.PI*(-1)/180)) + (this.selectObj.getVertices().get(i).getY()*(Math.cos(Math.PI*(-1) / 180))));
                        
                        this.selectObj.getVertices().get(i).setX(x);
                        this.selectObj.getVertices().get(i).setY(y);
                    }
                }
                clearAll();
                drawCanvas();
            });*/
        }
    }
    
     private void escalar(MouseEvent e) {
        if(e.getSource() == canvasXY && opcaoTrans == 3) {
            System.out.println("CANVAS X Y");
            
            this.canvasXY.setOnMouseDragged(event -> {
                
                if(controlMouseAxisX < event.getX()){
                    for (int i = 0; i < this.selectObj.getVertices().size(); i++) {
                        System.out.println(this.selectObj.getVertices().get(i).getX());
                        System.out.println(event.getX());

                        double x = this.selectObj.getVertices().get(i).getX()*1.09;
                        double y = this.selectObj.getVertices().get(i).getY()*1.09;
                        double z = this.selectObj.getVertices().get(i).getZ()*1.09;
                        
                        this.selectObj.getVertices().get(i).setX(x);
                        this.selectObj.getVertices().get(i).setY(y);
                        this.selectObj.getVertices().get(i).setZ(z);
                        
                        controlMouseAxisX = event.getX();
                    }
                }
                clearAll();
                drawCanvas();
                
                if(controlMouseAxisX > event.getX()){
                    for (int i = 0; i < this.selectObj.getVertices().size(); i++) {
                        double x = this.selectObj.getVertices().get(i).getX()*0.99;
                        double y = this.selectObj.getVertices().get(i).getY()*0.99;
                        double z = this.selectObj.getVertices().get(i).getZ()*0.99;
                        
                        this.selectObj.getVertices().get(i).setX(x);
                        this.selectObj.getVertices().get(i).setY(y);
                        this.selectObj.getVertices().get(i).setZ(z);
                        
                        controlMouseAxisX = event.getX();
                    }
                }
                clearAll();
                drawCanvas();
            });
        }
        if(e.getSource() == canvasYZ && opcaoTrans == 3) {
            System.out.println("CANVAS Y Z");
            
            this.canvasYZ.setOnMouseDragged(event -> {
                
                if(controlMouseAxisX < event.getX()){
                    for (int i = 0; i < this.selectObj.getVertices().size(); i++) {
                        System.out.println(this.selectObj.getVertices().get(i).getX());
                        System.out.println(event.getX());

                        double x = this.selectObj.getVertices().get(i).getX()*1.09;
                        double y = this.selectObj.getVertices().get(i).getY()*1.09;
                        double z = this.selectObj.getVertices().get(i).getZ()*1.09;
                        
                        this.selectObj.getVertices().get(i).setX(x);
                        this.selectObj.getVertices().get(i).setY(y);
                        this.selectObj.getVertices().get(i).setZ(z);
                        
                        controlMouseAxisX = event.getX();
                    }
                }
                clearAll();
                drawCanvas();
                
                if(controlMouseAxisX > event.getX()){
                    for (int i = 0; i < this.selectObj.getVertices().size(); i++) {
                        double x = this.selectObj.getVertices().get(i).getX()*0.99;
                        double y = this.selectObj.getVertices().get(i).getY()*0.99;
                        double z = this.selectObj.getVertices().get(i).getZ()*0.99;
                        
                        this.selectObj.getVertices().get(i).setX(x);
                        this.selectObj.getVertices().get(i).setY(y);
                        this.selectObj.getVertices().get(i).setZ(z);
                        
                        controlMouseAxisX = event.getX();
                    }
                }
                clearAll();
                drawCanvas();
            });
        }

        if(e.getSource() == canvasXZ && opcaoTrans == 3) {
            System.out.println("CANVAS X Z");
            
            this.canvasXZ.setOnMouseDragged(event -> {
                
                if(controlMouseAxisX < event.getX()){
                    for (int i = 0; i < this.selectObj.getVertices().size(); i++) {
                        System.out.println(this.selectObj.getVertices().get(i).getX());
                        System.out.println(event.getX());

                        double x = this.selectObj.getVertices().get(i).getX()*1.09;
                        double y = this.selectObj.getVertices().get(i).getY()*1.09;
                        double z = this.selectObj.getVertices().get(i).getZ()*1.09;
                        
                        this.selectObj.getVertices().get(i).setX(x);
                        this.selectObj.getVertices().get(i).setY(y);
                        this.selectObj.getVertices().get(i).setZ(z);
                        
                        controlMouseAxisX = event.getX();
                    }
                }
                clearAll();
                drawCanvas();
                
                if(controlMouseAxisX > event.getX()){
                    for (int i = 0; i < this.selectObj.getVertices().size(); i++) {
                        double x = this.selectObj.getVertices().get(i).getX()*0.99;
                        double y = this.selectObj.getVertices().get(i).getY()*0.99;
                        double z = this.selectObj.getVertices().get(i).getZ()*0.99;
                        
                        this.selectObj.getVertices().get(i).setX(x);
                        this.selectObj.getVertices().get(i).setY(y);
                        this.selectObj.getVertices().get(i).setZ(z);
                        
                        controlMouseAxisX = event.getX();
                    }
                }
                clearAll();
                drawCanvas();
            });
        }
    }
    
    @FXML
    public void revolucionarClicked(ActionEvent e) throws IOException {
        selOpcao(6);
        // Precisa de:
        // Número de seções/lados
        // Número de graus da rotação
        // Eixo a ser rotacionado
        
        int numSecoes = 0;
        double graus = 0;
        String eixo = " ";
        
        // Criando novo loader, pane para referência e setando local
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(RevolveController.class.getResource("/teobaldo/view/Revolve.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Revolução");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Setando o Controller.
        RevolveController rController = loader.getController();
        rController.setDialogStage(dialogStage);
        
        if(selectObj != null) {
            rController.setControlador(this.objControl, selectObj);
            rController.setCanvas(this.canvasXY, this.canvasYZ, this.canvasXZ, this.canvasPers);
        }
        
        dialogStage.show();
    }
}