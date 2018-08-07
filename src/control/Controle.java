package control;

import bean.Atividade;
import bean.Funcionario;
import bean.Individuo;
import bean.Registro;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import utils.Numeros;
import utils.StringsUtils;

/**
 *
 * @author Celso Souza
 * @version 1.0
 *
 *
 * ANOTAÇÕES:
 *
 * 01 - Na mutação e cruzamento a lista de atividades de cada funcionário deve
 * ser atualizada para o cauculo do balanceamento de horas.
 *
 *
 */
public class Controle {

    private static int maxAtividadePorFuncionario;

    private static int qtdMutacoes = Numeros.ZERO;
    private static int qtdCruzamentos = Numeros.ZERO;
    private static int mutacoesTotal = Numeros.ZERO;

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

    private static List<Funcionario> listaPossiveis = new ArrayList<>();

    public static Individuo start() {
        List<Individuo> populacao = new ArrayList<>();
        List<Individuo> populacaoAlterada = new ArrayList<>();

        Integer contadorDeConvergencia = Numeros.ZERO;
        float notaAnterior = Numeros.ZERO_FLOAT;

        populacao = gerarPopulacao();
        populacao = avaliarPopulacao(populacao);

        Individuo melhorInd = new Individuo();

        int i = Numeros.ZERO;
        
        Date inicio = Calendar.getInstance().getTime();
        qtdCruzamentos = Numeros.ZERO;
        do {
            qtdMutacoes = Numeros.ZERO;
            populacaoAlterada = cruzarIndividuos(populacao);

            populacao = avaliarPopulacao(populacaoAlterada);

            for (Individuo individuo : populacao) {
                if (individuo.getNota() > melhorInd.getNota()) {
                    melhorInd = new Individuo(individuo);
                }
            }
            i++;

            System.out.println("Geração: " + i + " Melhor nota: " + populacao.get(0).getNota() + " Melhor Geral: " + melhorInd.getNota() + " Mutações: " + qtdMutacoes);
            mutacoesTotal += qtdMutacoes;
            
        /*****************************************************************/
        /***************VERIFICA SE A POPUILAÇÃO CONVERGIU****************/
        /*****************************************************************/
        /**/if (notaAnterior == melhorInd.getNota()) {                 /**/
        /**/    contadorDeConvergencia++;                              /**/
        /**/} else{                                                    /**/
        /**/    contadorDeConvergencia = Numeros.ZERO;                 /**/
        /**/}                                                          /**/
        /**/                                                           /**/
        /**/if (contadorDeConvergencia > Numeros.LIMITE_CONVERGENCIA) {/**/
        /**/    break;                                                 /**/
        /**/}                                                          /**/
        /*****************************************************************/
        /*****************************************************************/
            
        notaAnterior = melhorInd.getNota();
        } while (true);
        Date fim = Calendar.getInstance().getTime();
        System.out.println(new SimpleDateFormat("HH:mm:ss").format(inicio));
        System.out.println(new SimpleDateFormat("HH:mm:ss").format(fim));
        
        /**
         * MOVER
         * 
        salvarRegistro(new Registro((i - Numeros.LIMITE_CONVERGENCIA), qtdMutacoes,
                melhorInd.getNota(), qtdCruzamentos,
                String.valueOf(new SimpleDateFormat("HH:mm:ss").format(inicio)),
                String.valueOf(new SimpleDateFormat("HH:mm:ss").format(fim))));
        */

        

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

    private static void salvarRegistro(Registro registro) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(StringsUtils.ENTITY_MANAGER);
        EntityManager manager = factory.createEntityManager();
        manager.getTransaction().begin();

        manager.persist(registro);

        manager.getTransaction().commit();
        manager.close();
        factory.close();
    }

