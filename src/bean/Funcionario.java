/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import utils.StringsUtils;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Celso Souza
 */
@Entity
@Table(name = "Funcionario")
public class Funcionario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    private Integer codigo;

    @Column
    private String nome = StringsUtils.VAZIA;

    @OneToOne
    private Cargo cargo = null;

    @Column
    private Integer tempo_exp = null;

    @Column
    private Integer tempo_proj = null;

    @OneToOne
    private Area area = null;

    @OneToMany(mappedBy = "responsavel")
    private List<Atividade> atividades = null;

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

    public Integer getTempo_exp() {
        return tempo_exp;
    }

    public void setTempo_exp(Integer tempo_exp) {
        this.tempo_exp = tempo_exp;
    }

    public Integer getTempo_proj() {
        return tempo_proj;
    }

    public void setTempo_proj(Integer tempo_proj) {
        this.tempo_proj = tempo_proj;
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

    public List<Atividade> getAtividades() {
        return atividades;
    }

    public void setAtividades(List<Atividade> atividades) {
        this.atividades = atividades;
    }
}
