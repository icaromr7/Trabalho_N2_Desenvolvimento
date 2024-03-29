/*
Classe para realizar o crud de processo.
Suas funcionalidades são de incluir, alterar, excluir e pesquisar um processo no banco de dados.
*/
package controle;

import dao.AdvogadoDao;
import dao.ProcessoDao;
import dao.TipoDeProcessoDao;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Processo;
import model.TipoDeProcesso;
import java.util.ArrayList;
import java.util.Date;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Advogado;
import model.Usuario;
import utilidade.Utilidade;

public class CadProcessoController implements Initializable{

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
    private ComboBox<String> cbClassificacao;

    @FXML
    private ComboBox<String> cbSituacao;

    @FXML
    private ComboBox<TipoDeProcesso> cbTpProcesso;

    @FXML
    private TextField codProcesso;

    @FXML
    private ComboBox<String> comboBoxTipo;

    @FXML
    private DatePicker dtDataProcesso;

    @FXML
    private TabPane tabPane;

    @FXML
    private TableColumn<Processo, Integer> tableColumnCpf_Cliente;

    @FXML
    private TableColumn<Processo, Integer> tableColumnId;
    
    @FXML
    private TableColumn<Processo, Date> tableColumnData;

    @FXML
    private TableView<Processo> tableView;

    @FXML
    private TextField txtCpfCliente;

    @FXML
    private TextArea txtDescricao;

    @FXML
    private TextField txtFieldPesquisa;

    @FXML
    private TextField txtNomeCliente;

    @FXML
    private TextField txtOabAdvogado;

    @FXML
    private TextField txtValorProcesso;
    
    private static final Pattern ONLY_LETTERS_PATTERN = Pattern.compile("[\\p{L}\\p{M}\\s]+");

    @FXML
    void btnAlterarOnAction(ActionEvent event) throws SQLException {
        int conta =0;
        String msg ="";
        if(codProcesso.getText().length()==0||codProcesso.getText().isEmpty()){
            msg+="Selecione um advogado na lista de pesquisa\n";
        }
        if(txtNomeCliente.getText().length()==0||txtNomeCliente.getText().isEmpty()){
            msg+="Informe o nome do Advogado.\n";
            conta++;
            txtNomeCliente.setFocusTraversable(true);
        }
        if(txtCpfCliente.getText().length()==0||txtCpfCliente.getText().isEmpty()){
            msg+="Informe o login.\n";
            conta++;
            txtCpfCliente.setFocusTraversable(true);
        }
        if(txtValorProcesso.getText().length()==0||txtValorProcesso.getText().isEmpty()){
            msg+="Informe a senha.\n";
            conta++;
            txtValorProcesso.setFocusTraversable(true);
        }
        if(cbTpProcesso.getValue()==null){
            msg+="Escolha o tipo de processo.\n";
            conta++;
        }
        if(dtDataProcesso.getValue()==null){
            msg+="Informe uma data para o processo\n";
            conta++;
        }
        if(cbSituacao.getValue()==null){
            msg+="Escolha a situação.\n";
            conta++;
        }
        if(cbClassificacao.getValue()==null){
            msg+="Escolha a classificação.\n";
            conta++;
        }
        if(txtDescricao.getText().length()==0||txtDescricao.getText().isEmpty()){
            msg+="Informe a descrição.\n";
            conta++;
            txtDescricao.setFocusTraversable(true);
        }
        if(conta>0){
            Utilidade.mensagemInformacao(msg);
        }
        else{
            boolean retorno;
            p = new Processo();
            pdao = new ProcessoDao();
            p = pdao.pesquisarPorId(Integer.parseInt(codProcesso.getText()),adv.getId());
            p.setNome_cliente(txtNomeCliente.getText());
            p.setCpf_cliente(txtCpfCliente.getText());
            p.setId_adv(adv.getId());
            p.setValor(Double.valueOf(txtValorProcesso.getText()));
            p.setId_tipo(cbTpProcesso.getValue().getId());
            p.setData(java.sql.Date.valueOf(dtDataProcesso.getValue()));
            p.setSituacao(cbSituacao.getValue());
            p.setClassificacao(cbClassificacao.getValue());
            p.setDescricao(txtDescricao.getText());
            try{
                retorno=pdao.alterar(p);
                if(retorno)
                    Utilidade.mensagemInformacao("AlTERAÇÃO DE PROCESSO REALIZADA COM SUCESSO");
                else
                    Utilidade.mensagemErro("AlTERAÇÃO DE PROCESSO NÃO REALIZADA");
            }catch(SQLException e){
                Utilidade.mensagemErro("ERRO DE AlTERAÇÃO DE PROCESSO");
            }
        }
        limpaDados();

    }

