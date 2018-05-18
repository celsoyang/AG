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

    private static int maxAtividadePorFuncionario;

    private static Integer medHoraDev = Numeros.ZERO;
    private static Integer medHoraAnalise = Numeros.ZERO;
    private static Integer medHoraAnaliseReq = Numeros.ZERO;
    private static Integer medHoraBanco = Numeros.ZERO;
    private static Integer medHoraTeste = Numeros.ZERO;

    public static Individuo start() {
        List<Individuo> populacao = new ArrayList<>();
        List<Individuo> populacaoAlterada = new ArrayList<>();

        populacao = gerarPopulacao();
        populacao = avaliarPopulacao(populacao);

        Individuo melhorInd = new Individuo();

        for (int i = 0; i < Numeros.NUMERO_GERACOES; i++) {

            populacaoAlterada = cruzarIndividuos(populacao);

            populacao = avaliarPopulacao(populacaoAlterada);

            for (Individuo individuo : populacao) {
                if (individuo.getNota() > melhorInd.getNota()) {
                    melhorInd = new Individuo(individuo);
                }
            }

//            System.out.println("Geração: " + i + " Melhor nota: " + melhorInd.getNota());
        }
        return melhorInd;
    }

    private static void caucularMediasArea(List<Atividade> listaAtv, List<Funcionario> listaFunc) {

        Integer qtdHoraDev = Numeros.ZERO;
        Integer qtdHoraAnalise = Numeros.ZERO;
        Integer qtdHoraAnaliseReq = Numeros.ZERO;
        Integer qtdHoraBanco = Numeros.ZERO;
        Integer qtdHoraTeste = Numeros.ZERO;

        Integer qtdFuncDev = Numeros.ZERO;
        Integer qtdFuncAnalise = Numeros.ZERO;
        Integer qtdFuncAnaliseReq = Numeros.ZERO;
        Integer qtdFuncBanco = Numeros.ZERO;
        Integer qtdFuncTeste = Numeros.ZERO;

        for (Atividade atv : listaAtv) {
            switch (atv.getArea().getCodigo()) {
                case 1:
                    qtdHoraDev += atv.getPrazo();
                    break;
                case 2:
                    qtdHoraAnalise += atv.getPrazo();
                    break;
                case 3:
                    qtdHoraAnaliseReq += atv.getPrazo();
                    break;
                case 4:
                    qtdHoraBanco += atv.getPrazo();
                    break;
                case 5:
                    qtdHoraTeste += atv.getPrazo();
                    break;
                default:
            }
        }

        for (Funcionario f : listaFunc) {
            switch (f.getArea().getCodigo()) {
                case 1:
                    qtdFuncDev++;
                    break;
                case 2:
                    qtdFuncAnalise++;
                    break;
                case 3:
                    qtdFuncAnaliseReq++;
                    break;
                case 4:
                    qtdFuncBanco++;
                    break;
                case 5:
                    qtdFuncTeste++;
                    break;
                default:
            }
        }

        medHoraDev = qtdHoraDev / qtdFuncDev;
        medHoraAnalise = qtdHoraAnalise / qtdFuncAnalise;
        medHoraBanco = qtdHoraBanco / qtdFuncBanco;
        medHoraTeste = qtdHoraTeste / qtdFuncTeste;
        medHoraAnaliseReq = qtdHoraAnaliseReq / qtdFuncAnaliseReq;
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
        
        Numeros.MAX_NOTA = listaAtv.size() * Numeros.VINTE;

        caucularMediasArea(listaAtv, listaFunc);

        maxAtividadePorFuncionario = listaAtv.size() / listaFunc.size();
        maxAtividadePorFuncionario += Numeros.UM;

        for (int i = 0; i < Numeros.NUMERO_INDIVIDUOS; i++) {
            indiv = new Individuo();

            indiv.setAtividades(retornarAtvAssociadas(listaFunc, listaAtv));

            listaIndividuos.add(indiv);
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
                        if (listaF.get(index).getAtividades().size() < maxAtividadePorFuncionario) {
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
                    nota += 10;
                } else {
                    nota += 1;
                }

                /*VERIFICAR SE NIVEL É IGUAL*/
                if (Objects.equals(atv.getNivel().getCodigo(), atv.getResponsavel().getCargo().getCodigo())) {
                    nota += 10;
                } else if (atv.getNivel().getCodigo() < atv.getResponsavel().getCargo().getCodigo()) {
                    nota += 1;
                }

                //VERIFICA BALANCEAMENTO DE HORAS
                int horas = Numeros.ZERO;
                for (Atividade at : atv.getResponsavel().getAtividades()) {
                    horas += at.getPrazo();
                }

                switch (atv.getResponsavel().getArea().getCodigo()) {
                    case 1:
                        nota += (10 - retornarDiferencaHoras(medHoraDev, horas));
                        break;
                    case 2:
                        nota += (10 - retornarDiferencaHoras(medHoraAnalise, horas));
                        break;
                    case 3:
                        nota += (10 - retornarDiferencaHoras(medHoraAnaliseReq, horas));
                        break;
                    case 4:
                        nota += (10 - retornarDiferencaHoras(medHoraBanco, horas));
                        break;
                    case 5:
                        nota += (10 - retornarDiferencaHoras(medHoraTeste, horas));
                        break;
                    default:
                        break;
                }
            }
            ind.setNota(nota);
        }

        /**
         * ORGANIZA O ARRAY LEVANDO EM CONSIDERAÇÃO A NOTA DO INDIVÍDUO
         */
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
        List<Individuo> melhoresPosCruzamento = new ArrayList<>();
        int indexPai01;
        int indexPai02;

        Individuo pai1 = new Individuo();
        Individuo pai2 = new Individuo();

        //int qtdCruzamentos = (int) (Math.random() * Numeros.MAX_CRUZAMENTOS);
        for (int i = 0; i < Numeros.MAX_CRUZAMENTOS; i++) {
            indexPai01 = (int) (Math.random() * populacao.size());

            do {
                indexPai02 = (int) (Math.random() * populacao.size());
            } while (indexPai01 == indexPai02);

            pai1 = new Individuo(populacao.get(indexPai01));
            pai2 = new Individuo(populacao.get(indexPai02));

            Individuo filho1 = new Individuo(pai1);
            Individuo filho2 = new Individuo(pai1);

            int pontoCruzamento = (int) (Math.random() * pai1.getAtividades().size());

            /**
             * PRIMEIRA METADE DO PAI1 COM SEGUNDA METADE DO PAI2
             */
            for (int j = 0; j < pontoCruzamento; j++) {
                filho1.getAtividades().get(j).setResponsavel(pai1.getAtividades().get(j).getResponsavel());
            }

            for (int k = pontoCruzamento; k < pai2.getAtividades().size(); k++) {
                filho1.getAtividades().get(k).setResponsavel(pai2.getAtividades().get(k).getResponsavel());
            }

            filho1 = mutarIndividuo(filho1);

            /**
             * PRIMEIRA METADE DO PAI2 COM SEGUNDA METADE DO PAI1
             */
            for (int l = 0; l < pontoCruzamento; l++) {
                filho2.getAtividades().get(l).setResponsavel(pai2.getAtividades().get(l).getResponsavel());
            }

            for (int m = pontoCruzamento; m < pai1.getAtividades().size(); m++) {
                filho2.getAtividades().get(m).setResponsavel(pai1.getAtividades().get(m).getResponsavel());
            }

            filho2 = mutarIndividuo(filho2);

            List<Individuo> lista = new ArrayList<>();

            lista.add(pai1);
            lista.add(pai2);
            lista.add(filho1);
            lista.add(filho2);

            melhoresPosCruzamento = avaliarPopulacao(lista);

            populacao.set(indexPai01, melhoresPosCruzamento.get(Numeros.ZERO));
            populacao.set(indexPai02, melhoresPosCruzamento.get(Numeros.UM));
        }
        return populacao;
    }

    public static Individuo mutarIndividuo(Individuo ind) {
        Funcionario func = new Funcionario();
        Funcionario funcAux = new Funcionario();
        int index;

        double mutar = (double) (Math.random() * 1);

        if (mutar > Numeros.PROBABILIDADE_MUTACAO) {

            index = (int) (Math.random() * ind.getAtividades().size());
            
            for (int i = 0; i < ind.getAtividades().size(); i++) {
                if (!Objects.equals(ind.getAtividades().get(i).getResponsavel().getArea().getCodigo(), ind.getAtividades().get(i).getArea().getCodigo())
                        || !Objects.equals(ind.getAtividades().get(i).getResponsavel().getCargo().getCodigo(), ind.getAtividades().get(i).getNivel().getCodigo())) {
                    func = new Funcionario(ind.getAtividades().get(index).getResponsavel());
                    index = i;
                    break;
                }
            }

            for (Atividade atv : ind.getAtividades()) {
                try {
                    if (Objects.equals(atv.getArea().getCodigo(), func.getArea().getCodigo())
                            && Objects.equals(atv.getNivel().getCodigo(), func.getCargo().getCodigo())) {
                        funcAux = new Funcionario(atv.getResponsavel());

                        atv.setResponsavel(func);

                        ind.getAtividades().get(index).setResponsavel(funcAux);
                        break;
                    }
                } catch (NullPointerException npe) {
                }
            }
        }
        return ind;
    }
    
    private static Integer retornarDiferencaHoras(int horaFunc, int mediaArea){
        Integer diferenca = Numeros.ZERO;
        
        if(horaFunc < mediaArea){
            diferenca = mediaArea - horaFunc;
        } else {
            diferenca = horaFunc - mediaArea ;
        }        
        return diferenca;
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
