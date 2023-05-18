package controle;

import dao.AdvogadoDao;
import dao.UsuarioDao;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Advogado;
import utilidade.Utilidade;

public class CadAdvogadoController {

    @FXML
    private TextField CodOAB;

    @FXML
    private Tab abaCadastro;

    @FXML
    private Tab abaPesquisa;

    @FXML
    private Button btnAlterar;

    @FXML
    private Button btnExcluir;

    @FXML
    private Button btnIncluir;

    @FXML
    private Button btnPesquisa;

    @FXML
    private ComboBox<String> comboBoxPesquisa;

    @FXML
    private Label labelPesquisa;

    @FXML
    private Label labelTableView;

    @FXML
    private TextField nomeAdvogado;

    @FXML
    private TabPane tabPane;

    @FXML
    private TableColumn<?, ?> tableColumnNomeAtor;

    @FXML
    private TableView<?> tableView;

    @FXML
    private TextField txtFieldPesquisa;

    @FXML
    private TextField txtLogin;

    @FXML
    private TextField txtSenha;
    
    private Advogado advo;
    private AdvogadoDao advodao;
    private UsuarioDao usudao;

    @FXML
    void btnAlterarOnAction(ActionEvent event) {

    }

    @FXML
    void btnExcluirOnAction(ActionEvent event) {
        Advogado adv = null;
        boolean retorno;
        if(CodOAB.getText().length()==0||CodOAB.getText().isEmpty()){
            Utilidade.mensagemErro("Informe a OAB");
            CodOAB.setFocusTraversable(true);
        }else{
            adv = new Advogado();
            adv.setOab(Integer.parseInt(CodOAB.getText()));
            advodao = new AdvogadoDao();
            try{
                retorno=advodao.excluir(adv);
                if(retorno)
                    Utilidade.mensagemInformacao("EXCLUSÃO DE ADVOGADO REALIZADA COM SUCESSO");
                else
                    Utilidade.mensagemErro("EXCLUSÃO DE ADVOGADO NÃO REALIZADA");
            }catch(SQLException e){
                Utilidade.mensagemErro("ERRO DE EXCLUSÃO DE ADVOGADO");
            }
            limpaDados();
        }

    }
     
    @FXML
    void btnIncluirOnAction(ActionEvent event) throws SQLException {
        Advogado adv = null;
        boolean retorno;
        int conta =0;
        String msg="";
        if(CodOAB.getText().length()==0||CodOAB.getText().isEmpty()){
            msg+="Informe a OAB.\n";
            CodOAB.setFocusTraversable(true);
        }
        if(nomeAdvogado.getText().length()==0||nomeAdvogado.getText().isEmpty()){
            msg+="Informe o nome do Advogado.\n";
            nomeAdvogado.setFocusTraversable(true);
        }
        if(txtLogin.getText().length()==0||txtLogin.getText().isEmpty()){
            msg+="Informe o login.\n";
            txtLogin.setFocusTraversable(true);
        }
        if(txtSenha.getText().length()==0||txtSenha.getText().isEmpty()){
            msg+="Informe a senha.\n";
            txtSenha.setFocusTraversable(true);
        }
        if(verificaLogin(txtLogin.getText())==false){
            msg+="Esse login já existe.\n";
        }
        if(verificaOAB(CodOAB.getText())==false){
            msg+="Essa OAB já foi cadastrada.\n";
        }
        if(conta>0){
            Utilidade.mensagemInformacao(msg);
        }
        else{
            advo = new Advogado();
            advo.setOab(Integer.parseInt(CodOAB.getText()));
            advo.setNome(nomeAdvogado.getText());
            advo.setAdvlogin(txtLogin.getText());
            
            advodao = new AdvogadoDao();
            try{
                retorno = advodao.incluir(advo);
                if(retorno)
                    Utilidade.mensagemInformacao("INCLUSÃO DE ADVOGADO REALIZADA COM SUCESSO");
                else
                    Utilidade.mensagemErro("INCLUSÃO DE ADVOGADO NÃO REALIZADA");
            }catch(SQLException e){
                Utilidade.mensagemErro("ERRO DE INCLUSÃO DE ADVOGADO");
            }
            limpaDados();
        }
    }

    @FXML
    void btnPesquisaOnAction(ActionEvent event) {

    }
    
    public void limpaDados(){
        this.CodOAB.setText("");
        this.nomeAdvogado.setText("");
        this.txtLogin.setText("");
        this.txtSenha.setText("");
    }
    public boolean verificaOAB(String oab) throws SQLException{
        int aux = Integer.parseInt(oab);
        return advodao.consultar(aux)==null;
    }
    public boolean verificaLogin(String login) throws SQLException{
        return usudao.consultar(login)==null;
    }
}
