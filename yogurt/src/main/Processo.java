
package main;

import java.util.ArrayList;

/**
 *
 * @author rafae
 */
public class Processo 
{    
    public int tempoChegada;
    private int tempoTermino;
    public int tempoParaProcessar;
    public int prioridade;
    private boolean terminado;
    public String cor;
    public String id;
    public Processo(int newTempoChegada, int newTempoProcessar, int newPrioridade, String newId, String newCor) {
        tempoChegada = newTempoChegada;
        tempoTermino=-1;
        tempoParaProcessar = newTempoProcessar;
        prioridade = newPrioridade;
        terminado = false;
        cor = newCor;
        id = newId;
    }

    public Processo(Processo original){
        this(original.tempoChegada, original.tempoParaProcessar, original.prioridade, original.id, original.cor);
        this.tempoTermino = original.tempoTermino;
    }

    public int getTempoChegada() {
        return tempoChegada;
    }

    public int getTempoParaProcessar() {
        return tempoParaProcessar;
    }

    public void setTempoParaProcessar(int tempoParaProcessar) {
        this.tempoParaProcessar = tempoParaProcessar;
    }

    public int getPrioridade() {
        return prioridade;
    }
    public boolean getTerminado()
    {
        return terminado;
    }
    public void setTerminado(boolean terminado)
    {
        this.terminado = terminado;
    }
    public void setTempoTermino(int termino)
    {
        tempoTermino=termino;
    }
    public int getTempoTermino()
    {
        return tempoTermino;
    }
    public String getCor()
    {
        return cor;
    }
    public String getId() {return id;}
    public String toString()
    {
        return cor;
    }
}
