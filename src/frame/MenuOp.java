/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frame;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.stage.Stage;

/**
 *
 * @author ceolivei
 */
@SuppressWarnings("FieldMayBeFinal")
public class MenuOp extends MenuBar{    
    
    private Menu menuPrinc;
    
    private Menu opFunc;
    
    private Menu opAtv;
    
    private Menu opOther;
    
    @SuppressWarnings("Convert2Lambda")
    public MenuOp(Stage stage){
        opFunc = new Menu("Funcionário");
        opAtv = new Menu("Atividade");
        opOther = new Menu("Outras");        
        menuPrinc = new Menu("Início");
        
        menuPrinc.getItems().addAll(opFunc,opAtv,opOther);
        
        this.getMenus().addAll(menuPrinc);
        
        opFunc.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AG.loadInitialFrame(stage);
            }
        });
        
        opAtv.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AG.loadAtvFrame(stage);
            }
        });
        
        opOther.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AG.loadOtherFrame(stage);
            }
        });
        
    }
    
}
