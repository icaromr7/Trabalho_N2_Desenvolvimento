/*
Classe que da inicio ao programa. Sua única funcionalidade é chamar a tela de login.
*/
package Main;

import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author icaro
 */
public class Trabalho_N2_Desenvolvimento extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        ResourceBundle rbJanela = new ResourceBundle() {
                @Override
                protected Object handleGetObject(String key) {
                    if( key.contains("stage")){
                        return stage;
                    }
                    else{
                        return null;
                    }
                }

                @Override
                public Enumeration<String> getKeys() {
                    throw new UnsupportedOperationException("Not supported yet."); 
                }
            };
        Parent root = FXMLLoader.load(getClass().getResource("/view/TelaLogin.fxml"),rbJanela);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.setResizable(false);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
