/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import utils.StringsUtils;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Celso Souza
 */
@Entity
@Table(name="funcionario")
public class Funcionario implements Serializable {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer codigo = null;

    @Column
    private String nome = StringsUtils.VAZIA;

    @OneToOne
    private Cargo cargo = null;

    @Column
    private Integer tempoExp = null;
    
    @Column
    private Integer tempoProj = null;
    
    @OneToOne
    private Area area = null;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getTempoExp() {
        return tempoExp;
    }

    public void setTempoExp(Integer tempoExp) {
        this.tempoExp = tempoExp;
    }

    public Integer getTempoProj() {
        return tempoProj;
    }

    public void setTempoProj(Integer tempoProj) {
        this.tempoProj = tempoProj;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
    
}