    @FXML
    void btnExcluirOnAction(ActionEvent event) {
        p = null;
        boolean retorno;
        if(codProcesso.getText().length()==0||codProcesso.getText().isEmpty()){
            Utilidade.mensagemErro("Selecione um processo na tabela da aba pesquisa");
            codProcesso.setFocusTraversable(true);
        }else{
            p = new Processo();
            p.setId(Integer.parseInt(codProcesso.getText()));
            pdao = new ProcessoDao();
            try{
                retorno=pdao.excluir(p);
                if(retorno)
                    Utilidade.mensagemInformacao("EXCLUSÃO DE PROCESSO REALIZADA COM SUCESSO");
                else
                    Utilidade.mensagemErro("EXCLUSÃO DE PROCESSO NÃO REALIZADA");
            }catch(SQLException e){
                Utilidade.mensagemErro("ERRO DE EXCLUSÃO DE PROCESSO");
            }
            limpaDados();
        }
    }
    
    private Processo p;
    private ProcessoDao pdao;
    private Usuario usu;
    private Advogado adv;
    private AdvogadoDao advdao;
    private TipoDeProcesso tp;
    private TipoDeProcessoDao tpdao;
    private ArrayList<Processo> processos = new ArrayList<>();
    private ObservableList<Processo> oblProcessos;

    @FXML
    void btnIncluirOnAction(ActionEvent event) {
        p = null;
        boolean retorno;
        int conta =0;
        String msg="";
        if(txtNomeCliente.getText().length()==0||txtNomeCliente.getText().isEmpty()){
            msg+="Informe o nome do Advogado.\n";
            conta++;
            txtNomeCliente.setFocusTraversable(true);
        }
        if(txtCpfCliente.getText().length()==0||txtCpfCliente.getText().isEmpty()){
            msg+="Informe o login.\n";
            conta++;
            txtCpfCliente.setFocusTraversable(true);
        }
        if(txtValorProcesso.getText().length()==0||txtValorProcesso.getText().isEmpty()){
            msg+="Informe a senha.\n";
            conta++;
            txtValorProcesso.setFocusTraversable(true);
        }
        if(cbTpProcesso.getValue()==null){
            msg+="Escolha o tipo de processo.\n";
            conta++;
        }
        if(dtDataProcesso.getValue()==null){
            msg+="Informe uma data para o processo\n";
            conta++;
        }
        if(cbSituacao.getValue()==null){
            msg+="Escolha a situação.\n";
            conta++;
        }
        if(cbClassificacao.getValue()==null){
            msg+="Escolha a classificação.\n";
            conta++;
        }
        if(txtDescricao.getText().length()==0||txtDescricao.getText().isEmpty()){
            msg+="Informe a descrição.\n";
            conta++;
            txtDescricao.setFocusTraversable(true);
        }
        if(conta>0){
            Utilidade.mensagemInformacao(msg);
        }
        else{
            p= new Processo();
            p.setNome_cliente(txtNomeCliente.getText());
            p.setCpf_cliente(txtCpfCliente.getText());
            p.setId_adv(adv.getId());
            p.setValor(Double.valueOf(txtValorProcesso.getText()));
            p.setId_tipo(cbTpProcesso.getValue().getId());
            LocalDate localDate = dtDataProcesso.getValue();
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            p.setData(date);
            p.setSituacao(cbSituacao.getValue());
            p.setClassificacao(cbClassificacao.getValue());
            p.setDescricao(txtDescricao.getText());
            pdao = new ProcessoDao();
            try{
                retorno = pdao.incluir(p);
                if(retorno)
                    Utilidade.mensagemInformacao("INCLUSÃO DE PROCESSO REALIZADA COM SUCESSO");
                else
                    Utilidade.mensagemErro("INCLUSÃO DE PROCESSO NÃO REALIZADA");
            }catch(SQLException e){
                Utilidade.mensagemErro("ERRO DE INCLUSÃO DE PROCESSO");
            }       
            limpaDados();
        }

    }

    @FXML
    void btnPesquisaOnAction(ActionEvent event) {
        String aux="";
        aux=txtFieldPesquisa.getText();
        pdao= new ProcessoDao();
        p = new Processo();
        if(!comboBoxTipo.getValue().equals("Por código")){
            try{
                processos = pdao.pesquisarPorCpfCliente(aux,adv.getId());
            }catch(SQLException e){
                Utilidade.mensagemErro("Erro na consulta");
            }
            try{
                carregarTableViewProcessosComColecao(processos);
            }catch(SQLException e){
                Utilidade.mensagemErro("Erro ao alimentar a tabela com dados");
            }
        }
        else{
            if(Utilidade.validaConteudoInteiro(aux)==true){
                try{
                    int cod= Integer.parseInt(aux);
                    p = pdao.pesquisarPorId(cod,adv.getId());
                    processos = new ArrayList<>();
                    processos.add(p);
                }catch(SQLException e){
                    Utilidade.mensagemErro("Erro na consulta");
                }
            }
            try{
                carregarTableViewProcessosComColecao(processos);
            }catch(SQLException e){
                Utilidade.mensagemErro("Erro ao alimentar a tabela com dados");
            }
        }

    }
 
