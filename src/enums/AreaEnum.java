/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enums;

import java.util.Objects;
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

    String descricao = StringsUtils.VAZIA;

    private AreaEnum(Integer cod, String desc) {
        codigo = cod;
        descricao = desc;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     *
     * @param desc
     * @return enum correspondente
     */
    public static AreaEnum getByDescricao(String desc) {
        for (AreaEnum en : AreaEnum.values()) {
            if (en.getDescricao().equalsIgnoreCase(desc)) {
                return en;
            }
        }
        return null;
    }

    /**
     *
     * @param cod
     * @return enum correspondente
     */
    public static AreaEnum getByCodigo(Integer cod) {
        for (AreaEnum en : AreaEnum.values()) {
            if (Objects.equals(cod, en.getCodigo())) {
                return en;
            }
        }
        return null;
    }

}
