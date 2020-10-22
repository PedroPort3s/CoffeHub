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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CadCompraController {
	
	private static Stage CadCompra; 

    @FXML
    private JFXTextField txtCodCompra;

    @FXML
    private JFXTextField txtDataCompra;

    @FXML
    private JFXListView<Produto> lvProdutos;

    @FXML
    private JFXTextField txtProduto;

    @FXML
    private JFXButton btnAddProduto;

    @FXML
    private JFXButton btnBuscarProduto;

    @FXML
    private JFXButton btnLimparProduto;

    @FXML
    private JFXTextField txtCodProduto;

    @FXML
    private JFXButton btnBuscarFornecedor;

    @FXML
    private JFXTextField txtFornecedor;

    @FXML
    private JFXButton btnLimparFornecedor;

    @FXML
    private JFXTextField txtCodFornecedor;

    @FXML
    private JFXButton btnGravar;

    @FXML
    private JFXButton btnVoltar;

    @FXML
    private JFXButton btnLimpar;

    @FXML
    private JFXButton btnFinalizar;
    
	public Stage getCadCompra() {
		if (CadCompra == null)
		{
			try {
		    	Stage primaryStage = new Stage();
				AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/views/compraVenda/CadCompra.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.initStyle(StageStyle.TRANSPARENT);
				CadCompra = primaryStage;
			}
		  catch(Exception e)
		  	{
			  Alert alert = new Alert(AlertType.ERROR,e.getMessage(),ButtonType.OK);
			  alert.showAndWait();	
		  	}
		}
		return CadCompra;
	}
    

    @FXML
    void btnAddProduto_Action(ActionEvent event) {

    }

    @FXML
    void btnBuscarFornecedor_Action(ActionEvent event) {

    }

    @FXML
    void btnBuscarProduto_Action(ActionEvent event) {

    }

    @FXML
    void btnFinalizar_Action(ActionEvent event) {

    }

    @FXML
    void btnGravar_Action(ActionEvent event) {

    }

    @FXML
    void btnLimparFornecedor_Action(ActionEvent event) {

    }

    @FXML
    void btnLimparProduto_Action(ActionEvent event) {

    }

    @FXML
    void btnLimpar_Action(ActionEvent event) {

    }

    @FXML
    void btnVoltar_Action(ActionEvent event) {
    	CadCompra.close();
    	CadCompra = null;
    	new PesquisaCompraController().getPesquisaCompra().show(); 
    }

    @FXML
    void lvProdutos_MouseClicked(MouseEvent event) {

    }

    @FXML
    void txtCodVenda_MouseClicked(MouseEvent event) {

    }

}
