/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author rafae
 */
public class CadastrarprocessosController implements Initializable {

    @FXML
    private TextField txtTempoChegada;
    @FXML
    private TextField txtTempoProcessar;
    @FXML
    private TextField txtPrioridade;
    @FXML
    private ColorPicker cbCor;
    @FXML
    private Button btnCriar;
    @FXML
    private Button btnRemover;
    @FXML
    private Button btnProcessar;
    @FXML
    private ComboBox<?> cbAlgoritmo;
    @FXML
    private Button btnLimpar;
    @FXML
    private TextField txtPrioridade1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clickProcesso(ActionEvent event) {
    }

    @FXML
    private void clickRemover(ActionEvent event) {
    }

    @FXML
    private void clickProcessar(ActionEvent event) {
    }

    @FXML
    private void clickLimpar(ActionEvent event) {
    }
    
}
