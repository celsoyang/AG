/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frame;

import bean.Area;
import bean.Atividade;
import bean.Cargo;
import java.awt.HeadlessException;
import java.util.List;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
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

    @SuppressWarnings("Convert2Lambda")
    private void configTabela() {
        listaAtividades = new TableView<>();
        
        TableColumn columnCod = new TableColumn("Código");
        TableColumn columnTitulo = new TableColumn("Título");
        TableColumn columnArea = new TableColumn("Área");
        TableColumn columnNivel = new TableColumn("Nível");
        TableColumn columnResp = new TableColumn("Responsável");
        
        columnCod.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        columnTitulo.setCellValueFactory(new PropertyValueFactory<>("nome"));
        columnArea.setCellValueFactory(new Callback<CellDataFeatures<Atividade, Area>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Atividade, Area> param) {
                return new ObservableValue<String>() {
                    @Override
                    public void addListener(ChangeListener<? super String> listener) {
                    }

                    @Override
                    public void removeListener(ChangeListener<? super String> listener) {
                    }

                    @Override
                    public String getValue() {
                        return param.getValue().getArea().getDescricao();
                    }

                    @Override
                    public void addListener(InvalidationListener listener) {
                    }

                    @Override
                    public void removeListener(InvalidationListener listener) {
                    }
                };
            }
        });
        columnNivel.setCellValueFactory(new Callback<CellDataFeatures<Atividade, Cargo>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Atividade, Cargo> param) {
                return new ObservableValue<String>() {
                    @Override
                    public void addListener(ChangeListener<? super String> listener) {
                    }

                    @Override
                    public void removeListener(ChangeListener<? super String> listener) {
                    }

                    @Override
                    public String getValue() {
                        return param.getValue().getNivel().getDescricao();
                    }

                    @Override
                    public void addListener(InvalidationListener listener) {
                    }

                    @Override
                    public void removeListener(InvalidationListener listener) {
                    }
                };
            }
        });
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

    @SuppressWarnings("UnusedAssignment")
    private void carregarLista() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(StringsUtils.ENTITY_MANAGER);
        EntityManager manager = factory.createEntityManager();

        List<Atividade> lista = (List<Atividade>) manager.createQuery("select atv from Atividade atv").getResultList();

        listaAtividades.getItems().addAll(lista);
    }

    @SuppressWarnings("Convert2Lambda")
    private void configButton() {
        btEdit = new Button("Editar");
        btDelete = new Button("Apagar");
        
        btEdit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Integer index = listaAtividades.getSelectionModel().getSelectedIndex();
                atividade = listaAtividades.getItems().get(index);
                AG.loadAtvFrame(stage, atividade);
            }
        });
        
        btDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Integer index = listaAtividades.getSelectionModel().getSelectedIndex();
                atividade = listaAtividades.getItems().get(index);
                try {
                    EntityManagerFactory factory = Persistence.createEntityManagerFactory(StringsUtils.ENTITY_MANAGER);
                    EntityManager manager = factory.createEntityManager();

                    manager.getTransaction().begin();
                    manager.remove(atividade);
                    manager.getTransaction().commit();
                    JOptionPane.showMessageDialog(null, StringsUtils.MSG_DELETADO_SUCESSO);
                } catch (HeadlessException e) {
                    JOptionPane.showMessageDialog(null, StringsUtils.MSG_ERRO_PROCESSO);
                }
            }
        });
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
    }
    
}
