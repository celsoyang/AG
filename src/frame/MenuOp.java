/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frame;

import bean.Atividade;
import bean.Individuo;
import control.Controle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import utils.Numeros;
import utils.StringsUtils;

/**
 *
 * @author Celso Souza
 */
@SuppressWarnings("FieldMayBeFinal")
public class MenuOp extends MenuBar {

    private Menu menuFunc;
    private MenuItem opFuncList;
    private MenuItem opFuncAdd;
    private Menu menuAtv;
    private MenuItem opAtvList;
    private MenuItem opAtvAdd;
    private Menu menuOp;
    private MenuItem menuOpMelhorAssociacao;
    private MenuItem menuOpStart;

    @SuppressWarnings("Convert2Lambda")
    public MenuOp(Stage stage) {

        menuFunc = new Menu(StringsUtils.FUNCIONARIOS);
        opFuncList = new MenuItem(StringsUtils.LISTAR);
        opFuncAdd = new MenuItem(StringsUtils.ADICIONAR);
        menuAtv = new Menu(StringsUtils.ATIVIDADES);
        opAtvList = new MenuItem(StringsUtils.LISTAR);
        opAtvAdd = new MenuItem(StringsUtils.ADICIONAR);
        menuOp = new Menu(StringsUtils.OPCOES);
        menuOpMelhorAssociacao = new MenuItem(StringsUtils.MOSTRAR_MELHOR);
        menuOpStart = new MenuItem(StringsUtils.START);

        menuOp.getItems().addAll(menuOpMelhorAssociacao, menuOpStart);
        menuFunc.getItems().addAll(opFuncList, opFuncAdd);
        menuAtv.getItems().addAll(opAtvList, opAtvAdd);

        this.getMenus().addAll(menuFunc, menuAtv, menuOp);

        menuOp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //AG.loadOtherFrame(stage);
            }
        });

        menuOpMelhorAssociacao.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Controle.caucularMaiorNota();
            }
        });

        opFuncList.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AG.loadFuncListaFrame(stage);
            }
        });

        opFuncAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AG.loadFuncFrame(stage);
            }
        });

        opAtvList.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AG.loadAtvListFrame(stage);
            }
        });

        opAtvAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AG.loadAtvFrame(stage);
            }
        });

        menuOpStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Individuo bestInd = new Individuo();

                do {
                    bestInd = Controle.start();
                } while(bestInd.getNota() < Numeros.NOTA_PISO);
                
                Controle.imprimirAssociacao(bestInd);
                System.out.println("Melhor Nota: " + bestInd.getNota());
                System.exit(Numeros.ZERO);
//                Controle.gerarTabelaAssociacao(bestInd);
            }
        });

    }

}
