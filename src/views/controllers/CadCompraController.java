package views.controllers;

import java.net.URL;

import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import control.compra_venda.ControlCompra;
import control.compra_venda.ControlCompraItens;
import control.produto.ControlProduto;
import entitys.Compra;
import entitys.Compra_Item;
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
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CadCompraController implements Initializable {

	private static Stage CadCompra;

	private static Compra compraPrivate;

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

	@FXML
	private Label lblTotalCompra;

	public Stage getCadCompra() {
		if (CadCompra == null) {
			try {
				Stage primaryStage = new Stage();
				AnchorPane root = (AnchorPane) FXMLLoader
						.load(getClass().getResource("/views/compraVenda/CadCompra.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.initStyle(StageStyle.TRANSPARENT);
				CadCompra = primaryStage;
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
				alert.showAndWait();
			}
		}
		return CadCompra;
	}

	@FXML
	void btnAddProduto_Action(ActionEvent event) throws Exception {
		try {
			if (!txtCodProduto.getText().equals("")) {
				Produto produtoCarregado = new ControlProduto().Carregar(Integer.parseInt(txtCodProduto.getText()));

				if (produtoCarregado != null) {
					Compra_Item item = new Compra_Item();
					item.setProduto(produtoCarregado);

					item.setQtd_item(1);
					item.setValor_unitario(produtoCarregado.getValor_un());

					if (compraPrivate == null) {
						Date date = new Date();
						compraPrivate = new Compra();
						compraPrivate.setData_origem(date);

						Fornecedor fornecedor = new Fornecedor();
						fornecedor.setCod(1);
						/* fornecedor.setData_contrato(date); */
						fornecedor.setDocumento("123456789101");
						fornecedor.setEmail("a@a.com");
						fornecedor.setEndereco("CUPELUDO");
						fornecedor.setNome("Guina");
						fornecedor.setTelefone("44444444444");
						compraPrivate.setFornecedor(fornecedor);

						Funcionario funcionario = new Funcionario();
						funcionario.setCod(1);
						funcionario.setData_contratacao(date);
						funcionario.setDocumento("123456789101");
						funcionario.setEmail("a@a.com");
						funcionario.setEndereco("CUPELUDO");
						funcionario.setNome("Guina");
						funcionario.setTelefone("44444444444");
						compraPrivate.setFuncionario(funcionario);

						compraPrivate.setStatus("A");
						
						compraPrivate.setCod(new ControlCompra().Inserir(compraPrivate));

						RecarregarCompra(compraPrivate);
					}

					if (new ControlCompraItens().AdicionarItem(compraPrivate, item) != 1) {
						throw new Exception("Não foi possivel inserir o item na compra");
					}
					RecarregarCompra(compraPrivate);
				}
			}
		}
		catch (Exception e) {			
			Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.showAndWait();
		}
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
		try {
			/*
			 * Compra compra = new Compra();
			 * 
			 * Date date = new Date();
			 * 
			 * SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
			 * String currentDateTime = format.format(date);
			 * 
			 * 
			 * 
			 * compra.setData_origem(date);
			 * 
			 * Fornecedor fornecedor = new Fornecedor(); fornecedor.setCod(1);
			 * fornecedor.setData_contrato(date); fornecedor.setDocumento("123456789101");
			 * fornecedor.setEmail("a@a.com"); fornecedor.setEndereco("CUPELUDO");
			 * fornecedor.setNome("Guina"); fornecedor.setTelefone("44444444444");
			 * compra.setFornecedor(fornecedor);
			 * 
			 * Funcionario funcionario = new Funcionario(); funcionario.setCod(1);
			 * funcionario.setData_contratacao(date);
			 * funcionario.setDocumento("123456789101"); funcionario.setEmail("a@a.com");
			 * funcionario.setEndereco("CUPELUDO"); funcionario.setNome("Guina");
			 * funcionario.setTelefone("44444444444"); compra.setFuncionario(funcionario );
			 * 
			 * compra.setStatus("A");
			 * 
			 * if(new ControlCompra().Inserir(compra) == 1) { Alert alert = new
			 * Alert(AlertType.INFORMATION);
			 * 
			 * alert.setTitle("Sucesso");
			 * alert.setHeaderText("Compra inserida com sucesso");
			 * 
			 * alert.showAndWait(); }
			 */
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);

			alert.setTitle("Atenção");
			alert.setHeaderText(e.getMessage());

			alert.showAndWait();
		} catch (Error e) {
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

		lblTotalCompra.setVisible(false);
		lblTotalCompra.setText("");

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

	void CarregarProduto(Produto produto) throws Exception {
		try {
			if (produto != null) {
				txtCodProduto.setText(produto.getCod() + "");
				txtProduto.setText(produto.getDescricao());
			} else {
				throw new Exception("Não foi possivel carregar o produto selecionado");
			}
		}

		catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);

			alert.setTitle("Atenção");
			alert.setHeaderText(e.getMessage());

			alert.showAndWait();
		}
	}

	void RecarregarCompra(Compra compra) {
		try {
			if (compra.getCod() > 0) {
				compra = new ControlCompra().Carregar(compra.getCod());

				if (compra == null) {
					throw new Exception("Erro ao recarregar compra");
				}

				txtCodCompra.setText(compra.getCod() + "");
				txtDataCompra.setText(compra.getData_origem() + "");
				
				Fornecedor fornecedorCompra = new Fornecedor();
				fornecedorCompra.setCod(1);
				fornecedorCompra.setNome("Pedrinho");
				
				txtCodFornecedor.setText(fornecedorCompra.getCod() + "");
				txtFornecedor.setText(fornecedorCompra.getNome() + "");
				
				
				txtStatusCompra.setText(compra.getStatus());
				lblTotalCompra.setVisible(true);
				lblTotalCompra.setText("Total: " + compra.TotalCompra());
				

				// Lista de produto para o list view
				compra.getItens().forEach(p -> lvProdutos.getItems().add(p.getProduto()));

			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);

			alert.setTitle("Atenção");
			alert.setHeaderText(e.getMessage());

			alert.showAndWait();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		try {
			if (compraPrivate != null) {
				RecarregarCompra(compraPrivate);
			}
			
			if (ProdutoEstatico != null && ProdutoEstatico.getCod() > 0) {
				this.CarregarProduto(ProdutoEstatico);
			}

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);

			alert.setTitle("Atenção");
			alert.setHeaderText(e.getMessage());

			alert.showAndWait();
		}
	}

}
