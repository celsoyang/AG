/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import enums.AreaEnum;
import java.io.Serializable;
import utils.StringsUtils;

/**
 *
 * @author ceolivei
 */
public class Atividade implements Serializable {
    
    private Integer codigo = null;
        
    private String nome = StringsUtils.VAZIA;
    
    private Integer nivel = null;
    
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

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public AreaEnum getArea() {
        return area;
    }

    public void setArea(AreaEnum area) {
        this.area = area;
    }
    
}
