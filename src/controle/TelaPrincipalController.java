package controle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Usuario;

public class TelaPrincipalController implements Initializable{

    @FXML
    private Button btnCadAdvogado;

    @FXML
    private Button btnCadProcesso;

    @FXML
    private Button btnCadTipoDeProcesso;

    @FXML
    private Button btnDeslogar;

    @FXML
    private Button btnPsqProcesso;

    @FXML
    private Pane painel;

    @FXML
    private Label txtUsuario;
    
    @FXML
    private Pane paneExibir;
    
    @FXML
    private Pane painelSuperior;
    
    private Stage stage;
    private Usuario usu;

    @FXML
    void btnCadAdvogadoOnAction(ActionEvent event) throws IOException {
        Pane a = (Pane) FXMLLoader.load(getClass().getResource("/view/CadAdvogado.fxml"));
        paneExibir.getChildren().setAll(a);
    }

    @FXML
    void btnCadProcessoOnAction(ActionEvent event) throws IOException {
        Pane a = (Pane) FXMLLoader.load(getClass().getResource("/view/CadProcesso.fxml"));
        paneExibir.getChildren().setAll(a);

    }

    @FXML
    void btnCadTipoDeProcessoOnAction(ActionEvent event) throws IOException {
        Pane a = (Pane) FXMLLoader.load(getClass().getResource("/view/CadTipoDeProcesso.fxml"));
        paneExibir.getChildren().setAll(a);
    }

    @FXML
    void btnDeslogarOnAction(ActionEvent event) throws IOException {
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
            stage.setResizable(false);
            stage.show();
    }

    @FXML
    void btnPsqProcessoOnACtion(ActionEvent event) throws IOException {
        Pane a = (Pane) FXMLLoader.load(getClass().getResource("/view/PesquisaProcesso.fxml"));
        paneExibir.getChildren().setAll(a);
    }

    @Override
    public void initialize(URL location, ResourceBundle rb) {
        this.stage = (Stage) rb.getObject("stage");
        this.usu = (Usuario) rb.getObject("Usuario");
        txtUsuario.setText(usu.getLogin());
        
    }
    

}
