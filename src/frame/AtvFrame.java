/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frame;

import bean.Area;
import bean.Atividade;
import bean.Cargo;
import enums.AreaEnum;
import enums.CargoEnum;
import java.util.List;
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
import utils.StringsUtils;

/**
 *
 * @author ceolivei
 */
public class AtvFrame extends BorderPane {

    private Label lbDescricao;
    private Label lbArea;
    private Label lbNivel;
    private Label lbResp;

    private TextField tfDescricao;
    private ComboBox<String> comboArea;
    private ComboBox<String> comboNivel;
    private Label responsavel;

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
        lbResp = new Label("Responsável");

        tfDescricao = new TextField();
        comboArea = new ComboBox<>();
        comboNivel = new ComboBox<>();
        responsavel = new Label();
        configCombos();

        pnCenter = new GridPane();
        pnCenter.add(lbDescricao, 0, 0);
        pnCenter.add(tfDescricao, 0, 1);
        pnCenter.add(lbArea, 0, 2);
        pnCenter.add(comboArea, 0, 3);
        pnCenter.add(lbNivel, 0, 4);
        pnCenter.add(comboNivel, 0, 5);
        pnCenter.add(lbResp, 0, 6);
        pnCenter.add(responsavel, 0, 7);

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
        for (Area ar : listaArea) {
            comboArea.getItems().add(ar.getDescricao());
        }
        
        List<Cargo> listaCargo = (List<Cargo>) manager.createQuery("select car from Cargo car").getResultList();
        for (Cargo c : listaCargo) {
            comboNivel.getItems().add(c.getDescricao());
        }
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
                    Atividade atv = new Atividade();
                    atv.setNome(tfDescricao.getText());
//                    atv.setArea(AreaEnum.getByDescricao(comboArea.getValue()).getCodigo().toString());
//                    atv.setNivel(CargoEnum.getByDescricao(comboNivel.getValue()).getCodigo().toString());
                    EntityManagerFactory factory = Persistence.createEntityManagerFactory(StringsUtils.ENTITY_MANAGER);
                    EntityManager manager = factory.createEntityManager();

                    manager.getTransaction().begin();
                    manager.persist(atv);
                    manager.getTransaction().commit();
                    manager.close();
                } catch (Exception e) {

                }                
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
        responsavel.setText("");
    }

    private void carregarAtividade() {
        tfDescricao.setText(atividade.getNome());
//        comboArea.setValue(AreaEnum.getByCodigo(Integer.parseInt(atividade.getArea())).getDescricao());
//        comboNivel.setValue(CargoEnum.getByCodigo(Integer.parseInt(atividade.getNivel())).getDescricao());
//        responsavel.setText(atividade.getResponsavel());
    }
}
