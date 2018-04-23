package control;

import bean.Atividade;
import bean.Funcionario;
import frame.AG;
import java.util.ArrayList;
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
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("AG");

        EntityManager entityManager = factory.createEntityManager();
        Integer codigo = Integer.parseInt(JOptionPane.showInputDialog("Informe o código"));
        Funcionario f = entityManager.find(Funcionario.class, codigo);

        entityManager.close();
        AG.loadFuncFrame(stage, f);
    }

    public static void gerarPopulacao() {

        JOptionPane.showMessageDialog(null, "Gerados");
    }

    public static void associarAtividades() {
        List<Funcionario> listaFunc = new ArrayList<>();
        List<Atividade> listaAtv = new ArrayList<>();

        EntityManagerFactory factory = Persistence.createEntityManagerFactory(StringsUtils.ENTITY_MANAGER);
        EntityManager manager = factory.createEntityManager();

        listaFunc = (List<Funcionario>) manager.createQuery("select f from Funcionario f").getResultList();
        listaAtv = (List<Atividade>) manager.createQuery("select at from Atividade at").getResultList();

        manager.getTransaction().begin();
        
        listaAtv = assosciar(listaFunc,listaAtv);

        for (Atividade atv : listaAtv) {
            Funcionario f = manager.find(Funcionario.class, atv.getResponsavel().getCodigo());
            f = atv.getResponsavel();
            manager.persist(f);

        }

        for (Atividade atividade : listaAtv) {
            Atividade a = manager.find(Atividade.class, atividade.getCodigo());
            a = atividade;
            manager.persist(a);
        }

        manager.getTransaction().commit();
        manager.close();
        JOptionPane.showMessageDialog(null, "Associado");
    }

    private static List<Atividade> assosciar(List<Funcionario> listaFunc, List<Atividade> listaAtv) {
        Integer maxAtv = Numeros.ZERO;
        Integer index = null;

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

    private static boolean verificarAssociacaoAtv(List<Atividade> listaAtv) {
        for (Atividade atividade : listaAtv) {
            if (atividade.getResponsavel() == null) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
