/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frame;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import utils.Numeros;

/**
 *
 * @author ceolivei
 */
public class AG extends Application {
    
    private static Scene scene;
    private static FuncFrame frame;
    private static AtvFrame atvFrame;
    
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
        frame = new FuncFrame(stage);        
        scene = new Scene(frame, Numeros.LARGURA_FRAME,Numeros.ALTURA_FRAME);
        
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
    
}
