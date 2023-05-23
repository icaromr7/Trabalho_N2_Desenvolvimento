package controle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class PesquisaProcessoController {

    @FXML
    private TableColumn<?, ?> ColunaCliente;

    @FXML
    private TableColumn<?, ?> ColunaCodigo;

    @FXML
    private TableColumn<?, ?> ColunaData;

    @FXML
    private TableColumn<?, ?> ColunaProcesso;

    @FXML
    private Button btnVerDetalhe;

    @FXML
    private TableView<?> tableView;

    @FXML
    private TextField txtCodigo;

    @FXML
    void btnVerDetalheOnAction(ActionEvent event) {

    }

}
