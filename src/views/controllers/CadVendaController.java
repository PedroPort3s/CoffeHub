package views.controllers;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import control.compra_venda.ControlVenda;
import control.compra_venda.ControlVendaItens;
import control.produto.ControlProduto;
import dao.ClienteDAO;
import entitys.Cliente;
import entitys.Funcionario;
import entitys.Produto;
import entitys.Venda;
import entitys.Venda_Item;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.Logado;
import views.controllers.cliente.PesquisaClienteGeralController;

public class CadVendaController implements Initializable {

	private double QtdProximoProduto;

	private static Stage CadVenda;

	public static Produto ProdutoEstatico = null;

	public static Cliente ClienteEstatico = null;

	public static Venda VendaPrivate;

	public static Venda VendaCarregada;

	@FXML
	private JFXButton btnVoltar;

	@FXML
	private JFXButton btnLimpar;

	@FXML
	private JFXTextField txtCliente;

	@FXML
	private JFXTextField txtDataOrigem;

	@FXML
	private JFXTextField txtDataConfirmacao;

	@FXML
	private JFXTextField txtCodVenda;

	@FXML
	private JFXListView<Produto> lvProdutos;

	@FXML
	private JFXTextField txtProduto;

	@FXML
	private Label lblValorTotal;

	@FXML
	private JFXButton btnAddProduto;

	@FXML
	private JFXButton btnFinalizar;

	@FXML
	private JFXButton btnBuscarProduto;

	@FXML
	private JFXButton btnLimparProduto;

	@FXML
	private JFXTextField txtCodProduto;

	@FXML
	private JFXButton btnAlterarQtd;

	@FXML
	private JFXButton btnEditar;

	@FXML
	private JFXTextField txtCodCliente;

	@FXML
	private JFXTextField txtStatus;

	@FXML
	private JFXButton btnBuscarCliente;

	@FXML
	private JFXButton btnLimparCliente;

	public Stage getCadVenda() {
		if (CadVenda == null) {
			try {
				Stage primaryStage = new Stage();
				AnchorPane root = (AnchorPane) FXMLLoader
						.load(getClass().getResource("/views/compraVenda/CadVenda.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.initStyle(StageStyle.TRANSPARENT);
				CadVenda = primaryStage;
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
				alert.showAndWait();
			}
		}
		return CadVenda;
	}

	@FXML 
	void btnAddProduto_Action(ActionEvent event) {
		try {
			if (!txtCodProduto.getText().equals("")) {
				Produto produtoCarregado = new ControlProduto().Carregar(Integer.parseInt(txtCodProduto.getText()));

				if (produtoCarregado != null) {
					Venda_Item item = new Venda_Item();
					item.setProduto(produtoCarregado);

					if (QtdProximoProduto >= 1)
						item.setQtd_item(QtdProximoProduto);
					else
						item.setQtd_item(QtdProximoProduto = 1);

					item.setValor_venda(produtoCarregado.getValor_un());

					if (VendaPrivate == null || VendaPrivate.getCod() == 0) {
						Date date = new Date();
						VendaPrivate = new Venda();
						VendaPrivate.setData_origem(date);

						Cliente clienteVenda = new ClienteDAO().buscarId(Integer.parseInt(txtCodCliente.getText()));
						VendaPrivate.setCliente(clienteVenda);

						// SET LOGGED FUNCIONARIO PARA GRAVAR
						VendaPrivate.setFuncionario(Logado.Funcionario);

						VendaPrivate.setStatus("A");

						VendaPrivate.setCod(new ControlVenda().Inserir(VendaPrivate));

						CarregarVenda(VendaPrivate);
					} else {
						CarregarVenda(VendaPrivate);
					}

					if (new ControlVendaItens().AdicionarItem(VendaPrivate, item) != 1)
						throw new Exception("Não foi possivel inserir o item na venda");
					else {
						txtCodProduto.setText("");
						txtProduto.setText("");
						QtdProximoProduto = 1;
						CarregarVenda(VendaPrivate);
					}
				}
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.showAndWait();
		}
	}

	void Finalizar() {
		try {
			Alert ConfirmaFinalizar = new Alert(AlertType.CONFIRMATION);

			ConfirmaFinalizar.setTitle("Finalizar");
			ConfirmaFinalizar.setHeaderText("Deseja realmente finalizar esta venda?");

			Optional<ButtonType> result = ConfirmaFinalizar.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK) {
				if (txtCodVenda.getText().equals("") == false) {
					Venda vendaFinalizar = new ControlVenda().Carregar(Integer.parseInt(txtCodVenda.getText()));

					if (vendaFinalizar != null) {
						if (new ControlVenda().Finalizar(vendaFinalizar) == 1) {
							Limpar();
							Alert alert = new Alert(AlertType.INFORMATION);

							alert.setTitle("Atenção");
							alert.setHeaderText("Venda Finalizada com sucesso");

							alert.showAndWait();
						} else {
							throw new Exception("Não foi possivel finalizar a venda");
						}
					}
				}
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);

			alert.setTitle("Atenção");
			alert.setHeaderText(e.getMessage());

			alert.showAndWait();
		}
	}

	@FXML
	void btnFinalizar_Action(ActionEvent event) {
		this.Finalizar();
	}

	void Limpar() {
		VendaCarregada = null;
		VendaPrivate = null;
		ClienteEstatico = null;
		ProdutoEstatico = null;
		QtdProximoProduto = 1;

		txtCodProduto.setText("");
		txtProduto.setText("");

		txtCodVenda.setText("");
		txtCodCliente.setText("");
		txtCliente.setText("");

		txtDataConfirmacao.setText("");
		txtDataOrigem.setText("");

		txtStatus.setText("");

		btnEditar.setVisible(false);
		
		lvProdutos.getItems().clear();
		
		lblValorTotal.setVisible(false);
	}

	@FXML
	void btnLimpar_Action(ActionEvent event) {
		Limpar();
	}

	@FXML
	void btnVoltar_Action(ActionEvent event) {
		Limpar();
		CadVenda.close();
		CadVenda = null;
		new PesquisaVendaController().getPesquisaVenda().show();
	}

	@FXML
	void lvProdutos_MouseClicked(MouseEvent event) {
		try {
			Alert ConfirmaRemover = new Alert(AlertType.CONFIRMATION);

			ConfirmaRemover.setTitle("Remover Item");
			ConfirmaRemover.setHeaderText("Deseja realmente remover o item selecionado?");

			Optional<ButtonType> result = ConfirmaRemover.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK) {
				Venda venda = new ControlVenda().Carregar(Integer.parseInt(txtCodVenda.getText()));

				List<Venda_Item> itens = venda.getItens();
				Venda_Item vi = itens.get(lvProdutos.getSelectionModel().getSelectedIndex());

				if (venda != null && vi != null) {
					if (new ControlVendaItens().RemoverItem(venda, vi) == 1) {
						this.CarregarVenda(venda);
					}
				}
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);

			alert.setTitle("Atenção");
			alert.setHeaderText(e.getMessage());

			alert.showAndWait();
		}
	}

	@FXML
	void btnLimparProduto_Action(ActionEvent event) {
		txtCodProduto.setText("");
		txtProduto.setText("");
		ProdutoEstatico = null;
	}

	@FXML
	void btnBuscarProduto_Action(ActionEvent event) {
		CadVenda.close();
		CadVenda = null;
		PesquisaProdutoGeralController.compraVenda = "VENDA";
		new PesquisaProdutoGeralController().getPesquisaProdutoGeral().show();
	}

	@FXML
	void btnLimparCliente_Action(ActionEvent event) {
		txtCodCliente.setText("");
		txtCliente.setText("");
		ClienteEstatico = null;
	}

	@FXML
	void btnBuscarCliente_Action(ActionEvent event) {
		CadVenda.close();
		CadVenda = null;
		new PesquisaClienteGeralController().getPesquisaClienteGeral().show();
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

	@FXML
	void btnAlterarQtd_Action(ActionEvent event) {
		try {
			TextInputDialog textDialog = new TextInputDialog();
			textDialog.setTitle("Alterar quantidade");
			textDialog.setHeaderText("Alterar quantidade do proximo produto");
			textDialog.setContentText("Informe uma quantidade: ");

			Optional<String> resultado = textDialog.showAndWait();
			String quantidadeString = resultado.map(Object::toString).orElse(null);

			if (Integer.parseInt(quantidadeString) > 0) {
				QtdProximoProduto = Integer.parseInt(quantidadeString);
				textDialog.close();
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);

			alert.setTitle("Atenção");
			alert.setHeaderText(e.getMessage());

			alert.showAndWait();
		}
	}

	@FXML
	void btnEditar_Action(ActionEvent event) {

	}

	void CarregarCliente(Cliente cliente) {
		try {
			if (cliente != null) {
				txtCodCliente.setText(cliente.getCod() + "");
				txtCliente.setText(cliente.getNome());
			} else {
				throw new Exception("Não foi possivel carregar o cliente selecionado");
			}
		}

		catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);

			alert.setTitle("Atenção");
			alert.setHeaderText(e.getMessage());

			alert.showAndWait();
		}
	}

