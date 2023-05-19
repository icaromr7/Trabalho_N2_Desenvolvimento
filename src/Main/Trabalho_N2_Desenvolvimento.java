/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package Main;

import java.io.FileInputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *
 * @author icaro
 */
public class Trabalho_N2_Desenvolvimento extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/TelaPrincipal.fxml"));
        Image image = new Image(new FileInputStream("C:\\Users\\icaro\\OneDrive\\Documentos\\NetBeansProjects\\Trabalho_N2_Desenvolvimento\\images\\Slogan3.png"));
        ImageView imageView1 = new ImageView(image);
        imageView1.setX(0); 
        imageView1.setY(0);
        imageView1.setFitHeight(246); 
        imageView1.setFitWidth(1627);
        Group root2 = new Group(root,imageView1);
        
        Scene scene = new Scene(root2);
        
        stage.setScene(scene);
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
