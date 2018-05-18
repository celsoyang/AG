/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frame;

import bean.Atividade;
import bean.Individuo;
import control.Controle;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
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
    private MenuItem menuOpAssociar;
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
        menuOpAssociar = new MenuItem(StringsUtils.ASSOCIAR_ATIVIDADES);
        menuOpStart = new MenuItem(StringsUtils.START);

        menuOp.getItems().addAll(menuOpAssociar, menuOpStart);
        menuFunc.getItems().addAll(opFuncList, opFuncAdd);
        menuAtv.getItems().addAll(opAtvList, opAtvAdd);

        this.getMenus().addAll(menuFunc, menuAtv, menuOp);

        menuOp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //AG.loadOtherFrame(stage);
            }
        });

        menuOpAssociar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

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

//                do {
//                    bestInd = Controle.start();
//                } while (bestInd.getNota() < Numeros.MAX_NOTA);
                Individuo melhor = new Individuo();
                List<String> tentativas = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    bestInd = Controle.start();

                    if (bestInd.getNota() > melhor.getNota()) {
                        melhor = new Individuo(bestInd);
                    }

                    tentativas.add("Tentativa: " + (i + 1) + " Melhor nota: " + bestInd.getNota());
                }
                
                for (String tent : tentativas) {
                    System.out.println(tent);
                }

                for (Atividade atv : melhor.getAtividades()) {
                    System.out.println();
                    System.out.println("***********************************************");
                    System.out.println(atv.getNome().toUpperCase());
                    System.out.println("RESPONSÁVEL: " + atv.getResponsavel().getNome());
                    System.out.println("-----------------------------------------------");
                    System.out.println("ÁREA ATIVIDADE  : " + atv.getArea().getDescricao());
                    System.out.println("ÁREA FUNCIONÁRIO: " + atv.getResponsavel().getArea().getDescricao());
                    System.out.println("-----------------------------------------------");
                    System.out.println("NÍVEL ATIVIDADE  : " + atv.getNivel().getDescricao());
                    System.out.println("NÍVEL FUNCIONÁRIO: " + atv.getResponsavel().getCargo().getDescricao());
                    System.out.println("***********************************************\n");
                }
                System.out.println("Melhor Nota: " + melhor.getNota());
            }
        });

    }

}
