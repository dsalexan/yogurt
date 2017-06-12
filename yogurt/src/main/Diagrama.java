package main;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Danilo on 11/06/2017.
 */
public class Diagrama {
    ArrayList<Registro> registros;

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

    public Diagrama(){
        this.registros = new ArrayList<>();
    }

    public Diagrama(ArrayList<DiagramaGantt> gantt){
        this.registros = new ArrayList<>();

        for(DiagramaGantt d : gantt){
            this.Adicionar(d.tempoIni, d.tempoFim, d.processo, "#FAFAFA");
        }
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
