package controle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class PesquisaProcessoController {

    @FXML
    private TableColumn<?, ?> ColunaAdvogado;

    @FXML
    private TableColumn<?, ?> ColunaCliente;

    @FXML
    private TableColumn<?, ?> ColunaCodigo;

    @FXML
    private TableColumn<?, ?> ColunaData;

    @FXML
    private TableColumn<?, ?> ColunaProcesso;

    @FXML
    private Button btnCodigo;

    @FXML
    private Button btnPesquisaAdvogado;

    @FXML
    private Button btnPesquisaCpf;

    @FXML
    private Button btnVerDetalhe;

    @FXML
    private ComboBox<?> cbAdvogado;

    @FXML
    private TableView<?> tableView;

    @FXML
    private TextField txtCPF;

    @FXML
    private TextField txtCodigo;

    @FXML
    void btnCodigoOnAction(ActionEvent event) {

    }

    @FXML
    void btnPesquisaAdvogadoOnAction(ActionEvent event) {

    }

    @FXML
    void btnPesquisaCpfOnAction(ActionEvent event) {

    }

    @FXML
    void btnVerDetalheOnAction(ActionEvent event) {

    }

}
