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
    private ControladorGeralSingleton()
    {
        listaProcessos = new ArrayList<>();
        escalonador = null;
        diagrama = null;
        processoAtual=2;
        Processo p = new Processo(0, 10, 5, "ffb366");
        Processo p2 = new Processo(0, 20, 5, "ffb365");
        listaProcessos.add(p);
        listaProcessos.add(p2);
    }
    public static ControladorGeralSingleton getInstancia()
    {
        if(instancia == null)
        {
            instancia = new ControladorGeralSingleton();
        }
        return instancia;
    }
    public void Processar(String algoritmo)
    {
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
    public void Processar(String algoritmo, int quantum) //só RR1
    {
        escalonador = new Escalonadores(copiaLista());
        escalonador.RR(quantum);
        diagrama = escalonador.diagrama;
    }
    private ArrayList<Processo> copiaLista()
    {
        ArrayList<Processo> copia = new ArrayList<>();
        
        for(int i=0; i<listaProcessos.size(); i++)
        {
            Processo p1 = new Processo(listaProcessos.get(i).tempoChegada, listaProcessos.get(i).tempoParaProcessar, listaProcessos.get(i).prioridade, listaProcessos.get(i).cor);
            copia.add(p1);
        }
            
       
        return copia;
    }
}
