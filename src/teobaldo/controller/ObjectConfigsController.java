package teobaldo.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }   
}