package views.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import entitys.Produto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class CadVendaController {

    @FXML
    private JFXButton btnGravar;

    @FXML
    private JFXButton btnVoltar;

    @FXML
    private JFXButton btnLimpar;

    @FXML
    private JFXTextField txtCliente;

    @FXML
    private JFXTextField txtFuncionario;

    @FXML
    private JFXTextField txtDataOrigem;

    @FXML
    private JFXTextField txtDataConfirmacao;

    @FXML
    private JFXTextField txtCodVenda;

    @FXML
    private JFXComboBox<?> cbStatus;

    @FXML
    private JFXListView<Produto> lvProdutos;

    @FXML
    private JFXTextField txtProduto;

    @FXML
    private Label lblValorTotal;

    @FXML
    private JFXButton btnAddProduto;

    @FXML
    private JFXButton btnFinalizar;

    @FXML
    void btnAddProduto_Action(ActionEvent event) {

    }

    @FXML
    void btnFinalizar_Action(ActionEvent event) {

    }

    @FXML
    void btnGravar_Action(ActionEvent event) {

    }

    @FXML
    void btnLimpar_Action(ActionEvent event) {

    }

    @FXML
    void btnVoltar_Action(ActionEvent event) {

    }

    @FXML
    void lvProdutos_MouseClicked(MouseEvent event) {

    }

    @FXML
    void txtCliente_MouseClicked(MouseEvent event) {

    }

    @FXML
    void txtCodVenda_MouseClicked(MouseEvent event) {

    }

    @FXML
    void txtFuncionario_MouseClicked(MouseEvent event) {

    }

}
