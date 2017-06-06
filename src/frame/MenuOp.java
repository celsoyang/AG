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
    
    private Menu menuInicio;
    
    private Menu menuFuncionario;
    
    private Menu menuAtividade;
    
    private Menu menuOpcoes;
    
    @SuppressWarnings("Convert2Lambda")
    public MenuOp(Stage stage){
        menuInicio = new Menu("Início");
        menuFuncionario = new Menu("Funcionário");
        menuAtividade = new Menu("Atividade");
        menuOpcoes = new Menu("Outras");
        
        menuInicio.getItems().addAll(menuFuncionario,menuAtividade,menuOpcoes);
        
        this.getMenus().addAll(menuInicio);
        
        menuFuncionario.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AG.loadInitialFrame(stage);
            }
        });
        
        menuAtividade.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AG.loadAtvFrame(stage);
            }
        });
        
        menuOpcoes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AG.loadOtherFrame(stage);
            }
        });
        
    }
    
}
