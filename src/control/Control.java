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

/**
 *
 * @author Celso Souza
 * @version 1.0
 */
public class Control {

    /**
     * 
     * @param obj Objeto a ser salvo
     * @param file Arquivo onde o objeto será salvo
     */
    public static void salvarObject(Object obj, File file) {
        FileOutputStream fos;
        ObjectOutputStream ous;
        try {
            fos = new FileOutputStream(file);
            ous = new ObjectOutputStream(fos);

            ous.writeObject(obj);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param file
     * @return Lista de objetos Funcionario
     */
    @SuppressWarnings("Convert2Diamond")
    public static List<Funcionario> carregarFuncionarios(File file) {
        List<Funcionario> retorno = null;
        FileInputStream fis;
        ObjectInputStream ois;

        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            
            retorno = new ArrayList<Funcionario>();

            retorno = (List<Funcionario>) ois.readObject();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    /**
     *
     * @param file
     * @return Lista de objetos Atividade
     */
    @SuppressWarnings("Convert2Diamond")
    public static List<Atividade> carregarAtividades(File file) {
        List<Atividade> retorno = null;
        FileInputStream fis;
        ObjectInputStream ois;

        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            
            retorno = new ArrayList<Atividade>();

            retorno = (List<Atividade>) ois.readObject();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
