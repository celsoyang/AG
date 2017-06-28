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

/**
 *
 * @author ceolivei
 */
@Entity
public class Associacao implements Serializable {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo = null;
    
    private Integer[][] associacao = null;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer[][] getAssociacao() {
        return associacao;
    }

    public void setAssociacao(Integer[][] associacao) {
        this.associacao = associacao;
    }
}