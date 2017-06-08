/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import enums.AreaEnum;
import utils.StringsUtils;
import enums.CargoEnum;

/**
 *
 * @author Celso Souza
 */
public class Funcionario {

    
    private Integer codigo = null;

    private String nome = StringsUtils.VAZIA;

    private CargoEnum cargo = null;

    private Double tempoExp = null;
    
    private Double tempoProj = null;
    
    private AreaEnum area = null;

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

    public CargoEnum getCargo() {
        return cargo;
    }

    public void setCargo(CargoEnum cargo) {
        this.cargo = cargo;
    }

    public Double getTempoExp() {
        return tempoExp;
    }

    public void setTempoExp(Double tempoExp) {
        this.tempoExp = tempoExp;
    }

    public Double getTempoProj() {
        return tempoProj;
    }

    public void setTempoProj(Double tempoProj) {
        this.tempoProj = tempoProj;
    }

    public AreaEnum getArea() {
        return area;
    }

    public void setArea(AreaEnum area) {
        this.area = area;
    }    
}