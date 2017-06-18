package main;

/**
 * Created by Danilo on 17/06/2017.
 */
public class Algoritmo extends Object{
    String nome;
    double tempoMedio;

    public Algoritmo(String n){
        this(n, 0);
    }

    public Algoritmo(String n, double t){
        this.nome = n;
        this.tempoMedio = t;
    }
}
