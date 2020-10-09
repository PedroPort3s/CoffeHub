package views.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import control.produto.ControlProduto;
import entitys.Produto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PesquisaProdutoGeralController implements Initializable{

	private static Stage PesquisaProdutoGeral;

    @FXML
    private JFXButton btnPesquisar;

    @FXML
    private JFXButton btnVoltar;

    @FXML
    private JFXTextField txtDescricao;

    @FXML
    private JFXListView<Produto> lvProdutos;

	public Stage getPesquisaProdutoGeral() {
		if (PesquisaProdutoGeral == null)
		{
			try {
		    	Stage primaryStage = new Stage();
				AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/views/pesquisa/PesquisaProdutoGeral.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.initStyle(StageStyle.TRANSPARENT);
				PesquisaProdutoGeral = primaryStage;
			}
		  catch(Exception e)
		  	{
			  Alert alert = new Alert(AlertType.ERROR,e.getMessage(),ButtonType.OK);
			  alert.showAndWait();	
		  	}
		}
		return PesquisaProdutoGeral;
	}
    @FXML
    void btnPesquisar_Action(ActionEvent event) {
    	try 
    	{
			this.ListarProdutosPesquisa(txtDescricao.getText());
		}
    	catch (Exception e) 
    	{
			  Alert alert = new Alert(AlertType.ERROR,e.getMessage(),ButtonType.OK);
			  alert.showAndWait();	
		}
    }

    @FXML
    void btnVoltar_Action(ActionEvent event) {
		PesquisaProdutoGeral.hide();
		PesquisaProdutoGeral = null;
		
    }

    @FXML
    void lvProdutos_MouseClicked(MouseEvent event) {

    }

    void ListarProdutos() 
    {
    	List<Produto>lstProdutos;
    	lvProdutos.getItems().clear();
    	
    	try 
    	{
    		lstProdutos = new ControlProduto().Listar();
			if (lstProdutos != null) 
			{
				lstProdutos.forEach(p -> lvProdutos.getItems().add(p));
			}
		}
    	
    	catch (Exception e) 
    	{
			  Alert alert = new Alert(AlertType.ERROR,e.getMessage(),ButtonType.OK);
			  alert.showAndWait();
    	}
    	
    	catch(Error e) 
    	{
			  Alert alert = new Alert(AlertType.ERROR,e.getMessage(),ButtonType.OK);
			  alert.showAndWait();
    	}
    }
    
    void ListarProdutosPesquisa(String pesquisa) 
    {
    	List<Produto>lstProdutos;
    	lvProdutos.getItems().clear();
    	
    	try 
    	{
    		lstProdutos = new ControlProduto().Listar(pesquisa);
			if (lstProdutos != null) 
			{
				lstProdutos.forEach(p -> lvProdutos.getItems().add(p));
			}
		}
    	
    	catch (Exception e) 
    	{
			  Alert alert = new Alert(AlertType.ERROR,e.getMessage(),ButtonType.OK);
			  alert.showAndWait();
    	}
    	
    	catch(Error e) 
    	{
			  Alert alert = new Alert(AlertType.ERROR,e.getMessage(),ButtonType.OK);
			  alert.showAndWait();
    	}
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.ListarProdutos();
	}
}