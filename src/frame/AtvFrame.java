/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frame;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author ceolivei
 */
public class AtvFrame  extends BorderPane{
    
    private MenuBar menuBar;    
    private Menu menu;
    private static Stage stage;
    
    
    public AtvFrame(Stage stage){
        
        setStage(stage);
        
        this.setTop(new MenuOp(stage));
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        AtvFrame.stage = stage;
    }
    
}
