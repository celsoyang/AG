package control;

import bean.Atividade;
import bean.Funcionario;
import bean.Individuo;
import frame.AG;
import java.util.ArrayList;
import java.util.Arrays;
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

    public static List<String> sequencias = new ArrayList<String>();

    public static void loadFuncionario(Stage stage) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(StringsUtils.ENTITY_MANAGER);

        EntityManager entityManager = factory.createEntityManager();
        Integer codigo = Integer.parseInt(JOptionPane.showInputDialog(StringsUtils.MSG_INFORME_CODIGO));
        Funcionario f = entityManager.find(Funcionario.class, codigo);

        entityManager.close();
        AG.loadFuncFrame(stage, f);
    }

    public static void gerarPopulacao() {
        List<Individuo> listaIndividuos;
        listaIndividuos = new ArrayList<>();
        Individuo ind;
        Integer qtd = Integer.parseInt(JOptionPane.showInputDialog(null, StringsUtils.MSG_INFORME_IDIVIDUOS));

        for (int i = 0; i < qtd; i++) {
            ind = new Individuo();
            ind.setAtividades(retornaAtvAssociadas());
            listaIndividuos.add(ind);
        }

        JOptionPane.showMessageDialog(null, StringsUtils.MSG_GERADOS);
    }

    private static List<Individuo> retornarPopulacao() {
        List<Individuo> listaIndividuos;
        listaIndividuos = new ArrayList<>();
        Individuo ind;
        Integer qtd = Integer.parseInt(JOptionPane.showInputDialog(null, StringsUtils.MSG_INFORME_IDIVIDUOS));

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
        List<Funcionario> listaFunc = new ArrayList<>();
        List<Atividade> listaAtv = new ArrayList<>();

        EntityManagerFactory factory = Persistence.createEntityManagerFactory(StringsUtils.ENTITY_MANAGER);
        EntityManager manager = factory.createEntityManager();

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

    public static void avaliarPopulacao() {
        List<Individuo> populacao = retornarPopulacao();
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
        float notas[] = new float[populacao.size()];
        for (int i = 0; i < populacao.size(); i++) {
            notas[i] = populacao.get(i).getNota();
        }
        Arrays.sort(notas);
        for (int i = 0; i < notas.length; i++) {
            System.out.println("Indivídou: " + i + " Nota: " + notas[i]);
        }
    }

    private static boolean verificarAssociacaoAtv(List<Atividade> listaAtv) {
        for (Atividade atividade : listaAtv) {
            if (atividade.getResponsavel() == null) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