    @Override
    public void initialize(URL location, ResourceBundle rb) {
        this.adv = (Advogado) rb.getObject("Advogado");
        this.txtOabAdvogado.setText(Integer.toString(adv.getOab()));
        ObservableList<String> oblsituacao = FXCollections.observableArrayList("Em andamento","Finalizado");
        cbSituacao.setItems(oblsituacao);
        ObservableList<String> oblclassificacao = FXCollections.observableArrayList("Público","Sigiloso");
        cbClassificacao.setItems(oblclassificacao);
        ArrayList <TipoDeProcesso> tdp = new ArrayList();
        tpdao = new TipoDeProcessoDao();
        try {
            tdp = tpdao.consultar();
        } catch (SQLException ex) {
            Logger.getLogger(CadProcessoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ObservableList<TipoDeProcesso> obltdp = FXCollections.observableArrayList(tdp);
        cbTpProcesso.setItems(obltdp);
        //foco tab
        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>(){
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue){
                if(observable.getValue().getText().equals("Pesquisa"))try {
                    carregarTableViewProcessos();
                    ObservableList<String> tipo = FXCollections.observableArrayList("Por código","Por CPF do cliente");
                    comboBoxTipo.setItems(tipo);
                } catch (SQLException ex) {
                    Logger.getLogger(CadAdvogadoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        //Seleção na tabela
        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener(){
            public void changed(ObservableValue arg0, Object arg1, Object arg2){
                try {
                    selecionarItemTableViewProcesso((Processo)arg2);
                } catch (SQLException ex) {
                    Logger.getLogger(CadAdvogadoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        //Limitar quantidade de caractesres do nome cliente
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (ONLY_LETTERS_PATTERN.matcher(newText).matches() && newText.length() <= 40) {
                return change;
            }
            return null;
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        txtNomeCliente.setTextFormatter(textFormatter);
        //Limitar quantidade de caractesres do cpf_cliente e só aceitar números
        TextFormatter<String> textFormatter2 = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*") && newText.length() <= 11) {
                return change;
            } else {
                return null;
            }
        });
        txtCpfCliente.setTextFormatter(textFormatter2);
        //Limitar quantidade de caracteres do valor e formatação pra double
        TextFormatter<String> textFormatter3 = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("-?\\d*(\\.\\d*)?") && newText.length() <= 10) {
                return change;
            } else {
                return null;
            }
        });
        txtValorProcesso.setTextFormatter(textFormatter3);
        //Limitar quantidade de caractesres da descrição do projeto
        TextFormatter<String> textFormatter4 = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() <= 1000) {
                return change;
            } else {
                return null;
            }
        });
        txtDescricao.setTextFormatter(textFormatter4);
    }
    
    public void limpaDados(){
        codProcesso.setText("");
        txtNomeCliente.setText("");
        txtCpfCliente.setText("");
        txtValorProcesso.setText("");
        txtDescricao.setText("");
        dtDataProcesso.setValue(null);
    }
    //Carregar a Tabela com o nome dos advogados
    public void carregarTableViewProcessos() throws SQLException{
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnCpf_Cliente.setCellValueFactory(new PropertyValueFactory<>("cpf_cliente"));
        tableColumnData.setCellValueFactory(new PropertyValueFactory<>("data"));
        pdao = new ProcessoDao();
        processos= pdao.pesquisarPorIdAdvogado(adv.getId());
        oblProcessos = FXCollections.observableArrayList(processos);
        this.tableView.setItems(oblProcessos);
    }
    //Selecionar advogado
    public void selecionarItemTableViewProcesso(Processo p) throws SQLException {
    if (p != null) {
        pdao = new ProcessoDao();
        this.codProcesso.setText(Integer.toString(p.getId()));
        this.txtNomeCliente.setText(p.getNome_cliente());
        this.txtCpfCliente.setText(p.getCpf_cliente());
        adv.setId(p.getId_adv());
        advdao = new AdvogadoDao();
        adv = advdao.consultar(adv);
        this.txtOabAdvogado.setText(Integer.toString(adv.getOab()));
        this.txtValorProcesso.setText(String.valueOf(p.getValor()) + "0");
        this.cbSituacao.setValue(p.getSituacao());
        this.cbClassificacao.setValue(p.getClassificacao());
        tpdao = new TipoDeProcessoDao();
        tp = tpdao.consultar(p.getId_tipo());
        this.cbTpProcesso.setValue(tp);
        String data = p.getData().toString();
        data = data.substring(8, 10) + "/" + data.substring(5, 7) + "/" + data.substring(0, 4);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate ld = LocalDate.parse(data, formatter);
        this.dtDataProcesso.setValue(ld);
        this.txtDescricao.setText(p.getDescricao());
        tabPane.getSelectionModel().select(abaCadastro);
    }
}
    //Atualização de table por pesquisa
    public void carregarTableViewProcessosComColecao(ArrayList<Processo> processos) throws SQLException{
        oblProcessos = FXCollections.observableArrayList(processos);
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnCpf_Cliente.setCellValueFactory(new PropertyValueFactory<>("cpf_cliente"));
        tableColumnData.setCellValueFactory(new PropertyValueFactory<>("data"));
        this.tableView.setItems(oblProcessos);
    }

}
