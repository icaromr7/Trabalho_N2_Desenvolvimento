package controle;

import dao.UsuarioDao;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Usuario;
import utilidade.Utilidade;

public class CadAdmController implements Initializable{

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
    private ComboBox<String> cbIdioma;

    @FXML
    private Label labelTableView;

    @FXML
    private TabPane tabPane;

    @FXML
    private TableColumn<Usuario, String> tableColumnNomeAdm;
    
    @FXML
    private TableColumn<Usuario, Integer> tableColumnId;

    @FXML
    private TableView<Usuario> tableView;

    @FXML
    private TextField txtFieldPesquisa;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtLogin;

    @FXML
    private TextField txtSenha;
    
    private UsuarioDao usudao;
    private Usuario usu;
    private ArrayList<Usuario> usuarios;
    private ObservableList<Usuario> oblUsuarios;
    private Usuario usuarioLogado;
    private Stage stage;
    private ResourceBundle rbJanela;
    private String avisoLogin,avisoSenha,avisoExiste;
    private String avisoAdm,avisoTabela,Inc1,Inc2,Inc3,Alt1,Alt2,Alt3,Exc1,Exc2,Exc3;

    @FXML
    void btnAlterarOnAction(ActionEvent event) throws SQLException {
        boolean retorno;
        int conta =0;
        String msg="";
        if(txtID.getText().length()==0||txtID.getText().isEmpty()){
            msg+=avisoTabela;
            txtID.setFocusTraversable(true);
            conta++;
        }
        if(txtLogin.getText().length()==0||txtLogin.getText().isEmpty()){
            msg+=avisoLogin;
            conta++;
           
        }
        if(txtSenha.getText().length()==0||txtSenha.getText().isEmpty()){
            msg+=avisoSenha;
            conta++;
            
        }
        if(verificaLogin(txtLogin.getText())==true&&!usu.getLogin().equals(txtLogin.getText())){
            msg+=avisoExiste;
            conta++;
        }      
        if(conta>0){
            Utilidade.mensagemInformacao(msg);
        }
        else{
            usu = new Usuario();
            usu.setId(Integer.parseInt(txtID.getText()));
            usu.setLogin(txtLogin.getText());
            usu.setSenha(txtSenha.getText());
            usu.setCargo("Administrador");
            usudao= new UsuarioDao();
            try{
                retorno = usudao.alterar(usu);
                if(retorno){
                    Utilidade.mensagemInformacao(Alt1);                   
                    limpaDados();
                }
                else
                    Utilidade.mensagemErro(Alt2);
            }catch(SQLException e){
                Utilidade.mensagemErro(Alt3);
            }
            
        }

    }

    @FXML
    void btnExcluirOnAction(ActionEvent event) throws SQLException, IOException {
        boolean retorno;
        usudao= new UsuarioDao();
        String msg = "";
        int conta=0;
        if(txtID.getText().length()==0||txtID.getText().isEmpty()){
            msg+=avisoTabela;
            txtID.setFocusTraversable(true);
            conta++;
        }
        if(usudao.obterQuantidadeUsuarios()==1){
            msg+=avisoAdm;
            conta++;
        }
        if(conta>0){
            Utilidade.mensagemInformacao(msg);
        }
        else{
            usu = new Usuario();
            usu.setId(Integer.parseInt(txtID.getText()));
            usudao= new UsuarioDao();
            try{
                retorno = usudao.excluir(usu);
            if(retorno){
                Utilidade.mensagemInformacao(Exc1);
                if(usuarioLogado.getId()==Integer.parseInt(txtID.getText())){
                    deslogar();
                }
            }
            else
                Utilidade.mensagemErro(Exc2);
            }catch(SQLException e){
                Utilidade.mensagemErro(Exc3);
            }
            limpaDados();                            
        }

    }

    @FXML
    void btnIncluirOnAction(ActionEvent event) throws SQLException {
        boolean retorno;
        int conta =0;
        String msg="";
        
        if(txtLogin.getText().length()==0||txtLogin.getText().isEmpty()){
            msg+=avisoLogin;
            conta++;
           
        }
        if(txtSenha.getText().length()==0||txtSenha.getText().isEmpty()){
            msg+=avisoSenha;
            conta++;
            
        }
        if(verificaLogin(txtLogin.getText())==true){
            msg+=avisoExiste;
            conta++;
        }      
        if(conta>0){
            Utilidade.mensagemInformacao(msg);
        }
        else{
            usu = new Usuario();
            usu.setLogin(txtLogin.getText());
            usu.setSenha(txtSenha.getText());
            usu.setCargo("Administrador");
            usudao= new UsuarioDao();
            try{
                retorno = usudao.incluir(usu);
                if(retorno){
                    Utilidade.mensagemInformacao(Inc1);                   
                    limpaDados();
                }
                else
                    Utilidade.mensagemErro(Inc2);
            }catch(SQLException e){
                Utilidade.mensagemErro(Inc3);
            }
            
        }
    }