    public static List<Individuo> gerarPopulacao() {
        List<Individuo> listaIndividuos = new ArrayList<>();
        Individuo indiv;

        EntityManagerFactory factory = Persistence.createEntityManagerFactory(StringsUtils.ENTITY_MANAGER);
        EntityManager manager = factory.createEntityManager();

        List<Funcionario> listaFunc = new ArrayList<>();
        List<Atividade> listaAtv = new ArrayList<>();

        listaFunc = new ArrayList<>((List<Funcionario>) manager.createQuery(StringsUtils.SELECT_FUNCIONARIO_TESTE).getResultList());
        listaAtv = new ArrayList<>((List<Atividade>) manager.createQuery(StringsUtils.SELECT_ATIVIDADE_TESTE).getResultList());

        listaPossiveis = new ArrayList<>(listaFunc);

        caucularMediasArea(listaAtv, listaFunc);

        maxAtividadePorFuncionario = listaAtv.size() / listaFunc.size();
        maxAtividadePorFuncionario += Numeros.UM;

        for (int i = 0; i < Numeros.NUMERO_INDIVIDUOS; i++) {
            indiv = new Individuo();

            indiv.setAtividades(retornarAtvAssociadas(listaFunc, listaAtv));

            listaIndividuos.add(indiv);
        }
//        System.out.println("Indivíduos Gerados");
//        manager.close();
//        factory.close();
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
            ind = ordenarListaAtvFunc(ind);
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

                /*VERIFICA BALANCEAMENTO DE HORAS*/
                int horas = Numeros.ZERO;

                for (Atividade at : atv.getResponsavel().getAtividades()) {
                    horas += at.getPrazo();
                }

                if (horas > Numeros.ZERO) {

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
        List<Individuo> candidatos = new ArrayList<>(populacao.subList(Numeros.ZERO, Numeros.QTD_MELHORES));

        Individuo pai1 = new Individuo();
        Individuo pai2 = new Individuo();

        //int qtdCruzamentos = (int) (Math.random() * Numeros.MAX_CRUZAMENTOS);
        for (int i = 0; i < Numeros.MAX_CRUZAMENTOS; i++) {
            indexPai01 = (int) (Math.random() * candidatos.size());

            do {
                indexPai02 = (int) (Math.random() * candidatos.size());
            } while (indexPai01 == indexPai02);

            pai1 = new Individuo(candidatos.get(indexPai01));
            pai2 = new Individuo(candidatos.get(indexPai02));

            Individuo filho1 = new Individuo();
            Individuo filho2 = new Individuo();

            int pontoCruzamento = (int) (Math.random() * pai1.getAtividades().size());

            /**
             * PRIMEIRA METADE DO PAI1 COM SEGUNDA METADE DO PAI2
             */
//            System.out.println();
//            imprimirGene("P 01", pai1);
//            imprimirGene("P 02", pai2);
//            System.out.println("Ponto de Cruzamento: " + pontoCruzamento);
            for (int j = 0; j < pontoCruzamento; j++) {
                filho1.getAtividades().add(pai1.getAtividades().get(j));
            }

            for (int k = pontoCruzamento; k < pai2.getAtividades().size(); k++) {
                filho1.getAtividades().add(pai2.getAtividades().get(k));
            }

//            imprimirGene("F 01", filho1);
            qtdCruzamentos++;
            filho1 = new Individuo(mutarIndividuo(filho1));

            /**
             * PRIMEIRA METADE DO PAI2 COM SEGUNDA METADE DO PAI1
             */
            for (int l = 0; l < pontoCruzamento; l++) {
                filho2.getAtividades().add(pai2.getAtividades().get(l));
            }

            for (int m = pontoCruzamento; m < pai1.getAtividades().size(); m++) {
                filho2.getAtividades().add(pai1.getAtividades().get(m));
            }

//            imprimirGene("F 02", filho2);
            qtdCruzamentos++;
            filho2 = new Individuo(mutarIndividuo(filho2));

            List<Individuo> lista = new ArrayList<>();

            lista.add(pai1);
            lista.add(pai2);
            lista.add(filho1);
            lista.add(filho2);

            melhoresPosCruzamento = avaliarPopulacao(lista);

            candidatos.set(indexPai01, melhoresPosCruzamento.get(Numeros.ZERO));
            candidatos.set(indexPai02, melhoresPosCruzamento.get(Numeros.UM));
        }

        populacao.addAll(candidatos);

        return avaliarPopulacao(populacao).subList(0, Numeros.NUMERO_INDIVIDUOS);
    }

    public static Individuo mutarIndividuo(Individuo ind) {
        int indexAtv;
        int indexFunc;

        double mutar = (double) (Math.random() * 1);
        
        /***********************************************
         ****MUTAÇÃO FORÇADA****************************
         ***********************************************/
        if (mutar < Numeros.PROBABILIDADE_MUTACAO) {
            qtdMutacoes++;

            for (int i = 0; i < Numeros.QTD_GENES_MUTADOS; i++) {
                ind = ordenarListaAtvFunc(ind);

                indexAtv = (int) (Math.random() * ind.getAtividades().size());
                indexFunc = (int) (Math.random() * listaPossiveis.size());

                if (!Objects.equals(ind.getAtividades().get(indexAtv).getArea().getCodigo(), ind.getAtividades().get(indexAtv).getResponsavel().getArea().getCodigo())
                        || !Objects.equals(ind.getAtividades().get(indexAtv).getNivel().getCodigo(), ind.getAtividades().get(indexAtv).getResponsavel().getCargo().getCodigo())) {

                    if (Objects.equals(ind.getAtividades().get(indexAtv).getArea().getCodigo(), listaPossiveis.get(indexFunc).getArea().getCodigo())) {

                        listaPossiveis.get(indexFunc).getAtividades().add(ind.getAtividades().get(indexAtv));
                        ind.getAtividades().get(indexAtv).setResponsavel(listaPossiveis.get(indexFunc));
                    }

                }
            }
        }
        
        
        /***********************************************
         ****MUTAÇÃO SEM FORÇAR*************************
        /**********************************************
        if (mutar < Numeros.PROBABILIDADE_MUTACAO) {
            qtdMutacoes++;

            for (int i = 0; i < Numeros.QTD_GENES_MUTADOS; i++) {
                ind = ordenarListaAtvFunc(ind);

                indexAtv = (int) (Math.random() * ind.getAtividades().size());
                indexFunc = (int) (Math.random() * listaPossiveis.size());

                listaPossiveis.get(indexFunc).getAtividades().add(ind.getAtividades().get(indexAtv));
                ind.getAtividades().get(indexAtv).setResponsavel(listaPossiveis.get(indexFunc));

            }
        }*/
            
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

    /**
     * @param Individou a ser atualizado
     * @return Individou atualizado
     *
     * Atualiza a lista de atividades de cada funcionário para avaliação correta
     * das horas
     */
    private static Individuo ordenarListaAtvFunc(Individuo ind) {

        for (Atividade atividade : ind.getAtividades()) {
            atividade.getResponsavel().setAtividades(new ArrayList<>());
        }

        for (Atividade atv : ind.getAtividades()) {
            atv.getResponsavel().getAtividades().add(atv);
        }

        return ind;
    }

    public static void imprimirGene(String tag, Individuo ind) {
        System.out.print(tag + ": ");
        for (Atividade atv : ind.getAtividades()) {
            System.out.print(atv.getResponsavel().getCodigo() + "-");
        }
        System.out.println();
    }

    public static void imprimirAssociacao(Individuo ind) {
        for (Atividade atv : ind.getAtividades()) {
            System.out.println();
            System.out.println("***********************************************");
            System.out.println(atv.getNome().toUpperCase());
            System.out.println("RESPONSÁVEL: " + atv.getResponsavel().getNome());
            System.out.println("-----------------------------------------------");
            System.out.println("ÁREA ATIVIDADE  : " + atv.getArea().getDescricao());
            System.out.println("ÁREA FUNCIONÁRIO: " + atv.getResponsavel().getArea().getDescricao());
            System.out.println("-----------------------------------------------");
            System.out.println("NÍVEL ATIVIDADE  : " + atv.getNivel().getDescricao());
            System.out.println("NÍVEL FUNCIONÁRIO: " + atv.getResponsavel().getCargo().getDescricao());
            System.out.println("***********************************************");
        }
        System.out.println("Nota: " + ind.getNota());
        System.out.println("Cruzamentos: " + qtdCruzamentos);
        System.out.println("Mutações: " + mutacoesTotal);
    }
    
    public static void salvarAssociacao(Individuo ind){
        Atividade atv;
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(StringsUtils.ENTITY_MANAGER);
        EntityManager manager = factory.createEntityManager();
        
        manager.getTransaction().begin();
        
        for (Atividade a : ind.getAtividades()) {
            atv = new Atividade();
            atv = manager.find(Atividade.class, a.getCodigo());
            atv.setResponsavel(a.getResponsavel());
            manager.persist(atv);
        }
        
        manager.getTransaction().commit();
        manager.close();
        factory.close();
    }
    
    public static void gerarTabelaAssociacao(Individuo ind) {
        List<Funcionario> funcionarios = new ArrayList<>();
        for (Atividade atividade : ind.getAtividades()) {
            if (!funcionarios.contains(atividade.getResponsavel())) {
                funcionarios.add(atividade.getResponsavel());
            }
        }

        for (Funcionario func : funcionarios) {
            System.out.println("***********************************************");
            System.out.println("Funcionário: " + func.getNome());
            System.out.println("Atividades: ");
            for (Atividade atv : func.getAtividades()) {
                System.out.println(atv.getCodigo() + " - " + atv.getNome());
            }
        }
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
        listaAtv.get(10).setResponsavel(listaFunc.get(4));
        listaAtv.get(11).setResponsavel(listaFunc.get(4));
        listaAtv.get(12).setResponsavel(listaFunc.get(4));

        listaAtv.get(13).setResponsavel(listaFunc.get(5));
        listaAtv.get(14).setResponsavel(listaFunc.get(5));
        listaAtv.get(15).setResponsavel(listaFunc.get(5));
        listaAtv.get(16).setResponsavel(listaFunc.get(5));

        listaAtv.get(17).setResponsavel(listaFunc.get(6));
        listaAtv.get(18).setResponsavel(listaFunc.get(6));
        listaAtv.get(19).setResponsavel(listaFunc.get(6));
        listaAtv.get(20).setResponsavel(listaFunc.get(6));

        listaAtv.get(21).setResponsavel(listaFunc.get(7));
        listaAtv.get(22).setResponsavel(listaFunc.get(7));
        listaAtv.get(23).setResponsavel(listaFunc.get(7));
        listaAtv.get(24).setResponsavel(listaFunc.get(7));
        listaAtv.get(25).setResponsavel(listaFunc.get(7));

        listaAtv.get(26).setResponsavel(listaFunc.get(8));
        listaAtv.get(27).setResponsavel(listaFunc.get(8));
        listaAtv.get(28).setResponsavel(listaFunc.get(8));
        listaAtv.get(29).setResponsavel(listaFunc.get(8));

        listaAtv.get(30).setResponsavel(listaFunc.get(9));
        listaAtv.get(31).setResponsavel(listaFunc.get(9));

        listaAtv.get(32).setResponsavel(listaFunc.get(10));
        listaAtv.get(33).setResponsavel(listaFunc.get(10));

        listaAtv.get(34).setResponsavel(listaFunc.get(11));
        listaAtv.get(35).setResponsavel(listaFunc.get(11));

        listaAtv.get(36).setResponsavel(listaFunc.get(12));
        listaAtv.get(37).setResponsavel(listaFunc.get(12));
        listaAtv.get(38).setResponsavel(listaFunc.get(12));

        listaAtv.get(39).setResponsavel(listaFunc.get(13));
        listaAtv.get(40).setResponsavel(listaFunc.get(13));
        listaAtv.get(41).setResponsavel(listaFunc.get(13));
        listaAtv.get(42).setResponsavel(listaFunc.get(13));

        listaAtv.get(43).setResponsavel(listaFunc.get(14));
        listaAtv.get(44).setResponsavel(listaFunc.get(14));
        listaAtv.get(45).setResponsavel(listaFunc.get(14));
        listaAtv.get(46).setResponsavel(listaFunc.get(14));
        listaAtv.get(47).setResponsavel(listaFunc.get(14));

        listaAtv.get(48).setResponsavel(listaFunc.get(15));
        listaAtv.get(49).setResponsavel(listaFunc.get(15));

        listaAtv.get(50).setResponsavel(listaFunc.get(16));
        listaAtv.get(51).setResponsavel(listaFunc.get(16));

        listaAtv.get(52).setResponsavel(listaFunc.get(17));
        listaAtv.get(53).setResponsavel(listaFunc.get(17));

        listaAtv.get(54).setResponsavel(listaFunc.get(18));
        listaAtv.get(55).setResponsavel(listaFunc.get(18));

        listaAtv.get(56).setResponsavel(listaFunc.get(19));
        listaAtv.get(57).setResponsavel(listaFunc.get(19));
        listaAtv.get(58).setResponsavel(listaFunc.get(19));
        listaAtv.get(59).setResponsavel(listaFunc.get(19));

        //Associação das atividades dos funcionários
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
        listaFunc.get(4).getAtividades().add(listaAtv.get(10));
        listaFunc.get(4).getAtividades().add(listaAtv.get(11));
        listaFunc.get(4).getAtividades().add(listaAtv.get(12));

        listaFunc.get(5).getAtividades().add(listaAtv.get(13));
        listaFunc.get(5).getAtividades().add(listaAtv.get(14));
        listaFunc.get(5).getAtividades().add(listaAtv.get(15));
        listaFunc.get(5).getAtividades().add(listaAtv.get(16));

        listaFunc.get(6).getAtividades().add(listaAtv.get(17));
        listaFunc.get(6).getAtividades().add(listaAtv.get(18));
        listaFunc.get(6).getAtividades().add(listaAtv.get(19));
        listaFunc.get(6).getAtividades().add(listaAtv.get(20));

        listaFunc.get(7).getAtividades().add(listaAtv.get(21));
        listaFunc.get(7).getAtividades().add(listaAtv.get(22));
        listaFunc.get(7).getAtividades().add(listaAtv.get(23));
        listaFunc.get(7).getAtividades().add(listaAtv.get(24));
        listaFunc.get(7).getAtividades().add(listaAtv.get(25));

        listaFunc.get(8).getAtividades().add(listaAtv.get(26));
        listaFunc.get(8).getAtividades().add(listaAtv.get(27));
        listaFunc.get(8).getAtividades().add(listaAtv.get(28));
        listaFunc.get(8).getAtividades().add(listaAtv.get(29));

        listaFunc.get(9).getAtividades().add(listaAtv.get(30));
        listaFunc.get(9).getAtividades().add(listaAtv.get(31));

        listaFunc.get(10).getAtividades().add(listaAtv.get(32));
        listaFunc.get(10).getAtividades().add(listaAtv.get(33));

        listaFunc.get(11).getAtividades().add(listaAtv.get(34));
        listaFunc.get(11).getAtividades().add(listaAtv.get(35));

        listaFunc.get(12).getAtividades().add(listaAtv.get(36));
        listaFunc.get(12).getAtividades().add(listaAtv.get(37));
        listaFunc.get(12).getAtividades().add(listaAtv.get(38));

        listaFunc.get(13).getAtividades().add(listaAtv.get(39));
        listaFunc.get(13).getAtividades().add(listaAtv.get(40));
        listaFunc.get(13).getAtividades().add(listaAtv.get(41));
        listaFunc.get(13).getAtividades().add(listaAtv.get(42));

        listaFunc.get(14).getAtividades().add(listaAtv.get(43));
        listaFunc.get(14).getAtividades().add(listaAtv.get(44));
        listaFunc.get(14).getAtividades().add(listaAtv.get(45));
        listaFunc.get(14).getAtividades().add(listaAtv.get(46));
        listaFunc.get(14).getAtividades().add(listaAtv.get(47));

        listaFunc.get(15).getAtividades().add(listaAtv.get(48));
        listaFunc.get(15).getAtividades().add(listaAtv.get(49));

        listaFunc.get(16).getAtividades().add(listaAtv.get(50));
        listaFunc.get(16).getAtividades().add(listaAtv.get(51));

        listaFunc.get(17).getAtividades().add(listaAtv.get(52));
        listaFunc.get(17).getAtividades().add(listaAtv.get(53));

        listaFunc.get(18).getAtividades().add(listaAtv.get(54));
        listaFunc.get(18).getAtividades().add(listaAtv.get(55));

        listaFunc.get(19).getAtividades().add(listaAtv.get(56));
        listaFunc.get(19).getAtividades().add(listaAtv.get(57));
        listaFunc.get(19).getAtividades().add(listaAtv.get(58));
        listaFunc.get(19).getAtividades().add(listaAtv.get(59));

        caucularMediasArea(listaAtv, listaFunc);

        Individuo ind = new Individuo();

        ind.setAtividades(listaAtv);

        List<Individuo> lista = new ArrayList<>();
        lista.add(ind);
        lista = avaliarPopulacao(lista);

        manager.close();

        System.out.println("Maior Nota: " + lista.get(0).getNota());

        imprimirAssociacao(ind);
    }
}
