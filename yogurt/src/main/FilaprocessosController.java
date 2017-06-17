/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author rafae
 */
public class FilaprocessosController implements Initializable {

    @FXML
    private VBox vbxProcessos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Processo p1 =  new Processo(0, 1, 1, "1", "#3399ff");
        Processo p2 =  new Processo(0, 1, 1, "2", "80B3FF");
        Processo p3 =  new Processo(0, 1, 1, "3", "FF8080");
        Processo p4 =  new Processo(0, 1, 1, "4", "AE592D");


        final Pane pne = new Pane();
        pne.setPrefHeight(83.0);
        pne.setPrefWidth(200.0);
        pne.setBackground(new Background(new BackgroundFill(Color.web("#FAFAFA"), CornerRadii.EMPTY, Insets.EMPTY)));

        final Label pid = new Label("Process ID");
        pid.setFont(new Font(13.0));
        pid.setLayoutX(8.0);
        pid.setLayoutY(7.0);

        final Label arrvl = new Label("Arrival");
        arrvl.setFont(new Font(11.0));
        arrvl.setLayoutX(14.0);
        arrvl.setLayoutY(25.0);

        final Label exctn = new Label("Execution");
        exctn.setFont(new Font(11.0));
        exctn.setLayoutX(14.0);
        exctn.setLayoutY(39.0);

        final Label clr = new Label("#RRGGBB");
        clr.setFont(new Font(11.0));
        clr.setLayoutX(14.0);
        clr.setLayoutY(56.0);
        clr.setBackground(new Background(new BackgroundFill(Color.web("#FF0000"), CornerRadii.EMPTY, Insets.EMPTY)));
        clr.setPadding(new Insets(0, 3, 0, 3));
        clr.setTextFill(new Color(1.0, 1.0, 1.0, 1.0));

        final Button edit = new Button("Editar");
        edit.setFont(new Font(11.0));
        edit.setLayoutX(104.0);
        edit.setLayoutY(50.0);

        final Button remove = new Button("X");
        remove.setFont(new Font(11.0));
        remove.setLayoutX(157.0);
        remove.setLayoutY(50.0);


        pne.getChildren().add(pid);
        pne.getChildren().add(arrvl);
        pne.getChildren().add(exctn);
        pne.getChildren().add(clr);
        pne.getChildren().add(edit);
        pne.getChildren().add(remove);

        vbxProcessos.getChildren().add(pne);
        vbxProcessos.setPrefHeight(vbxProcessos.getPrefHeight() + pne.getPrefHeight());
    }    
    
}
