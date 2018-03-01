package control;

import bean.Area;
import bean.Atividade;
import bean.Cargo;
import bean.Funcionario;
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
import java.util.Objects;
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
    public static void salvarAtividade(Atividade atv){
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
    
    public static ImageView returnLoadGif(){
        Image im = new Image("/files/load2.gif");
        ImageView iv = new ImageView(im);
        
        return iv;
    }
    
    
    public static void gerarFucionarios(){
        int nome = 0;
        int sobreNome01 = 0;
        int sobreNome02 = 0;        
        StringBuilder nomeFunc = new StringBuilder();
        int lenth = StringsUtils.NOME.length();
        
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(StringsUtils.ENTITY_MANAGER);                
        EntityManager manager = factory.createEntityManager();

        manager.getTransaction().begin();
        for (int i = 0; i < Numeros.QTD_FUNC; i++) {
                       
            nome = (int) (Math.random() * lenth);
            sobreNome01 = (int) (Math.random() * lenth);
            sobreNome02 = (int) (Math.random() * lenth);
            
            
            
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
                func.setTempo_exp((int) (Math.random() * 36));
                func.setTempo_proj((int) (Math.random() * 12));

                manager.persist(func);
            
        }
        
        manager.getTransaction().commit();       
        manager.close();
        factory.close();
        
        JOptionPane.showMessageDialog(null, "Gerados");
    }
}
