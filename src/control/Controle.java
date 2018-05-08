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

    private static Integer qtdFunc = Numeros.ZERO;
    private static Integer qtdAtv = Numeros.ZERO;

    public static Individuo start() {
        List<Individuo> populacao = new ArrayList<>();
        List<Individuo> populacaoAlterada = new ArrayList<>();

        populacao = gerarPopulacao();
        populacao = avaliarPopulacao(populacao);

        Individuo melhorInd = new Individuo();

        for (int i = 0; i < Numeros.NUMERO_GERACOES; i++) {
            for (int j = 0; j < Numeros.NUMERO_INDIVIDUOS / 2; j++) {
                populacaoAlterada = cruzarIndividuos(populacao);
            }
            populacao = avaliarPopulacao(populacaoAlterada);

            for (Individuo individuo : populacao) {
                if (individuo.getNota() > melhorInd.getNota()) {
                    melhorInd = new Individuo(individuo);
                }
            }

            System.out.println("Geração: " + i + " Melhor nota: " + melhorInd.getNota());
        }
        return melhorInd;
    }

    public void loadFuncionario(Stage stage) {
//        Integer codigo = Integer.parseInt(JOptionPane.showInputDialog(StringsUtils.MSG_INFORME_CODIGO));
//        Funcionario f = manager.find(Funcionario.class, codigo);
//
//        AG.loadFuncFrame(stage, f);
    }

    public static List<Individuo> gerarPopulacao() {
        List<Individuo> listaIndividuos = new ArrayList<>();
        Individuo indiv;

        EntityManagerFactory factory = Persistence.createEntityManagerFactory(StringsUtils.ENTITY_MANAGER);
        EntityManager manager = factory.createEntityManager();

        List<Funcionario> listaFunc = new ArrayList<>();
        List<Atividade> listaAtv = new ArrayList<>();

        listaFunc = (List<Funcionario>) manager.createQuery(StringsUtils.SELECT_FUNCIONARIO).getResultList();
        listaAtv = (List<Atividade>) manager.createQuery(StringsUtils.SELECT_ATIVIDADE).getResultList();

        qtdFunc = listaFunc.size();
        qtdAtv = listaAtv.size();

        for (int i = 0; i < Numeros.NUMERO_INDIVIDUOS; i++) {
            indiv = new Individuo();

            indiv.setAtividades(retornarAtvAssociadas(listaFunc, listaAtv));

            listaIndividuos.add(indiv);
            System.out.println("Indivíduo: " + i + " Gerado");
        }
        System.out.println("Indivíduos Gerados");
        manager.close();
        return listaIndividuos;
    }

    private static List<Atividade> retornarAtvAssociadas(List<Funcionario> listaFunc, List<Atividade> listaAtv) {

        List<Funcionario> listaF = new ArrayList<>();
        List<Atividade> listaA = new ArrayList<>();

        for (Atividade atividade : listaAtv) {
            listaA.add(new Atividade(atividade));
        }

        for (Funcionario funcionario : listaFunc) {
            listaF.add(new Funcionario(funcionario));
        }

        Integer index = null;

        for (Atividade atividade : listaA) {
            atividade.setResponsavel(null);
        }

        for (Funcionario funcionario : listaF) {
            funcionario.setAtividades(new ArrayList<>());
        }

        do {
            for (Atividade atividade : listaA) {
                if (atividade.getResponsavel() == null) {
                    Boolean ficar = Boolean.TRUE;
                    do {
                        index = (int) (Math.random() * listaF.size());
                        if (listaF.get(index).getAtividades().size() < 3) {
                            listaF.get(index).getAtividades().add(atividade);
                            atividade.setResponsavel(listaF.get(index));
                            ficar = Boolean.FALSE;
                        }
                    } while (ficar);
                }
            }
        } while (verificarAssociacaoAtv(listaA));

        return listaA;
    }

    @SuppressWarnings("Convert2Lambda")
    public static List<Individuo> avaliarPopulacao(List<Individuo> populacao) {
        float nota = Numeros.ZERO_FLOAT;

        for (Individuo ind : populacao) {
            nota = Numeros.ZERO_FLOAT;
            for (Atividade atv : ind.getAtividades()) {
                /**
                 * VERIFICAR SE ÁREA É IGUAL
                 */
                if (Objects.equals(atv.getArea().getCodigo(), atv.getResponsavel().getArea().getCodigo())) {
                    nota += 2;
                } else {
                    nota += 0.1;
                }

                /*VERIFICAR SE NIVEL É IGUAL*/
                if (Objects.equals(atv.getNivel().getCodigo(), atv.getResponsavel().getCargo().getCodigo())) {
                    nota += 2;
                } else if (atv.getNivel().getCodigo() < atv.getResponsavel().getCargo().getCodigo()) {
                    nota += 1;
                } else if (atv.getNivel().getCodigo() > atv.getResponsavel().getCargo().getCodigo()) {
                    nota += 0.1;
                }
                nota += atv.getResponsavel().getTempo_exp();
                nota += atv.getResponsavel().getTempo_proj();

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

        return populacao;
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

        for (int i = 0; i < Numeros.NUMERO_CRUZAMENTOS; i++) {
            indv01 = populacao.get(i).getAtividades();
            indv02 = populacao.get((Numeros.NUMERO_CRUZAMENTOS - 1) - i).getAtividades();

            for (Atividade atv : indv01) {
                pai01.add(atv.getResponsavel());
            }

            for (Atividade atv : indv02) {
                pai02.add(atv.getResponsavel());
            }

            cromossomoXPai01 = pai01.subList(0, qtdAtv / 2);
            cromossomoYPai01 = pai01.subList(qtdAtv / 2, qtdAtv);

            cromossomoXPai02 = pai02.subList(0, qtdAtv / 2);
            cromossomoYPai02 = pai02.subList(qtdAtv / 2, qtdAtv);

            /**
             * *****************************************************************************************
             * CRUZAMENTO DE CROMOSSOMOS X01 - X02
             * *****************************************************************************************
             */
            geneDescendente = new Individuo(populacao.get(i));

            for (int a = 0; a < qtdFunc; a++) {
                geneDescendente.getAtividades().get(a).setResponsavel(cromossomoXPai01.get(a));
            }

            for (int b = 0; b < qtdFunc; b++) {
                geneDescendente.getAtividades().get(b + qtdFunc).setResponsavel(cromossomoXPai02.get(b));
            }

            float mutar = (float) (Math.random() * 1);
            if (mutar < 0.3) {
                geneDescendente = mutarIndividuo(geneDescendente);
            }

            descendentes.add(geneDescendente);

            /**
             * *****************************************************************************************
             * CRUZAMENTO DE CROMOSSOMOS X01 - Y02
             * *****************************************************************************************
             */
            geneDescendente = new Individuo(populacao.get(i));

            for (int j = 0; j < qtdFunc; j++) {
                geneDescendente.getAtividades().get(j).setResponsavel(cromossomoXPai01.get(j));
            }

            for (int k = 0; k < qtdFunc; k++) {
                geneDescendente.getAtividades().get(k + qtdFunc).setResponsavel(cromossomoYPai02.get(k));
            }

            mutar = (float) (Math.random() * 1);
            if (mutar < 0.3) {
                geneDescendente = mutarIndividuo(geneDescendente);
            }

            descendentes.add(new Individuo(geneDescendente));

            /**
             * *****************************************************************************************
             * CRUZAMENTO DE CROMOSSOMOS Y01 - X02
             * *****************************************************************************************
             */
            geneDescendente = new Individuo(populacao.get(i));

            for (int j = 0; j < qtdFunc; j++) {
                geneDescendente.getAtividades().get(j).setResponsavel(cromossomoYPai01.get(j));
            }

            for (int k = 0; k < qtdFunc; k++) {
                geneDescendente.getAtividades().get(k + qtdFunc).setResponsavel(cromossomoXPai02.get(k));
            }

            mutar = (float) (Math.random() * 1);
            if (mutar < 0.3) {
                geneDescendente = mutarIndividuo(geneDescendente);
            }

            descendentes.add(new Individuo(geneDescendente));

            /**
             * *****************************************************************************************
             * CRUZAMENTO DE CROMOSSOMOS Y01 - Y02
             * *****************************************************************************************
             */
            geneDescendente = new Individuo(populacao.get(i));

            for (int j = 0; j < qtdFunc; j++) {
                geneDescendente.getAtividades().get(j).setResponsavel(cromossomoYPai01.get(j));
            }

            for (int k = 0; k < qtdFunc; k++) {
                geneDescendente.getAtividades().get(k + qtdFunc).setResponsavel(cromossomoYPai02.get(k));
            }

            mutar = (float) (Math.random() * 1);
            if (mutar < 0.3) {
                geneDescendente = mutarIndividuo(geneDescendente);
            }

            descendentes.add(new Individuo(geneDescendente));

        }
        descendentes = avaliarPopulacao(descendentes);
        return descendentes.subList(0, Numeros.NUMERO_INDIVIDUOS);
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
                break;
            }
        }

        return ind;
    }

    /**
     * Verifica se Existem atividades sem um responsável
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
