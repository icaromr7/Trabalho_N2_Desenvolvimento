/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controle;

import dao.AdvogadoDao;
import dao.TipoDeProcessoDao;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Advogado;
import model.Processo;
import model.TipoDeProcesso;

/**
 * FXML Controller class
 *
 * @author icaro
 */
public class DetalhesController implements Initializable {
    
    @FXML
    private Label txtClassificacao;

    @FXML
    private Label txtCodigo;

    @FXML
    private Label txtCpfCliente;

    @FXML
    private Label txtData;

    @FXML
    private TextArea txtDescricao;

    @FXML
    private Label txtNomeAdvogado;

    @FXML
    private Label txtNomeCliente;

    @FXML
    private Label txtOab;

    @FXML
    private Label txtSituacao;

    @FXML
    private Label txtTipo;

    @FXML
    private Label txtValor;
    
    private Processo p;
    private Advogado adv;
    private AdvogadoDao advdao;
    private TipoDeProcesso tdp;
    private TipoDeProcessoDao tdpdao;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.p = (Processo) rb.getObject("Processo");
        advdao = new AdvogadoDao();
        adv = new Advogado();
        try {
            adv = advdao.consultarID(p.getId_adv());
        } catch (SQLException ex) {
            Logger.getLogger(DetalhesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tdp = new TipoDeProcesso();
        tdpdao = new TipoDeProcessoDao();
        try {
            tdp = tdpdao.consultar(p.getId_tipo());
        } catch (SQLException ex) {
            Logger.getLogger(DetalhesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        txtCodigo.setText(""+p.getId());
        txtNomeCliente.setText(p.getNome_cliente());
        txtCpfCliente.setText(p.getCpf_cliente());
        txtNomeAdvogado.setText(adv.getNome());
        txtOab.setText(""+adv.getOab());
        txtValor.setText("R$ "+ p.getValor()+"0");
        txtTipo.setText(tdp.getTipoDoProcesso());
        txtSituacao.setText(p.getSituacao());
        txtClassificacao.setText(p.getClassificacao());
        txtData.setText(p.getData().toString());
        txtDescricao.setText(p.getDescricao());
    }    
    
}
