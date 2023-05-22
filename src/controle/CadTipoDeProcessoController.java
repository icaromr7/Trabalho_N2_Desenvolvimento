package controle;

import dao.TipoDeProcessoDao;
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
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.TipoDeProcesso;
import utilidade.Utilidade;

public class CadTipoDeProcessoController implements Initializable{

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
    private TextField codCat;

    @FXML
    private ComboBox<String> comboBoxTipo;

    @FXML
    private TextField nomeTipoProcesso;

    @FXML
    private TabPane tabPane;

    @FXML
    private TableColumn<TipoDeProcesso, String> tableColumnNomeCat;

    @FXML
    private TableView<TipoDeProcesso> tableView;

    @FXML
    private TextField txtFieldPesquisa;
    
    private TipoDeProcesso tp;
    private TipoDeProcessoDao tpdao;
    private ArrayList<TipoDeProcesso> tipos = new ArrayList<>();
    private ObservableList<TipoDeProcesso> oblTipos;

    @FXML
    void btnAlterarOnAction(ActionEvent event) {
        tp = null;
        boolean retorno;
        String msg="";
        int conta=0;
        if(nomeTipoProcesso.getText().length()==0 || nomeTipoProcesso.getText().isEmpty()){
            msg="Informe um nome válido para o Tipo de processo\n";
            nomeTipoProcesso.setFocusTraversable(true);
            conta++;
        }
        if(codCat.getText().length()==0 || codCat.getText().isEmpty() || codCat.getText().equals("")){
            msg+="Você deve selecionar um tipo de processo na aba de pesquisa";
            conta++;
        }
        if(conta>0){
            Utilidade.mensagemInformacao(msg);
        }else{
            tp = new TipoDeProcesso();
            tp.setTipoDoProcesso(nomeTipoProcesso.getText());
            tp.setId(Integer.parseInt(codCat.getText()));
            tpdao = new TipoDeProcessoDao();
            try{
                retorno = tpdao.alterar(tp);
                if(retorno)
                    Utilidade.mensagemInformacao("ALTERAÇÃO DE TIPO DE PROCESSO REALIZADA COM SUCESSO");
                else
                    Utilidade.mensagemErro("ALTERAÇÃO DE TIPO DE PROCESSO NÃO REALIZADA");
            }catch(SQLException e){
                Utilidade.mensagemErro("ERRO DE ALTERAÇÃO DE TIPO DE PROCESSO");
            }
        }
        limpaDadosTela();
    }

    @FXML
    void btnExcluirOnAction(ActionEvent event) {
        tp=null;
        boolean retorno;
        if(nomeTipoProcesso.getText().length()==0||nomeTipoProcesso.getText().isEmpty()){
            Utilidade.mensagemErro("Selecione um tipo de processo");
            nomeTipoProcesso.setFocusTraversable(true);
        }else{
            tp= new TipoDeProcesso();
            tp.setId(Integer.parseInt(codCat.getText()));
            tpdao= new TipoDeProcessoDao();
            try{
                retorno=tpdao.excluir(tp);
                if(retorno)
                    Utilidade.mensagemInformacao("EXCLUSÃO DE TIPO DE PROCESSO REALIZADA COM SUCESSO");
                else
                    Utilidade.mensagemErro("EXCLUSÃO DE TIPO DE PROCESSO NÃO REALIZADA");
            }catch(SQLException e){
                Utilidade.mensagemErro("ERRO DE EXCLUSÃO DE TIPO DE PROCESSO");
            }
            limpaDadosTela();
        }

    }

    @FXML
    void btnIncluirOnAction(ActionEvent event) {
        tp=null;
        boolean retorno;
        if(nomeTipoProcesso.getText().length()==0||nomeTipoProcesso.getText().isEmpty()){
            Utilidade.mensagemErro("Informe um nome válido para o tipo de processo");
            nomeTipoProcesso.setFocusTraversable(true);
        }
        else{
            tp= new TipoDeProcesso();
            tp.setTipoDoProcesso(nomeTipoProcesso.getText());
            tpdao = new TipoDeProcessoDao();
            try{
                retorno = tpdao.incluir(tp);
                if(retorno)
                    Utilidade.mensagemInformacao("INCLUSÃO DE TIPO DE PROCESSO REALIZADA COM SUCESSO");
                else
                    Utilidade.mensagemErro("INCLUSÃO DE TIPO DE PROCESSO NÃO REALIZADA");
            }catch(SQLException e){
                Utilidade.mensagemErro("ERRO DE INCLUSÃO DE TIPO DE PROCESSO");
            }
            limpaDadosTela();
            
        }

    }

