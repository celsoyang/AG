/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frame;

import bean.Area;
import bean.Cargo;
import bean.Funcionario;
import control.Controle;
import java.util.List;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
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
import utils.Numeros;
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
        
        carregarLista();
    }

    @SuppressWarnings({"Convert2Diamond", "Convert2Lambda"})
    private void configTable() {
        listaFunc = new TableView<Funcionario>();
        
        TableColumn columnNome = new TableColumn("Nome");
        TableColumn columnCargo = new TableColumn("Cargo");
        TableColumn columnArea = new TableColumn("Area");
        TableColumn columnExp = new TableColumn("Tempo de Experiência");
        
        columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        /** 
         * Essa papagaiada toda é pra retornar o valor de 
         * dentro de um objeto que esta no objeto principal da tabela         * 
         */
        columnCargo.setCellValueFactory(new Callback<CellDataFeatures<Funcionario, Cargo>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Funcionario, Cargo> param) {
                return new ObservableValue<String>() {
                    @Override
                    public void addListener(ChangeListener<? super String> listener) {
                    }

                    @Override
                    public void removeListener(ChangeListener<? super String> listener) {
                    }

                    @Override
                    public String getValue() {
                        return param.getValue().getCargo().getDescricao();
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
        columnArea.setCellValueFactory(new Callback<CellDataFeatures<Funcionario, Area>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Funcionario, Area> param) {
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
        columnExp.setCellValueFactory(new PropertyValueFactory<>("tempoExp"));
        
        columnNome.setMinWidth(Numeros.LARGURA_TABELA * 0.35);
        columnCargo.setMinWidth(Numeros.LARGURA_TABELA * 0.20);
        columnArea.setMinWidth(Numeros.LARGURA_TABELA * 0.25);
        columnExp.setMinWidth(Numeros.LARGURA_TABELA * 0.20);        
        
        listaFunc.setMinHeight(Numeros.ALTURA_TABELA);
        listaFunc.setMinWidth(Numeros.LARGURA_TABELA);
        
        listaFunc.getColumns().addAll(columnNome,columnCargo,columnArea,columnExp);        
    }

    private void carregarLista() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(StringsUtils.ENTITY_MANAGER);
        EntityManager manager = factory.createEntityManager();
        
        List<Funcionario> func;
        func = (List<Funcionario>) manager.createQuery("select f from Funcionario f").getResultList();
        
        listaFunc.getItems().addAll(func);
        this.setCenter(pnCenter);
    }

    @SuppressWarnings("Convert2Lambda")
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