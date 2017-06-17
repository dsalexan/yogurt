/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;

/**
 *
 * @author rafae
 */
public class ControladorGeralSingleton {
    private static ControladorGeralSingleton instancia;
    public ArrayList<Processo> listaProcessos;
    public Escalonadores escalonador;
    public ArrayList<DiagramaGantt> diagrama;
    public int processoAtual; //começando do indice 1 !!!


    public CadastrarprocessosController ProcessScreen;
    public FilaprocessosController ProcessStack;
    public DiagramaganttController ProcessDiagram;

    private ControladorGeralSingleton() {
        listaProcessos = new ArrayList<>();
        escalonador = null;
        diagrama = null;


        Processo p1 =  new Processo(0, 1, 1, "1", "#3399ff");
        Processo p2 =  new Processo(0, 1, 1, "2", "80B3FF");
        Processo p3 =  new Processo(0, 1, 1, "3", "FF8080");
        Processo p4 =  new Processo(0, 1, 1, "4", "AE592D");

        listaProcessos.add(p1);
        listaProcessos.add(p2);
        listaProcessos.add(p3);
        listaProcessos.add(p4);
    }

    public static ControladorGeralSingleton getInstancia() {
        if(instancia == null)
        {
            instancia = new ControladorGeralSingleton();
        }
        return instancia;
    }
    public void Processar(String algoritmo) {
        escalonador = new Escalonadores(copiaLista()); // manda uma copia (não a referencia)
        switch (algoritmo)
        {
            case "FIFO":     
                escalonador.FIFO();
            break;
            case "SJF":  
                escalonador.SJF();
            break;
            case "SRT":  
                escalonador.SRT();
            break;
            case "Prioridade":  
                escalonador.PRIO();
            break;
            case "Prioridade Preemptivo":  
                escalonador.PRIO_PREEMPTIVO();
            break;
        }
        diagrama = escalonador.diagrama;
    }
    public void Processar(String algoritmo, int quantum){ //só RR1
        escalonador = new Escalonadores(copiaLista());
        escalonador.RR(quantum);
        diagrama = escalonador.diagrama;
    }
    private ArrayList<Processo> copiaLista() {
        ArrayList<Processo> copia = new ArrayList<>();
        
        for(int i=0; i<listaProcessos.size(); i++)
        {
            Processo p1 = new Processo(listaProcessos.get(i).tempoChegada, listaProcessos.get(i).tempoParaProcessar, listaProcessos.get(i).prioridade, listaProcessos.get(i).cor);
            copia.add(p1);
        }
            
       
        return copia;
    }

    public void updateProcessScreen(Processo p){
        this.ProcessScreen.loadProcess(p);
    }
    public void updateProcessStack() {
        this.ProcessStack.updateStack();
    }
    public void updateProcess(Processo p) {
        for(int i = 0; i < listaProcessos.size(); i++){
            if(listaProcessos.get(i).getId().equals(p.getId())){
                listaProcessos.get(i).id = p.getId();
                listaProcessos.get(i).cor = p.getCor();
                listaProcessos.get(i).tempoChegada = p.getTempoChegada();
                listaProcessos.get(i).tempoParaProcessar = p.getTempoParaProcessar();
                listaProcessos.get(i).prioridade = p.getPrioridade();

                break;
            }
        }

        updateProcessStack();
    }
}
