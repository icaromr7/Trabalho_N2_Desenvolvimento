package controle;

import com.sun.javafx.css.StyleClassSet;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.ProcessBuilder.Redirect.to;
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
        AdvogadoSelecionado();
        Pane a = (Pane) FXMLLoader.load(getClass().getResource("/view/CadAdvogado.fxml"));
        paneExibir.getChildren().setAll(a);
    }

    @FXML
    void btnCadProcessoOnAction(ActionEvent event) throws IOException {
        ProcessoSelecionado();
        Pane a = (Pane) FXMLLoader.load(getClass().getResource("/view/CadProcesso.fxml"));
        paneExibir.getChildren().setAll(a);

    }

    @FXML
    void btnCadTipoDeProcessoOnAction(ActionEvent event) throws IOException {
        TpProcessoSelecionado();
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
        PsqProcessoSelecionado();
        Pane a = (Pane) FXMLLoader.load(getClass().getResource("/view/PesquisaProcesso.fxml"));
        paneExibir.getChildren().setAll(a);
    }

    @Override
    public void initialize(URL location, ResourceBundle rb) {
        this.stage = (Stage) rb.getObject("stage");
        this.usu = (Usuario) rb.getObject("Usuario");
        txtUsuario.setText(usu.getLogin());
        
    }
    //Alterar cor do button após opção selecionada
    //Advogado
    public void AdvogadoSelecionado(){
        btnCadAdvogado.setStyle("-fx-background-color:#e0e1dd;" +
     "-fx-border-color: none;" +
     "-fx-border-width: 0px;" +
     "-fx-background-radius: 50px; -fx-text-fill: black;" +
     "-fx-border-radius: 50px;" +
     "-fx-border-style: solid;");
        btnCadProcesso.setStyle("-fx-background-color: rgba(0, 0, 0, 0.1); -fx-border-color: none; -fx-border-width: 0px;");
        btnCadTipoDeProcesso.setStyle("-fx-background-color: rgba(0, 0, 0, 0.1); -fx-border-color: none; -fx-border-width: 0px;");
        btnPsqProcesso.setStyle("-fx-background-color: rgba(0, 0, 0, 0.1); -fx-border-color: none; -fx-border-width: 0px;");
    }
    //Tipo de processo
    public void TpProcessoSelecionado(){
        btnCadTipoDeProcesso.setStyle("-fx-background-color:#e0e1dd;" +
     "-fx-border-color: none;" +
     "-fx-border-width: 0px;" +
     "-fx-background-radius: 50px; -fx-text-fill: black;" +
     "-fx-border-radius: 50px;" +
     "-fx-border-style: solid;");
        btnCadProcesso.setStyle("-fx-background-color: rgba(0, 0, 0, 0.1); -fx-border-color: none; -fx-border-width: 0px;");
        btnCadAdvogado.setStyle("-fx-background-color: rgba(0, 0, 0, 0.1); -fx-border-color: none; -fx-border-width: 0px;");
        btnPsqProcesso.setStyle("-fx-background-color: rgba(0, 0, 0, 0.1); -fx-border-color: none; -fx-border-width: 0px;");
    }
    //Processo
    public void ProcessoSelecionado(){
        btnCadProcesso.setStyle("-fx-background-color:#e0e1dd;" +
     "-fx-border-color: none;" +
     "-fx-border-width: 0px;" +
     "-fx-background-radius: 50px; -fx-text-fill: black;" +
     "-fx-border-radius: 50px;" +
     "-fx-border-style: solid;");
        btnCadTipoDeProcesso.setStyle("-fx-background-color: rgba(0, 0, 0, 0.1); -fx-border-color: none; -fx-border-width: 0px;");
        btnCadAdvogado.setStyle("-fx-background-color: rgba(0, 0, 0, 0.1); -fx-border-color: none; -fx-border-width: 0px;");
        btnPsqProcesso.setStyle("-fx-background-color: rgba(0, 0, 0, 0.1); -fx-border-color: none; -fx-border-width: 0px;");
    }
    //Pesquisa Processo
    public void PsqProcessoSelecionado(){
        btnPsqProcesso.setStyle("-fx-background-color:#e0e1dd;" +
     "-fx-border-color: none;" +
     "-fx-border-width: 0px;" +
     "-fx-background-radius: 50px; -fx-text-fill: black;" +
     "-fx-border-radius: 50px;" +
     "-fx-border-style: solid;");
        btnCadTipoDeProcesso.setStyle("-fx-background-color: rgba(0, 0, 0, 0.1); -fx-border-color: none; -fx-border-width: 0px;");
        btnCadAdvogado.setStyle("-fx-background-color: rgba(0, 0, 0, 0.1); -fx-border-color: none; -fx-border-width: 0px;");
        btnCadProcesso.setStyle("-fx-background-color: rgba(0, 0, 0, 0.1); -fx-border-color: none; -fx-border-width: 0px;");
    }
}
