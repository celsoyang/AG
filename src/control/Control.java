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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ceolivei
 */
public class Control {

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

    public static List<Funcionario> carregarFuncionario() {
        return null;
    }

    public static List<Atividade> carregarAtividades() {
        return null;
    }
}
