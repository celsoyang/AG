/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import utils.StringsUtils;

/**
 *
 * @author ceolivei
 */
public class Associacao {
    
    private List<Atividade> atividades = null;
    private List<Funcionario> funcionarios = null;
    private Boolean[][] associacao = null;
    private Integer linhas = null;
    private Integer colunas = null;
    
    public Associacao(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(StringsUtils.ENTITY_MANAGER);
        EntityManager manager = factory.createEntityManager();
        
        atividades = (List<Atividade>) manager.createQuery("select atv from Atividade atv").getResultList();        
        funcionarios = (List<Funcionario>) manager.createQuery("select f from Funcionario f").getResultList();
        
        linhas = funcionarios.size();
        colunas = atividades.size();
        
        associacao = new Boolean[linhas][colunas];
        
        iniciarAssociacao(atividades, funcionarios);
    }

    private void iniciarAssociacao(List<Atividade> atv, List<Funcionario> func) {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                if(Objects.equals(atv.get(j).getResponsavel(), func.get(i))){
                    associacao[i][j] = Boolean.TRUE;
                } else {
                    associacao[i][j] = Boolean.FALSE;
                }
            }
        }
    }
    
    public void apresentarAssociacao() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                System.out.print(" - " + associacao[i][j]);
            }
            System.out.println("");
        }
    }
    
}
