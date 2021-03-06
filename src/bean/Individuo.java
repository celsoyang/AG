/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.ArrayList;
import java.util.List;
import utils.Numeros;

/**
 *
 * @author celso
 */
public class Individuo {

    private List<Atividade> atividades = new ArrayList<>();

    private float nota = Numeros.ZERO_FLOAT;

    public Individuo() {
    }

    public Individuo(List<Atividade> listaAtv) {
        setAtividades(listaAtv);
    }
    
    public Individuo(Individuo ind) {
        setAtividades(ind.getAtividades());
        setNota(ind.nota);
    }

    public List<Atividade> getAtividades() {
        return atividades;
    }

    public void setAtividades(List<Atividade> atividades) {
        this.atividades = atividades;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }
}
