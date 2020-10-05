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

public class PesquisaProdutoController {

	private static Stage PesquisaProduto;
	
    @FXML
    private JFXTextField txtCodProd;

    @FXML
    private JFXButton btnPesquisar;

    @FXML
    private JFXButton btnVoltar;

    @FXML
    private JFXButton btnCadProduto;
    
    @FXML
    private JFXTextField txtDescricao;

	public Stage getCadProduto() {
		if (PesquisaProduto == null)
		{
			try {
		    	Stage primaryStage = new Stage();
				AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/views/Produto/PesquisaProduto.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.initStyle(StageStyle.TRANSPARENT);
				PesquisaProduto = primaryStage;
			}
		  catch(Exception e)
		  	{
			  Alert alert = new Alert(AlertType.ERROR,e.getMessage(),ButtonType.OK);
			  alert.showAndWait();	
		  	}
		}
		return PesquisaProduto;
	}
    
    @FXML
    void btnCadProduto_Action(ActionEvent event) {
    	new CadProdutoController().getCadProduto().show();
    }
    
    @FXML
    void btnPesquisar_Action(ActionEvent event) {

    }

    @FXML
    void btnVoltar_Action(ActionEvent event) {
    	PesquisaProduto.close();
    }

}
