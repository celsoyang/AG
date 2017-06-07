/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frame;

import control.Conexao;
import enums.SgbdEnum;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import utils.StringsUtils;

/**
 *
 * @author ceolivei
 */
@SuppressWarnings("FieldMayBeFinal")
public class ConnectionFrame extends BorderPane {

    private Label lbSgbd;
    private Label lbBanco;
    private Label lbUser;
    private Label lbSenha;
    
    private ComboBox<String> comboSgbd;
    private ComboBox<String> comboBanco;
    private TextField tfUser;
    private PasswordField pfSenha;

    private Button btConectar;

    private GridPane pnTop;
    private GridPane pnCenter;
    private GridPane pnBottom;

    private MenuOp menu;

    public ConnectionFrame(Stage stage) {
        lbBanco = new Label("Banco");
        lbSenha = new Label("Senha");
        lbSgbd = new Label("SGBD");
        lbUser = new Label("Usuário");

        pfSenha = new PasswordField();
        tfUser = new TextField();

        comboBanco = new ComboBox<>();
        comboSgbd = new ComboBox<>();

        configCombo();

        menu = new MenuOp(stage);
        pnTop = new GridPane();
        pnTop.add(menu, 0, 0);

        pnCenter = new GridPane();
        pnCenter.add(lbSgbd, 0, 0);
        pnCenter.add(comboSgbd, 0, 1);
        pnCenter.add(lbBanco, 0, 2);
        pnCenter.add(comboBanco, 0, 3);
        pnCenter.add(lbUser, 0, 6);
        pnCenter.add(tfUser, 0, 7);
        pnCenter.add(lbSenha, 0, 8);
        pnCenter.add(pfSenha, 0, 9);

        btConectar = new Button("Conectar");
        configButton();

        pnBottom = new GridPane();
        pnBottom.add(btConectar, 0, 0);
        
        configFields();

        this.setTop(pnTop);
        this.setCenter(pnCenter);
        this.setBottom(pnBottom);
        this.setPadding(new Insets(0, 10, 10, 10));
    }

    private void configCombo() {
        for (SgbdEnum en : SgbdEnum.values()) {
            comboBanco.getItems().add(en.getBanco());
            comboSgbd.getItems().add(en.getDescricao());
        }
    }

    @SuppressWarnings("Convert2Lambda")
    private void configButton() {
        btConectar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SgbdEnum en = SgbdEnum.getByDescricao(comboSgbd.getValue());
                Conexao.startConnection(en.getUrlConnection(), StringsUtils.POSTGRE, 
                        comboBanco.getValue(), en.getPorta(), tfUser.getText(), pfSenha.getText());
            }
        });
    }

    private void configFields() {
        lbBanco.setFont(StringsUtils.FONTE_SISTEMA);
        lbSenha.setFont(StringsUtils.FONTE_SISTEMA);
        lbSgbd.setFont(StringsUtils.FONTE_SISTEMA);
        lbUser.setFont(StringsUtils.FONTE_SISTEMA);
        tfUser.setFont(StringsUtils.FONTE_SISTEMA);        
    }
}
