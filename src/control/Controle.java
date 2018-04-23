package control;

import bean.Area;
import bean.Atividade;
import bean.Cargo;
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

    public static void gerarFucionarios() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(StringsUtils.ENTITY_MANAGER);
        EntityManager manager = factory.createEntityManager();

        manager.getTransaction().begin();
        for (int i = 0; i < Numeros.QTD_FUNC; i++) {
            Funcionario f = criarFuncionario();
            manager.persist(f);
        }

        manager.getTransaction().commit();
        manager.close();
        factory.close();

        JOptionPane.showMessageDialog(null, "Gerados");
    }

    private static Funcionario criarFuncionario() {
        int nome = 0;
        int sobreNome01 = 0;
        int sobreNome02 = 0;
        StringBuilder nomeFunc = new StringBuilder();

        do {
            nome = (int) (Math.random() * StringsUtils.NOMES_FUNC.length);
            sobreNome01 = (int) (Math.random() * StringsUtils.NOMES_FUNC.length);
            sobreNome02 = (int) (Math.random() * StringsUtils.NOMES_FUNC.length);
        } while (veificarSequencia(sequencias, String.valueOf(nome) + String.valueOf(sobreNome01) + String.valueOf(sobreNome02)));

        sequencias.add(String.valueOf(nome) + String.valueOf(sobreNome01) + String.valueOf(sobreNome02));

        nomeFunc = new StringBuilder();
        nomeFunc.append(StringsUtils.NOMES_FUNC[nome]);
        nomeFunc.append(" ");
        nomeFunc.append(StringsUtils.SOBRENOMES_FUNC[sobreNome01]);
        nomeFunc.append(" ");
        nomeFunc.append(StringsUtils.SOBRENOMES_FUNC[sobreNome02]);

        Funcionario func = new Funcionario();
        func.setArea(new Area((int) (Math.random() * 5) + 1));
        func.setCargo(new Cargo((int) (Math.random() * 5) + 1));
        func.setNome(nomeFunc.toString());
        func.setTempo_exp((int) (Math.random() * 36) + 6);
        func.setTempo_proj((int) (Math.random() * 12) + 6);

        return func;
    }

    private static Boolean veificarSequencia(List<String> listaSequencias, String sequencia) {
        Boolean retorno = Boolean.FALSE;
        for (String seq : listaSequencias) {
            if (seq.equals(sequencia)) {
                retorno = Boolean.TRUE;
            }
        }
        return retorno;
    }

    public static void associarAtividades() {
        List<Funcionario> listaFunc = new ArrayList<>();
        List<Atividade> listaAtv = new ArrayList<>();
        Integer maxAtv = Numeros.ZERO;
        Integer index = null;

        EntityManagerFactory factory = Persistence.createEntityManagerFactory(StringsUtils.ENTITY_MANAGER);
        EntityManager manager = factory.createEntityManager();

        manager.getTransaction().begin();
        listaFunc = (List<Funcionario>) manager.createQuery("select f from Funcionario f").getResultList();
        listaAtv = (List<Atividade>) manager.createQuery("select at from Atividade at").getResultList();

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

        for (Funcionario funcionario : listaFunc) {
            Funcionario f = manager.find(Funcionario.class, funcionario.getCodigo());
            f = funcionario;
            manager.persist(f);
            System.out.println(funcionario.getNome() + ": " + funcionario.getAtividades().size());

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

    private static boolean verificarAssociacaoAtv(List<Atividade> listaAtv) {
        for (Atividade atividade : listaAtv) {
            if (atividade.getResponsavel() == null) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
