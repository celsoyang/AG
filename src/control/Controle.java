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

    private static Integer medHoraDevJr = Numeros.ZERO;
    private static Integer medHoraDevPl = Numeros.ZERO;

    private static Integer medHoraAnaliseJr = Numeros.ZERO;
    private static Integer medHoraAnalisePl = Numeros.ZERO;
    private static Integer medHoraAnaliseSr = Numeros.ZERO;

    private static Integer medHoraAnaliseReqJr = Numeros.ZERO;
    private static Integer medHoraAnaliseReqSr = Numeros.ZERO;

    private static Integer medHoraBancoJr = Numeros.ZERO;
    private static Integer medHoraBancoPl = Numeros.ZERO;
    private static Integer medHoraBancoAP = Numeros.ZERO;
    private static Integer medHoraBancoAS = Numeros.ZERO;

    private static Integer medHoraTesteJr = Numeros.ZERO;
    private static Integer medHoraTestePl = Numeros.ZERO;
    private static Integer medHoraTesteAP = Numeros.ZERO;

    public static Individuo start() {
        List<Individuo> populacao = new ArrayList<>();
        List<Individuo> populacaoAlterada = new ArrayList<>();

        populacao = gerarPopulacao();
        populacao = avaliarPopulacao(populacao);

        Individuo melhorInd = new Individuo();

        /*
        for (int i = 0; i < Numeros.NUMERO_GERACOES; i++) {

            populacaoAlterada = cruzarIndividuos(populacao);

            populacao = avaliarPopulacao(populacaoAlterada);

            for (Individuo individuo : populacao) {
                if (individuo.getNota() > melhorInd.getNota()) {
                    melhorInd = new Individuo(individuo);
                }
            }

            System.out.println("Geração: " + i + " Melhor nota: " + melhorInd.getNota());
        }
         */
        int i = 1;
        do {

            populacaoAlterada = cruzarIndividuos(populacao);

            populacao = avaliarPopulacao(populacaoAlterada);

            for (Individuo individuo : populacao) {
                if (individuo.getNota() > melhorInd.getNota()) {
                    melhorInd = new Individuo(individuo);
                }
            }
            i++;

            System.out.println("Geração: " + i + " Melhor nota: " + melhorInd.getNota());
        } while (melhorInd.getNota() < Numeros.NOTA_PISO);

        return melhorInd;
    }

    private static void caucularMediasArea(List<Atividade> listaAtv, List<Funcionario> listaFunc) {

        Integer qtdHoraDevJr = Numeros.ZERO;
        Integer qtdHoraDevPl = Numeros.ZERO;

        Integer qtdHoraAnaliseJr = Numeros.ZERO;
        Integer qtdHoraAnalisePl = Numeros.ZERO;
        Integer qtdHoraAnaliseSr = Numeros.ZERO;

        Integer qtdHoraAnaliseReqJr = Numeros.ZERO;
        Integer qtdHoraAnaliseReqSr = Numeros.ZERO;

        Integer qtdHoraBancoJr = Numeros.ZERO;
        Integer qtdHoraBancoPl = Numeros.ZERO;
        Integer qtdHoraBancoAP = Numeros.ZERO;
        Integer qtdHoraBancoAS = Numeros.ZERO;

        Integer qtdHoraTesteJr = Numeros.ZERO;
        Integer qtdHoraTestePl = Numeros.ZERO;
        Integer qtdHoraTesteAP = Numeros.ZERO;

        /**
         * ***********************************
         */
        Integer qtdFuncDevJr = Numeros.ZERO;
        Integer qtdFuncDevPl = Numeros.ZERO;

        Integer qtdFuncAnaliseJr = Numeros.ZERO;
        Integer qtdFuncAnalisePl = Numeros.ZERO;
        Integer qtdFuncAnaliseSr = Numeros.ZERO;

        Integer qtdFuncAnaliseReqJr = Numeros.ZERO;
        Integer qtdFuncAnaliseReqSr = Numeros.ZERO;

        Integer qtdFuncBancoJr = Numeros.ZERO;
        Integer qtdFuncBancoPl = Numeros.ZERO;
        Integer qtdFuncBancoAP = Numeros.ZERO;
        Integer qtdFuncBancoAS = Numeros.ZERO;

        Integer qtdFuncTesteJr = Numeros.ZERO;
        Integer qtdFuncTestePl = Numeros.ZERO;
        Integer qtdFuncTesteAP = Numeros.ZERO;

        for (Atividade atv : listaAtv) {
            switch (atv.getArea().getCodigo()) {
                case 1:
                    switch (atv.getNivel().getCodigo()) {
                        case 1:
                            qtdHoraDevJr += atv.getPrazo();
                            break;
                        case 2:
                            qtdHoraDevPl += atv.getPrazo();
                            break;
                        default:
                            break;
                    }
                    break;
                case 2:
                    switch (atv.getNivel().getCodigo()) {
                        case 3:
                            qtdHoraAnaliseJr += atv.getPrazo();
                            break;
                        case 4:
                            qtdHoraAnalisePl += atv.getPrazo();
                            break;
                        case 5:
                            qtdHoraAnaliseSr += atv.getPrazo();
                            break;
                        default:
                    }
                    break;
                case 3:
                    switch (atv.getNivel().getCodigo()) {
                        case 1:
                            qtdHoraAnaliseReqJr += atv.getPrazo();
                            break;
                        case 5:
                            qtdHoraAnaliseReqSr += atv.getPrazo();
                            break;
                        default:
                    }
                    break;
                case 4:
                    switch (atv.getNivel().getCodigo()) {
                        case 1:
                            qtdHoraBancoJr += atv.getPrazo();
                            break;
                        case 2:
                            qtdHoraBancoPl += atv.getPrazo();
                            break;
                        case 4:
                            qtdHoraBancoAP += atv.getPrazo();
                            break;
                        case 5:
                            qtdHoraBancoAS += atv.getPrazo();
                            break;
                        default:
                    }
                    break;
                case 5:
                    switch (atv.getNivel().getCodigo()) {
                        case 1:
                            qtdHoraTesteJr += atv.getPrazo();
                            break;
                        case 2:
                            qtdHoraTestePl += atv.getPrazo();
                            break;
                        case 4:
                            qtdHoraTesteAP += atv.getPrazo();
                            break;
                        default:
                            break;
                    }
                    break;
                default:
            }
        }

        for (Funcionario f : listaFunc) {
            switch (f.getArea().getCodigo()) {
                case 1:
                    switch (f.getCargo().getCodigo()) {
                        case 1:
                            qtdFuncDevJr++;
                            break;
                        case 2:
                            qtdFuncDevPl++;
                            break;
                        default:
                            break;
                    }
                    break;
                case 2:
                    switch (f.getCargo().getCodigo()) {
                        case 3:
                            qtdFuncAnaliseJr++;
                            break;
                        case 4:
                            qtdFuncAnalisePl++;
                            break;
                        case 5:
                            qtdFuncAnaliseSr++;
                            break;
                        default:
                    }
                    break;
                case 3:
                    switch (f.getCargo().getCodigo()) {
                        case 1:
                            qtdFuncAnaliseReqJr++;
                            break;
                        case 5:
                            qtdFuncAnaliseReqSr++;
                            break;
                        default:
                    }
                    break;
                case 4:
                    switch (f.getCargo().getCodigo()) {
                        case 1:
                            qtdFuncBancoJr++;
                            break;
                        case 2:
                            qtdFuncBancoPl++;
                            break;
                        case 4:
                            qtdFuncBancoAP++;
                            break;
                        case 5:
                            qtdFuncBancoAS++;
                            break;
                        default:
                    }
                    break;
                case 5:
                    switch (f.getCargo().getCodigo()) {
                        case 1:
                            qtdFuncTesteJr++;
                            break;
                        case 2:
                            qtdFuncTestePl++;
                            break;
                        case 4:
                            qtdFuncTesteAP++;
                        default:
                            break;
                    }
                    break;
                default:
            }
        }

        medHoraDevJr = qtdHoraDevJr / qtdFuncDevJr;
        medHoraDevPl = qtdHoraDevPl / qtdFuncDevPl;

        medHoraAnaliseJr = qtdHoraAnaliseJr / qtdFuncAnaliseJr;
        medHoraAnalisePl = qtdHoraAnalisePl / qtdFuncAnalisePl;
        medHoraAnaliseSr = qtdHoraAnaliseSr / qtdFuncAnaliseSr;

        medHoraAnaliseReqJr = qtdHoraAnaliseReqJr / qtdFuncAnaliseReqJr;
        medHoraAnaliseReqSr = qtdHoraAnaliseReqSr / qtdFuncAnaliseReqSr;

        medHoraBancoJr = qtdHoraBancoJr / qtdFuncBancoJr;
        medHoraBancoPl = qtdHoraBancoPl / qtdFuncBancoPl;
        medHoraBancoAP = qtdHoraBancoAP / qtdFuncBancoAP;
        medHoraBancoAS = qtdHoraBancoAS / qtdFuncBancoAS;

        medHoraTesteJr = qtdHoraTesteJr / qtdFuncTesteJr;
        medHoraTestePl = qtdHoraTestePl / qtdFuncTestePl;
        medHoraTesteAP = qtdHoraTesteAP / qtdFuncTesteAP;
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

//        Numeros.MAX_NOTA = listaAtv.size() * Numeros.VINTE;
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

        listaAtv.forEach((atividade) -> {
            listaA.add(new Atividade(atividade));
        });

        listaFunc.forEach((funcionario) -> {
            listaF.add(new Funcionario(funcionario));
        });

        Integer index = null;

        listaA.forEach((atividade) -> {
            atividade.setResponsavel(null);
        });

        listaF.forEach((funcionario) -> {
            funcionario.setAtividades(new ArrayList<>());
        });

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

                /*Ajustar pra considerar area e cargo*/
                switch (atv.getResponsavel().getArea().getCodigo()) {
                    case 1:
                        switch (atv.getResponsavel().getCargo().getCodigo()) {
                            case 1:
                                nota += (10 - retornarDiferencaHoras(medHoraDevJr, horas));
                                break;
                            case 2:
                                nota += (10 - retornarDiferencaHoras(medHoraDevPl, horas));
                                break;
                            default:
                                break;
                        }
                        break;
                    case 2:
                        switch (atv.getResponsavel().getCargo().getCodigo()) {
                            case 3:
                                nota += (10 - retornarDiferencaHoras(medHoraAnaliseJr, horas));
                                break;
                            case 4:
                                nota += (10 - retornarDiferencaHoras(medHoraAnalisePl, horas));
                                break;
                            case 5:
                                nota += (10 - retornarDiferencaHoras(medHoraAnaliseSr, horas));
                                break;
                            default:
                        }
                        break;
                    case 3:
                        switch (atv.getResponsavel().getCargo().getCodigo()) {
                            case 1:
                                nota += (10 - retornarDiferencaHoras(medHoraAnaliseReqJr, horas));
                                break;
                            case 5:
                                nota += (10 - retornarDiferencaHoras(medHoraAnaliseReqSr, horas));
                                break;
                            default:
                        }
                        break;
                    case 4:
                        switch (atv.getResponsavel().getCargo().getCodigo()) {
                            case 1:
                                nota += (10 - retornarDiferencaHoras(medHoraBancoJr, horas));
                                break;
                            case 2:
                                nota += (10 - retornarDiferencaHoras(medHoraBancoPl, horas));
                                break;
                            case 4:
                                nota += (10 - retornarDiferencaHoras(medHoraBancoAP, horas));
                                break;
                            case 5:
                                nota += (10 - retornarDiferencaHoras(medHoraBancoAS, horas));
                                break;
                            default:
                        }
                        break;
                    case 5:
                        switch (atv.getResponsavel().getCargo().getCodigo()) {
                            case 1:
                                nota += (10 - retornarDiferencaHoras(medHoraTesteJr, horas));
                                break;
                            case 2:
                                nota += (10 - retornarDiferencaHoras(medHoraTestePl, horas));
                                break;
                            case 4:
                                nota += (10 - retornarDiferencaHoras(medHoraTesteAP, horas));
                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                }
            }
            ind.setNota(nota);
        }

        /**
         * ORGANIZA O ARRAY LEVANDO EM CONSIDERAÇÃO A NOTA DO INDIVÍDUO
         *
         * Collections.sort(populacao, new Comparator<Individuo>() {
         *
         * @Override public int compare(Individuo o1, Individuo o2) { Individuo
         * ind01 = o1; Individuo ind02 = o2;
         *
         * if (ind01.getNota() > ind02.getNota()) { return -1; } else if
         * (ind01.getNota() < ind02.getNota()) { return 1; } else { return 0; }
         * }
        });
         */
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
        int indexAux;

        double mutar = (double) (Math.random() * 1);
        /*
        if (mutar > Numeros.PROBABILIDADE_MUTACAO) {
            
            index = (int) (Math.random() * ind.getAtividades().size());            
            func = new Funcionario(ind.getAtividades().get(index).getResponsavel());
            
            do{
                indexAux = (int) (Math.random() * ind.getAtividades().size());
            }while(Objects.equals(index, indexAux));
            
            funcAux = new Funcionario(ind.getAtividades().get(indexAux).getResponsavel());            
            ind.getAtividades().get(index).setResponsavel(funcAux);
            ind.getAtividades().get(indexAux).setResponsavel(func);            
        }
         */

 /* MUTAÇÃO FORÇADA*/
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

            while (true) {
                Atividade atv = new Atividade();

                atv = ind.getAtividades().get((int) (Math.random() * ind.getAtividades().size()));                

                try {
                    if (Objects.equals(atv.getArea().getCodigo(), func.getArea().getCodigo())
                            && Objects.equals(atv.getNivel().getCodigo(), func.getCargo().getCodigo())) {
                        
                        funcAux = new Funcionario(atv.getResponsavel());

                        atv.setResponsavel(func);

                        ind.getAtividades().get(index).setResponsavel(funcAux);
                        break;
                    }
                } catch (NullPointerException npe) {
                    break;
                }
                
            }
        }
        return ind;
    }

    private static Integer retornarDiferencaHoras(int horaFunc, int mediaArea) {
        Integer diferenca = Numeros.ZERO;

        if (horaFunc < mediaArea) {
            diferenca = mediaArea - horaFunc;
        } else {
            diferenca = horaFunc - mediaArea;
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

    public static void caucularMaiorNota() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(StringsUtils.ENTITY_MANAGER);
        EntityManager manager = factory.createEntityManager();

        List<Funcionario> listaFunc = new ArrayList<>();
        List<Atividade> listaAtv = new ArrayList<>();

        listaFunc = (List<Funcionario>) manager.createQuery(StringsUtils.SELECT_FUNCIONARIO_TESTE).getResultList();
        listaAtv = (List<Atividade>) manager.createQuery(StringsUtils.SELECT_ATIVIDADE_TESTE).getResultList();

        listaAtv.get(0).setResponsavel(listaFunc.get(0));
        listaAtv.get(1).setResponsavel(listaFunc.get(0));

        listaAtv.get(2).setResponsavel(listaFunc.get(1));
        listaAtv.get(3).setResponsavel(listaFunc.get(1));

        listaAtv.get(4).setResponsavel(listaFunc.get(2));
        listaAtv.get(5).setResponsavel(listaFunc.get(2));

        listaAtv.get(6).setResponsavel(listaFunc.get(3));
        listaAtv.get(7).setResponsavel(listaFunc.get(3));

        listaAtv.get(8).setResponsavel(listaFunc.get(4));
        listaAtv.get(9).setResponsavel(listaFunc.get(4));

        listaAtv.get(10).setResponsavel(listaFunc.get(5));
        listaAtv.get(11).setResponsavel(listaFunc.get(5));
        listaAtv.get(12).setResponsavel(listaFunc.get(5));

        listaAtv.get(13).setResponsavel(listaFunc.get(6));
        listaAtv.get(14).setResponsavel(listaFunc.get(6));
        listaAtv.get(15).setResponsavel(listaFunc.get(6));
        listaAtv.get(16).setResponsavel(listaFunc.get(6));

        listaAtv.get(17).setResponsavel(listaFunc.get(7));
        listaAtv.get(18).setResponsavel(listaFunc.get(7));
        listaAtv.get(19).setResponsavel(listaFunc.get(7));
        listaAtv.get(20).setResponsavel(listaFunc.get(7));

        listaAtv.get(21).setResponsavel(listaFunc.get(8));
        listaAtv.get(22).setResponsavel(listaFunc.get(8));
        listaAtv.get(23).setResponsavel(listaFunc.get(8));
        listaAtv.get(24).setResponsavel(listaFunc.get(8));
        listaAtv.get(25).setResponsavel(listaFunc.get(8));

        listaAtv.get(26).setResponsavel(listaFunc.get(9));
        listaAtv.get(27).setResponsavel(listaFunc.get(9));
        listaAtv.get(28).setResponsavel(listaFunc.get(9));
        listaAtv.get(29).setResponsavel(listaFunc.get(9));

        listaAtv.get(30).setResponsavel(listaFunc.get(10));

        listaAtv.get(31).setResponsavel(listaFunc.get(11));

        listaAtv.get(32).setResponsavel(listaFunc.get(12));
        listaAtv.get(33).setResponsavel(listaFunc.get(12));

        listaAtv.get(34).setResponsavel(listaFunc.get(13));
        listaAtv.get(35).setResponsavel(listaFunc.get(13));

        listaAtv.get(36).setResponsavel(listaFunc.get(14));
        listaAtv.get(37).setResponsavel(listaFunc.get(14));
        listaAtv.get(38).setResponsavel(listaFunc.get(14));

        listaAtv.get(39).setResponsavel(listaFunc.get(15));
        listaAtv.get(40).setResponsavel(listaFunc.get(15));
        listaAtv.get(41).setResponsavel(listaFunc.get(15));
        listaAtv.get(42).setResponsavel(listaFunc.get(15));

        listaAtv.get(43).setResponsavel(listaFunc.get(16));
        listaAtv.get(44).setResponsavel(listaFunc.get(16));
        listaAtv.get(45).setResponsavel(listaFunc.get(16));
        listaAtv.get(46).setResponsavel(listaFunc.get(16));
        listaAtv.get(47).setResponsavel(listaFunc.get(16));

        listaAtv.get(48).setResponsavel(listaFunc.get(17));

        listaAtv.get(49).setResponsavel(listaFunc.get(18));

        listaAtv.get(50).setResponsavel(listaFunc.get(19));
        listaAtv.get(51).setResponsavel(listaFunc.get(19));

        listaAtv.get(52).setResponsavel(listaFunc.get(20));
        listaAtv.get(53).setResponsavel(listaFunc.get(20));

        listaAtv.get(54).setResponsavel(listaFunc.get(21));
        listaAtv.get(55).setResponsavel(listaFunc.get(21));

        listaAtv.get(56).setResponsavel(listaFunc.get(22));
        listaAtv.get(57).setResponsavel(listaFunc.get(22));
        listaAtv.get(58).setResponsavel(listaFunc.get(22));
        listaAtv.get(59).setResponsavel(listaFunc.get(22));

        listaFunc.get(0).getAtividades().add(listaAtv.get(0));
        listaFunc.get(0).getAtividades().add(listaAtv.get(1));

        listaFunc.get(1).getAtividades().add(listaAtv.get(2));
        listaFunc.get(1).getAtividades().add(listaAtv.get(3));

        listaFunc.get(2).getAtividades().add(listaAtv.get(4));
        listaFunc.get(2).getAtividades().add(listaAtv.get(5));

        listaFunc.get(3).getAtividades().add(listaAtv.get(6));
        listaFunc.get(3).getAtividades().add(listaAtv.get(7));

        listaFunc.get(4).getAtividades().add(listaAtv.get(8));
        listaFunc.get(4).getAtividades().add(listaAtv.get(9));

        listaFunc.get(5).getAtividades().add(listaAtv.get(10));
        listaFunc.get(5).getAtividades().add(listaAtv.get(11));
        listaFunc.get(5).getAtividades().add(listaAtv.get(12));

        listaFunc.get(6).getAtividades().add(listaAtv.get(13));
        listaFunc.get(6).getAtividades().add(listaAtv.get(14));
        listaFunc.get(6).getAtividades().add(listaAtv.get(15));
        listaFunc.get(6).getAtividades().add(listaAtv.get(16));

        listaFunc.get(7).getAtividades().add(listaAtv.get(17));
        listaFunc.get(7).getAtividades().add(listaAtv.get(18));
        listaFunc.get(7).getAtividades().add(listaAtv.get(19));
        listaFunc.get(7).getAtividades().add(listaAtv.get(20));

        listaFunc.get(8).getAtividades().add(listaAtv.get(21));
        listaFunc.get(8).getAtividades().add(listaAtv.get(22));
        listaFunc.get(8).getAtividades().add(listaAtv.get(23));
        listaFunc.get(8).getAtividades().add(listaAtv.get(24));
        listaFunc.get(8).getAtividades().add(listaAtv.get(25));

        listaFunc.get(9).getAtividades().add(listaAtv.get(26));
        listaFunc.get(9).getAtividades().add(listaAtv.get(27));
        listaFunc.get(9).getAtividades().add(listaAtv.get(28));
        listaFunc.get(9).getAtividades().add(listaAtv.get(29));

        listaFunc.get(10).getAtividades().add(listaAtv.get(30));

        listaFunc.get(11).getAtividades().add(listaAtv.get(31));

        listaFunc.get(12).getAtividades().add(listaAtv.get(32));
        listaFunc.get(12).getAtividades().add(listaAtv.get(33));

        listaFunc.get(13).getAtividades().add(listaAtv.get(34));
        listaFunc.get(13).getAtividades().add(listaAtv.get(35));

        listaFunc.get(14).getAtividades().add(listaAtv.get(36));
        listaFunc.get(14).getAtividades().add(listaAtv.get(37));
        listaFunc.get(14).getAtividades().add(listaAtv.get(38));

        listaFunc.get(15).getAtividades().add(listaAtv.get(39));
        listaFunc.get(15).getAtividades().add(listaAtv.get(40));
        listaFunc.get(15).getAtividades().add(listaAtv.get(41));
        listaFunc.get(15).getAtividades().add(listaAtv.get(42));

        listaFunc.get(16).getAtividades().add(listaAtv.get(43));
        listaFunc.get(16).getAtividades().add(listaAtv.get(44));
        listaFunc.get(16).getAtividades().add(listaAtv.get(45));
        listaFunc.get(16).getAtividades().add(listaAtv.get(46));
        listaFunc.get(16).getAtividades().add(listaAtv.get(47));

        listaFunc.get(17).getAtividades().add(listaAtv.get(48));

        listaFunc.get(18).getAtividades().add(listaAtv.get(49));

        listaFunc.get(19).getAtividades().add(listaAtv.get(50));
        listaFunc.get(19).getAtividades().add(listaAtv.get(51));

        listaFunc.get(20).getAtividades().add(listaAtv.get(52));
        listaFunc.get(20).getAtividades().add(listaAtv.get(53));

        listaFunc.get(21).getAtividades().add(listaAtv.get(54));
        listaFunc.get(21).getAtividades().add(listaAtv.get(55));

        listaFunc.get(22).getAtividades().add(listaAtv.get(56));
        listaFunc.get(22).getAtividades().add(listaAtv.get(57));
        listaFunc.get(22).getAtividades().add(listaAtv.get(58));
        listaFunc.get(22).getAtividades().add(listaAtv.get(59));

        caucularMediasArea(listaAtv, listaFunc);

        Individuo ind = new Individuo();

        ind.setAtividades(listaAtv);

        List<Individuo> lista = new ArrayList<>();
        lista.add(ind);
        lista = avaliarPopulacao(lista);

        manager.close();

        System.out.println("Maior Nota: " + lista.get(0).getNota());
    }
}
