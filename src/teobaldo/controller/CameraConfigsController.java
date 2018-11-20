package teobaldo.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Diedrich_
 */
public class CameraConfigsController implements Initializable {
    
    private Stage dialogStage;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}
