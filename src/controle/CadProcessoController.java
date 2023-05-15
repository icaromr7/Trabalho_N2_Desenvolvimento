package controle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CadProcessoController {

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
    private ComboBox<?> cbClassificacao;

    @FXML
    private ComboBox<?> cbSituacao;

    @FXML
    private ComboBox<?> cbTpProcesso;

    @FXML
    private TextField codProcesso;

    @FXML
    private ComboBox<?> comboBoxTipo;

    @FXML
    private DatePicker dtDataProcesso;

    @FXML
    private TabPane tabPane;

    @FXML
    private TableColumn<?, ?> tableColumnNomeCat;

    @FXML
    private TableView<?> tableView;

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
    void btnAlterarOnAction(ActionEvent event) {

    }

    @FXML
    void btnExcluirOnAction(ActionEvent event) {

    }

    @FXML
    void btnIncluirOnAction(ActionEvent event) {

    }

    @FXML
    void btnPesquisaOnAction(ActionEvent event) {

    }

}
