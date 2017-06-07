/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frame;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author ceolivei
 */
public class ConnectionFrame extends BorderPane{
    
    private Label lbSgbd;
    private Label lbBanco;
    private Label lbPorta;
    private Label lbUser;
    private Label lbSenha;
    
    private ComboBox<String> comboSgbd;
    private ComboBox<String> comboBanco;
    private TextField tfPorta;
    private TextField tfUser;
    private TextField tfSenha;
    
    private GridPane pnTop;
    private GridPane pncenter;
    private GridPane pnBottom;
    
    public ConnectionFrame(Stage stage){
        lbBanco = new Label("Banco");
    }
}
