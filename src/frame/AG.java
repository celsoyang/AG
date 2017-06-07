/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frame;

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
    private static AtvFrame atvFrame;
    private static ConnectionFrame connectFrame;
    
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
    public static void loadOtherFrame(Stage stage){
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
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
    public static void loadConnectionFrame(Stage stage) {
        connectFrame = new ConnectionFrame(stage);
        scene = new Scene(connectFrame, Numeros.LARGURA_FRAME,Numeros.ALTURA_FRAME);
        
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
    
}
