package views.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import control.produto.ControlCategoria;
import control.produto.ControlProduto;
import entitys.Categoria;
import entitys.Produto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class PesquisaProdutoController implements Initializable{

	private static Stage PesquisaProduto;

    @FXML
    private JFXButton btnPesquisar;

    @FXML
    private JFXButton btnVoltar;

    @FXML
    private JFXButton btnCadProduto;
    
    @FXML
    private JFXTextField txtDescricao;
    
    @FXML
    private JFXComboBox<Categoria> cbCategoria;

    @FXML
    private JFXListView<Produto> lvProdutos;

	public Stage getPesquisaProduto() {
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
    	PesquisaProduto.hide();
    	PesquisaProduto = null;
    	new CadProdutoController().getCadProduto().show();
    }
    
    @FXML
    void btnPesquisar_Action(ActionEvent event) {
    	try 
    	{
    		this.ListarProdutos();
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
    		PesquisaProduto.hide();
    		PesquisaProduto = null;
    		new HomeController().getHome().show();
		}
    	catch (Exception e) 
    	{
			  Alert alert = new Alert(AlertType.ERROR,e.getMessage(),ButtonType.OK);
			  alert.showAndWait();
		}
    }
    
    void ListarProdutos() 
    {
    	List<Produto>lstProdutos;
    	lvProdutos.getItems().clear();
    	
    	try 
    	{
    		lstProdutos = new ControlProduto().ListarEmCategoria(txtDescricao.getText(), cbCategoria.getSelectionModel().getSelectedItem().getCod());
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
    
    
    @FXML
    void lvProdutos_MouseClicked(MouseEvent event)
    {
    	int codProduto = lvProdutos.getSelectionModel().getSelectedItem().getCod();
    	
    	if (codProduto > 0) 
    	{
    		try
    		{
    			Produto produto = new ControlProduto().Carregar(codProduto); 
    			if(produto != null) 
    			{
					/* new CadCategoriaController().CarregarCategoria(categoria); */
					CadProdutoController.ProdutoEstatico= produto; 
    				PesquisaProduto.hide();
    		    	PesquisaProduto = null;
    				new CadProdutoController().getCadProduto().show();
       			}
    			else
    			{
    				throw new Exception("Não foi possível carregar o produto selecionado");
    			} 
			} 
    		catch (Exception e) {
  			  Alert alert = new Alert(AlertType.ERROR,e.getMessage(),ButtonType.OK);
  			  alert.showAndWait();
			}
		}
    }
    
    void ListarCategoria() 
    {
    	try 
    	{    				
    		
    		List<Categoria> lstCategorias = new ArrayList<>();    		
    		lstCategorias.add(new Categoria("TODAS"));
    		
    		
    		List<Categoria> lstCategoriasBanco = new ControlCategoria().Listar(""); 
    		
    		if (lstCategoriasBanco != null && lstCategorias.size() > 0) 
    		{
    			
			lstCategoriasBanco.forEach(c -> {
			 Categoria categoria = new Categoria();
			
			 categoria.setCod(c.getCod());
			 categoria.setNome(c.getNome());
			 
			 lstCategorias.add(categoria);
			});
			
			}    		
    		
    		ObservableList <Categoria> categorias = FXCollections.observableArrayList(lstCategorias);    		

    		cbCategoria.setItems(categorias);    
    		
    		cbCategoria.getSelectionModel().select(0);
		}
    	
    	catch (ClassNotFoundException e)
    	{
    		Alert alert = new Alert(AlertType.WARNING);

            alert.setTitle("Atenção");
            alert.setHeaderText(e.getMessage());
            
            alert.showAndWait();
		}
    	catch (SQLException e) {
    		Alert alert = new Alert(AlertType.WARNING);

            alert.setTitle("Atenção");
            alert.setHeaderText(e.getMessage());
            
            alert.showAndWait();
		}
    	
    }
        
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.ListarCategoria();
		this.ListarProdutos();
	}

}
