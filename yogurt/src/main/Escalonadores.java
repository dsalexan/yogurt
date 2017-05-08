
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
   
    public void FIFO()
    {
        Processo proximo =processos.get(0);
        for(int i=0;processosRestantes>0;i+=0)//i é o contador do tempo
        {
            proximo = processos.get(0);
            for(int j=0;j<processos.size();j++) //selecionar o processos com o menor tempo de chegada.
            {
                if(proximo.getTempoChegada() > processos.get(j).getTempoChegada())
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
    public void SJF()
    {
         for(int i=0;processosRestantes>0;i+=0)//i é o contador do tempo
        {
             ArrayList<Processo> prontos = new ArrayList<>();
             Processo menorTempoChegada=processos.get(0);
             for(int j=0;j<processos.size();j++) //colocar na lista os processos prontos para serem executados
            {
                if(processos.get(j).getTempoChegada()<=i) //verificar se tem algum processo que chegou em um tempo menor ou igual ao tempo atual
                {
                    prontos.add(processos.get(j));
                }
                if(menorTempoChegada.getTempoChegada() > processos.get(j).getTempoChegada())
                {
                    menorTempoChegada = processos.get(j);
                }
            }
            
            if(prontos.size()>0)
            {  
                Processo menorProcesso=prontos.get(0);
                for(int j=0;j<prontos.size();j++) //agora selecionar o menor processo dentre os prontos
                {
                    if(menorProcesso.getTempoParaProcessar() > prontos.get(j).getTempoParaProcessar())
                    {
                        menorProcesso =  prontos.get(j);
                    }
                }
                  DiagramaGantt novo = new DiagramaGantt(i, i+menorProcesso.getTempoParaProcessar(), menorProcesso);
                  diagrama.add(novo);
                  i=novo.tempoFim; //passa o tempo que o processo fica no processador
                  menorProcesso.setTempoParaProcessar(0); //processo termina de processar
                  menorProcesso.setTerminado(true);
                  menorProcesso.setTempoTermino(i); //coloca o tempo em que terminou;
                  processos.remove(menorProcesso); //remove da lista de processos
                    processosRestantes--; 
            }
            else //nenhum processo chegou ainda cpu vazia
            {
                DiagramaGantt vazio = new DiagramaGantt(i,menorTempoChegada.getTempoChegada(),null);
                diagrama.add(vazio);
                i=menorTempoChegada.getTempoChegada();
            }
            
        }
    }
    public void SRT()
    {
        DiagramaGantt novo =null;
        for(int i=0;processosRestantes>0;i+=0)//i é o contador do tempo
        {
             ArrayList<Processo> prontos = new ArrayList<>();
             Processo menorTempoChegada=processos.get(0);
             for(int j=0;j<processos.size();j++) //colocar na lista os processos prontos para serem executados
            {
                if(processos.get(j).getTempoChegada()<=i) //verificar se tem algum processo que chegou em um tempo menor ou igual ao tempo atual
                {
                    prontos.add(processos.get(j));
                }
                if(menorTempoChegada.getTempoChegada() > processos.get(j).getTempoChegada())
                {
                    menorTempoChegada = processos.get(j);
                }
            }
             
              if(prontos.size()>0)
            {  
                Processo menorProcesso=prontos.get(0);
                for(int j=0;j<prontos.size();j++) //agora selecionar o menor processo dentre os prontos
                {
                    if(menorProcesso.getTempoParaProcessar() > prontos.get(j).getTempoParaProcessar())
                    {
                        menorProcesso =  prontos.get(j);
                    }
                }
                if (novo == null) //nao tem nenhum processo no processador, mas tem processo pronto - alocando para ele
                {
                    novo = new DiagramaGantt(i, i, menorProcesso);
                    i++;
                    novo.processo.setTempoParaProcessar(novo.processo.getTempoParaProcessar()-1);
                }
                else //tem algum processo no processador
                {
                    if(novo.processo.getTempoParaProcessar() ==0) //o processo terminou de processar
                    {
                        novo.processo.setTempoTermino(i);//colocando o tempo que terminou no processo
                        novo.processo.setTerminado(true);
                        novo.tempoFim =i; //colocando o tempo que terminou no diagrama
                        diagrama.add(novo); //finalmente coloca o processo na lista do diagrama
                        processos.remove(novo.processo); //remove da lista de processos
                        processosRestantes--; 
                        novo=null; //liberou o processador
                    }
                    else if (menorProcesso.getTempoParaProcessar()<novo.processo.getTempoParaProcessar()) //preempção, trocar processo
                    {
                        System.out.print("\nPreempçãoz\n");
                        novo.tempoFim =i; //colocando o tempo em q o processo saiu do processador
                        diagrama.add(novo); //fecha o ciclo do processo atual
                        novo = new DiagramaGantt(i, i, menorProcesso); //novo processo entra no processador
                        i++; //passa o tempo
                        novo.processo.setTempoParaProcessar(novo.processo.getTempoParaProcessar()-1); //diminui o tempo restante para terminar
                    }
                    else //nao tem menor processo pronto, processo atual continua no processador
                    {
                        i++; //passa o tempo
                        novo.processo.setTempoParaProcessar(novo.processo.getTempoParaProcessar()-1); //diminui o tempo restante para terminar
                    }
                }
                  
            }
            else //nenhum processo chegou ainda cpu vazia
            {
                DiagramaGantt vazio = new DiagramaGantt(i,menorTempoChegada.getTempoChegada(),null);
                diagrama.add(vazio);
                i=menorTempoChegada.getTempoChegada();
            }
        }
    }
  
}
