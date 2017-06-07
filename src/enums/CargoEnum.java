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
public enum CargoEnum {

    PROGRAMADOR_JR(1, "Programador Jr.", 1),
    PROGRAMADOR_PL(2, "Programador Pleno", 2),
    ANALISTA_JR(3, "Analista Jr.", 3),
    ANALISTA_PL(4, "Analista Pleno", 4),
    ANLISTA_SR(5, "Analista Sênior", 5);

    private Integer codigo = null;

    private String descricao = StringsUtils.VAZIA;

    private Integer level = null;

    private CargoEnum(Integer cod, String desc, Integer lvl) {
        codigo = cod;
        descricao = desc;
        level = lvl;
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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     *
     * @param desc
     * @return enum correspondente
     */
    public static CargoEnum getByDescricao(String desc) {
        for (CargoEnum en : CargoEnum.values()) {
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
    public static CargoEnum getByCodigo(Integer cod) {
        for (CargoEnum en : CargoEnum.values()) {
            if (Objects.equals(en.getCodigo(), cod)) {
                return en;
            }
        }
        return null;
    }
}
