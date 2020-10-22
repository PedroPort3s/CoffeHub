package views.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import entitys.Venda;
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

public class PesquisaCompraController {
	
	private static Stage PesquisaCompra;

    @FXML
    private JFXButton btnPesquisar;

    @FXML
    private JFXButton btnVoltar;

    @FXML
    private JFXButton btnCadCompra;

    @FXML
    private JFXTextField txtFornecedor;

    @FXML
    private JFXTextField txtFuncionario;

    @FXML
    private JFXTextField txtCodCompra;

    @FXML
    private JFXButton btnBuscarFuncionario;

    @FXML
    private JFXButton btnLimparFuncionario;

    @FXML
    private JFXButton btnBuscarFornecedor;

    @FXML
    private JFXButton btnLimparCliente;

    @FXML
    private JFXListView<Venda> lvVendas;

    @FXML
    private JFXTextField txtCodFuncionario;

    @FXML
    private JFXTextField txtCodFornecedor;

    
	public Stage getPesquisaCompra() {
		if (PesquisaCompra == null)
		{
			try {
		    	Stage primaryStage = new Stage();
				AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/views/compraVenda/PesquisaCompra.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.initStyle(StageStyle.TRANSPARENT);
				PesquisaCompra = primaryStage;
			}
		  catch(Exception e)
		  	{
			  Alert alert = new Alert(AlertType.ERROR,e.getMessage(),ButtonType.OK);
			  alert.showAndWait();	
		  	}
		}
		return PesquisaCompra;
	}
    
    @FXML
    void btnBuscarFornecedor_Action(ActionEvent event) {

    }

    @FXML
    void btnBuscarFuncionario_Action(ActionEvent event) {

    }

    @FXML
    void btnCadCompra_Action(ActionEvent event) {
    	PesquisaCompra.hide();
    	PesquisaCompra = null;
    	new CadCompraController().getCadCompra().show();
    }

    @FXML
    void btnLimparCliente_Action(ActionEvent event) {

    }

    @FXML
    void btnLimparFuncionario_Action(ActionEvent event) {

    }

    @FXML
    void btnPesquisar_Action(ActionEvent event) {

    }

    @FXML
    void btnVoltar_Action(ActionEvent event) {
    	try
    	{			
    		PesquisaCompra.hide();
    		PesquisaCompra = null;
    		new HomeController().getHome().show();
		}
    	catch (Exception e) 
    	{
			  Alert alert = new Alert(AlertType.ERROR,e.getMessage(),ButtonType.OK);
			  alert.showAndWait();
		}
    }

    @FXML
    void lvVendas_MouseClicked(MouseEvent event) {

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
