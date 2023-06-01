package controle;

import dao.AdvogadoDao;
import dao.UsuarioDao;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import model.Advogado;
import model.Usuario;
import utilidade.Utilidade;

public class CadAdvogadoController implements Initializable{

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
    private TableColumn<Advogado, String> tableColumnNomeAdvogado;

    @FXML
    private TableView<Advogado> tableView;

    @FXML
    private TextField txtFieldPesquisa;
    
    @FXML
    private TextField txtID;

    @FXML
    private TextField txtLogin;

    @FXML
    private TextField txtSenha;
    
    private Advogado advo;
    private AdvogadoDao advodao;
    private UsuarioDao usudao;
    private Usuario usu;
    private ArrayList<Advogado> advogados = new ArrayList<>();
    private ObservableList<Advogado> oblAdvogados;

    @FXML
    void btnAlterarOnAction(ActionEvent event) throws SQLException {
        int conta =0;
        String msg ="";
        boolean retorno;
        if(txtID.getText().length()==0||txtID.getText().isEmpty()){
            msg+="Selecione um advogado na lista de pesquisa\n";
        }
        if(CodOAB.getText().length()!=6||CodOAB.getText().isEmpty()){
            msg+="Informe a OAB corretamente (6 números).\n";
            conta++;
        }
        if(nomeAdvogado.getText().length()==0||nomeAdvogado.getText().isEmpty()){
            msg+="Informe o nome do Advogado.\n";
            conta++;
            
        }
        if(txtLogin.getText().length()==0||txtLogin.getText().isEmpty()){
            msg+="Informe o login.\n";
            conta++;          
        }
        if(txtSenha.getText().length()==0||txtSenha.getText().isEmpty()){
            msg+="Informe a senha.\n";
            conta++;
            
        }
        if(verificaLogin(txtLogin.getText())!=false&&!usu.getLogin().equals(txtLogin.getText())){
            msg+="Esse login já existe.\n";
            conta++;          
        }
        if(verificaOAB(CodOAB.getText())!=false&&Integer.parseInt(CodOAB.getText())!=advo.getOab()){
            msg+="Essa OAB já foi cadastrada.\n";
            conta++;        
        }
        if(conta>0){
            Utilidade.mensagemInformacao(msg);
        }
        else{
            advo = new Advogado();
            advodao = new AdvogadoDao();
            advo = advodao.consultarID(Integer.parseInt(txtID.getText()));
            int id_login = advo.getId_login();
            int id_advo = advo.getId();
            usu = new Usuario();
            usu.setLogin(txtLogin.getText());
            usu.setSenha(txtSenha.getText());
            usu.setId(id_login);
            usudao= new UsuarioDao();
            try{
                retorno = usudao.alterar(usu);
                if(retorno){
                    advo = new Advogado();
                    advo.setId(id_advo);
                    advo.setOab(Integer.parseInt(CodOAB.getText()));
                    advo.setNome(nomeAdvogado.getText());
                    advodao = new AdvogadoDao();
                    try{
                    retorno = advodao.alterar(advo);
                    if(retorno)
                        Utilidade.mensagemInformacao("ALTERAÇÃO DE ADVOGADO E LOGIN REALIZADA COM SUCESSO");
                    else
                        Utilidade.mensagemErro("ALTERAÇÃO DE ADVOGADO E LOGIN NÃO REALIZADA");
                    }catch(SQLException e){
                        Utilidade.mensagemErro("ERRO DE ALTERAÇÃO DE ADVOGADO E LOGIN");
                    }
                    limpaDados();
                }
                else
                    Utilidade.mensagemErro("ALTERAÇÃO DE LOGIN NÃO REALIZADA");
            }catch(SQLException e){
                Utilidade.mensagemErro("ERRO DE ALTERAÇÃO DE LOGIN");
            }
            
        }

    }