    @FXML
    void btnPesquisaOnAction(ActionEvent event) {
        String aux="";
        aux=txtFieldPesquisa.getText();
        usudao= new UsuarioDao();
        usu = new Usuario();
        if(!comboBoxPesquisa.getValue().equals("Por OAB")){
            try{
                usuarios = usudao.consultarAdm(aux);
            }catch(SQLException e){
                Utilidade.mensagemErro("Erro na consulta");
            }
            try{
                carregarTableViewAdmComColecao(usuarios);
            }catch(SQLException e){
                Utilidade.mensagemErro("Erro ao alimentar a tabela com dados");
            }
        }
        else{
            if(Utilidade.validaConteudoInteiro(aux)==true){
                try{
                    int cod= Integer.parseInt(aux);
                    usu = new Usuario();
                    usudao = new UsuarioDao();
                    usu.setId(cod);
                    usu = usudao.consultar(usu);
                    usuarios = new ArrayList<>();
                    usuarios.add(usu);
                    try{
                carregarTableViewAdmComColecao(usuarios);
            }catch(SQLException e){
                Utilidade.mensagemErro("Erro ao alimentar a tabela com dados");
            }
                }catch(SQLException e){
                    Utilidade.mensagemErro("Erro na consulta");
                }
            }else{
                Utilidade.mensagemErro("Insira apenas números!");
            }
            
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle rb) {
        padrao();
        ObservableList<String> aux = FXCollections.observableArrayList("English","Español","Português");
        cbIdioma.setItems(aux);
        this.stage = (Stage) rb.getObject("stage");
        this.usuarioLogado = (Usuario) rb.getObject("Usuario");
        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>(){
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue){
                if(observable.getValue().getText().equals("Pesquisa"))try {
                    carregarTableViewAdm();
                    ObservableList<String> tipo = FXCollections.observableArrayList("Por id","Por login");
                    comboBoxPesquisa.setItems(tipo);
                } catch (SQLException ex) {
                    Logger.getLogger(CadAdvogadoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        //Seleção na tabela
        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener(){
            public void changed(ObservableValue arg0, Object arg1, Object arg2){
                try {
                    selecionarItemTableViewAdm((Usuario)arg2);
                } catch (SQLException ex) {
                    Logger.getLogger(CadAdvogadoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        //Limitar tamanho do login
        TextFormatter<String> textFormatter2 = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() <= 12) {
                return change;
            } else {
                return null;
            }
        });
        txtLogin.setTextFormatter(textFormatter2);
        //Limitar tamanho da senha
 
        TextFormatter<String> textFormatter3 = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() <= 12) {
                return change;
            } else {
                return null;
            }
        });
        txtSenha.setTextFormatter(textFormatter3); 
    }
    
    // Verificador do login
    public boolean verificaLogin(String login) throws SQLException{
        Usuario usuario = new Usuario();
        usudao = new UsuarioDao();
        usuario.setLogin(login);
        Usuario resultado = usudao.consultar(login);

        return resultado != null;
    }
    //Limpar dados da tela
    public void limpaDados(){
        this.txtID.setText("");
        this.txtLogin.setText("");
        this.txtSenha.setText("");
    }
    public void carregarTableViewAdm() throws SQLException{
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnNomeAdm.setCellValueFactory(new PropertyValueFactory<>("login"));
        usudao = new UsuarioDao();
        usuarios= usudao.consultarAdms();
        oblUsuarios = FXCollections.observableArrayList(usuarios);
        this.tableView.setItems(oblUsuarios);
    }
    //Selecionar advogado
    public void selecionarItemTableViewAdm(Usuario a)throws SQLException{
        this.usu = a;
        this.txtID.setText(Integer.toString(a.getId()));
        this.txtLogin.setText(a.getLogin());
        this.txtSenha.setText(a.getSenha());
        tabPane.getSelectionModel().select(abaCadastro);
    }
    //Atualização de table por pesquisa
    public void carregarTableViewAdmComColecao(ArrayList<Usuario> usuarios) throws SQLException{
        oblUsuarios = FXCollections.observableArrayList(usuarios);
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnNomeAdm.setCellValueFactory(new PropertyValueFactory<>("login"));
        this.tableView.setItems(oblUsuarios);
    }
    public void deslogar() throws IOException{
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
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
    }
    public void padrao(){
        this.avisoTabela="Selecione um usuario na tabela de pesquisa\n";
        this.avisoLogin="Informe o login.\n";
        this.avisoSenha="Informe a senha.\n";
        this.avisoExiste="Esse login já existe.\n";
        this.avisoAdm="Existe apenas um Administrador, não é possível a exclusão!\n";
        this.Alt1="ALTERAÇÃO REALIZADA COM SUCESSO";
        this.Alt2="ALTERAÇÃO NÂO REALIZADA COM SUCESSO";
        this.Alt3="ERRO NA ALTERAÇÃO";
        this.Exc1="EXLCUSÃO REALIZADA COM SUCESSO";
        this.Exc2="EXLCUSÃO NÃO REALIZADA COM SUCESSO";
        this.Exc3="ERRO NA EXCLUSÃO";
        this.Inc1="INCLUSÃO REALIZADA COM SUCESSO";
        this.Inc2="INCLUSÃO NÃO REALIZADA COM SUCESSO";
        this.Inc3="ERRO NA INCLUSÃO";
    }
    @FXML
    void cbIdiomaOnAction(ActionEvent event) {
        if(cbIdioma.getValue().equals("Español")){
            this.avisoTabela="Seleccione un usuario de la tabla de búsqueda\n";
            this.avisoLogin="Ingrese inicio de sesión.\n";
            this.avisoSenha="Ingrese la contraseña.\n";
            this.avisoExiste="Este inicio de sesión ya existe.\n";
            this.avisoAdm="Solo hay un administrador, ¡no es posible eliminarlo!\n";
            this.Alt1="CAMBIO REALIZADO CON ÉXITO";
            this.Alt2="CAMBIO NO REALIZADO CON ÉXITO";
            this.Alt3="ERROR DE CAMBIO";
            this.Exc1="EXCLUSIÓN REALIZADA CON ÉXITO";
            this.Exc2="EXCLUSIÓN NO REALIZADA CON ÉXITO";
            this.Exc3="ERROR DE EXCLUSIÓN";
            this.Inc1="INCLUSIÓN REALIZADA CON ÉXITO";
            this.Inc2="INCLUSIÓN NO REALIZADA CON ÉXITO";
            this.Inc3="ERROR DE INCLUSIÓN";
            this.txtLogin.setPromptText("Usuario (Máx.: 12 dígitos)");
            this.txtSenha.setPromptText("Contraseña (Máx.: 12 dígitos)");
            this.btnIncluir.setText("Incluir");
            this.btnAlterar.setText("Alterar");
            this.btnExcluir.setText("Borrar");
        }
        else if(cbIdioma.getValue().equals("English")){
            this.avisoTabela="Select a user from the lookup table\n";
            this.avisoLogin="Please enter login.\n";
            this.avisoSenha="Enter password.\n";
            this.avisoExiste="This login already exists.\n";
            this.avisoAdm="There is only one administrator, it is not possible to delete it!\n";
            this.Alt1="CHANGE MADE SUCCESSFULLY";
            this.Alt2="CHANGE NOT SUCCESSFULLY MADE";
            this.Alt3="CHANGE MISTAKE";
            this.Exc1="EXCLUSION MADE SUCCESSFULLY";
            this.Exc2="EXCLUSION NOT SUCCESSFULLY MADE";
            this.Exc3="EXCLUSION ERROR";
            this.Inc1="INCLUSION MADE SUCCESSFULLY";
            this.Inc2="INCLUSION NOT SUCCESSFULLY MADE";
            this.Inc3="INCLUSION ERROR";
            this.txtLogin.setPromptText("User (Max: 12 digits)");
            this.txtSenha.setPromptText("Password (Max: 12 digits)");
            this.btnIncluir.setText("Include");
            this.btnAlterar.setText("Alter");
            this.btnExcluir.setText("Delete");
        }
        else if(cbIdioma.getValue().equals("Português")){
            this.avisoTabela="Selecione um usuario na tabela de pesquisa\n";
            this.avisoLogin="Informe o login.\n";
            this.avisoSenha="Informe a senha.\n";
            this.avisoExiste="Esse login já existe.\n";
            this.avisoAdm="Existe apenas um Administrador, não é possível a exclusão!\n";
            this.Alt1="ALTERAÇÃO REALIZADA COM SUCESSO";
            this.Alt2="ALTERAÇÃO NÂO REALIZADA COM SUCESSO";
            this.Alt3="ERRO NA ALTERAÇÃO";
            this.Exc1="EXLCUSÃO REALIZADA COM SUCESSO";
            this.Exc2="EXLCUSÃO NÃO REALIZADA COM SUCESSO";
            this.Exc3="ERRO NA EXCLUSÃO";
            this.Inc1="INCLUSÃO REALIZADA COM SUCESSO";
            this.Inc2="INCLUSÃO NÃO REALIZADA COM SUCESSO";
            this.Inc3="ERRO NA INCLUSÃO";
            this.txtLogin.setPromptText("Usuário (Máx.: 12 dígitos)");
            this.txtSenha.setPromptText("Senha (Máx.: 12 dígitos)");
            this.btnIncluir.setText("Incluir");
            this.btnAlterar.setText("Alterar");
            this.btnExcluir.setText("Excluir");
        }
    }

}
