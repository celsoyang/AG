package control;

import bean.Area;
import bean.Atividade;
import bean.Cargo;
import bean.Funcionario;
import bean.MaxArea;
import frame.AG;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    /**
     *
     * Salvar Funcionario na lista
     *
     * @param func
     */
    @SuppressWarnings("InfiniteRecursion")
    public static void salvarFuncionario(Funcionario func) {
        /*FileOutputStream fos;
        ObjectOutputStream ous;
        List<Funcionario> lista;
        File file = new File(StringsUtils.PATH_FUNCIONARIOS);
        try {
            lista = carregarFuncionarios();
            lista.add(func);
            fos = new FileOutputStream(file);
            ous = new ObjectOutputStream(fos);

            ous.writeObject(lista);
        } catch (FileNotFoundException ex) {
            file.mkdir();
            salvarFuncionario(func);
        } catch (IOException ex) {
            Logger.getLogger(Controle.class.getName()).log(Level.SEVERE, null, ex);
        }*/

    }

    @SuppressWarnings("InfiniteRecursion")
    public static void salvarAtividade(Atividade atv) {
        FileOutputStream fos;
        ObjectOutputStream ous;
        List<Atividade> lista;
        File file = new File(StringsUtils.PATH_ATIVIDADES);
        try {
            lista = carregarAtividades();
            lista.add(atv);
            fos = new FileOutputStream(file);
            ous = new ObjectOutputStream(fos);

            ous.writeObject(lista);
        } catch (FileNotFoundException ex) {
            file.mkdir();
            salvarAtividade(atv);
        } catch (IOException ex) {
            Logger.getLogger(Controle.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * @return Lista de objetos Funcionario
     * @throws java.io.IOException
     */
    @SuppressWarnings({"Convert2Diamond", "InfiniteRecursion"})
    public static List<Funcionario> carregarFuncionarios() throws IOException {
        List<Funcionario> retorno = null;
        FileInputStream fis;
        ObjectInputStream ois;
        File file = new File(StringsUtils.PATH_FUNCIONARIOS);
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);

            retorno = new ArrayList<Funcionario>();

            retorno = (List<Funcionario>) ois.readObject();
        } catch (FileNotFoundException ex) {
            file.mkdir();
            file.createNewFile();
            return carregarFuncionarios();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Controle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    /**
     *
     * @return Lista de objetos Atividade
     */
    @SuppressWarnings({"Convert2Diamond", "InfiniteRecursion"})
    public static List<Atividade> carregarAtividades() {
        List<Atividade> retorno = null;
        FileInputStream fis;
        ObjectInputStream ois;
        File file = new File(StringsUtils.PATH_ATIVIDADES);
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);

            retorno = new ArrayList<Atividade>();

            retorno = (List<Atividade>) ois.readObject();
        } catch (FileNotFoundException ex) {
            file.mkdir();
            return carregarAtividades();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Controle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public static void loadFuncionario(Stage stage) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("AG");

        EntityManager entityManager = factory.createEntityManager();
        Integer codigo = Integer.parseInt(JOptionPane.showInputDialog("Informe o código"));
        Funcionario f = entityManager.find(Funcionario.class, codigo);

        entityManager.close();
        AG.loadFuncFrame(stage, f);
    }

    public static ImageView returnLoadGif() {
        Image im = new Image("/files/load2.gif");
        ImageView iv = new ImageView(im);

        return iv;
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

    public static void gerarAtividade() {
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
        MaxArea maxAreaAtv = new MaxArea();
        MaxArea maxAreaFunc = new MaxArea();

        EntityManagerFactory factory = Persistence.createEntityManagerFactory(StringsUtils.ENTITY_MANAGER);
        EntityManager manager = factory.createEntityManager();

        manager.getTransaction().begin();
        listaFunc = (List<Funcionario>) manager.createQuery("select f from Funcionario f").getResultList();
        listaAtv = (List<Atividade>) manager.createQuery("select at from Atividade at").getResultList();

        for (Atividade atividade : listaAtv) {
            switch (atividade.getArea().getCodigo()) {
                case 1:
                    maxAreaAtv.setQtdDev(maxAreaAtv.getQtdDev() + Numeros.UM);
                    break;
                case 2:
                    maxAreaAtv.setQtdTest(maxAreaAtv.getQtdTest() + Numeros.UM);
                    break;
                case 3:
                    maxAreaAtv.setQtdBanco(maxAreaAtv.getQtdBanco() + Numeros.UM);
                    break;
                case 4:
                    maxAreaAtv.setQtdAnaliseReq(maxAreaAtv.getQtdAnaliseReq() + Numeros.UM);
                    break;
                case 5:
                    maxAreaAtv.setQtdAnaliseSoft(maxAreaAtv.getQtdAnaliseSoft() + Numeros.UM);
                    break;
                default:
                    break;
            }
        }

        for (Funcionario funcionario : listaFunc) {
            switch (funcionario.getArea().getCodigo()) {
                case 1:
                    maxAreaFunc.setQtdDev(maxAreaFunc.getQtdDev() + Numeros.UM);
                    break;
                case 2:
                    maxAreaFunc.setQtdTest(maxAreaFunc.getQtdTest() + Numeros.UM);
                    break;
                case 3:
                    maxAreaFunc.setQtdBanco(maxAreaFunc.getQtdBanco() + Numeros.UM);
                    break;
                case 4:
                    maxAreaFunc.setQtdAnaliseReq(maxAreaFunc.getQtdAnaliseReq() + Numeros.UM);
                    break;
                case 5:
                    maxAreaFunc.setQtdAnaliseSoft(maxAreaFunc.getQtdAnaliseSoft() + Numeros.UM);
                    break;
                default:
                    break;
            }
        }

        for (Atividade atv : listaAtv) {
            for (Funcionario func : listaFunc) {
                if (atv.getArea().equals(func.getArea())) {
                    if (atv.getNivel().getCodigo() <= func.getCargo().getCodigo()) {
                        switch (atv.getArea().getCodigo()) {
                            case 1:
                                if (func.getAtividades().size() < seZeroRetorneUm((maxAreaAtv.getQtdDev() / maxAreaFunc.getQtdDev()))) {
                                    func.getAtividades().add(atv);
                                    atv.setResponsavel(func);
                                }
                                break;
                            case 2:
                                if (func.getAtividades().size() < seZeroRetorneUm((maxAreaAtv.getQtdTest()/ maxAreaFunc.getQtdTest()))) {
                                    func.getAtividades().add(atv);
                                    atv.setResponsavel(func);
                                }
                                break;
                            case 3:
                                if (func.getAtividades().size() < seZeroRetorneUm((maxAreaAtv.getQtdBanco()/ maxAreaFunc.getQtdBanco()))) {
                                    func.getAtividades().add(atv);
                                    atv.setResponsavel(func);
                                }
                                break;
                            case 4:
                                if (func.getAtividades().size() < seZeroRetorneUm((maxAreaAtv.getQtdAnaliseReq()/ maxAreaFunc.getQtdAnaliseReq()))) {
                                    func.getAtividades().add(atv);
                                    atv.setResponsavel(func);
                                }
                                break;
                            case 5:
                                if (func.getAtividades().size() < seZeroRetorneUm((maxAreaAtv.getQtdAnaliseSoft()/ maxAreaFunc.getQtdAnaliseSoft()))) {
                                    func.getAtividades().add(atv);
                                    atv.setResponsavel(func);
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }

        /*Duplicação de chave primaria na tabela atividade_funcionario*/
        for (Funcionario funcionario : listaFunc) {
            Funcionario f = manager.find(Funcionario.class, funcionario.getCodigo());
            f = funcionario;
            manager.persist(f);
            System.out.println(funcionario.getNome() + ": " + funcionario.getAtividades().size());
            
        }
        
        for (Atividade atividade : listaAtv) {
            Atividade a = manager.find(Atividade.class, atividade.getCodigo());
            a =  atividade;
            manager.persist(a);
            if(atividade.getResponsavel() != null){
                System.out.println(atividade.getNome() + ": " + atividade.getResponsavel().getNome() + ": " + 
                        atividade.getResponsavel().getCargo().getDescricao() + ": " + atividade.getResponsavel().getArea().getDescricao());
            } else {
                System.out.println(atividade.getNome() + ": Sem responsável");
            }
        }

        manager.getTransaction().commit();
        manager.close();
        JOptionPane.showMessageDialog(null, "Associado");
    }

    public static Integer seZeroRetorneUm(Integer valor) {
        if (valor.equals(Numeros.ZERO)) {
            return Numeros.UM;
        } else {
            return valor;
        }
    }
}
