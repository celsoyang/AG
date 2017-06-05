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
    
    
    
}
