package control;

import bean.Atividade;
import bean.Funcionario;
import bean.Individuo;
import frame.AG;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import utils.Numeros;
import utils.StringsUtils;

/**
 *
 * @author Celso Souza
 * @version 1.0
 */
public class Controle {

    private static List<Funcionario> listaFunc = new ArrayList<>();
    private static List<Atividade> listaAtv = new ArrayList<>();

    private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory(StringsUtils.ENTITY_MANAGER);
    private static final EntityManager manager = factory.createEntityManager();

    public static List<Individuo> start() {
        List<Individuo> populacao = new ArrayList<>();
        List<Individuo> populacaoAlterada = new ArrayList<>();

        populacao = gerarrPopulacao();
        populacao = avaliarPopulacao(populacao);

        for (int i = 0; i < 300; i++) {
            for (int j = 0; j < 49; j++) {
                populacaoAlterada = cruzarPaisAoMeio(populacao);
            }
            populacao = avaliarPopulacao(populacaoAlterada);
            System.out.println("Geração: " + i);
        }
        return populacao;
    }

    public static void loadFuncionario(Stage stage) {
        
        Integer codigo = Integer.parseInt(JOptionPane.showInputDialog(StringsUtils.MSG_INFORME_CODIGO));
        Funcionario f = manager.find(Funcionario.class, codigo);
        
        AG.loadFuncFrame(stage, f);
    }

    public static List<Individuo> gerarrPopulacao() {
        List<Individuo> listaIndividuos;
        listaIndividuos = new ArrayList<>();
        Individuo ind;
        Integer qtd = 100;//Integer.parseInt(JOptionPane.showInputDialog(null, StringsUtils.MSG_INFORME_IDIVIDUOS));

        for (int i = 0; i < qtd; i++) {
            ind = new Individuo();
            ind.setAtividades(retornaAtvAssociadas());
            listaIndividuos.add(ind);
        }

        return listaIndividuos;
    }

    private static List<Atividade> retornaAtvAssociadas() {
        Integer maxAtv = Numeros.ZERO;
        Integer index = null;

        listaFunc = (List<Funcionario>) manager.createQuery(StringsUtils.SELECT_FUNCIONARIO).getResultList();
        listaAtv = (List<Atividade>) manager.createQuery(StringsUtils.SELECT_ATIVIDADE).getResultList();

        maxAtv = listaAtv.size() / listaFunc.size();
        if (listaAtv.size() % listaFunc.size() > Numeros.ZERO) {
            maxAtv++;
        }

        do {
            for (Atividade atividade : listaAtv) {
                if (atividade.getResponsavel() == null) {
                    Boolean ficar = Boolean.TRUE;
                    do {
                        index = (int) (Math.random() * listaFunc.size());
                        if (listaFunc.get(index).getAtividades().size() < maxAtv) {
                            listaFunc.get(index).getAtividades().add(atividade);
                            atividade.setResponsavel(listaFunc.get(index));
                            ficar = Boolean.FALSE;
                        }
                    } while (ficar);
                }
            }
        } while (verificarAssociacaoAtv(listaAtv));

        return listaAtv;
    }

    public static List<Individuo> avaliarPopulacao(List<Individuo> populacao) {
        float nota = Numeros.ZERO_FLOAT;

        for (Individuo ind : populacao) {
            nota = Numeros.ZERO_FLOAT;
            for (Atividade atv : ind.getAtividades()) {
                /**
                 * VERIFICAR SE ÁREA É IGUAL
                 */
                if (atv.getArea().equals(atv.getResponsavel().getArea())) {
                    nota += 1;
                } else {
                    nota += 0.1;
                }

                /*VERIFICAR SE NIVEL É IGUAL*/
                if (atv.getNivel().equals(atv.getResponsavel().getCargo())) {
                    nota += 1;
                } else if (atv.getNivel().getCodigo() < atv.getResponsavel().getCargo().getCodigo()) {
                    nota += 0.5;
                } else if (atv.getNivel().getCodigo() > atv.getResponsavel().getCargo().getCodigo()) {
                    nota += 0.1;
                }

            }
            ind.setNota(nota);
        }

        Collections.sort(populacao, new Comparator<Individuo>() {
            @Override
            public int compare(Individuo o1, Individuo o2) {
                Individuo ind01 = o1;
                Individuo ind02 = o2;

                if (ind01.getNota() > ind02.getNota()) {
                    return -1;
                } else if (ind01.getNota() < ind02.getNota()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        /*
        for (Individuo ind : populacao) {
            System.out.println("Indivídou: " + ind + " Nota: " + ind.getNota());
        }
         */
        return populacao;
    }

    /**
     * @param pai01
     * @param pai02
     *
     * @return descendente gerado a partir dos pais selecionados
     *
     * Gera novo individuo cruzando
     */
    public static Individuo cruzarGenesIntercalados(Individuo pai01, Individuo pai02) {
        Individuo descendente = new Individuo();
        
        /*Valor temporário para teste*/
        for (int i = 0; i < 41; i++) {
            descendente.getAtividades().add(pai01.getAtividades().get(i));
            descendente.getAtividades().add(pai02.getAtividades().get(i + 1));
        }
        return descendente;
    }

    /**
     * *
     * @param pai01
     * @param pai02
     *
     * @return descendente gerado a partir dos pais selecionados
     *
     * Gera um novo individuo juntado metade de cada pai
     */
    public static List<Individuo> cruzarPaisAoMeio(List<Individuo> populacao) {        
        List<Individuo> descendentes = new ArrayList<>();
        
        List<Atividade> genesPai01 = new ArrayList<>();
        List<Atividade> genesPai02 = new ArrayList<>();
        List<Atividade> geneDescendente;
        
        for (int i = 0; i < 49; i++) {
            genesPai01 = populacao.get(i).getAtividades().subList(0, 30);
            genesPai02 = populacao.get(99 - i).getAtividades().subList(30, 60);
            
            geneDescendente = new ArrayList<>();
            geneDescendente.addAll(genesPai01);
            geneDescendente.addAll(genesPai02);            
            
            descendentes.add(new Individuo(geneDescendente));
            
            /**************************************************************************/
            
            geneDescendente = new ArrayList<>();
            
            geneDescendente.addAll(genesPai02);
            geneDescendente.addAll(genesPai01);            
            
            descendentes.add(new Individuo(geneDescendente));
        }                        
        return descendentes;
    }

    public static Individuo mutarIndividuo(Individuo ind) {
        Funcionario func = new Funcionario();
        Funcionario funcAux = new Funcionario();
        int index;
        int indexAux;
        for (int i = 0; i < 2; i++) {

            index = (int) (ind.getAtividades().size() * Math.random());
            indexAux = (int) (ind.getAtividades().size() * Math.random());

            func = ind.getAtividades().get(index).getResponsavel();
            funcAux = ind.getAtividades().get(indexAux).getResponsavel();

            ind.getAtividades().get(index).setResponsavel(funcAux);
            ind.getAtividades().get(indexAux).setResponsavel(func);
        }
        return ind;
    }

    /**
     *
     */
    private static boolean verificarAssociacaoAtv(List<Atividade> listaAtv) {
        for (Atividade atividade : listaAtv) {
            if (atividade.getResponsavel() == null) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
