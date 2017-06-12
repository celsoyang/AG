/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frame;

import bean.Funcionario;
import control.Controle;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import utils.Numeros;

/**
 *
 * @author ceolivei
 */
@SuppressWarnings("FieldMayBeFinal")
public class FuncListFrame extends BorderPane{
        
    private Label lbFunc;    
    private TableView<Funcionario> listaFunc;
    private MenuOp menu;
    private GridPane pnTop;
    private GridPane pnCenter;
    private MenuItem menuFunc_add;
    private MenuItem menuFunc_load;
    
    /**
     *
     * @param stage
     */
    public FuncListFrame(Stage stage){
        configTable();
        configMenu(stage);
        
        lbFunc = new Label("Funcionários");
        pnTop = new GridPane();
        pnTop.add(menu, 0, 0);
        
        pnCenter = new GridPane();
        pnCenter.setAlignment(Pos.CENTER);
        pnCenter.add(lbFunc, 0, 0);
        pnCenter.add(listaFunc, 0, 1);
        
        this.setTop(pnTop);
        this.setCenter(pnCenter);
        this.setPadding(new Insets(0, 10, 10, 10));
        
        carregarLista();
    }

    private void configTable() {
        listaFunc = new TableView<Funcionario>();
        
        TableColumn columnNome = new TableColumn("Nome");
        TableColumn columnCargo = new TableColumn("Cargo");
        TableColumn columnArea = new TableColumn("Area");
        TableColumn columnExp = new TableColumn("Tempo de Experiência");
        
        columnNome.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("nome"));
        columnCargo.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("cargo"));
        columnArea.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("area"));
        columnExp.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("tempoExp"));
        
        columnNome.setMinWidth(Numeros.LARGURA_TABELA * 0.35);
        columnCargo.setMinWidth(Numeros.LARGURA_TABELA * 0.20);
        columnArea.setMinWidth(Numeros.LARGURA_TABELA * 0.25);
        columnExp.setMinWidth(Numeros.LARGURA_TABELA * 0.20);        
        
        listaFunc.setMinHeight(Numeros.ALTURA_TABELA);
        listaFunc.setMinWidth(Numeros.LARGURA_TABELA);
        
        listaFunc.getColumns().addAll(columnNome,columnCargo,columnArea,columnExp);        
    }

    private void carregarLista() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("AG");
        EntityManager manager = factory.createEntityManager();                
        
        List<Funcionario> func;
        func = (List<Funcionario>) manager.createQuery("select f from Funcionario f").getResultList();
        
        listaFunc.getItems().addAll(func);        
    }

    private void configMenu(Stage stage) {        
        menu = new MenuOp(stage);
        menuFunc_add = new MenuItem("Adicionar");
        menuFunc_load = new MenuItem("Carregar");
        menu.getMenus().get(1).getItems().addAll(menuFunc_add, menuFunc_load);
        
        menuFunc_load.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Controle.loadFuncionario(stage);
            }
        });
        
        menuFunc_add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AG.loadFuncFrame(stage);
            }
        });
    }
    
}
