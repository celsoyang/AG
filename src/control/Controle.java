package control;

import bean.Atividade;
import bean.Funcionario;
import bean.Individuo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import utils.Numeros;
import utils.StringsUtils;

/**
 *
 * @author Celso Souza
 * @version 1.0
 */
public class Controle {

    public static List<Individuo> start() {
        List<Individuo> populacao = new ArrayList<>();
        List<Individuo> populacaoAlterada = new ArrayList<>();

        populacao = gerarPopulacao();
        populacao = avaliarPopulacao(populacao);

        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 50; j++) {
                populacaoAlterada = cruzarIndividuos(populacao);
            }
            populacao = avaliarPopulacao(populacaoAlterada);

            int idx = (int) (Math.random() * populacao.size());

            populacao.get(idx).setAtividades(mutarIndividuo(populacao.get(idx)).getAtividades());

            System.out.println("Geração: " + i + " Melhor nota: " + populacao.get(0).getNota());
        }
        return populacao;
    }

    public void loadFuncionario(Stage stage) {
//        Integer codigo = Integer.parseInt(JOptionPane.showInputDialog(StringsUtils.MSG_INFORME_CODIGO));
//        Funcionario f = manager.find(Funcionario.class, codigo);
//
//        AG.loadFuncFrame(stage, f);
    }

    public static List<Individuo> gerarPopulacao() {
        List<Individuo> listaIndividuos = new ArrayList<>();
        Integer qtd = 100;
        Individuo indiv;

        for (int i = 0; i < qtd; i++) {
            indiv = new Individuo();

            indiv.setAtividades(retornarAtvAssociadas());

            listaIndividuos.add(indiv);
        }
        return listaIndividuos;
    }

    private static List<Atividade> retornarAtvAssociadas() {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory(StringsUtils.ENTITY_MANAGER);
        EntityManager manager = factory.createEntityManager();

        Integer index = null;

        List<Funcionario> listaFunc = new ArrayList<>();
        List<Atividade> listaAtv = new ArrayList<>();

        listaFunc = (List<Funcionario>) manager.createQuery(StringsUtils.SELECT_FUNCIONARIO).getResultList();
        listaAtv = (List<Atividade>) manager.createQuery(StringsUtils.SELECT_ATIVIDADE).getResultList();

        for (Atividade atividade : listaAtv) {
            atividade.setResponsavel(null);
        }

        for (Funcionario funcionario : listaFunc) {
            funcionario.setAtividades(new ArrayList<>());
        }

        do {
            for (Atividade atividade : listaAtv) {
                if (atividade.getResponsavel() == null) {
                    Boolean ficar = Boolean.TRUE;
                    do {
                        index = (int) (Math.random() * listaFunc.size());
                        if (listaFunc.get(index).getAtividades().size() < 2) {
                            listaFunc.get(index).getAtividades().add(atividade);
                            atividade.setResponsavel(listaFunc.get(index));
                            ficar = Boolean.FALSE;
                        }
                    } while (ficar);
                }
            }
        } while (verificarAssociacaoAtv(listaAtv));

        manager.close();

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
        return populacao.subList(0, 100);
    }

    /**
     * *
     * @param populacao
     *
     * @return descendente gerado a partir dos pais selecionados
     *
     * Gera um novo individuo juntado metade de cada pai
     */
    public static List<Individuo> cruzarIndividuos(List<Individuo> populacao) {
        List<Individuo> descendentes = new ArrayList<>();

        List<Atividade> indv01 = new ArrayList<>();
        List<Atividade> indv02 = new ArrayList<>();

        List<Funcionario> pai01 = new ArrayList<>();
        List<Funcionario> pai02 = new ArrayList<>();

        List<Funcionario> cromossomoXPai01 = new ArrayList<>();
        List<Funcionario> cromossomoYPai01 = new ArrayList<>();

        List<Funcionario> cromossomoXPai02 = new ArrayList<>();
        List<Funcionario> cromossomoYPai02 = new ArrayList<>();

        Individuo geneDescendente;

        for (int i = 0; i < 50; i++) {
            indv01 = populacao.get(i).getAtividades();
            indv02 = populacao.get(99 - i).getAtividades();

            for (Atividade atv : indv01) {
                pai01.add(atv.getResponsavel());
            }

            for (Atividade atv : indv02) {
                pai02.add(atv.getResponsavel());
            }

            cromossomoXPai01 = pai01.subList(0, 30);
            cromossomoYPai01 = pai01.subList(30, 60);

            cromossomoXPai02 = pai02.subList(0, 30);
            cromossomoYPai02 = pai02.subList(30, 60);

            /**
             * *****************************************************************************************
             * CRUZAMENTO DE CROMOSSOMOS X01 - X02
             * *****************************************************************************************
             */
            geneDescendente = new Individuo(populacao.get(i));

            for (int a = 0; a < 30; a++) {
                geneDescendente.getAtividades().get(a).setResponsavel(cromossomoXPai01.get(a));
            }

            for (int b = 0; b < 30; b++) {
                geneDescendente.getAtividades().get(b + 30).setResponsavel(cromossomoXPai02.get(b));
            }

            descendentes.add(geneDescendente);

            /**
             * *****************************************************************************************
             * CRUZAMENTO DE CROMOSSOMOS X01 - Y02
             * *****************************************************************************************
             */
            geneDescendente = new Individuo(populacao.get(i));

            for (int j = 0; j < 30; j++) {
                geneDescendente.getAtividades().get(j).setResponsavel(cromossomoXPai01.get(j));
            }

            for (int k = 0; k < 30; k++) {
                geneDescendente.getAtividades().get(k + 30).setResponsavel(cromossomoYPai02.get(k));
            }

            descendentes.add(new Individuo(geneDescendente));

            /**
             * *****************************************************************************************
             * CRUZAMENTO DE CROMOSSOMOS Y01 - X02
             * *****************************************************************************************
             */
            geneDescendente = new Individuo(populacao.get(i));

            for (int j = 0; j < 30; j++) {
                geneDescendente.getAtividades().get(j).setResponsavel(cromossomoYPai01.get(j));
            }

            for (int k = 0; k < 30; k++) {
                geneDescendente.getAtividades().get(k + 30).setResponsavel(cromossomoXPai02.get(k));
            }

            descendentes.add(new Individuo(geneDescendente));

            /**
             * *****************************************************************************************
             * CRUZAMENTO DE CROMOSSOMOS Y01 - Y02
             * *****************************************************************************************
             */
            geneDescendente = new Individuo(populacao.get(i));

            for (int j = 0; j < 30; j++) {
                geneDescendente.getAtividades().get(j).setResponsavel(cromossomoYPai01.get(j));
            }

            for (int k = 0; k < 30; k++) {
                geneDescendente.getAtividades().get(k + 30).setResponsavel(cromossomoYPai02.get(k));
            }

            descendentes.add(new Individuo(geneDescendente));

        }
        return descendentes;
    }

    public static Individuo mutarIndividuo(Individuo ind) {
        Funcionario func = new Funcionario();
        Funcionario funcAux = new Funcionario();
        int index;

        index = (int) (Math.random() * ind.getAtividades().size());

        for (int i = 0; i < ind.getAtividades().size(); i++) {
            if (!Objects.equals(ind.getAtividades().get(i).getResponsavel().getArea().getCodigo(), ind.getAtividades().get(i).getArea().getCodigo())) {
                func = ind.getAtividades().get(index).getResponsavel();
                index = i;
                break;
            }
        }

        for (Atividade atv : ind.getAtividades()) {
            if (Objects.equals(atv.getArea().getCodigo(), func.getArea().getCodigo())
                    && Objects.equals(atv.getNivel().getCodigo(), func.getCargo().getCodigo())) {
                funcAux = atv.getResponsavel();

                atv.setResponsavel(func);

                ind.getAtividades().get(index).setResponsavel(funcAux);
            }
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
