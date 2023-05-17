package controle;

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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Usuario;

public class TelaLoginController implements Initializable{

    @FXML
    private Button btnEntrar;

    @FXML
    private TextField txtLogin;

    @FXML
    private TextField txtSenha;
    
    Usuario usu = null;
    UsuarioDao usudao = new UsuarioDao();

    @FXML
    void btnEntrarOnAction(ActionEvent event) throws IOException, SQLException {
        usu.setLogin(txtLogin.getText());
        usu.setSenha(txtSenha.getText());
        if(verificaUsuario(usu)){
            
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
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    public boolean verificaUsuario(Usuario u) throws SQLException{
        boolean aux;
        Usuario usu2= usudao.consultar(u);
        if(usu.getLogin().equals(usu2.getLogin())&&usu.getSenha().equals(usu2.getSenha())){
            this.usu=usu2;
            return true;
        }
        else{
            return false;
        }
    }

}
