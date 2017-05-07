
package projetoso;

/**
 *
 * @author rafae
 */
public class Processo 
{    
    private int tempoChegada;
    private int tempoTermino;
    private int tempoParaProcessar;
    private int prioridade;
    private boolean terminado;
    private String cor;
    public Processo(int newTempoChegada, int newTempoProcessar, int newPrioridade, String newCor)
    {
        tempoChegada = newTempoChegada;
        tempoTermino=-1;
        tempoParaProcessar = newTempoProcessar;
        prioridade = newPrioridade;
        terminado = false;
        cor = newCor;
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
}
