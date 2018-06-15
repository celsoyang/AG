/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import utils.Numeros;

/**
 *
 * @author celso
 */
@Entity
@Table(name = "Registro")
public class Registro implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cod;

    @Column
    private Integer qtdGeracoes = Numeros.ZERO;

    @Column
    private float melhorNota = Numeros.ZERO;

    @Column
    private Integer qtdMutacoes = Numeros.ZERO;

    @Column
    private Integer qtdCruzamentos = Numeros.ZERO;
    
    @Column
    private long tempoGasto = Numeros.ZERO_LONG;

    public Registro() {
    }

    public Registro(Integer QG, Integer QM, float MN, Integer QC, long TG) {
        setQtdGeracoes(QG);
        setQtdMutacoes(QM);
        setMelhorNota(MN);
        setQtdCruzamentos(QC);
        setTempoGasto(TG);
    }

    public Integer getQtdCruzamentos() {
        return qtdCruzamentos;
    }

    public void setQtdCruzamentos(Integer qtdCruzamentos) {
        this.qtdCruzamentos = qtdCruzamentos;
    }

    public Integer getQtdGeracoes() {
        return qtdGeracoes;
    }

    public void setQtdGeracoes(Integer qtdGeracoes) {
        this.qtdGeracoes = qtdGeracoes;
    }

    public float getMelhorNota() {
        return melhorNota;
    }

    public void setMelhorNota(float melhorNota) {
        this.melhorNota = melhorNota;
    }

    public Integer getQtdMutacoes() {
        return qtdMutacoes;
    }

    public void setQtdMutacoes(Integer qtdMutacoes) {
        this.qtdMutacoes = qtdMutacoes;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    public long getTempoGasto() {
        return tempoGasto;
    }

    public void setTempoGasto(long tempoGasto) {
        this.tempoGasto = tempoGasto;
    }
}
