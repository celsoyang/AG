/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frame;

import control.Controle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 *
 * @author Celso Souza
 */
@SuppressWarnings("FieldMayBeFinal")
public class MenuOp extends MenuBar {

    private Menu menuFunc;
    private MenuItem opFuncList;
    private MenuItem opFuncAdd;
    private Menu menuAtv;
    private MenuItem opAtvList;
    private MenuItem opAtvAdd;
    private Menu menuOp;
    private MenuItem menuOp_opcao;

    @SuppressWarnings("Convert2Lambda")
    public MenuOp(Stage stage) {        

        menuFunc = new Menu("Funcionário");
        opFuncList = new MenuItem("Listar");
        opFuncAdd = new MenuItem("Adicionar");
        menuAtv = new Menu("Atividade");
        opAtvList = new MenuItem("Listar");
        opAtvAdd = new MenuItem("Adicionar");
        menuOp = new Menu("Opções");

        menuOp_opcao = new MenuItem("Opção");
        menuOp.getItems().addAll(menuOp_opcao);
        menuFunc.getItems().addAll(opFuncList, opFuncAdd);
        menuAtv.getItems().addAll(opAtvList, opAtvAdd);

        this.getMenus().addAll(menuFunc, menuAtv, menuOp);


        menuOp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Controle.gerarFucionarios();
                //AG.loadOtherFrame(stage);
            }
        });

        menuOp_opcao.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            }
        });
        
        opFuncList.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AG.loadFuncListaFrame(stage);
            }
        });
        
        opFuncAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AG.loadFuncFrame(stage);
            }
        });
        
        opAtvList.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AG.loadAtvListFrame(stage);
            }
        });
        
        opAtvAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AG.loadAtvFrame(stage);
            }
        });

    }

}
