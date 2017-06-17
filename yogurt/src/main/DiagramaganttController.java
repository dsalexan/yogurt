/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * FXML Controller class
 *
 * @author rafae
 */
public class DiagramaganttController implements Initializable {

    @FXML
    private HBox hbxTimeline;
    @FXML
    private HBox hbxBoxes;

    private Diagrama diagram;


    private ControladorGeralSingleton CONTROL;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CONTROL = ControladorGeralSingleton.getInstancia();
        CONTROL.ProcessDiagram = this;

        // TODO

        //create dummy-test diagram
        /*
        Processo p1 =  new Processo(0, 1, 1, "1", "#3399ff");
        Processo p2 =  new Processo(0, 1, 1, "2", "80B3FF");
        Processo p3 =  new Processo(0, 1, 1, "3", "FF8080");
        Processo p4 =  new Processo(0, 1, 1, "4", "AE592D");

        ArrayList<DiagramaGantt> diagramG = new ArrayList<DiagramaGantt>();
        diagramG.add(new DiagramaGantt(0, 1, p1));
        diagramG.add(new DiagramaGantt(1, 2, p2));
        diagramG.add(new DiagramaGantt(2, 4, p1));
        diagramG.add(new DiagramaGantt(4, 8, p3));
        diagramG.add(new DiagramaGantt(8, 11, p4));
        diagramG.add(new DiagramaGantt(11, 12, p1));
        diagramG.add(new DiagramaGantt(12, 17, p1));
        diagramG.add(new DiagramaGantt(17, 21, p2));
        diagramG.add(new DiagramaGantt(21, 24, p1));
        diagramG.add(new DiagramaGantt(24, 28, p3));
        diagramG.add(new DiagramaGantt(28, 31, p4));
        diagramG.add(new DiagramaGantt(31, 37, p1));

        //parse DiagramaGantt to Diagrama
        diagram = new Diagrama(diagramG);
        loadDiagram(diagram);*/

    }

    // CAPSULE METHODS //

    public void loadDiagram(Diagrama d){
        // create timeline
        drawTimeline(d, hbxTimeline);

        // create diagram
        drawDiagram(d, hbxBoxes);
    }

    // DRAWING METHODS //

    public Label generateRegister(String text){
        return  generateRegister(text, 22.0, "#fafafa");
    }

    public Label generateRegister(String text, double width, String cor){
        final Label lbl = new Label(text);
        lbl.setAlignment(Pos.CENTER);
        lbl.setContentDisplay(ContentDisplay.CENTER);
        lbl.setPrefHeight(22.0);
        lbl.setPrefWidth(width);
        lbl.setMinWidth(width);
        lbl.setBackground(new Background(new BackgroundFill(Color.web(cor), CornerRadii.EMPTY, Insets.EMPTY)));
        lbl.setTextAlignment(TextAlignment.CENTER);

        return lbl;
    }

    public Label generateTime(String text, double width){
        final Label lbl = new Label(text);
        lbl.setAlignment(Pos.CENTER);
        lbl.setContentDisplay(ContentDisplay.CENTER);
        lbl.setPrefHeight(15.0);
        lbl.setPrefWidth(width);
        lbl.setMinWidth(width);
        lbl.setBackground(new Background(new BackgroundFill(Color.rgb(244, 244, 244), CornerRadii.EMPTY, Insets.EMPTY)));
        lbl.setTextAlignment(TextAlignment.CENTER);

        lbl.setFont(Font.font("System", 10.0));

        return lbl;
    }


    public void drawTimeline(Diagrama diagram, HBox timeline){
        double currentSpace = 3.5 - 7.5; // primeira caixa n tem espaço a esquerda, só a padding

        for(int t = diagram.getTempoInicial(); t <= diagram.getTempoFinal(); t++) {
            currentSpace += 15.0 + 7.5;
            if (currentSpace > timeline.getPrefWidth()){ // linha do tempo vai passar o limite da tela
                timeline.setPrefWidth(currentSpace);
            }

            timeline.getChildren().add(generateTime(String.valueOf(t), 15.0));
        }

        // fill the rest of timeline space
        double filledSpace = diagram.getTempoFinal() * (15.0 + 7.5) - 7.5;
        double emptySpace = timeline.getPrefWidth() - filledSpace - 3.5;

        timeline.getChildren().add(generateTime(String.valueOf("-"), emptySpace));
    }

    public void drawDiagram(Diagrama diagram, HBox boxes) {
        //generate figures for each entry in the diagram
        //merge equal figures wich are next to each other
        double currentSpace = 1.0 - 1.0; // primeira caixa n tem espaço a esquerda, só a padding

        for(Diagrama.Registro r : diagram.registros) {
            double width = (22.0 + 1.0) * (r.fim - r.inicio) - 1;
            currentSpace += width + 1.0;
            if (currentSpace > boxes.getPrefWidth()){
                boxes.setPrefWidth(currentSpace);
            }

            boxes.getChildren().add(generateRegister(String.valueOf(r.processo.getId()), width, r.processo.getCor()));
        }

        double filledSpace = diagram.getTempoFinal() * (22.0 + 1.0) - 1.0;
        double emptySpace = boxes.getPrefWidth() - filledSpace;
        boxes.getChildren().add(generateRegister(String.valueOf("-"), emptySpace, "#fafafa"));
    }

    public  void clearHBox(HBox hbx){
        hbx.getChildren().removeAll();
    }
}
