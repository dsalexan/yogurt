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
     
   
    @FXML
    private Label lblProcesso;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cbAlgoritmo.getItems().addAll("FIFO","RR","SJF","SRT","Prioridade", "Prioridade Preemptivo");
        cbAlgoritmo.setValue("FIFO");
        controlador= ControladorGeralSingleton.getInstancia();
        lblProcesso.setText("Processo "+(controlador.processoAtual));
        txtQuantum.setDisable(true);
      /*
        if(controlador.processoAtual <= controlador.listaProcessos.size()) // a tela tem que mostrar um processo ja cadastrado
       {
           cbCor.setValue(Color.web(controlador.listaProcessos.get(controlador.processoAtual-1).cor));
           txtPrioridade.setText("" + (controlador.listaProcessos.get(controlador.processoAtual-1).prioridade) );
           txtTempoChegada.setText("" + (controlador.listaProcessos.get(controlador.processoAtual-1).tempoChegada) );
           txtTempoProcessar.setText("" + (controlador.listaProcessos.get(controlador.processoAtual-1).tempoParaProcessar) );
       }*/
       
    }    
    @FXML
    private void clickCriarProcesso(ActionEvent event) 
    {
        if(controlador.processoAtual<= controlador.listaProcessos.size())
        {
            controlador.processoAtual=controlador.listaProcessos.size()+1;
        }
        try
        {   
            if( !verificarMesmaCor(cbCor.getValue().toString())) //verifica se essa cor ja existe em algum processo
            {
                int tempoChegada = Integer.parseInt(txtTempoChegada.getText());
                int tempoProcessar = Integer.parseInt(txtTempoProcessar.getText());
                int prioridade =  Integer.parseInt(txtPrioridade.getText());
              
                Processo p = new Processo(tempoChegada,tempoProcessar ,prioridade, cbCor.getValue().toString());
                controlador.listaProcessos.add(p);         
                controlador.processoAtual = controlador.listaProcessos.size()+1; // para aparecer no label qual é o proximo processo a ser cadastrado
                clickLimpar(event);
            }
            else
                JOptionPane.showMessageDialog(new JFrame(),  "Ja existe um processo com esse identificador de cor");
            
        } catch (NumberFormatException ex) 
        {
          JOptionPane.showMessageDialog(new JFrame(),  "Preencha os campos corretamente");
        }
        
          //refresh na tela de fila de processos
    }
    
    @FXML
    private void clickRemover(ActionEvent event) {
        if(controlador.processoAtual <= controlador.listaProcessos.size())
        {
            controlador.listaProcessos.remove(controlador.processoAtual -1);
            controlador.processoAtual = controlador.listaProcessos.size()+1;
            clickLimpar(event);
        }
        else
        {
             JOptionPane.showMessageDialog(new JFrame(),  "Esse processo não está cadastrado");
        }
        
          //refresh na tela de fila de processos
    }

    @FXML
    private void clickProcessar(ActionEvent event) 
    {
        if(controlador.listaProcessos.size()>0)
        {
            if(cbAlgoritmo.getValue().equals("RR"))
            {
               try
               { 
                    int quantum =  Integer.parseInt(txtQuantum.getText());
                    controlador.Processar("RR",quantum);

               } catch (NumberFormatException ex) 
               {
                    JOptionPane.showMessageDialog(new JFrame(),  "Preencha o campo de Quantum corretamente");
               }
           }
           else
                controlador.Processar(cbAlgoritmo.getValue());
            
           System.out.println(controlador.escalonador.toString());
        }
        else
        {
             JOptionPane.showMessageDialog(new JFrame(),  "Sem processos na fila");
        }
       
    }

    @FXML
    private void clickLimpar(ActionEvent event) {
        txtPrioridade.setText("");
        txtQuantum.setText("");
        txtTempoChegada.setText("");
        txtTempoProcessar.setText("");
        lblProcesso.setText("Processo "+(controlador.processoAtual));
    }

    @FXML
    private void clickLimparFila(ActionEvent event) 
    {
        controlador.processoAtual=1;
        for(int i= controlador.listaProcessos.size()-1 ;i>=0;i--) 
            controlador.listaProcessos.remove(i);
        
        clickLimpar(event);
        //refresh na tela de fila de processos
    }

    @FXML
    private void clickAtualizar(ActionEvent event) 
    {
        int i = controlador.processoAtual; //i processos de 1 a n, para dar get usar i-1 
        try
        {
            int tempoChegada = Integer.parseInt(txtTempoChegada.getText());
            int tempoProcessar = Integer.parseInt(txtTempoProcessar.getText());
            int prioridade =  Integer.parseInt(txtPrioridade.getText());
            if(i<= controlador.listaProcessos.size())
            {
                if( !verificarMesmaCor(cbCor.getValue().toString())) //verifica se essa cor ja existe em algum processo
                {  
                    controlador.listaProcessos.get(i-1).cor = cbCor.getValue().toString();
                    controlador.listaProcessos.get(i-1).prioridade = prioridade;
                    controlador.listaProcessos.get(i-1).tempoChegada = tempoChegada;
                    controlador.listaProcessos.get(i-1).tempoParaProcessar = tempoProcessar;
                    
                    controlador.processoAtual = controlador.listaProcessos.size()+1;
                    clickLimpar(event);
                }
                else
                    JOptionPane.showMessageDialog(new JFrame(),  "Ja existe um processo com esse identificador de cor");
            }
            else
            {
                JOptionPane.showMessageDialog(new JFrame(),  "Processo não cadastrado ainda");
            }
        }
        catch (NumberFormatException ex) 
        {
          JOptionPane.showMessageDialog(new JFrame(),  "Preencha os campos corretamente");
        }
    }
    
    private boolean verificarMesmaCor(String cor)
    {
        for(int i=0; i<controlador.listaProcessos.size();i++)
            if(controlador.listaProcessos.get(i).cor.equals(cor)) return true;
        return false;
    }

    @FXML
    private void escolhaComboAlgoritmo(ActionEvent event) 
    {
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
