package teobaldo.controller;

import java.awt.Color;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import teobaldo.model.Face;
import teobaldo.model.Objeto;

/**
 * FXML Controller class
 *
 * @author Diedrich_
 */
public class ObjectConfigsController implements Initializable {
    
    private Stage dialogStage;
    
    ObjetoController objControl;
    Face face;
    Objeto obj;
    
    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private TextField kaRTextField;

    @FXML
    private TextField kaGTextField;
    
    @FXML
    private TextField kaBTextField;
    
    @FXML
    private TextField kdRTextField;

    @FXML
    private TextField kdGTextField;

    @FXML
    private TextField kdBTextField;
    
    @FXML
    private TextField ksRTextField;
    
    @FXML
    private TextField ksGTextField;
    
    @FXML
    private TextField ksBTextField;
    
    @FXML
    private ColorPicker objColorPicker;
    
    @FXML
    private Button aplicarButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        objColorPicker = new ColorPicker();
    }
    
    @FXML
    public void aplicarButtonClicked(ActionEvent event) {
        
        if(obj != null) {
            // Pegando as variáveis dos campos respectivos
            obj.setKaR(Double.parseDouble(this.getKaRTextField().getText()));
            obj.setKaG(Double.parseDouble(this.getKaGTextField().getText()));
            obj.setKaB(Double.parseDouble(this.getKaBTextField().getText()));
            obj.setKdR(Double.parseDouble(this.getKdRTextField().getText()));
            obj.setKdG(Double.parseDouble(this.getKdGTextField().getText()));
            obj.setKdB(Double.parseDouble(this.getKdBTextField().getText()));
            obj.setKsR(Double.parseDouble(this.getKsRTextField().getText()));
            obj.setKsG(Double.parseDouble(this.getKsGTextField().getText()));
            obj.setKsB(Double.parseDouble(this.getKsBTextField().getText()));
            
            javafx.scene.paint.Color fx = objColorPicker.getValue();
            obj.setCor(new Color((float) fx.getRed(), (float) fx.getGreen(), (float) fx.getBlue(), (float) fx.getOpacity()));
        }
        
        //draw3D();
    }
    
    public TextField getKaRTextField() {
        return this.kaRTextField;
    }
    
    public TextField getKaGTextField() {
        return this.kaGTextField;
    }
    
    public TextField getKaBTextField() {
        return this.kaBTextField;
    }
    
    public TextField getKdRTextField() {
        return this.kdRTextField;
    }
    
    public TextField getKdGTextField() {
        return this.kdGTextField;
    }
    
    public TextField getKdBTextField() {
        return this.kdBTextField;
    }
    
    public TextField getKsRTextField() {
        return this.ksRTextField;
    }
    
    public TextField getKsGTextField() {
        return this.ksGTextField;
    }
    
    public TextField getKsBTextField() {
        return this.ksBTextField;
    }
    
    public Button getAplicarButton() {
        return aplicarButton;
    }
    
    public Color getColor() {
        return this.obj.getCor();
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}