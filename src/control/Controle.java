package control;

import bean.Atividade;
import bean.Funcionario;
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
        FileOutputStream fos;
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
        }
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
     */
    @SuppressWarnings({"Convert2Diamond", "InfiniteRecursion"})
    public static List<Funcionario> carregarFuncionarios() {
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
}
