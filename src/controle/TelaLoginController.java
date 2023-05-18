package controle;

import Main.Trabalho_N2_Desenvolvimento;
import dao.UsuarioDao;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Usuario;
import utilidade.Utilidade;

public class TelaLoginController implements Initializable{

    @FXML
    private Button btnEntrar;

    @FXML
    private TextField txtLogin;

    @FXML
    private TextField txtSenha;
    
    private Usuario usu;
    private UsuarioDao usudao = new UsuarioDao();

    @FXML
    void btnEntrarOnAction(ActionEvent event) throws IOException, SQLException, CloneNotSupportedException {
        int conta=0;
        String msg = "";
        if(txtLogin.getText().length()==0 || txtLogin.getText().isEmpty()){
            conta++;
            msg+="Digite o login.\n";
        }
        if(txtSenha.getText().length()==0 || txtSenha.getText().isEmpty()){
            conta++;
            msg+="Digite a senha.";
        }
        if(conta>0){
            Utilidade.mensagemInformacao(msg);
        }
        usu = new Usuario();
        usu.setLogin(txtLogin.getText());
        usu.setSenha(txtSenha.getText());
        if(verificaUsuario(usu)==true){
            
            ResourceBundle rbJanela = new ResourceBundle() {
                @Override
                protected Object handleGetObject(String key) {
                    if( key.contains("Usuario")){
                        return usu;
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
            Parent root = FXMLLoader.load(getClass().getResource("/view/MenuPrincipal.fxml"), rbJanela);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            //stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            stage.close();
        }
        else{
            Utilidade.mensagemInformacao("Login ou senha incorreta!");           
        }
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    public boolean verificaUsuario(Usuario u) throws SQLException{
        boolean aux;
        Usuario usu2= usudao.consultar(u);
        if(usu2==null){
            return false;
        }
        else{
            if(usu.getLogin().equals(usu2.getLogin())&&usu.getSenha().equals(usu2.getSenha())){
                this.usu=usu2;
                return true;
            }
            else{
                return false;
            }
            
        }
        
    }

}
