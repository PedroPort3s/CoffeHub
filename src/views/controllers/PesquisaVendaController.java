package views.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PesquisaVendaController {

	private static Stage PesquisaVenda;
	
    @FXML
    private JFXButton btnCadVenda;
	
    @FXML
    private JFXButton btnPesquisar;

    @FXML
    private JFXButton btnVoltar;

    @FXML
    private JFXTextField txtCliente;

    @FXML
    private JFXTextField txtFuncionario;

    @FXML
    private JFXTextField txtCodVenda;

    @FXML
    private JFXButton btnBuscarFuncionario;

    @FXML
    private JFXButton btnLimparFuncionario;

    @FXML
    private JFXButton btnBuscarCliente;

    @FXML
    private JFXButton btnLimparCliente;

    @FXML
    private JFXListView<?> lvVendas;

    @FXML
    private JFXTextField txtCodFuncionario;

    @FXML
    private JFXTextField txtCodCliente;

	public Stage getPesquisaVenda() {
		if (PesquisaVenda == null)
		{
			try {
		    	Stage primaryStage = new Stage();
				AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/views/compraVenda/PesquisaVenda.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.initStyle(StageStyle.TRANSPARENT);
				PesquisaVenda = primaryStage;
			}
		  catch(Exception e)
		  	{
			  Alert alert = new Alert(AlertType.ERROR,e.getMessage(),ButtonType.OK);
			  alert.showAndWait();	
		  	}
		}
		return PesquisaVenda;
	}
    
    @FXML
    void btnBuscarCliente_Action(ActionEvent event) {

    }

    @FXML
    void btnBuscarFuncionario_Action(ActionEvent event) {

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
    	PesquisaVenda.close();
    	PesquisaVenda = null;
    	new HomeController().getHome().show();
    }

    @FXML
    void btnCadVenda_Action(ActionEvent event) {
    	PesquisaVenda.hide();
    	PesquisaVenda = null;
    	new CadVendaController().getCadVenda().show();
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
    void lvVendas_MouseClicked(MouseEvent event) {

    }

}
