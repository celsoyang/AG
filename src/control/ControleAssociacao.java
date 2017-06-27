/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import bean.Associacao;
import bean.Atividade;
import bean.Funcionario;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Persistence;
import utils.Numeros;
import utils.StringsUtils;

/**
 *
 * @author ceolivei
 */
public class ControleAssociacao implements Serializable{
    
    private List<Atividade> atividades = null;
    private List<Funcionario> funcionarios = null;
    private Associacao associacao = null;
    private Integer linhas = null;
    private Integer colunas = null;
    
    public ControleAssociacao(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(StringsUtils.ENTITY_MANAGER);
        EntityManager manager = factory.createEntityManager();
        
        atividades = (List<Atividade>) manager.createQuery("select atv from Atividade atv").getResultList();        
        funcionarios = (List<Funcionario>) manager.createQuery("select f from Funcionario f").getResultList();
        
        linhas = funcionarios.size();
        colunas = atividades.size();
        
        associacao = new Associacao();
        associacao.setAssociacao(new Integer[linhas][colunas]);
        manager.close();
        factory.close();
    }

    private void criarMatrizAssociacoes(List<Atividade> atv, List<Funcionario> func) {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                if(Objects.equals(atv.get(j).getResponsavel(), func.get(i))){
                    associacao.getAssociacao()[i][j] = Numeros.UM;
                } else {
                    associacao.getAssociacao()[i][j] = Numeros.ZERO;
                }
            }
        }
    }
    
    private void apresentarAssociacao() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                System.out.print("  " + associacao.getAssociacao()[i][j]);
            }
            System.out.println("");
        }
    }
    
    public void associar(){
        int f;
        
        for(int i = 0; i < atividades.size(); i++){
            double val = Math.random() * funcionarios.size();
            f = (int)val;
            if(Objects.isNull(atividades.get(i).getResponsavel())){
                atividades.get(i).setResponsavel(funcionarios.get(f));
            }
        }
        criarMatrizAssociacoes(atividades, funcionarios);
        apresentarAssociacao();
        salvarAssociacao();
    }

    private void salvarAssociacao() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(StringsUtils.ENTITY_MANAGER);
        EntityManager manager = factory.createEntityManager();
        
        manager.getTransaction().begin();
        manager.persist(associacao);
        manager.getTransaction().commit();
        
        manager.close();
        factory.close();
    }
    
}
