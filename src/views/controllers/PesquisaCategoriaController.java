package views.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import control.produto.ControlCategoria;
import entitys.Categoria;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PesquisaCategoriaController implements Initializable{
	
	private static Stage PesquisaCategoria;

    @FXML
    private JFXTextField txtDescricao;

    @FXML
    private JFXButton btnPesquisar;

    @FXML
    private JFXButton btnVoltar;

    @FXML
    private JFXButton btnCadCategoria;
    
    @FXML
    private JFXListView<Categoria> lvCategorias;

	public Stage getPesquisaCategoria() {
		if (PesquisaCategoria == null)
		{
			try {
		    	Stage primaryStage = new Stage();
				AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/views/Produto/PesquisaCategoria.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.initStyle(StageStyle.TRANSPARENT);
				PesquisaCategoria = primaryStage;
			}
		  catch(Exception e)
		  	{
			  Alert alert = new Alert(AlertType.ERROR,e.getMessage(),ButtonType.OK);
			  alert.showAndWait();	
		  	}
		}
		return PesquisaCategoria;
	}
    
    @FXML
    void btnCadCategoria_Action(ActionEvent event) {
    	PesquisaCategoria.hide();
    	PesquisaCategoria = null;
    	new CadCategoriaController().getCadCategoria().show();
    }

    @FXML
    void btnPesquisar_Action(ActionEvent event) {
    	try 
    	{
    		this.ListarCategorias();
		}
    	catch (Exception e)
    	{
			  Alert alert = new Alert(AlertType.ERROR,e.getMessage(),ButtonType.OK);
			  alert.showAndWait();
		}
    }

    @FXML
    void btnVoltar_Action(ActionEvent event) {
    	try
    	{			
    		PesquisaCategoria.hide();
    		PesquisaCategoria = null;
    		new HomeController().getHome().show();
		}
    	catch (Exception e) 
    	{
			  Alert alert = new Alert(AlertType.ERROR,e.getMessage(),ButtonType.OK);
			  alert.showAndWait();
		}
    }

    void ListarCategorias() 
    {
    	List<Categoria>	lstCategorias;
    	lvCategorias.getItems().clear();
    	
    	try 
    	{
			lstCategorias = new ControlCategoria().Listar(txtDescricao.getText());
			if (lstCategorias != null) 
			{
				lstCategorias.forEach(f -> lvCategorias.getItems().add(f));
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
    

    @FXML
    void lvCategorias_MouseClicked(MouseEvent event) {
    	int codCategoria = lvCategorias.getSelectionModel().getSelectedItem().getCod();
    	
    	if (codCategoria > 0) 
    	{
    		try
    		{
    			Categoria categoria = new ControlCategoria().Carregar(codCategoria); 
    			if(categoria != null) 
    			{
					/* new CadCategoriaController().CarregarCategoria(categoria); */
    				CadCategoriaController.CategoriaEstatica = categoria;
    				PesquisaCategoria.hide();
    		    	PesquisaCategoria = null;
    				new CadCategoriaController().getCadCategoria().show();
       			}
    			else
    			{
    				throw new Exception("Não foi possível carregar a categoria selecionada");
    			} 
			} 
    		catch (Exception e) {
  			  Alert alert = new Alert(AlertType.ERROR,e.getMessage(),ButtonType.OK);
  			  alert.showAndWait();
			}
		}
    	
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.ListarCategorias();
	}
}
