/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frame;

import bean.Funcionario;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.hibernate.jpa.internal.QueryImpl;
import utils.Numeros;
import utils.QueryUtils;
import utils.StringsUtils;

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
    
    /**
     *
     * @param stage
     */
    public FuncListFrame(Stage stage){
        configTable();
        lbFunc = new Label("Funcionários");
        menu = new MenuOp(stage);
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
        
        manager.getTransaction().begin();
        
        List<Funcionario> func;
        func = (List<Funcionario>) manager.createQuery("select f from Funcionario f").getResultList();
        
        listaFunc.getItems().addAll(func);
    }
    
}
