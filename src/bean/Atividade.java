/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import utils.Numeros;
import utils.StringsUtils;

/**
 *
 * @author ceolivei
 */
@Entity
public class Atividade implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo = null;

    private String nome = StringsUtils.VAZIA;

    @OneToOne
    private Cargo nivel = null;

    @OneToOne
    private Area area = null;

    @ManyToOne
    private Funcionario responsavel = null;

    private int prazo = Numeros.ZERO;

    public Atividade() {
    }

    public Atividade(Atividade atv) {
        this.area = atv.getArea();
        this.codigo = atv.getCodigo();
        this.nivel = atv.getNivel();
        this.nome = atv.getNome();
        this.prazo = atv.getPrazo();
        this.responsavel = atv.getResponsavel();
    }

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

    public Cargo getNivel() {
        return nivel;
    }

    public void setNivel(Cargo nivel) {
        this.nivel = nivel;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Funcionario getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Funcionario responsavel) {
        this.responsavel = responsavel;
    }

    public int getPrazo() {
        return prazo;
    }

    public void setPrazo(int prazo) {
        this.prazo = prazo;
    }
}
