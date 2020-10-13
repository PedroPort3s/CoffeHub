package views.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import entitys.Produto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CadVendaController {

	private static Stage CadVenda;
	
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
    private JFXListView<Produto> lvProdutos;

    @FXML
    private JFXTextField txtProduto;

    @FXML
    private Label lblValorTotal;

    @FXML
    private JFXButton btnAddProduto;

    @FXML
    private JFXButton btnFinalizar;
    
	public Stage getCadVenda() {
		if (CadVenda == null)
		{
			try {
		    	Stage primaryStage = new Stage();
				AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/views/compraVenda/CadVenda.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.initStyle(StageStyle.TRANSPARENT);
				CadVenda = primaryStage;
			}
		  catch(Exception e)
		  	{
			  Alert alert = new Alert(AlertType.ERROR,e.getMessage(),ButtonType.OK);
			  alert.showAndWait();	
		  	}
		}
		return CadVenda;
	}
	

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
    	CadVenda.close();
    	CadVenda = null;
    	new PesquisaVendaController().getPesquisaVenda().show();
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
    
    @FXML
    void txtProduto_MouseClicked(MouseEvent event) {
    	CadVenda.hide();
    	new PesquisaProdutoGeralController().getPesquisaProdutoGeral().show();
    }


}