	void CarregarVenda(Venda venda) {
		try {
			if (venda != null) {
				
				if (venda.getCod() > 0) {
					venda = new ControlVenda().Carregar(venda.getCod());

					if (venda == null) {
						throw new Exception("Erro ao carregar a venda");
					}
				}

				VendaPrivate = venda;

				txtCodVenda.setText(venda.getCod() + "");
				txtStatus.setText(venda.getStatus());
				txtDataOrigem.setText(venda.getData_origem() + "");

				txtCodCliente.setText(venda.getCliente().getCod() + "");
				txtCliente.setText(venda.getCliente().getNome());

				lblValorTotal.setText("Total: R$ 0.00");

				// Lista de produto para o list view
				lvProdutos.getItems().clear();
				if (venda.getItens() != null && venda.getItens().size() > 0) {
					venda.getItens().forEach(p -> lvProdutos.getItems().add(p.getProduto()));
					lblValorTotal.setText("Total: R$" + venda.TotalVenda());
				}
				if (venda.getStatus().equals("F")) {
					btnAddProduto.setVisible(false);
					btnBuscarCliente.setVisible(false);
					btnLimparCliente.setVisible(false);
					btnBuscarProduto.setVisible(false);
					btnLimparProduto.setVisible(false);
					btnEditar.setVisible(false);
					btnAlterarQtd.setVisible(false);
				} else {
					btnEditar.setVisible(true);
					btnAlterarQtd.setVisible(true);
				}
			} else {
				throw new Exception("Não foi possivel carregar a venda selecionada");
			}
		}

		catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);

			alert.setTitle("Atenção");
			alert.setHeaderText(e.getMessage());

			alert.showAndWait();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		try {
			if (ProdutoEstatico != null && ProdutoEstatico.getCod() > 0) {
				this.CarregarProduto(ProdutoEstatico);
			}
			if (VendaPrivate != null && VendaPrivate.getCod() > 0) {
				this.CarregarVenda(VendaPrivate);
			}
			if (ClienteEstatico != null && ClienteEstatico.getCod() > 0) {
				this.CarregarCliente(ClienteEstatico);
			}

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);

			alert.setTitle("Atenção");
			alert.setHeaderText(e.getMessage());

			alert.showAndWait();
		}
	}
}
