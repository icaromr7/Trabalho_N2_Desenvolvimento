/*
Classe que controla a tela de Login
Suas funcionalidades são de verificar se o login e senha estão corretos
em caso de correto, vai chamar a tela principal.
No caso de incorreto, apresentará um alerta informando qual é o erro.
*/
package controle;

import dao.UsuarioDao;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Usuario;
import utilidade.Utilidade;

public class TelaLoginController implements Initializable{

    @FXML
    private Button btnEntrar;
    
    @FXML
    private TextField txtLogin;

    @FXML
    private PasswordField txtSenha;
    
    private Stage stage;
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
                    else if( key.contains("stage")){
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
            Parent root = FXMLLoader.load(getClass().getResource("/view/TelaPrincipal.fxml"), rbJanela);
            Image image = new Image(new FileInputStream("C:\\Users\\icaro\\OneDrive\\Documentos\\NetBeansProjects\\Trabalho_N2_Desenvolvimento\\images\\Slogan.png"));
            ImageView imageView1 = new ImageView(image);
            imageView1.setX(8); 
            imageView1.setY(5);
            imageView1.setFitHeight(246); 
            imageView1.setFitWidth(300);
            Group root2 = new Group(root,imageView1);
            Scene scene = new Scene(root2);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Tela Principal");
            //stage.initModality(Modality.APPLICATION_MODAL);
            stage.setX(0);
            stage.setY(0);
            stage.showAndWait();
        }
        else{
            Utilidade.mensagemInformacao("Login ou senha incorreta!");           
        }
        
    }
    
    

    @Override
    public void initialize(URL location, ResourceBundle rb) {
        this.stage = (Stage) rb.getObject("stage");
        //foco Login
        txtLogin.focusedProperty().addListener(new ChangeListener<Boolean>(){
            public void changed(ObservableValue<? extends Boolean>arg0,Boolean arg1, Boolean arg2){
                if(!arg2){
                }else{
                }
            }
        });
        //foco Senha
        txtSenha.focusedProperty().addListener(new ChangeListener<Boolean>(){
            public void changed(ObservableValue<? extends Boolean>arg0,Boolean arg1, Boolean arg2){
                if(!arg2){
                }else{
                }
            }
        });
        
    }
    public boolean verificaUsuario(Usuario u) throws SQLException{
        boolean aux;
        usudao = new UsuarioDao();
        Usuario usu2= usudao.consultar(u.getLogin());
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
