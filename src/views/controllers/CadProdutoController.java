package views.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CadProdutoController {

	private static Stage CadProduto;
	
    @FXML
    private JFXButton btnGravar;

    @FXML
    private JFXButton btnVoltar1;

    @FXML
    private JFXTextField txtCodProd;

    @FXML
    private JFXTextField txtDescricao;

    @FXML
    private JFXButton btnLimpar;

    @FXML
    private JFXTextField txtValor;

    @FXML
    private JFXTextField txtQtd;

    @FXML
    private JFXTextField txtUnMedida;
    

	public Stage getCadProduto() {
		if (CadProduto == null)
		{
			try {
		    	Stage primaryStage = new Stage();
				AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/views/Produto/CadProduto.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.initStyle(StageStyle.TRANSPARENT);
				CadProduto = primaryStage;
			}
		  catch(Exception e)
		  	{
			  Alert alert = new Alert(AlertType.ERROR,e.getMessage(),ButtonType.OK);
			  alert.showAndWait();	
		  	}
		}
		return CadProduto;
	}
    
    @FXML
    void btnGravar_Action(ActionEvent event) {

    }

    @FXML
    void btnLimpar_Action(ActionEvent event) {

    }

    @FXML
    void btnVoltar_Action(ActionEvent event) {
    	CadProduto.close();
    	new PesquisaProdutoController().getCadProduto().show();
    }

}
