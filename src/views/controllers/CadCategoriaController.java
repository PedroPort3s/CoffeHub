package views.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import control.produto.ControlCategoria;
import entitys.Categoria;
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

public class CadCategoriaController {
	
	private static Stage CadCategoria;

    @FXML
    private JFXTextField txtCodCategoria;

    @FXML
    private JFXTextField txtDescricao;

    @FXML
    private JFXButton btnGravar;

    @FXML
    private JFXButton btnVoltar;

    @FXML
    private JFXButton btnLimpar;
    
	public Stage getCadCategoria() {
		if (CadCategoria == null)
		{
			try {
		    	Stage primaryStage = new Stage();
				AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/views/Produto/CadCategoria.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.initStyle(StageStyle.TRANSPARENT);
				CadCategoria = primaryStage;
			}
		  catch(Exception e)
		  	{
			  Alert alert = new Alert(AlertType.ERROR,e.getMessage(),ButtonType.OK);
			  alert.showAndWait();	
		  	}
		}
		return CadCategoria;
	}

    @FXML
    void btnGravar_Action(ActionEvent event) {
    	try
    	{   		
    		Categoria categoria = new Categoria(txtDescricao.getText());
			new ControlCategoria().Inserir(categoria);
		}
    	catch (Exception e)
    	{
    		Alert alert = new Alert(AlertType.WARNING);

            alert.setTitle("Atenção");
            alert.setHeaderText(e.getMessage());
            
            alert.showAndWait();
		}
    	catch(Error er)
    	{
    		Alert alert = new Alert(AlertType.WARNING);

            alert.setTitle("Atenção");
            alert.setHeaderText(er.getMessage());
            
            alert.showAndWait();
    	}
    }

    @FXML
    void btnLimpar_Action(ActionEvent event) {
    	txtDescricao.setText("");
    }

    @FXML
    void btnVoltar_Action(ActionEvent event) {
    	CadCategoria.close();
    	new PesquisaCategoriaController().getPesquisaCategoria().show();
    }

}
