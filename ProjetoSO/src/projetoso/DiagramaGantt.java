/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetoso;

/**
 *
 * @author rafae
 */
public class DiagramaGantt //cada objeto representa um nó do diagrama
{
    Processo processo;
    int tempoIni, tempoFim;
    public DiagramaGantt(int tempoIni, int tempoFim, Processo processo)
    {
        this.tempoIni=tempoIni;
        this.tempoFim=tempoFim;
        this.processo=processo;
    }
}
