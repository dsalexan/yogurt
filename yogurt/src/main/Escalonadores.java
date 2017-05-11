
package main;

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
   
    public void FIFO() {
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
        //return diagrama;
    }

    public void RR(int quantum){
        ArrayList<Processo> processosOrdenados = (ArrayList<Processo>) processos.clone();
        ArrayList<Processo> processosRestantes = (ArrayList<Processo>) processos.clone();
        Processo p; // processo qualquer
        int t = 0; // tempo
        int i = 0; // indice do processo
        int te = 0; // tempo percorrido

        processosOrdenados.sort((o1, o2) -> o1.getTempoChegada()-o2.getTempoChegada());
        while(processosRestantes > 0){ // relógio ticka enquanto houverem processos
            p = processosOrdenados.get(i);

            te = (quantum > p.getTempoParaProcessar()) ? p.getTempoParaProcessar() : quantum;
            t += te;

            processosOrdenados.get(i).setTempoParaProcessar(p.getTempoParaProcessar() - quantum);
            if(te == p.getTempoParaProcessar()){ // esse brother ja deu
                processosOrdenados.get(i).setTempoTermino(t);
                processosOrdenados.get(i).setTerminado(true);

                processosRestantes--;
            }

            for(int r=0;r < processos.size(); r++) {
                i = (i + 1 >= processos.size()) ? 0 : i + 1;

                if (!processosOrdenados.get(i).getTerminado() && processosOrdenados.get(i).getTempoChegada() <= t){
                    // i válido, sair do loop
                    r = processos.size();
                }else{ // i inválido, passar o tempo
                    r = 0; // resetar fila circular

                }
            }
        }
    }
  
}