    @FXML
    void btnExcluirOnAction(ActionEvent event) throws SQLException {
        Advogado adv = null;
        boolean retorno;
        if(CodOAB.getText().length()==0||CodOAB.getText().isEmpty()){
            Utilidade.mensagemErro("Informe a OAB");
            CodOAB.setFocusTraversable(true);
        }else{
            advo = new Advogado();
            advodao = new AdvogadoDao();
            advo = advodao.consultarID(Integer.parseInt(txtID.getText()));
            int id_login = advo.getId_login();
            int id_advo = advo.getId();
            advo = new Advogado();
            advo.setId(id_advo);
            advo.setOab(Integer.parseInt(CodOAB.getText()));
            advo.setNome(nomeAdvogado.getText());
            advodao = new AdvogadoDao();
            
            try{
                retorno = advodao.excluir(advo);
                if(retorno){
                    usu = new Usuario();
                    usu.setId(id_login);
                    usu.setLogin(txtLogin.getText());
                    usu.setSenha(txtSenha.getText());
                    usudao= new UsuarioDao();
                    try{
                        retorno = usudao.excluir(usu);
                    if(retorno)
                        Utilidade.mensagemInformacao("EXCLUSÃO DE ADVOGADO E LOGIN REALIZADA COM SUCESSO");
                    else
                        Utilidade.mensagemErro("EXCLUSÃO DE ADVOGADO E LOGIN NÃO REALIZADA");
                    }catch(SQLException e){
                        Utilidade.mensagemErro("ERRO DE EXCLUSÃO DE ADVOGADO E LOGIN");
                    }
                    limpaDados();
                }
                else
                    Utilidade.mensagemErro("INCLUSÃO DE LOGIN NÃO REALIZADA");
            }catch(SQLException e){
                Utilidade.mensagemErro("ERRO DE INCLUSÃO DE LOGIN");
            }
        }

    }
     
    @FXML
    void btnIncluirOnAction(ActionEvent event) throws SQLException {
        Advogado adv = null;
        boolean retorno;
        int conta =0;
        String msg="";
        if(CodOAB.getText().length()!=6||CodOAB.getText().isEmpty()){
            msg+="Informe a OAB corretamente (6 números).\n";
            conta++;
            
        }
        if(nomeAdvogado.getText().length()==0||nomeAdvogado.getText().isEmpty()){
            msg+="Informe o nome do Advogado.\n";
            conta++;
            
        }
        if(txtLogin.getText().length()==0||txtLogin.getText().isEmpty()){
            msg+="Informe o login.\n";
            conta++;
           
        }
        if(txtSenha.getText().length()==0||txtSenha.getText().isEmpty()){
            msg+="Informe a senha.\n";
            conta++;
            
        }
        if(verificaLogin(txtLogin.getText())==true){
            msg+="Esse login já existe.\n";
            conta++;
        }
        if(verificaOAB(CodOAB.getText())==true){
            msg+="Essa OAB já foi cadastrada.\n";
            conta++;
        }
        if(conta>0){
            Utilidade.mensagemInformacao(msg);
        }
        else{
            usu = new Usuario();
            usu.setLogin(txtLogin.getText());
            usu.setSenha(txtSenha.getText());
            usudao= new UsuarioDao();
            try{
                retorno = usudao.incluir(usu);
                if(retorno){
                    advo = new Advogado();
                    advo.setOab(Integer.parseInt(CodOAB.getText()));
                    advo.setNome(nomeAdvogado.getText());
                    usu= usudao.consultar(usu);
                    advo.setId_login(usu.getId());
                    advodao = new AdvogadoDao();
                    try{
                    retorno = advodao.incluir(advo);
                    if(retorno)
                        Utilidade.mensagemInformacao("INCLUSÃO DE ADVOGADO E LOGIN REALIZADA COM SUCESSO");
                    else
                        Utilidade.mensagemErro("INCLUSÃO DE ADVOGADO E LOGIN NÃO REALIZADA");
                        usudao.excluir(usu);
                    }catch(SQLException e){
                        Utilidade.mensagemErro("ERRO DE INCLUSÃO DE ADVOGADO E LOGIN");
                        usudao.excluir(usu);
                    }
                    limpaDados();
                }
                else
                    Utilidade.mensagemErro("INCLUSÃO DE LOGIN NÃO REALIZADA");
            }catch(SQLException e){
                Utilidade.mensagemErro("ERRO DE INCLUSÃO DE LOGIN");
            }
            
        }
    }

