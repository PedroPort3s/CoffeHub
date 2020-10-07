package views.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CadProdutoController  implements Initializable {

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
    
    @FXML
    private JFXComboBox<Categoria> cbCategoria;

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

    	try 
    	{
    		Categoria categoriaSelecionada = this.cbCategoria.getSelectionModel().getSelectedItem();
    		
			Produto produto = new Produto(txtDescricao.getText(), Double.parseDouble(txtValor.getText()), Integer.parseInt(txtQtd.getText()),
					txtUnMedida.getText(), categoriaSelecionada);
			
			if(new ControlProduto().Inserir(produto) == 1)
			{
				Limpar();
	    		Alert alert = new Alert(AlertType.INFORMATION);

	            alert.setTitle("Sucesso");
	            alert.setHeaderText("Produto inserido com sucesso");
	            
	            alert.showAndWait();
			}
		}
    	catch (Exception e)
    	{
    		Alert alert = new Alert(AlertType.WARNING);

            alert.setTitle("Atenção");
            alert.setHeaderText(e.getMessage());
            
            alert.showAndWait();
    	}
    	catch (Error e) 
    	{
    		Alert alert = new Alert(AlertType.WARNING);

            alert.setTitle("Atenção");
            alert.setHeaderText(e.getMessage());
            
            alert.showAndWait();
		}
    	
    }

    @FXML
    void btnLimpar_Action(ActionEvent event) {
    	Limpar();
    }

    @FXML
    void btnVoltar_Action(ActionEvent event) {
    	CadProduto.close();
    	new PesquisaProdutoController().getPesquisaProduto().show();
    }
    
    void ListarCategoria() 
    {
    	try 
    	{    				
    		List<Categoria> lstCategorias = new ControlCategoria().Listar("");
    		ObservableList <Categoria> categorias = FXCollections.observableArrayList(lstCategorias);
    		
    		cbCategoria.setItems(categorias);    		
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
    
    void Limpar() 
    {
    	txtCodProd.setText("");
    	txtDescricao.setText("");
    	txtQtd.setText("");
    	txtUnMedida.setText("");
    	txtValor.setText("");
    }
	
	  @Override public void initialize(URL arg0, ResourceBundle arg1) {
	  this.ListarCategoria(); 
	  }	 
}
