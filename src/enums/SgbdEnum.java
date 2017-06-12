/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enums;

import java.util.Objects;

/**
 *
 * @author ceolivei
 */
public enum SgbdEnum {
    
    POSTGRE_SQL(1, "Postgre SQL", "ag", "jdbc:postgresql", 5432),
    SQL_SERVER(2, "Microsoft SQL Server", "Banco Server", "jdbc:sqlserver", 0000);
    
    private Integer codigo = null;
    
    private String descricao = null;
    
    private String banco = null;
    
    private String urlConnection = null;
    
    private Integer porta = null;
    
    private SgbdEnum(Integer cod, String desc, String bc, String url, Integer port){
        codigo = cod;
        descricao = desc;
        banco = bc;
        urlConnection = url;
        porta = port;
    }
    
    public static SgbdEnum getByCod(Integer cod){
        for (SgbdEnum en : SgbdEnum.values()) {
            if(Objects.equals(cod, en.getCodigo())){
                return en;
            }        
        }
        return null;
    }
    
    public static SgbdEnum getByDescricao(String desc){
        for (SgbdEnum en : SgbdEnum.values()) {
            if(Objects.equals(desc, en.getDescricao())){
                return en;
            }        
        }
        return null;
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

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getUrlConnection() {
        return urlConnection;
    }

    public void setUrlConnection(String urlConnection) {
        this.urlConnection = urlConnection;
    }

    public Integer getPorta() {
        return porta;
    }

    public void setPorta(Integer porta) {
        this.porta = porta;
    }
    
}
