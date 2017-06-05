/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enums;

import utils.StringsUtils;

/**
 *
 * @author ceolivei
 */
public enum AreaEnum {

    DESENVOLVIMENTO(1, "Desenvolvimento"),
    ANALISE_SOFTWARE(2, "Análise de Software"),
    ANALISE_REQUISITOS(3, "Análise de Requisitos"),
    BANCO_DADOS(4, "Banco de Dados"),
    TESTE(5, "Teste");

    Integer codigo = null;

    String nome = StringsUtils.VAZIA;

    private AreaEnum(Integer cod, String desc) {
        codigo = cod;
        nome = desc;
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

}
