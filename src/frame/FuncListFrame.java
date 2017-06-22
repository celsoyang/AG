/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frame;

import bean.Area;
import bean.Atividade;
import bean.Cargo;
import bean.Funcionario;
import control.Controle;
import java.awt.HeadlessException;
import java.util.List;
import java.util.Objects;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
import javax.swing.JOptionPane;
import utils.Numeros;
import utils.StringsUtils;

/**
 *
 * @author ceolivei
 */
@SuppressWarnings("FieldMayBeFinal")
public class FuncListFrame extends BorderPane {

    private Label lbFunc;
    private TableView<Funcionario> listaFunc;
    private MenuOp menu;
    private GridPane pnTop;
    private GridPane pnCenter;
    private GridPane pnBottom;
    private Button btEdit;
    private Button btDelete;

    /**
     *
     * @param stage
     */
    public FuncListFrame(Stage stage) {
        configTable();
        configMenu(stage);

        lbFunc = new Label("Funcionários");
        pnTop = new GridPane();
        pnTop.add(menu, 0, 0);

        pnCenter = new GridPane();
        pnCenter.setAlignment(Pos.CENTER);
        pnCenter.add(lbFunc, 0, 0);
        pnCenter.add(listaFunc, 0, 1);

        configButton(stage);
        pnBottom = new GridPane();
        pnBottom.setAlignment(Pos.CENTER);
        pnBottom.addRow(0, btEdit, btDelete);
        pnBottom.setPadding(new Insets(0, 10, 10, 10));

        this.setTop(pnTop);
        this.setCenter(pnCenter);
        this.setBottom(pnBottom);

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

        listaFunc.getColumns().addAll(columnNome, columnCargo, columnArea, columnExp);
    }

    private void carregarLista() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(StringsUtils.ENTITY_MANAGER);
        EntityManager manager = factory.createEntityManager();

        List<Funcionario> funcionarios;
        funcionarios = (List<Funcionario>) manager.createQuery("select f from Funcionario f").getResultList();

        listaFunc.getItems().setAll(funcionarios);

        manager.close();
        factory.close();
    }

    @SuppressWarnings("Convert2Lambda")
    private void configMenu(Stage stage) {
        menu = new MenuOp(stage);
    }

    @SuppressWarnings("Convert2Lambda")
    private void configButton(Stage stage) {
        btEdit = new Button("Editar");
        btDelete = new Button("Apagar");

        btEdit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Integer index = listaFunc.getSelectionModel().getSelectedIndex();
                Funcionario f = listaFunc.getItems().get(index);
                AG.loadFuncFrame(stage, f);
            }
        });

        btDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Integer index = listaFunc.getSelectionModel().getSelectedIndex();
                Funcionario f = listaFunc.getItems().get(index);
                Integer op = JOptionPane.showConfirmDialog(null, StringsUtils.MSG_CONFIRMA_EXCLUSAO);
                if (Objects.equals(op, Numeros.ZERO)) {
                    try {
                        EntityManagerFactory factory = Persistence.createEntityManagerFactory(StringsUtils.ENTITY_MANAGER);
                        EntityManager manager = factory.createEntityManager();
                        manager.getTransaction().begin();
                        
                        Funcionario funcDelete = manager.find(Funcionario.class, f.getCodigo());
                        manager.remove(funcDelete);
                        manager.getTransaction().commit();
                        manager.close();
                        factory.close();
                        
                        JOptionPane.showMessageDialog(null, StringsUtils.MSG_DELETADO_SUCESSO);
                        carregarLista();
                    } catch (HeadlessException e) {
                        JOptionPane.showMessageDialog(null, StringsUtils.MSG_ERRO_PROCESSO);
                    }
                }
            }
        });
    }
}
