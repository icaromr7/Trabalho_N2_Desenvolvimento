package controle;

import dao.AdvogadoDao;
import dao.ProcessoDao;
import dao.TipoDeProcessoDao;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Advogado;
import model.Processo;
import model.TipoDeProcesso;
import model.Usuario;
import utilidade.Utilidade;

public class PesquisaProcessoController implements Initializable{

    @FXML
    private TableColumn<Processo, String> ColunaCliente;

    @FXML
    private TableColumn<Processo, Integer> ColunaCodigo;
    
    @FXML
    private TableColumn<Processo, Date> ColunaData;

    @FXML
    private TableColumn<Processo, String> ColunaCpfCliente;

    @FXML
    private Button btnVerDetalhe;
    
    @FXML
    private Button btnBuscar;

    @FXML
    private TableView<Processo> tableView;

    @FXML
    private TextField txtCodigo;
    
    @FXML
    private ComboBox<String> cbBusca;
    
    private Processo p;
    private ProcessoDao pdao;
    private Usuario usu;
    private Advogado adv;
    private AdvogadoDao advdao;
    private TipoDeProcesso tp;
    private TipoDeProcessoDao tpdao;
    private ArrayList<Processo> processos = new ArrayList<>();
    private ObservableList<Processo> oblProcessos;
    private ResourceBundle rbJanela;

    @FXML
    void btnVerDetalheOnAction(ActionEvent event) {
        Processo selectedProcesso = tableView.getSelectionModel().getSelectedItem();

    if (selectedProcesso != null) {
        rbJanela = new ResourceBundle() {
            @Override
            protected Object handleGetObject(String key) {
                if (key.contains("Processo")) {
                    return selectedProcesso;
                } else {
                    return null;
                }
            }

            @Override
            public Enumeration<String> getKeys() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Detalhes.fxml"));
            loader.setResources(rbJanela);
            Parent root = loader.load();
            
            Stage stage2 = new Stage(); 
            stage2.setScene(new Scene(root));
            stage2.setResizable(false);
            stage2.initModality(Modality.APPLICATION_MODAL);
            stage2.show();
        } catch (IOException ex) {
            Logger.getLogger(PesquisaProcessoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    }
    
    @FXML
    void btnBuscarOnAction(ActionEvent event) {
        String aux="";
        aux=txtCodigo.getText();
        pdao= new ProcessoDao();
        p = new Processo();
        if(!cbBusca.getValue().equals("Por código")){
            try{
                processos = pdao.pesquisarPorCpfCliente(aux);
                if("Advogado".equals(usu.getCargo())){
                   ArrayList<Processo> Sigilosos = new ArrayList<>();
                   Sigilosos= pdao.pesquisarPorCpfClienteSigiloso(aux, adv.getId());
                   for(int i=0; i<Sigilosos.size();i++){
                       processos.add(Sigilosos.get(i));
                   } 
                }
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
                    p = pdao.pesquisarPorId(cod);
                    processos = new ArrayList<>();
                    processos.add(p);
                    if(p==null&&usu.getCargo().equals("Advogado")){
                        p=pdao.pesquisarPorIdSigiloso(cod, adv.getId());
                        processos = new ArrayList<>();
                        processos.add(p); 
                    }
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
        this.usu = (Usuario) rb.getObject("Usuario");
        if(usu.getCargo().equals("Advogado")){
            this.adv = (Advogado) rb.getObject("Advogado");
        }
        ObservableList<String> tipo = FXCollections.observableArrayList("Por código","Por CPF do cliente");
        cbBusca.setItems(tipo);
        try {
            carregarTableViewProcesso();
        } catch (SQLException ex) {
            Logger.getLogger(PesquisaProcessoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Limitar dígitos do tipo de processo
         TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() <= 20) {
                return change;
            } else {
                return null;
            }
        });
        txtCodigo.setTextFormatter(textFormatter);
    }
    
    //Carregar a Tabela com os processos
    public void carregarTableViewProcesso() throws SQLException{
        ColunaCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
        ColunaCliente.setCellValueFactory(new PropertyValueFactory<>("nome_cliente"));
        ColunaCpfCliente.setCellValueFactory(new PropertyValueFactory<>("cpf_cliente"));
        ColunaData.setCellValueFactory(new PropertyValueFactory<>("data"));
        pdao = new ProcessoDao();
        processos= pdao.pesquisarPorPublico();
        oblProcessos = FXCollections.observableArrayList(processos);
        this.tableView.setItems(oblProcessos);
    }
    //Atualização de table por pesquisa
    public void carregarTableViewProcessosComColecao(ArrayList<Processo> processos) throws SQLException{
        oblProcessos = FXCollections.observableArrayList(processos);
        ColunaCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
        ColunaCliente.setCellValueFactory(new PropertyValueFactory<>("nome_cliente"));
        ColunaCpfCliente.setCellValueFactory(new PropertyValueFactory<>("cpf_cliente"));
        ColunaData.setCellValueFactory(new PropertyValueFactory<>("data"));
        this.tableView.setItems(oblProcessos);
    }

}
