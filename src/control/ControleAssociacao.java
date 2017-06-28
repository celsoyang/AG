/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import bean.Associacao;
import bean.Atividade;
import bean.Funcionario;
import ec.EvolutionState;
import ec.Evolve;
import ec.Individual;
import ec.Population;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import utils.Numeros;
import utils.StringsUtils;

/**
 *
 * @author ceolivei
 */
public class ControleAssociacao implements Serializable {

    private List<Atividade> atividades = null;
    private List<Funcionario> funcionarios = null;
    private Associacao associacao = null;
    private Integer linhas = null;
    private Integer colunas = null;

    public ControleAssociacao() {
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
                if (Objects.equals(atv.get(j).getResponsavel(), func.get(i))) {
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

    public void associar() {

        for (int i = 0; i < atividades.size(); i++) {
            if (Objects.isNull(atividades.get(i).getResponsavel())) {
                for (int j = 0; j < funcionarios.size(); j++) {
                    if (validarAssociacao(atividades.get(i), funcionarios.get(j))) {
                        atividades.get(i).setResponsavel(funcionarios.get(j));
                    }
                }
            }
        }
        criarMatrizAssociacoes(atividades, funcionarios);
        salvarAssociacao();
        apresentarAssociacao();
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

    public Double avaliarAssociacao(Associacao assoc) {        
        return null;
    }

    public Boolean validarAssociacao(Atividade atv, Funcionario func) {
        Boolean retorno = Boolean.FALSE;
        if (Objects.equals(atv.getArea(), func.getArea())) {
            retorno = Boolean.TRUE;
        } else if (Objects.equals(atv.getArea().getCodigo(), Numeros.UM)
                && Objects.equals(func.getArea().getCodigo(), Numeros.DOIS)) {
            retorno = Boolean.TRUE;
        }
        return retorno;
    }
    
    public void criarIndividuo(){
        
    }
}
