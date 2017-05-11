
package main;

import java.util.ArrayList;
import java.util.HashMap;

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

    public void RR(int quantum){
        ArrayList<Processo> processosOrdenados = (ArrayList<Processo>) processos.clone();
        ArrayList<Processo> processosNaFila;
        Processo p; // processo qualquer
        int t = 0; // tempo
        int i = 0; // indice do processo
        int te = 0; // tempo percorrido

        processosOrdenados.sort((o1, o2) -> o1.getTempoChegada()-o2.getTempoChegada());
        t = processosOrdenados.get(i).getTempoChegada();
        while(processosRestantes > 0){ // relógio ticka enquanto houverem processos
            processosNaFila = (ArrayList<Processo>) processos.clone();
            processosNaFila.sort((o1, o2) -> o1.getTempoChegada()-o2.getTempoChegada());

            p = processosOrdenados.get(i);

            te = (quantum > p.getTempoParaProcessar()) ? p.getTempoParaProcessar() : quantum;

            // atualiza o diagrama
            diagrama.add(new DiagramaGantt(t, t+te, processos.get(i)));

            t += te;

            processosOrdenados.get(i).setTempoParaProcessar(p.getTempoParaProcessar() - te);
            if(processosOrdenados.get(i).getTempoParaProcessar() == 0){ // esse brother ja deu
                processosOrdenados.get(i).setTempoTermino(t);
                processosOrdenados.get(i).setTerminado(true);

                processosRestantes--;
            }

            processosNaFila.removeIf(p1 -> p1.getTerminado());

            for(int r=0;r < processos.size() && processosRestantes>0; r++) {
                i = (i + 1 >= processos.size()) ? 0 : i + 1;


                if(processosNaFila.get(0).getCor().equals(processosOrdenados.get(i).getCor()) && processosOrdenados.get(i).getTempoChegada() > t){
                    t = processosOrdenados.get(i).getTempoChegada();// pula o tempo se precisar
                }

                if(!processosOrdenados.get(i).getTerminado()) {
                    if (processosOrdenados.get(i).getTempoChegada() <= t) {
                        // i válido, sair do loop
                        r = processos.size();
                    } else { // i inválido, recomeçar
                        r = 0; // reseta a fila, pois nenhum daqui pra frente chegou
                    }
                }


            }
        }

        int a = 4;
    }

    public void SJF() {
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

    public void SRT() {
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

    public void PRIO(){
      ArrayList<Processo> processosNaFila;
      ArrayList<Processo> processosOrdenados;
      Processo p; // processo
      int t = 0; // tempo
      int i = 0; // indice do processo

      while(processosRestantes > 0){ // enquanto nao executar todos os processos da lista
        do{
          // lista os processos na fila
          processosNaFila = (ArrayList<Processo>)processos.clone();
          processosNaFila.removeIf(p1 -> p1.getTerminado());

          //verifica que processos ja chegara
            final int t1 = t;
          processosNaFila.removeIf(p1 -> p1.getTempoChegada() > t1);

          //ordena a fila por prioridade
          processosNaFila.sort((p1, p2) -> p1.getTempoChegada() - p2.getTempoChegada()); // ordenar por tempo de chegada
          processosNaFila.sort((p1, p2) -> p1.getPrioridade() - p2.getPrioridade()); // ordenar por prioridade

          if(processosNaFila.size() == 0){
            // remove processos ordenados
            processosOrdenados = (ArrayList<Processo>)processos.clone();
            processosOrdenados.removeIf(p1 -> p1.getTerminado());

            //reordena a lista para buscar o processo por chegada e prioridade
            processosOrdenados.sort((p1, p2) -> p1.getPrioridade() - p2.getPrioridade()); // ordenar por prioridade
            processosOrdenados.sort((p1, p2) -> p1.getTempoChegada() - p2.getTempoChegada()); // ordenar por tempo de chegada

            //espera se necessário
            if(t < processosOrdenados.get(i).getTempoChegada()){
              t = processosOrdenados.get(i).getTempoChegada();
            }
          }
        } while(processosNaFila.size() == 0 && processosRestantes > 0);

        // atualiza o diagrama
        diagrama.add(new DiagramaGantt(t, t+processosNaFila.get(i).getTempoParaProcessar(), processosNaFila.get(i)));

        //executa o processo P
        t += processosNaFila.get(i).getTempoParaProcessar();
          processosNaFila.get(i).setTempoTermino(t);
          processosNaFila.get(i).setTempoParaProcessar(0);
          processosNaFila.get(i).setTerminado(true);

        processosRestantes--;
      }
    }

    public void PRIO_PREEMPTIVO(){
      ArrayList<Processo> processosNaFila;
      ArrayList<Processo> processosOrdenados;
      HashMap<String, Integer> memoria = new HashMap<String, Integer>(); // armazena a memoria de quando foi iniciado o processo
      Object[] ultimoProcesso = new Object[]{"", null}; // armazena ultimo processo a ser executado (detecta preempcao)
      Processo p; // processo
      int i = 0; // indice do processo
        int t = 0; // tempo do relogio
        int waitingTime = 0;

      for(t=1; processosRestantes > 0; t++){ // enquanto nao executar todos os processos da lista o relogio ticka
        // lista os processos na fila
        processosNaFila = (ArrayList<Processo>)processos.clone();
        processosNaFila.removeIf(p1 -> p1.getTerminado());

        //verifica que processos ja chegaram
        final int t1 = t;
          processosNaFila.removeIf(p1 -> p1.getTempoChegada() > t1);

          if(t1 == 45){
              int bk = 1;
          }

        if(processosNaFila.size() > 0){

          //ordena a fila por prioridade
          processosNaFila.sort((p1, p2) -> p1.getTempoChegada() - p2.getTempoChegada()); // ordenar por tempo de chegada
          processosNaFila.sort((p1, p2) -> p1.getPrioridade() - p2.getPrioridade()); // ordenar por prioridade

          // atualiza o diagrama quando OCORRE PREEMPCAO
          if(!ultimoProcesso[0].toString().equals(processosNaFila.get(i).getCor()) && !ultimoProcesso[0].toString().equals("")){
            p = (Processo) ultimoProcesso[1];
            diagrama.add(new DiagramaGantt(memoria.get(p.getCor()), t-1-waitingTime, p));
            memoria.remove(p.getCor());
          }

          if(!memoria.containsKey(processosNaFila.get(i).getCor())){
            memoria.put(processosNaFila.get(i).getCor(), t); // armazena iniciop da exeucao do processo
            ultimoProcesso = new Object[]{processosNaFila.get(i).getCor(), processosNaFila.get(i)};
          }

          //executa o processo P
            processosNaFila.get(i).setTempoParaProcessar(processosNaFila.get(i).getTempoParaProcessar()-1);
          if(processosNaFila.get(i).getTempoParaProcessar() == 0){
              processosNaFila.get(i).setTempoTermino(t);
              processosNaFila.get(i).setTerminado(true);

            processosRestantes--;
          }

            //reseta o contador de espera
            waitingTime = 0;
        }else{
            waitingTime++;
        }
      }
      
      //adiciona o ultimo processo ao diagrama
        p = (Processo) ultimoProcesso[1];
        diagrama.add(new DiagramaGantt(memoria.get(p.getCor()), t, p));
        memoria.remove(p.getCor());
    }
}
