/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frame;

import bean.Atividade;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
import javax.persistence.StoredProcedureQuery;
import utils.Numeros;
import utils.StringsUtils;

/**
 *
 * @author Celso Souza
 */
@SuppressWarnings("FieldMayBeFinal")
public class AtvListFrame  extends BorderPane{

    private Label lbAtividades;    
    
    private TableView<Atividade> listaAtividades;
    
    private GridPane pnTop;
    private GridPane pnCenter;
    private GridPane pnButton;
    
    private MenuOp menu;
    private static Stage stage;
    
    private MenuItem menuAtv_add;
    private MenuItem menuAtv_load;
    
    private Button btDelete;
    private Button btEdit;
    
    private Atividade atividade;
    
    
    public AtvListFrame(Stage stage){        
        setStage(stage);
        carregarTela();
    }
    
    public AtvListFrame(Stage stage, Atividade atv){
        setStage(stage);
        atividade = atv;
        carregarTela();
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        AtvListFrame.stage = stage;
    }

    @SuppressWarnings("Convert2Lambda")
    private void configMenu() {
        menu = new MenuOp(stage);
        menuAtv_add = new MenuItem("Adicionar");
        menuAtv_load = new MenuItem("Carregar");
        menu.getMenus().get(2).getItems().addAll(menuAtv_add, menuAtv_load);
        
        menuAtv_add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AG.loadAtvFrame(stage);
            }
        });
        
        menuAtv_load.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            }
        });
    }

    private void carregarTela() {
        configMenu();
        pnTop = new GridPane();
        pnTop.add(menu, 0, 0);
        
        configTabela();
        lbAtividades = new Label("Atividades");
        pnCenter = new GridPane();
        pnCenter.add(lbAtividades, 0, 0);
        pnCenter.add(listaAtividades, 0, 1);
        
        configButton();
        pnButton = new GridPane();
        pnButton.addRow(0, btEdit, btDelete);
        pnButton.setAlignment(Pos.CENTER);
        
        this.setTop(pnTop);
        this.setCenter(pnCenter);
        this.setBottom(pnButton); 
        this.setPadding(new Insets(0,10,10,10));
        
        carregarLista();
    }

    private void configTabela() {
        listaAtividades = new TableView<>();
        
        TableColumn columnCod = new TableColumn("Código");
        TableColumn columnTitulo = new TableColumn("Título");
        TableColumn columnArea = new TableColumn("Área");
        TableColumn columnNivel = new TableColumn("Nível");
        TableColumn columnResp = new TableColumn("Responsável");
        
        columnCod.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        columnTitulo.setCellValueFactory(new PropertyValueFactory<>("nome"));
        columnArea.setCellValueFactory(new PropertyValueFactory<>("area"));
        columnNivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));
        columnResp.setCellValueFactory(new PropertyValueFactory<>("responsavel"));

        columnCod.setMinWidth(Numeros.LARGURA_TABELA * 0.05);
        columnTitulo.setMinWidth(Numeros.LARGURA_TABELA * 0.3);
        columnArea.setMinWidth(Numeros.LARGURA_TABELA * 0.2);
        columnNivel.setMinWidth(Numeros.LARGURA_TABELA * 0.2);
        columnResp.setMinWidth(Numeros.LARGURA_TABELA * 0.25);
        
        listaAtividades.setMinWidth(Numeros.LARGURA_TABELA);
        listaAtividades.setMinHeight(Numeros.ALTURA_TABELA);
        
        listaAtividades.getColumns().addAll(columnCod, columnTitulo, columnArea, columnNivel, columnResp);
    }

    private void carregarLista() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("AG");
        EntityManager manager = factory.createEntityManager();
        
        StoredProcedureQuery spq = manager.createStoredProcedureQuery("");
        
        //Como fazer casting do retorno para o tipo Atividades
        //Ou extrair os dados do object pra jogar na tabela
        List<Atividade> lista = manager.createQuery
        ("select atv from Atividade atv").getResultList();
        
        listaAtividades.getItems().addAll(lista);        
    }

    @SuppressWarnings("Convert2Lambda")
    private void configButton() {
        btEdit = new Button("Editar");
        btDelete = new Button("Apagar");
        
        btEdit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AG.loadAtvFrame(stage);
            }
        });
        
        btDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            }
        });
    }
    
}
