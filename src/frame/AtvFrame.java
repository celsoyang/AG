/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frame;

import bean.Atividade;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ceolivei
 */
public class AtvFrame extends BorderPane{
    
    private Label lbCodigo;
    private Label lbDescricao;
    private Label lbArea;
    private Label lbNivel;
    private Label lbResp;
    
    private TextField tfCodigo;
    private TextField tfDescricao;
    private ComboBox<String> comboArea;
    private ComboBox<String> comboNivel;
    private ComboBox<String> comboResp;
    
    private Button btSalvar;
    private Button btLimpar;
    
    private MenuOp menu;
    
    private GridPane pnTop;
    private GridPane pnCenter;
    private GridPane pnBotton;
    
    private Atividade atividade;
    
    public AtvFrame(Stage stage){
        carregarTela(stage);
    }
    
    public AtvFrame(Stage stage, Atividade atv){
        carregarTela(stage);
        atividade = atv;
    }

    private void carregarTela(Stage stage) {
        lbCodigo = new Label("Código");
        lbDescricao = new Label("Descrição");
        lbArea = new Label("Área");
        lbNivel = new Label("Nível");
        lbResp = new Label("Responsável");
        
        tfCodigo = new TextField();
        tfCodigo.setEditable(Boolean.FALSE);
        tfDescricao = new TextField();
        comboArea = new ComboBox<>();
        comboNivel = new ComboBox<>();
        comboResp = new ComboBox<>();
        configCombos();
    }

    private void configCombos() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("AG");
        EntityManager manager = factory.createEntityManager();
        
    }
}
