package views.controllers;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CadCategoriaController implements Initializable{
	
	public static Categoria CategoriaEstatica = new Categoria();
	
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
    
    @FXML
    private JFXButton btnExcluir;

    @FXML
    private JFXButton btnEditar;
    
    
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
			if(new ControlCategoria().Inserir(categoria) == 1)
			{
				Limpar();
	    		Alert alert = new Alert(AlertType.INFORMATION);

	            alert.setTitle("Sucesso");
	            alert.setHeaderText("Categoria inserida com sucesso");
	            
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
    	Limpar();
    }

    @FXML
    void btnVoltar_Action(ActionEvent event) {
    	Limpar();
    	CadCategoria.close();
    	CadCategoria = null;
    	new PesquisaCategoriaController().getPesquisaCategoria().show();
    }
    
    void Limpar() {
    	txtCodCategoria.setText("");
    	txtDescricao.setText("");
    	CategoriaEstatica = null;
    	btnEditar.setVisible(false);
    	btnExcluir.setVisible(false);
    	btnGravar.setVisible(true);
    }
    
    public void CarregarCategoria(Categoria categoria) throws Exception{
    	if (categoria.getCod() > 0) 
    	{
    		try {
    			if(categoria != null) 
    			{
    				txtCodCategoria.setText(categoria.getCod() +  "");
    				txtDescricao.setText(categoria.getNome());
    				btnEditar.setVisible(true);
    				btnExcluir.setVisible(true);
    				btnGravar.setVisible(false);
    			
    			}
    		}
    		catch(Exception e)
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
    }
    
    @FXML
    void btnEditar_Action(ActionEvent event) {
    	Alert ConfirmaEditar = new Alert(AlertType.CONFIRMATION);
    	
    	ConfirmaEditar.setTitle("Edição");
    	ConfirmaEditar.setHeaderText("Confirma a alteração na categoria selecionada?");  	
    	
    	Optional<ButtonType> result = ConfirmaEditar.showAndWait();
    	 if (result.isPresent() && result.get() == ButtonType.OK) {  
    	try 
    	{
    		Categoria categoriaEditar = new Categoria(Integer.parseInt(txtCodCategoria.getText()), txtDescricao.getText());
    		if(new ControlCategoria().Editar(categoriaEditar) == 1)
    		{
				Limpar();
	    		Alert alert = new Alert(AlertType.INFORMATION);

	            alert.setTitle("Sucesso");
	            alert.setHeaderText("Categoria alterada com sucesso");
	            
	            alert.showAndWait();
    		}
    		
		} 
		catch(Exception e)
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
  }

    @FXML
    void btnExcluir_Action(ActionEvent event) {
    	Alert ConfirmaExcluir = new Alert(AlertType.CONFIRMATION);
    	
    	ConfirmaExcluir.setTitle("Exclusão");
    	ConfirmaExcluir.setHeaderText("Deseja realmente excluir a categoria selecionada?");  	
    	
    	Optional<ButtonType> result = ConfirmaExcluir.showAndWait();
    	 if (result.isPresent() && result.get() == ButtonType.OK) {    		     	
    	try 
    	{
			if(new ControlCategoria().Deletar(Integer.parseInt(txtCodCategoria.getText())) == 1)
			{
				Limpar();
	    		Alert alert = new Alert(AlertType.INFORMATION);

	            alert.setTitle("Sucesso");
	            alert.setHeaderText("Categoria deletada com sucesso");
	            
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
    }
  }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		/* this.CarregarCategoria(); */
		if(CategoriaEstatica != null)
		{
			try
			{				
				this.CarregarCategoria(CategoriaEstatica);
			}
			catch (Exception e)
			{
    			Alert alert = new Alert(AlertType.WARNING);
            	
    			alert.setTitle("Atenção");
    			alert.setHeaderText(e.getMessage());
                
    			alert.showAndWait();
			}
		}
	}
}


