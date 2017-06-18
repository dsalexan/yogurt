package main;

import com.sun.org.apache.regexp.internal.RE;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Danilo on 11/06/2017.
 */
public class Diagrama {
    ArrayList<Registro> registros;
    Algoritmo algoritmo;

    public class Registro{
        Processo processo;
        int inicio, fim;
        String cor;

        public Registro(int i, int f, Processo p, String c){
            this.processo = p;
            this.inicio = i;
            this.fim = f;
            this.cor = c;
        }
    }

    public Diagrama(String a){
        this.registros = new ArrayList<>();
        this.algoritmo = new Algoritmo(a);
    }

    public Diagrama(ArrayList<DiagramaGantt> gantt){
        this("Não especificado");

        for(DiagramaGantt d : gantt){
            this.Adicionar(d.tempoIni, d.tempoFim, d.processo, "#FAFAFA");
        }
    }

    public void setAlgoritmo(String a){
        this.algoritmo.nome = a;
    }
    public Algoritmo getAlgoritmo(){
        return this.algoritmo;
    }

    public void calcAverageWaiting(){
        double d = 0.0;

        for(Processo p: getAllProcess()) d += p.getTempoEspera();

        this.algoritmo.tempoMedio = d/getAllProcess().size();
    }

    public ArrayList<Processo> getAllProcess(){
        ArrayList<Processo> ps = new ArrayList<>();

        for(Registro r: this.registros){
            if(!ps.contains(r.processo) && r.processo != null)
                ps.add(r.processo);
        }

        return ps;
    }


    public void Adicionar(int inicio, int fim, Processo processo, String cor){
        this.registros.add(new Registro(inicio, fim, processo, cor));
    }
    
    public int getTempoInicial(){
        return this.registros.get(0).inicio;
    }

    public int getTempoFinal(){
        return this.registros.get(this.registros.size()-1).fim;
    }
}
