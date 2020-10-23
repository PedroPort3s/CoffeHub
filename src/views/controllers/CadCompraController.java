package views.controllers;

import java.net.URL;

import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import control.compra_venda.ControlCompra;
import control.produto.ControlProduto;
import entitys.Compra;
import entitys.Fornecedor;
import entitys.Funcionario;
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

public class CadCompraController implements Initializable{
	
	private static Stage CadCompra;
	
	public static Produto ProdutoEstatico = new Produto();
	
	public static Funcionario FuncionarioEstatico = new Funcionario();	

	public static Fornecedor FornecedorEstatico = new Fornecedor();

    @FXML
    private JFXTextField txtStatusCompra;
	
    @FXML
    private JFXTextField txtCodCompra;

    @FXML
    private JFXTextField txtDataCompra;

    @FXML
    private JFXListView<Produto> lvProdutos;

    @FXML
    private JFXTextField txtProduto;

    @FXML
    private JFXButton btnAddProduto;

    @FXML
    private JFXButton btnBuscarProduto;

    @FXML
    private JFXButton btnLimparProduto;

    @FXML
    private JFXTextField txtCodProduto;

    @FXML
    private JFXButton btnBuscarFornecedor;

    @FXML
    private JFXTextField txtFornecedor;

    @FXML
    private JFXButton btnLimparFornecedor;

    @FXML
    private JFXTextField txtCodFornecedor;

    @FXML
    private JFXButton btnGravar;

    @FXML
    private JFXButton btnVoltar;

    @FXML
    private JFXButton btnLimpar;

    @FXML
    private JFXButton btnFinalizar;
    
	public Stage getCadCompra() {
		if (CadCompra == null)
		{
			try {
		    	Stage primaryStage = new Stage();
				AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/views/compraVenda/CadCompra.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.initStyle(StageStyle.TRANSPARENT);
				CadCompra = primaryStage;
			}
		  catch(Exception e)
		  	{
			  Alert alert = new Alert(AlertType.ERROR,e.getMessage(),ButtonType.OK);
			  alert.showAndWait();	
		  	}
		}
		return CadCompra;
	}
    

    @FXML
    void btnAddProduto_Action(ActionEvent event) {

    }

    @FXML
    void btnBuscarFornecedor_Action(ActionEvent event) {

    }

    @FXML
    void btnBuscarProduto_Action(ActionEvent event) {
    	CadCompra.close();
    	CadCompra = null;
    	PesquisaProdutoGeralController.compraVenda = "COMPRA";
    	new PesquisaProdutoGeralController().getPesquisaProdutoGeral().show();
    }

    @FXML
    void btnFinalizar_Action(ActionEvent event) {

    }

    @FXML
    void btnGravar_Action(ActionEvent event) {
    	try 
    	{
			/*
			 * Categoria categoriaSelecionada =
			 * this.cbCategoria.getSelectionModel().getSelectedItem();
			 * 
			 * Produto produto = new Produto(txtDescricao.getText(), txtValor.getText(),
			 * txtQtd.getText(), txtUnMedida.getText(), categoriaSelecionada);
			 * 
			 * if(new ControlProduto().Inserir(produto) == 1) { Limpar(); Alert alert = new
			 * Alert(AlertType.INFORMATION);
			 * 
			 * alert.setTitle("Sucesso");
			 * alert.setHeaderText("Produto inserido com sucesso");
			 * 
			 * alert.showAndWait(); }
			 */
    		Compra compra = new Compra();    		
    		
			Date date = new Date();
			/*
			 * SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
			 * String currentDateTime = format.format(date);
			 */
    		
			
			compra.setData_origem(date);
    		if(new ControlCompra().Inserir(compra) == 1)
    		{
    		  Alert alert = new Alert(AlertType.INFORMATION);
	    		
   			  alert.setTitle("Sucesso");
   			  alert.setHeaderText("Compra inserida com sucesso");
   			  
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
    void btnLimparFornecedor_Action(ActionEvent event) {

    }

    @FXML
    void btnLimparProduto_Action(ActionEvent event) {
    	txtCodProduto.setText("");
    	txtProduto.setText("");
    	ProdutoEstatico = null;
    }
    
    void Limpar() {
    	txtCodProduto.setText("");
    	txtProduto.setText("");
    	ProdutoEstatico = null;
    	
    	txtCodCompra.setText("");
    	
    	txtCodFornecedor.setText("");
    	txtFornecedor.setText("");
    	FornecedorEstatico = null;
    	
    	txtDataCompra.setText("");
    	
    }

    @FXML
    void btnLimpar_Action(ActionEvent event) {
    	Limpar();
    }

    @FXML
    void btnVoltar_Action(ActionEvent event) {
    	CadCompra.close();
    	CadCompra = null;
    	new PesquisaCompraController().getPesquisaCompra().show(); 
    }

    @FXML
    void lvProdutos_MouseClicked(MouseEvent event) {

    }

    @FXML
    void txtFornecedor_MouseClicked(MouseEvent event) {

    }
    
    void CarregarProduto(Produto produto) throws Exception{
    	try
    	{    	
    	if (produto != null)
    	{
			txtCodProduto.setText(produto.getCod() + "");
			txtProduto.setText(produto.getDescricao());
		}
    	else 
    	{
			throw new Exception("Não foi possivel carregar o produto selecionado");
		}
    }
    	
    	catch(Exception e)
    	{
    		Alert alert = new Alert(AlertType.WARNING);

            alert.setTitle("Atenção");
            alert.setHeaderText(e.getMessage());
            
            alert.showAndWait();
    	}
    }
    
    void RecarregarCompra(Compra compra)
    {    
    	try 
    	{
			if(compra.getCod() > 0)
			{
				compra = new ControlCompra().Carregar(compra.getCod());					
				
				if(compra == null)
				{
					throw new Exception("Erro ao recarregar compra");
				}
				
				txtCodCompra.setText(compra.getCod() + "");
				txtDataCompra.setText(compra.getData_origem() + "");
				txtCodFornecedor.setText(compra.getFornecedor().getCod() + "");
				txtFornecedor.setText(compra.getFornecedor().getNome() + "");
				txtStatusCompra.setText(compra.getStatus());
				
				// Lista de produto para o list view
				compra.getItens().forEach(p -> lvProdutos.getItems().add(p.getProduto()));
				
				
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
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		  try
		  { 
			  if(ProdutoEstatico != null && ProdutoEstatico.getCod() > 0) 
			  {
				  this.CarregarProduto(ProdutoEstatico);
			  }
			  
		  } 
		  catch(Exception e)
		  {
			  Alert alert = new Alert(AlertType.WARNING);
		  
			  alert.setTitle("Atenção"); alert.setHeaderText(e.getMessage());
			  
			  alert.showAndWait(); 
		   }
		 }
    
}
