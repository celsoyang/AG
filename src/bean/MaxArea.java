/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import utils.Numeros;

/**
 *
 * @author celso
 */
public class MaxArea {

    Integer qtdDev = Numeros.ZERO;
    
    Integer qtdBanco = Numeros.ZERO;
    
    Integer qtdTest = Numeros.ZERO;
    
    Integer qtdAnaliseSoft = Numeros.ZERO;
    
    Integer qtdAnaliseReq = Numeros.ZERO;

    public Integer getQtdDev() {
        return qtdDev;
    }

    public void setQtdDev(Integer qtdDev) {
        this.qtdDev = qtdDev;
    }

    public Integer getQtdBanco() {
        return qtdBanco;
    }

    public void setQtdBanco(Integer qtdBanco) {
        this.qtdBanco = qtdBanco;
    }

    public Integer getQtdTest() {
        return qtdTest;
    }

    public void setQtdTest(Integer qtdTest) {
        this.qtdTest = qtdTest;
    }

    public Integer getQtdAnaliseSoft() {
        return qtdAnaliseSoft;
    }

    public void setQtdAnaliseSoft(Integer qtdAnaliseSoft) {
        this.qtdAnaliseSoft = qtdAnaliseSoft;
    }

    public Integer getQtdAnaliseReq() {
        return qtdAnaliseReq;
    }

    public void setQtdAnaliseReq(Integer qtdAnaliseReq) {
        this.qtdAnaliseReq = qtdAnaliseReq;
    }
}
