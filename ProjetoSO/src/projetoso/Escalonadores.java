
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
    @Override
    public String toString()
    {
        String string="";
        for(int i=0; i<diagrama.size();i++)
        {
            Processo p =diagrama.get(i).processo;
            if(p ==null) string+=("\nvazio, começo: "+diagrama.get(i).tempoIni + ", fim: "+diagrama.get(i).tempoFim);
            else string+=("\nProcesso "+diagrama.get(i).processo.getCor()+", começo: "+diagrama.get(i).tempoIni + ", fim: "+diagrama.get(i).tempoFim);
        }
        return string;
    } 
   
    public void FIFO()
    {
        Processo proximo =processos.get(0);
        for(int i=0;processosRestantes>0;i+=0)//i é o contador do tempo
        {
            proximo = processos.get(0);
            for(int j=0;j<processos.size();j++) //selecionar o processos com o menor tempo de chegada.
            {
                if( (proximo.getTempoChegada() > processos.get(j).getTempoChegada()) && (processos.get(j).getTerminado()==false) )
                {
                    proximo = processos.get(j);
                }
            }
            if(i<proximo.getTempoChegada())//proximo processo esta alem do tempo atual, cpu vai ficar desocupada
            {
                DiagramaGantt vazio = new DiagramaGantt(i,proximo.getTempoChegada(),null);
                diagrama.add(vazio);
                i=proximo.getTempoChegada();
            }
             DiagramaGantt novo = new DiagramaGantt(i, i+proximo.getTempoParaProcessar(), proximo);
             diagrama.add(novo);
             i=novo.tempoFim; //passa o tempo que o processo fica no processador
             proximo.setTempoParaProcessar(0); //processo termina de processar
             proximo.setTerminado(true);
             proximo.setTempoTermino(i); //coloca o tempo em que terminou;
             processos.remove(proximo); //remove da lista de processos
             processosRestantes--; 
        }
    }
    public void SJF()
    {
        for(int i=0;processosRestantes>0;i+=0)//i é o contador do tempo
        {
        }
    }
  
}