    @FXML
    void btnPesquisaOnAction(ActionEvent event) {
        String aux="";
        aux=txtFieldPesquisa.getText();
        advodao= new AdvogadoDao();
        advo = new Advogado();
        if(!comboBoxPesquisa.getValue().equals("Por OAB")){
            try{
                advogados = advodao.consultar(aux);
            }catch(SQLException e){
                Utilidade.mensagemErro("Erro na consulta");
            }
            try{
                carregarTableViewAdvogadosComColecao(advogados);
            }catch(SQLException e){
                Utilidade.mensagemErro("Erro ao alimentar a tabela com dados");
            }
        }
        else{
            if(Utilidade.validaConteudoInteiro(aux)==true){
                try{
                    int cod= Integer.parseInt(aux);
                    advo = new Advogado();
                    advodao = new AdvogadoDao();
                    advo = advodao.consultar(cod);
                    advogados = new ArrayList<>();
                    advogados.add(advo);
                    try{
                carregarTableViewAdvogadosComColecao(advogados);
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
    public void initialize(URL location, ResourceBundle resources) {
        //foco tab
        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>(){
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue){
                if(observable.getValue().getText().equals("Pesquisa"))try {
                    carregarTableViewAdvogados();
                    ObservableList<String> tipo = FXCollections.observableArrayList("Por OAB","Por nome");
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
                    selecionarItemTableViewAdvogado((Advogado)arg2);
                } catch (SQLException ex) {
                    Logger.getLogger(CadAdvogadoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        //Limitar tamanho e só aceitar números no codOAB
        TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*") && newText.length() <= 6) {
                return change;
            } else {
                return null;
            }
        });
        //Limitar tamanho do login
        CodOAB.setTextFormatter(textFormatter);
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
        CodOAB.setTextFormatter(textFormatter);
        TextFormatter<String> textFormatter3 = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() <= 12) {
                return change;
            } else {
                return null;
            }
        });
        txtSenha.setTextFormatter(textFormatter3);   
        //Limitar tamanho do nome
        CodOAB.setTextFormatter(textFormatter);
        TextFormatter<String> textFormatter4 = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() <= 40) {
                return change;
            } else {
                return null;
            }
        });
        nomeAdvogado.setTextFormatter(textFormatter4);
    }
    public void limpaDados(){
        this.txtID.setText("");
        this.CodOAB.setText("");
        this.nomeAdvogado.setText("");
        this.txtLogin.setText("");
        this.txtSenha.setText("");
    }
    //Verificador da oab
    public boolean verificaOAB(String oab) throws SQLException{
        if (oab.isEmpty()) {
        return false; // Retorna false se o campo estiver vazio
        }

        try {
            int aux = Integer.parseInt(oab);
            advodao = new AdvogadoDao();
            Advogado adv = new Advogado();
            adv = advodao.consultar(aux);
            return adv != null;
        } catch (NumberFormatException e) {
            return false; // Retorna false se o valor não puder ser convertido para um número inteiro
        }
    }
    // Verificador do login
    public boolean verificaLogin(String login) throws SQLException{
        Usuario usuario = new Usuario();
        usudao = new UsuarioDao();
        usuario.setLogin(login);
        Usuario resultado = usudao.consultar(login);

        return resultado != null;
    }
    //Carregar a Tabela com o nome dos advogados
    public void carregarTableViewAdvogados() throws SQLException{
        tableColumnNomeAdvogado.setCellValueFactory(new PropertyValueFactory<>("nome"));
        advodao = new AdvogadoDao();
        advogados= advodao.consultar();
        oblAdvogados = FXCollections.observableArrayList(advogados);
        this.tableView.setItems(oblAdvogados);
    }
    //Selecionar advogado
    public void selecionarItemTableViewAdvogado(Advogado a)throws SQLException{
        advo = new Advogado();
        advo=a;
        this.txtID.setText(Integer.toString(a.getId()));
        this.CodOAB.setText(Integer.toString(a.getOab()));
        this.nomeAdvogado.setText(a.getNome());
        usu = new Usuario();
        usudao= new UsuarioDao();
        usu.setId(a.getId_login());
        usu = usudao.consultar(usu);
        this.txtLogin.setText(usu.getLogin());
        this.txtSenha.setText(usu.getSenha());
        tabPane.getSelectionModel().select(abaCadastro);
    }
    //Atualização de table por pesquisa
    public void carregarTableViewAdvogadosComColecao(ArrayList<Advogado> atores) throws SQLException{
        oblAdvogados = FXCollections.observableArrayList(atores);
        tableColumnNomeAdvogado.setCellValueFactory(new PropertyValueFactory<>("nome"));
        this.tableView.setItems(oblAdvogados);
    }

    
}
