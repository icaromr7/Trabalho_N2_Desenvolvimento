package controle;

import dao.AdvogadoDao;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Advogado;
import model.Usuario;
import utilidade.Utilidade;

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
    
    @FXML
    private Label labelCamargo;

    @FXML
    private Label labeladv;
    
    private Stage stage;
    private Usuario usu;
    private Advogado adv;
    private AdvogadoDao advdao;
    private ResourceBundle rbJanela;
    
    
    @FXML
    void btnCadAdvogadoOnAction(ActionEvent event) throws IOException {
        if(usu.getCargo().equals("Administrador")){
            AdvogadoSelecionado();
            Pane a = (Pane) FXMLLoader.load(getClass().getResource("/view/CadAdvogado.fxml"),rbJanela);
            paneExibir.getChildren().setAll(a);
        }
        else{
            Utilidade.mensagemInformacao("Apenas o administrador tem acesso.");
        }
    }

    @FXML
    void btnCadProcessoOnAction(ActionEvent event) throws IOException {
        if(usu.getCargo().equals("Advogado")){
            AdvogadoSelecionado();
            Pane a = (Pane) FXMLLoader.load(getClass().getResource("/view/CadAdvogado.fxml"),rbJanela);
            paneExibir.getChildren().setAll(a);
        }
        else{
            Utilidade.mensagemInformacao("Apenas o advogado tem acesso.");
        }

    }

    @FXML
    void btnCadTipoDeProcessoOnAction(ActionEvent event) throws IOException {
        if(usu.getCargo().equals("Administrador")){
            TpProcessoSelecionado();
            Pane a = (Pane) FXMLLoader.load(getClass().getResource("/view/CadTipoDeProcesso.fxml"),rbJanela);
            paneExibir.getChildren().setAll(a);
        }
        else{
            Utilidade.mensagemInformacao("Apenas o administrador tem acesso.");
        }
        
    }

    @FXML
    void btnDeslogarOnAction(ActionEvent event) throws IOException {
        rbJanela = new ResourceBundle() {
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
        Pane a = (Pane) FXMLLoader.load(getClass().getResource("/view/PesquisaProcesso.fxml"),rbJanela);
        paneExibir.getChildren().setAll(a);
    }

    @Override
    public void initialize(URL location, ResourceBundle rb) {
        this.stage = (Stage) rb.getObject("stage");
        this.usu = (Usuario) rb.getObject("Usuario");
        advdao = new AdvogadoDao();
        try {
            this.adv = advdao.consultarIDLogin(usu.getId());
        } catch (SQLException ex) {
            Logger.getLogger(TelaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        txtUsuario.setText(usu.getLogin());
        DropShadow sombra = new DropShadow();
        sombra.setColor(Color.BLACK); // Cor da sombra
        sombra.setRadius(10); // Raio do desfoque da sombra
        sombra.setOffsetX(3); // Deslocamento horizontal da sombra
        sombra.setOffsetY(3);
        Font.loadFont("file:///C:/Users/icaro/OneDrive/Documentos/NetBeansProjects/Trabalho_N2_Desenvolvimento/font/Intro.otf", 8);
        labelCamargo.setStyle("-fx-font-family: 'intro';");
        labeladv.setStyle("-fx-font-family: 'intro';");
        labelCamargo.setEffect(sombra);
        labeladv.setEffect(sombra);
        
        rbJanela = new ResourceBundle() {
                @Override
                protected Object handleGetObject(String key) {
                    if( key.contains("Usuario")){
                        return usu;
                    }
                    else if(key.contains("Advogado")){
                        return adv;
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
