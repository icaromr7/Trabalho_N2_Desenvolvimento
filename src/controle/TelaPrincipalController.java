package controle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

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
    void btnDeslogarOnAction(ActionEvent event) {

    }

    @FXML
    void btnPsqProcessoOnACtion(ActionEvent event) throws IOException {
        Pane a = (Pane) FXMLLoader.load(getClass().getResource("/view/PesquisaProcesso.fxml"));
        paneExibir.getChildren().setAll(a);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
