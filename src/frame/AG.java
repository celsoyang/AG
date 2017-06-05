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

/**
 *
 * @author ceolivei
 */
public class AG extends Application {
    
    private static Scene scene;
    
    private static FuncFrame frame;
    private static AtvFrame otehrFrame;

    static void loadAtvFrame(Stage stage) {
        
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        loadInitialFrame(primaryStage);        
    }
    
    public static void loadInitialFrame(Stage stage){
        frame = new FuncFrame(stage);        
        scene = new Scene(frame, 700, 400);
        
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
    
    public static void loadOtherFrame(Stage stage){
        otehrFrame = new AtvFrame(stage);        
        scene = new Scene(otehrFrame, 700, 600);
        
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
