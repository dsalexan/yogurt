
package projetoso;

import java.util.ArrayList;

public class Escalonadores 
{
    ArrayList<Processo> processos;
    ArrayList<DiagramaGantt> diagrama;
    int processosRestantes;
    public Escalonadores( ArrayList<Processo> processos)
    {
        this.processos = processos;
        diagrama = new ArrayList<>();
        processosRestantes = processos.size();
    }
    //public 
    public ArrayList<DiagramaGantt>  FIFO()
    {
        Processo proximo =processos.get(0);
        for(int i=0;processosRestantes>0;i++)//i é o contador do tempo
        {
            for(int j=0;j<processos.size();j++) //selecionar o processos com o menor tempo de chegada.
            {
                if( (proximo.getTempoChegada() > processos.get(i).getTempoChegada()) && (processos.get(i).getTerminado()==false) )
                {
                    proximo = processos.get(i);
                }
            }
            if(i<proximo.getTempoChegada())//proximo processo esta alem do tempo atual, cpu vai ficar desocupada
            {
                DiagramaGantt vazio = new DiagramaGantt(i,proximo.getTempoChegada(),null);
                diagrama.add(vazio);
            }
             DiagramaGantt novo = new DiagramaGantt(i, i+proximo.getTempoParaProcessar(), proximo);
             diagrama.add(novo);
             i=novo.tempoFim; //passa o tempo que o processo fica no processador
             proximo.setTempoParaProcessar(0); //processo termina de processar
             processos.remove(proximo); //remove da lista de processos
             processosRestantes--; 
        }
        return diagrama;
    }
}
