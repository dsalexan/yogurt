/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
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

    private ControladorGeralSingleton CONTROL;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CONTROL = ControladorGeralSingleton.getInstancia();
        CONTROL.ProcessStack = this;

        updateStack();
        // TODO
        /*
        Processo p1 =  new Processo(0, 1, 1, "1", "#3399ff");
        Processo p2 =  new Processo(0, 1, 1, "2", "80B3FF");
        Processo p3 =  new Processo(0, 1, 1, "3", "FF8080");
        Processo p4 =  new Processo(0, 1, 1, "4", "AE592D");

        generateProcess(p1);
        generateProcess(p2);
        generateProcess(p3);

        removeProcess(p2.getId());*/
    }

    void updateStack(){
        vbxProcessos.getChildren().clear();
        vbxProcessos.setPrefHeight(0.0);

        for(Processo p : CONTROL.listaProcessos){
            generateProcess(p);
        }
    }

    void generateProcess(Processo p){
        final Pane pne = new Pane();
        pne.setPrefHeight(83.0);
        pne.setPrefWidth(200.0);
        pne.setBackground(new Background(new BackgroundFill(Color.web("#FAFAFA"), CornerRadii.EMPTY, Insets.EMPTY)));
        pne.setUserData(p);

        final Label pid = new Label(p.getId());
        pid.setFont(new Font(13.0));
        pid.setLayoutX(8.0);
        pid.setLayoutY(7.0);

        final Label prio = new Label("prio: " + p.getPrioridade());
        prio.setFont(new Font(10.0));
        prio.setLayoutX(149.0);
        prio.setLayoutY(9.0);

        final Label arrvl = new Label("Chegada: " + String.valueOf(p.getTempoChegada()));
        arrvl.setFont(new Font(11.0));
        arrvl.setLayoutX(14.0);
        arrvl.setLayoutY(25.0);

        final Label exctn = new Label("Processamento: " + String.valueOf(p.getTempoParaProcessar()));
        exctn.setFont(new Font(11.0));
        exctn.setLayoutX(14.0);
        exctn.setLayoutY(39.0);

        final Label clr = new Label(p.getCor());
        clr.setFont(new Font(11.0));
        clr.setLayoutX(14.0);
        clr.setLayoutY(56.0);
        clr.setBackground(new Background(new BackgroundFill(Color.web(p.getCor()), CornerRadii.EMPTY, Insets.EMPTY)));
        clr.setPadding(new Insets(0, 3, 0, 3));
        clr.setTextFill(new Color(1.0, 1.0, 1.0, 1.0));

        final Button edit = new Button("Editar");
        edit.setFont(new Font(11.0));
        edit.setLayoutX(106.0);
        edit.setLayoutY(50.0);
        edit.setUserData(p);
        edit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Processo process = (Processo)((Button)event.getSource()).getUserData();
                CONTROL.updateProcessScreen(process);
            }
        });

        final Button remove = new Button("X");
        remove.setFont(new Font(11.0));
        remove.setLayoutX(159.0);
        remove.setLayoutY(50.0);
        remove.setUserData(p);
        remove.setOnAction(event -> {
            Processo process = (Processo)((Button)event.getSource()).getUserData();
            CONTROL.listaProcessos.remove(process);
            removeProcess(process.getId());
        });


        pne.getChildren().add(pid);
        pne.getChildren().add(prio);
        pne.getChildren().add(arrvl);
        pne.getChildren().add(exctn);
        pne.getChildren().add(clr);
        pne.getChildren().add(edit);
        pne.getChildren().add(remove);

        vbxProcessos.getChildren().add(pne);
        vbxProcessos.setPrefHeight(vbxProcessos.getPrefHeight() + pne.getPrefHeight());
    }

    void removeProcess(String pid){
        int i = -1;

        for(i = 0; i<vbxProcessos.getChildren().size() && i != -1; i++){
            Processo p = (Processo)vbxProcessos.getChildren().get(i).getUserData();
            if(pid == p.getId()) break; // deleta o bicho i
            if(i == vbxProcessos.getChildren().size()) i = -2; // avisa q n é pra deletar nada
        }

        if(i != -1){
            vbxProcessos.setPrefHeight(vbxProcessos.getPrefHeight() - ((Pane)vbxProcessos.getChildren().get(i)).getPrefHeight());
            vbxProcessos.getChildren().remove(i);
        }
    }
    
}
