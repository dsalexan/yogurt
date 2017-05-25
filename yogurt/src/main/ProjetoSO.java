/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor
 */
package main;

import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author rafae
 */
public class ProjetoSO extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader  root = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root.load());
        URL inner1 = getClass().getResource("diagramagantt.fxml");
        URL inner2 = getClass().getResource("filaprocessos.fxml");
        URL inner3 = getClass().getResource("cadastrarprocessos.fxml");

        FXMLLoader innerLoader = new FXMLLoader(inner1);
        innerLoader.setRoot(root.getNamespace().get("diagramagantt"));
        innerLoader.load();

        innerLoader = new FXMLLoader(inner2);
        innerLoader.setRoot(root.getNamespace().get("filaprocessos"));
        innerLoader.load();
        
        innerLoader = new FXMLLoader(inner3);
        innerLoader.setRoot(root.getNamespace().get("cadastrarprocesso"));
        innerLoader.load();

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }
    
}
