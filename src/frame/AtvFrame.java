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
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import utils.StringsUtils;

/**
 *
 * @author ceolivei
 */
public class AtvFrame extends BorderPane {

    private Label lbDescricao;
    private Label lbArea;
    private Label lbNivel;
    private Label lbPrazo;

    private TextField tfDescricao;
    private ComboBox<String> comboArea;
    private ComboBox<String> comboNivel;
    private TextField tfPrazo;

    private Button btSalvar;
    private Button btLimpar;

    private MenuOp menu;
    private MenuItem op_add;
    private MenuItem op_load;

    private GridPane pnTop;
    private GridPane pnCenter;
    private GridPane pnBotton;

    private Atividade atividade;

    public AtvFrame(Stage stage) {
        carregarTela(stage);
    }

    public AtvFrame(Stage stage, Atividade atv) {
        carregarTela(stage);
        atividade = atv;
        carregarAtividade();
    }

    private void carregarTela(Stage stage) {

        configMenu(stage);
        pnTop = new GridPane();
        pnTop.add(menu, 0, 0);

        lbDescricao = new Label("Descrição");
        lbArea = new Label("Área");
        lbNivel = new Label("Nível");
        lbPrazo = new Label("Prazo (horas)");

        tfDescricao = new TextField();
        comboArea = new ComboBox<>();
        comboNivel = new ComboBox<>();
        tfPrazo = new TextField();
        tfPrazo.setAlignment(Pos.BASELINE_RIGHT);        
        configCombos();

        pnCenter = new GridPane();
        pnCenter.add(lbDescricao, 0, 0);
        pnCenter.add(tfDescricao, 0, 1);
        pnCenter.add(lbArea, 0, 2);
        pnCenter.add(comboArea, 0, 3);
        pnCenter.add(lbNivel, 0, 4);
        pnCenter.add(comboNivel, 0, 5);
        pnCenter.add(lbPrazo, 0, 6);
        pnCenter.add(tfPrazo, 0, 7);

        configButton();
        pnBotton = new GridPane();
        pnBotton.addRow(0, btSalvar, btLimpar);
        pnBotton.setAlignment(Pos.CENTER);

        this.setTop(pnTop);
        this.setCenter(pnCenter);
        this.setBottom(pnBotton);
        this.setPadding(new Insets(0, 10, 10, 10));
    }

    private void configCombos() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(StringsUtils.ENTITY_MANAGER);
        EntityManager manager = factory.createEntityManager();
        
        List<Area> listaArea = (List<Area>) manager.createQuery("select are from Area are").getResultList();
        listaArea.forEach((ar) -> {
            comboArea.getItems().add(ar.getDescricao());
        });
        
        List<Cargo> listaCargo = (List<Cargo>) manager.createQuery("select car from Cargo car").getResultList();
        
        listaCargo.forEach((c) -> {
            comboNivel.getItems().add(c.getDescricao());
        });
    }

    @SuppressWarnings("Convert2Lambda")
    private void configMenu(Stage stage) {
        menu = new MenuOp(stage);
    }

    @SuppressWarnings("Convert2Lambda")
    private void configButton() {
        btSalvar = new Button("Salvar");
        btLimpar = new Button("Limpar");

        btSalvar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Area area = new Area(comboArea.getSelectionModel().getSelectedIndex() + 1);
                    Cargo cargo = new Cargo(comboNivel.getSelectionModel().getSelectedIndex() + 1);
                    
                    Atividade atv;
                    
                    EntityManagerFactory factory = Persistence.createEntityManagerFactory(StringsUtils.ENTITY_MANAGER);
                    EntityManager manager = factory.createEntityManager();
                    
                    if(Objects.isNull(atividade)){
                        atv = new Atividade();
                    } else {
                        atv = manager.find(Atividade.class, atividade.getCodigo());
                    }
                    
                    atv.setNome(tfDescricao.getText());
                    atv.setArea(area);
                    atv.setNivel(cargo);
                    atv.setPrazo(Double.parseDouble(tfPrazo.getText()));
                    
                    manager.getTransaction().begin();
                    manager.persist(atv);
                    manager.getTransaction().commit();
                    JOptionPane.showMessageDialog(null, StringsUtils.MSG_SALVO_SUCESSO);
                    manager.close();
                } catch (HeadlessException e) {
                    JOptionPane.showMessageDialog(null, StringsUtils.MSG_ERRO_PROCESSO);
                }
                limparCampos();
            }
        });

        btLimpar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                limparCampos();
            }
        });
    }

    private void limparCampos() {
        tfDescricao.setText("");
        comboArea.setValue("");
        comboNivel.setValue("");
        tfPrazo.setText("");
    }

    private void carregarAtividade() {
        tfDescricao.setText(atividade.getNome());
        comboArea.setValue(atividade.getArea().getDescricao());
        comboNivel.setValue(atividade.getNivel().getDescricao());
        tfPrazo.setText(atividade.getPrazo().toString());
    }
}
