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
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author rafae
 */
public class CadastrarprocessosController implements Initializable {

    @FXML
    private TextField txtId;
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
    private ComboBox<String> cbAlgoritmo;
    @FXML
    private Button btnLimpar;
    @FXML
    private Button btnLimparFila;
    @FXML
    private TextField txtQuantum;
    @FXML
    private Button btnAtualizar;
    
    ControladorGeralSingleton controlador ;
    Processo processoAtual;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controlador= ControladorGeralSingleton.getInstancia();
        controlador.ProcessScreen = this;

        // TODO
        cbAlgoritmo.getItems().addAll("FIFO","RR","SJF","SRT","Prioridade", "Prioridade Preemptivo");
        cbAlgoritmo.setValue("FIFO");
        txtQuantum.setDisable(true);
        processoAtual = null;
       
    }

    public void loadProcess(Processo p){
        processoAtual = p;

        txtId.setText(processoAtual.getId());
        txtTempoChegada.setText(String.valueOf(processoAtual.getTempoChegada()));
        txtPrioridade.setText(String.valueOf(processoAtual.getPrioridade()));
        txtTempoProcessar.setText(String.valueOf(processoAtual.getTempoParaProcessar()));
        cbCor.setValue(Color.web(processoAtual.getCor()));

        txtId.setDisable(true);

        btnAtualizar.setDisable(false);
        btnRemover.setDisable(false);
    }


    @FXML
    private void clickCriarProcesso(ActionEvent event) {
        Processo p;
        try
        {   
            if(verificarMesmaCor(cbCor.getValue().toString(), null)) //verifica se essa cor ja existe em algum processo
                JOptionPane.showMessageDialog(new JFrame(),  "Ja existe um processo com essa cor.");
            else {
                if (verificarMesmoId(txtId.getText()))
                    JOptionPane.showMessageDialog(new JFrame(), "Já existe um processo com esse id.");
                else{
                    String id = txtId.getText();
                    int tempoChegada = Integer.parseInt(txtTempoChegada.getText());
                    int tempoProcessar = Integer.parseInt(txtTempoProcessar.getText());
                    int prioridade = Integer.parseInt(txtPrioridade.getText());

                    p = new Processo(tempoChegada, tempoProcessar, prioridade, id, cbCor.getValue().toString());
                    controlador.listaProcessos.add(p);
                    clickLimpar(event);
                }
            }
            
        } catch (NumberFormatException ex) 
        {
          JOptionPane.showMessageDialog(new JFrame(),  "Preencha os campos corretamente.");
        }

          //refresh na tela de fila de processos
        controlador.updateProcessStack();
    }
    
    @FXML
    private void clickRemover(ActionEvent event) {
        controlador.listaProcessos.remove(processoAtual);
        clickLimpar(event);

        //refresh na tela de fila de processos
        controlador.updateProcessStack();
    }

    @FXML
    private void clickProcessar(ActionEvent event) {
        if(controlador.listaProcessos.size()>0) {
            if(cbAlgoritmo.getValue().equals("RR")) {
               try {
                    int quantum =  Integer.parseInt(txtQuantum.getText());
                    controlador.Processar("RR",quantum);

               } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(new JFrame(),  "Preencha o campo de Quantum corretamente");
               }
           }
           else {
                controlador.Processar(cbAlgoritmo.getValue());
            }

           System.out.println(controlador.escalonador.toString());
        }
        else {
             JOptionPane.showMessageDialog(new JFrame(),  "Sem processos na fila");
        }
       
    }

    @FXML
    private void clickLimpar(ActionEvent event) {
        txtPrioridade.setText("");
        txtQuantum.setText("");
        txtTempoChegada.setText("");
        txtTempoProcessar.setText("");
        txtId.setText("");
        cbCor.setValue(Color.WHITE);

        txtId.setDisable(false);

        btnAtualizar.setDisable(true);
        btnRemover.setDisable(true);

        processoAtual = null;
    }

    @FXML
    private void clickLimparFila(ActionEvent event) {
        controlador.listaProcessos.clear();
        
        clickLimpar(event);
        //refresh na tela de fila de processos
        controlador.updateProcessStack();
    }

    @FXML
    private void clickAtualizar(ActionEvent event) {
        try
        {
            int tempoChegada = Integer.parseInt(txtTempoChegada.getText());
            int tempoProcessar = Integer.parseInt(txtTempoProcessar.getText());
            int prioridade =  Integer.parseInt(txtPrioridade.getText());

            if(verificarMesmaCor(cbCor.getValue().toString(), processoAtual.getId())) //verifica se essa cor ja existe em algum processo
                JOptionPane.showMessageDialog(new JFrame(),  "Ja existe um processo com essa cor");
            else{
                processoAtual.id = txtId.getText();
                processoAtual.cor = cbCor.getValue().toString();
                processoAtual.prioridade = prioridade;
                processoAtual.tempoChegada = tempoChegada;
                processoAtual.tempoParaProcessar = tempoProcessar;

                controlador.updateProcess(processoAtual);
                clickLimpar(event);
            }
        }
        catch (NumberFormatException ex) 
        {
          JOptionPane.showMessageDialog(new JFrame(),  "Preencha os campos corretamente");
        }
    }
    
    private boolean verificarMesmaCor(String cor, String id) {
        for(Processo p : controlador.listaProcessos){
            if(id == null) { if(p.getCor().equals(cor)) return true; }
            else { if(p.getCor().equals(cor) && !p.getId().equals(id)) return true; }

        }
        return false;
    }

    private boolean verificarMesmoId(String id){
        for(Processo p : controlador.listaProcessos){
            if(p.getId().equals(id)) return true;
        }
        return false;
    }

    @FXML
    private void escolhaComboAlgoritmo(ActionEvent event) {
       if(cbAlgoritmo.getValue().equals("RR"))
       {
           txtQuantum.setDisable(false);
       }
       else   
       {
           txtQuantum.setText("");
           txtQuantum.setDisable(true);
       }
          
    }
 
}
