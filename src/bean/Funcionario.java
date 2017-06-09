/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import enums.AreaEnum;
import utils.StringsUtils;
import enums.CargoEnum;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    @Column
    private String cargo = null;

    @Column
    private String tempoExp = null;
    
    @Column
    private String tempoProj = null;
    
    @Column
    private String area = null;

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

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getTempoExp() {
        return tempoExp;
    }

    public void setTempoExp(String tempoExp) {
        this.tempoExp = tempoExp;
    }

    public String getTempoProj() {
        return tempoProj;
    }

    public void setTempoProj(String tempoProj) {
        this.tempoProj = tempoProj;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
    
}