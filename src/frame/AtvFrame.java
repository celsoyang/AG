/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frame;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author Celso Souza
 */
public class AtvFrame  extends BorderPane{
    
    private MenuBar menuBar;    
    private MenuOp menu;
    private static Stage stage;
    
    private MenuItem menuAtv_add;
    private MenuItem menuAtv_load;
    
    
    public AtvFrame(Stage stage){
        
        setStage(stage);
        configMenu();
        
        this.setTop(new MenuOp(stage));
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        AtvFrame.stage = stage;
    }

    private void configMenu() {
        menu = new MenuOp(stage);
        menuAtv_add = new MenuItem("Adicionar");
        menuAtv_load = new MenuItem("Carregar");
        menu.getMenus().get(2).getItems().addAll(menuAtv_load,menuAtv_add);
    }
    
}
