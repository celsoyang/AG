/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frame;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author ceolivei
 */
public class InitialFrame extends BorderPane {
    
    private TextField tfFunc;    
    private TextField tfAtv;
    private FileChooser chooserFunc;
    private FileChooser chooserAtv;
    private Button btOk;
    private Button btCancel;
    
    private GridPane pnTop;
    private GridPane pnCenter;
    private GridPane pnBottom;
    
    private MenuOp menu;
    
    
    public InitialFrame(Stage stage) {
        tfFunc = new TextField();
        tfAtv = new TextField();
        chooserAtv = new FileChooser();
        chooserFunc = new FileChooser();
        btOk = new Button("Ok");
        btCancel = new Button("Cancelar");
        
        configMenu(stage);
        
        pnTop.add(menu, 0, 0);
        
        this.setTop(pnTop);        
    }

    private void configMenu(Stage stage) {
        menu = new MenuOp(stage);        
    }
}
