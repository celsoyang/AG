/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frame;

import bean.Funcionario;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import utils.Numeros;

/**
 *
 * @author Celso Souza
 * @version 1.0
 */
public class AG extends Application {
    
    private static Scene scene;
    private static FuncFrame funcFrame;
    private static FuncListFrame funcLista;
    private static AtvFrame atvFrame;
    private static AtvListFrame atvListFrame;
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        loadInitialFrame(primaryStage);
    }
    
    @SuppressWarnings("Convert2Lambda")
    static void loadAtvFrame(Stage stage) {
        atvFrame = new AtvFrame(stage);
        scene = new Scene(atvFrame, Numeros.LARGURA_FRAME, Numeros.ALTURA_FRAME);
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
        
        stage.setTitle("AG");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }
    
    @SuppressWarnings("Convert2Lambda")
    static void loadAtvListFrame(Stage stage) {
        atvListFrame = new AtvListFrame(stage);        
        scene = new Scene(atvListFrame, Numeros.LARGURA_FRAME, Numeros.ALTURA_FRAME);
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
        
        stage.setTitle("AG");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }
    
    @SuppressWarnings("Convert2Lambda")
    public static void loadFuncFrame(Stage stage){
        funcFrame = new FuncFrame(stage);        
        scene = new Scene(funcFrame, Numeros.LARGURA_FRAME,Numeros.ALTURA_FRAME);
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
        
        stage.setTitle("AG");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }
    
    @SuppressWarnings("Convert2Lambda")
    public static void loadFuncFrame(Stage stage, Funcionario func){
        funcFrame = new FuncFrame(stage, func);        
        scene = new Scene(funcFrame, Numeros.LARGURA_FRAME,Numeros.ALTURA_FRAME);
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
        
        stage.setTitle("AG");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }
    
    @SuppressWarnings("Convert2Lambda")
    public static void loadFuncListaFrame(Stage stage){
        funcLista = new FuncListFrame(stage);
        scene = new Scene(funcLista, Numeros.LARGURA_FRAME,Numeros.ALTURA_FRAME);
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
        
        stage.setTitle("AG");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    @SuppressWarnings("Convert2Lambda")
    private void loadInitialFrame(Stage stage) {                
        scene = new Scene(new MenuOp(stage), Numeros.LARGURA_FRAME,Numeros.ALTURA_FRAME);
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
        
        stage.setTitle("AG");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }
    
    @SuppressWarnings("Convert2Lambda")
    public static void loadOtherFrame(Stage stage){
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
