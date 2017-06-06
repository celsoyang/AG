/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frame;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author Celso Souza
 */
@SuppressWarnings("FieldMayBeFinal")
public class AtvFrame  extends BorderPane{
    
    private GridPane pnMenu = new GridPane();
    private GridPane pnCenter = new GridPane();
    private GridPane pnButton = new GridPane();
    
    private MenuOp menu;
    private static Stage stage;
    
    private MenuItem menuAtv_add;
    private MenuItem menuAtv_load;
    
    
    public AtvFrame(Stage stage){
        
        setStage(stage);
        configMenu();
        
        pnMenu.add(menu, 0, 0);
        
        this.setTop(pnMenu);
        this.setCenter(pnCenter);
        this.setBottom(pnButton);
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        AtvFrame.stage = stage;
    }

    @SuppressWarnings("Convert2Lambda")
    private void configMenu() {
        menu = new MenuOp(stage);
        menuAtv_add = new MenuItem("Adicionar");
        menuAtv_load = new MenuItem("Carregar");
        menu.getMenus().get(2).getItems().addAll(menuAtv_load,menuAtv_add);
        
        menuAtv_add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            }
        });
        
        menuAtv_load.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            }
        });
    }
    
}
