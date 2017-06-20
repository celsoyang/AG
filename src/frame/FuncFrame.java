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
import enums.AreaEnum;
import enums.CargoEnum;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import utils.StringsUtils;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

/**
 *
 * @author Celso Souza
 * 
 */
@SuppressWarnings("FieldMayBeFinal")
public class FuncFrame extends BorderPane{
    
    private Label lbNome = new Label(StringsUtils.NOME + StringsUtils.DOIS_PONTOS);
    private Label lbCargo = new Label(StringsUtils.CARGO + StringsUtils.DOIS_PONTOS);
    private Label lbTimeExp = new Label(StringsUtils.TEMPO_EXPERIENCIA + StringsUtils.DOIS_PONTOS);
    private Label lbTimeProj = new Label(StringsUtils.TEMPO_PROJETO + StringsUtils.DOIS_PONTOS);
    private Label lbArea = new Label(StringsUtils.AREA + StringsUtils.DOIS_PONTOS);
        
    private TextField tfNome = new TextField();
    private ComboBox<String> comboCargo = new ComboBox<>();
    private TextField tfTimeExp = new TextField();
    private TextField tfTimeProj = new TextField();
    private ComboBox<String> comboArea = new ComboBox<>();
    
    private Button btSalvar = new Button(StringsUtils.SALVAR);
    private Button btLimpar = new Button(StringsUtils.LIMPAR);
    
    private GridPane pnMenu = new GridPane();
    private GridPane pnCenter = new GridPane();
    private GridPane pnButton = new GridPane();
    
    private MenuOp menu;
    private MenuItem menuFunc_add;
    private MenuItem menuFunc_load;
    
    private static Stage stage;
    
    private Funcionario funcionario = new Funcionario();
        
    @SuppressWarnings("Convert2Lambda")
    public FuncFrame(Stage stage){
        setStage(stage);
        carregarTela();
    }
    
    public FuncFrame(Stage stage, Funcionario func){
        funcionario = func;
        setStage(stage);
        carregarTela();
        carregarFunc(func);
    }
    
    @SuppressWarnings("Convert2Lambda")
    private void carregarTela(){
        configCombo();
        configMenu();
        configFields();
        configButton();
                
        pnMenu.add(menu, 0, 0);
        
        pnCenter.add(lbNome, 0, 1);
        pnCenter.add(tfNome, 0, 2);
        pnCenter.add(lbCargo, 0, 3);
        pnCenter.add(comboCargo, 0, 4);
        pnCenter.add(lbTimeExp, 0, 5);
        pnCenter.add(tfTimeExp, 0, 6);
        pnCenter.add(lbTimeProj, 0, 7);
        pnCenter.add(tfTimeProj, 0, 8);
        pnCenter.add(lbArea, 0, 9);
        pnCenter.add(comboArea, 0, 10);
        
        pnButton.setAlignment(Pos.CENTER);
        pnButton.addRow(0, btSalvar, btLimpar);
        
        this.setTop(pnMenu);
        this.setCenter(pnCenter);
        this.setBottom(pnButton);
        this.setPadding(new Insets(0, 10, 10, 10));
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
    }
    
    private void carregarFunc(Funcionario func) {
        tfNome.setText(func.getNome());
        tfTimeExp.setText(func.getTempoExp().toString());
        tfTimeProj.setText(func.getTempoProj().toString());
        comboArea.setValue(new Area(1).getDescricao());
        comboCargo.setValue(new Cargo(2).getDescricao());
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        FuncFrame.stage = stage;
    }

    private void configCombo() {
        List<String> listaArea = new ArrayList<>();
        List<String> listaCargo = new ArrayList<>();
        for (AreaEnum en: AreaEnum.values()) {
            listaArea.add(en.getDescricao());
        }
        comboArea.getItems().addAll(listaArea);
        
        for (CargoEnum en : CargoEnum.values()) {
            listaCargo.add(en.getDescricao());
        }
        comboCargo.getItems().addAll(listaCargo);
    }

    @SuppressWarnings("Convert2Lambda")
    private void configMenu() {
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
        
    }

    private void configFields() {
        lbArea.setFont(StringsUtils.FONTE_SISTEMA);
        lbCargo.setFont(StringsUtils.FONTE_SISTEMA);
        lbNome.setFont(StringsUtils.FONTE_SISTEMA);
        lbTimeExp.setFont(StringsUtils.FONTE_SISTEMA);
        lbTimeProj.setFont(StringsUtils.FONTE_SISTEMA);
        
        tfNome.setFont(StringsUtils.FONTE_SISTEMA);
        tfTimeExp.setAlignment(Pos.BASELINE_RIGHT);
        tfTimeExp.setFont(StringsUtils.FONTE_SISTEMA);
        tfTimeProj.setAlignment(Pos.BASELINE_RIGHT);
        tfTimeProj.setFont(StringsUtils.FONTE_SISTEMA);
    }

    @SuppressWarnings("Convert2Lambda")
    private void configButton() {
        btSalvar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Cargo cargo = new Cargo(comboCargo.getSelectionModel().getSelectedIndex() + 1);
                Area area = new Area(comboArea.getSelectionModel().getSelectedIndex() + 1);
                        
                Funcionario func = new Funcionario();
                func.setNome(tfNome.getText());
                func.setCargo(cargo);
                func.setTempoExp(Integer.parseInt(tfTimeExp.getText()));
                func.setTempoProj(Integer.parseInt(tfTimeProj.getText()));
                func.setArea(area);                

                EntityManagerFactory factory = Persistence.createEntityManagerFactory(StringsUtils.ENTITY_MANAGER);
                
                EntityManager entityManager = factory.createEntityManager();
                
                entityManager.getTransaction().begin();
                
                entityManager.persist(func);
                
                entityManager.getTransaction().commit();
                
                JOptionPane.showMessageDialog(null, "Salvo Com sucesso");
                
                entityManager.close();
            }
        });
    }    
}
