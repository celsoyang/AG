/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frame;

import control.Conexao;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import utils.StringsUtils;

/**
 *
 * @author Celso Souza
 */
@SuppressWarnings("FieldMayBeFinal")
public class MenuOp extends MenuBar {    
    
    private Menu menuInicio;
    private MenuItem menuInicio_func;
    private MenuItem menuInicio_atv;
    private Menu menuFunc;
    private Menu menuAtv;
    
    private Menu menuOp;
    private MenuItem menuOp_conexao;
    
    
    @SuppressWarnings("Convert2Lambda")
    public MenuOp(Stage stage){
        menuInicio = new Menu("Início");
        menuInicio_func = new MenuItem("Perfil Funcionário");
        menuInicio_atv = new MenuItem("Perfil Atividade");
        
        menuInicio.getItems().addAll( menuInicio_func,menuInicio_atv);               
        menuFunc = new Menu("Funcionário");        
        menuAtv = new Menu("Atividade");        
        menuOp = new Menu("Opções");
        
        menuOp_conexao = new MenuItem("Distribuir");
        menuOp.getItems().addAll(menuOp_conexao);
        
        this.getMenus().addAll(menuInicio, menuFunc, menuAtv, menuOp);
        
        menuInicio_func.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AG.loadFuncFrame(stage);
            }
        });
        
        menuInicio_atv.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AG.loadAtvFrame(stage);
            }
        });
        
        menuOp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AG.loadOtherFrame(stage);
            }
        });
        
        menuOp_conexao.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Conexao.startConnection(StringsUtils.POSTGRE, "ag", "localhost", "5432", "postgres", "celsoyang");
            }
        });
        
    }
    
}
