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
        if(txtID.getText().length()==0||txtID.getText().isEmpty()){
            msg+="Selecione um advogado na lista de pesquisa\n";
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
            boolean retorno;
            advo = new Advogado();
            advo.setId(Integer.parseInt(txtID.getText()));
            advodao= new AdvogadoDao();
            advo = advodao.consultar(advo);
            advo.setOab(Integer.parseInt(CodOAB.getText()));
            advo.setNome(nomeAdvogado.getText());
            try{
                retorno=advodao.alterar(advo);
                if(retorno)
                    Utilidade.mensagemInformacao("AlTERAÇÃO DE ADVOGADO REALIZADA COM SUCESSO");
                else
                    Utilidade.mensagemErro("AlTERAÇÃO DE ADVOGADO NÃO REALIZADA");
            }catch(SQLException e){
                Utilidade.mensagemErro("ERRO DE AlTERAÇÃO DE ADVOGADO");
            }
        }
        limpaDados();

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
            usu = new Usuario();
            usu.setLogin(txtLogin.getText());
            usu.setSenha(txtSenha.getText());
            usudao= new UsuarioDao();
            try{
                retorno = usudao.incluir(usu);
                if(retorno)
                    Utilidade.mensagemInformacao("INCLUSÃO DE LOGIN REALIZADA COM SUCESSO");
                else
                    Utilidade.mensagemErro("INCLUSÃO DE LOGIN NÃO REALIZADA");
            }catch(SQLException e){
                Utilidade.mensagemErro("ERRO DE INCLUSÃO DE LOGIN");
            }
            limpaDados();
        }
    }

    @FXML
    void btnPesquisaOnAction(ActionEvent event) {
        String aux="";
        aux=txtFieldPesquisa.getText();
        advodao= new AdvogadoDao();
        advo = new Advogado();
        if(!comboBoxPesquisa.getValue().equals("Por código")){
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
                    advo = advodao.consultar(cod);
                    advogados = new ArrayList<>();
                    advogados.add(advo);
                }catch(SQLException e){
                    Utilidade.mensagemErro("Erro na consulta");
                }
            }
            try{
                carregarTableViewAdvogadosComColecao(advogados);
            }catch(SQLException e){
                Utilidade.mensagemErro("Erro ao alimentar a tabela com dados");
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
    }
    public void limpaDados(){
        this.txtID.setText("");
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
        this.txtID.setText(Integer.toString(a.getId()));
        this.CodOAB.setText(Integer.toString(a.getOab()));
        this.nomeAdvogado.setText(a.getNome());
        usu = new Usuario();
        usudao= new UsuarioDao();
        usu.setId(advo.getId_login());
        usu = usudao.consultar(usu);
        this.txtLogin.setText(usu.getLogin());
        tabPane.getSelectionModel().select(abaCadastro);
    }
    //Atualização de table por pesquisa
    public void carregarTableViewAdvogadosComColecao(ArrayList<Advogado> atores) throws SQLException{
        oblAdvogados = FXCollections.observableArrayList(atores);
        tableColumnNomeAdvogado.setCellValueFactory(new PropertyValueFactory<>("nome"));
        this.tableView.setItems(oblAdvogados);
    }

    
}
