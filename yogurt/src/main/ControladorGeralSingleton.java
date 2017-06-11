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
    private ControladorGeralSingleton()
    {
        listaProcessos = new ArrayList<>();
        escalonador = null;
        diagrama = null;
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
        escalonador = new Escalonadores(listaProcessos);
        switch (algoritmo)
        {
            case "FIFO":     
                escalonador.FIFO();
            break;
            case "RR":  
                escalonador.RR(0);
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
}