    @FXML
    void btnPesquisaOnAction(ActionEvent event) {
        String aux="";
        aux=txtFieldPesquisa.getText();
        tpdao= new TipoDeProcessoDao();
        tp = new TipoDeProcesso();
        if(!comboBoxTipo.getValue().equals("Por código")){
            try{
                tipos = tpdao.consultaEspecifica(aux);
            }catch(SQLException e){
                Utilidade.mensagemErro("Erro na consulta");
            }
            try{
                carregarTableViewAtoresComColecao(tipos);
            }catch(SQLException e){
                Utilidade.mensagemErro("Erro ao alimentar a tabela com dados");
            }
        }
        else{
            if(Utilidade.validaConteudoInteiro(aux)==true){
                try{
                    int cod= Integer.parseInt(aux);
                    tp = tpdao.consultar(cod);
                    tipos = new ArrayList<>();
                    tipos.add(tp);
                }catch(SQLException e){
                    Utilidade.mensagemErro("Erro na consulta");
                }
            }
            try{
                carregarTableViewAtoresComColecao(tipos);
            }catch(SQLException e){
                Utilidade.mensagemErro("Erro ao alimentar a tabela com dados");
            }
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //foco cadastro
        nomeTipoProcesso.focusedProperty().addListener(new ChangeListener<Boolean>(){
            public void changed(ObservableValue<? extends Boolean>arg0,Boolean arg1, Boolean arg2){
                if(!arg2){
                }else{
                }
            }
        });
        //foco pesquisa
        txtFieldPesquisa.focusedProperty().addListener(new ChangeListener<Boolean>(){
            public void changed(ObservableValue<? extends Boolean>arg0,Boolean arg1, Boolean arg2){
                if(!arg2){
                }else{
                }
            }
        });
        //foco tab
        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>(){
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue){
                if(observable.getValue().getText().equals("Pesquisa"))try {
                    carregarTableViewAtores();
                    ObservableList<String> tipo = FXCollections.observableArrayList("Por código","Por nome");
                    comboBoxTipo.setItems(tipo);
                } catch (SQLException ex) {
                    Logger.getLogger(CadTipoDeProcessoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        //Seleção na tabela
        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener(){
            public void changed(ObservableValue arg0, Object arg1, Object arg2){
                try {
                    selecionarItemTableViewAtor((TipoDeProcesso)arg2);
                } catch (SQLException ex) {
                    Logger.getLogger(CadTipoDeProcessoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    //Limpa Dados da tela
    public void limpaDadosTela(){
        this.codCat.setText("");
        this.nomeTipoProcesso.setText("");
    }
    //Carregar a Tabela com o nome dos atores
    public void carregarTableViewAtores() throws SQLException{
        tableColumnNomeCat.setCellValueFactory(new PropertyValueFactory<>("nomeAtor"));
        tpdao= new TipoDeProcessoDao();
        tipos= tpdao.consultar();
        oblTipos = FXCollections.observableArrayList(tipos);
        this.tableView.setItems(oblTipos);
    }
    //Selecionar ator
    public void selecionarItemTableViewAtor(TipoDeProcesso tp)throws SQLException{
        this.codCat.setText(Integer.toString(tp.getId()));
        this.nomeTipoProcesso.setText(tp.getTipoDoProcesso());
        tabPane.getSelectionModel().select(abaCadastro);
    }
    //Atualização de table por pesquisa
    public void carregarTableViewAtoresComColecao(ArrayList<TipoDeProcesso> atores) throws SQLException{
        oblTipos = FXCollections.observableArrayList(atores);
        tableColumnNomeCat.setCellValueFactory(new PropertyValueFactory<>("nomeAtor"));
        this.tableView.setItems(oblTipos);
    }

}
