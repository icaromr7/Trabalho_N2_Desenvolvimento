/*
Classe com metódos para auxiliar outras classes.
*/
package utilidade;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 *
 * @author icaro
 */
public class Utilidade {
    
    //Msg Informação
    public static void mensagemInformacao(String msg){
        msg = msg.toUpperCase();
        Alert alert;
        alert= new Alert(AlertType.INFORMATION,msg,ButtonType.OK);
        alert.setTitle("SICOPA");
        alert.setHeaderText("Informação");
        alert.show();
    }
    //Msg Erro
    public static void mensagemErro(String msg){
        msg = msg.toUpperCase();
        Alert alert;
        alert= new Alert(AlertType.ERROR,msg,ButtonType.OK);
        alert.setTitle("SICOPA");
        alert.setHeaderText("Erro");
        alert.show();
    }
    //Valida conteudo
    public static boolean validaConteudoInteiro(String s){
        int a=0;
        boolean retorno=true;
        try{
            a=Integer.parseInt(s);
        }catch(Exception e){
            retorno=false;
        }
        return retorno;
    }
    
}
