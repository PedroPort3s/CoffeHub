package views.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import control.compra_venda.ControlCompra;
import control.compra_venda.ControlCompraItens;
import control.produto.ControlProduto;
import dao.FornecedorDAO;
import entitys.Cliente;
import entitys.Compra;
import entitys.Compra_Item;
import entitys.Fornecedor;
import entitys.Produto;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import utils.GenericTableButton;
import utils.Logado;
import views.controllers.fornecedor.PesquisaFornecedorGeralController;

public class CadCompraController implements Initializable {

	private static int QtdProximoProduto;

	private static Stage CadCompra;

	private static Compra compraPrivate;

	public static Compra compraCarregada;

	public static Produto ProdutoEstatico = new Produto();

	public static Fornecedor FornecedorEstatico = new Fornecedor();

	@FXML
	private JFXTextField txtStatusCompra;

	@FXML
	private JFXTextField txtCodCompra;

	@FXML
	private JFXTextField txtDataCompra;

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
	private JFXButton btnVoltar;

	@FXML
	private JFXButton btnLimpar;

	@FXML
	private JFXButton btnFinalizar;

	@FXML
	private JFXButton btnEditar;

	@FXML
	private JFXButton btnAlterarQtd;

	@FXML
	private Label lblTotalCompra;

	@FXML
	private JFXButton btnEnviar;

	@FXML
	private JFXTextField txtQtdProxProduto;
	
	@FXML
    private TableView<Compra_Item> tableView;

    @FXML
    private TableColumn<Compra_Item, Integer> cCOD;

    @FXML
    private TableColumn<Compra_Item, String> cNome;

    @FXML
    private TableColumn<Compra_Item, String> cValor;

    @FXML
    private TableColumn<Compra_Item, Double> cQtd;
    
    @FXML
    private TableColumn<Compra_Item, Void> cEditarQtd;

    @FXML
    private TableColumn<Compra_Item, String> cCategoria;

    @FXML
    private TableColumn<Compra_Item, String> cMedida;
    
    @FXML
    private TableColumn<Compra_Item, String> cTotal;

    @FXML
    private TableColumn<Compra_Item, Compra_Item> cRemover;

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

					if (QtdProximoProduto >= 1)
						item.setQtd_item(QtdProximoProduto);
					else
						item.setQtd_item(1);

					item.setValor_unitario(produtoCarregado.getValor_un());

					if (compraPrivate == null || compraPrivate.getCod() == 0) {
						Date date = new Date();
						compraPrivate = new Compra();
						compraPrivate.setData_origem(date);

						Fornecedor fornecedorCompra = new FornecedorDAO()
								.buscarId(Integer.parseInt(txtCodFornecedor.getText()));
						compraPrivate.setFornecedor(fornecedorCompra);

						// SET LOGGED FUNCIONARIO PARA GRAVAR
						compraPrivate.setFuncionario(Logado.Funcionario);

						compraPrivate.setStatus("A");

						compraPrivate.setCod(new ControlCompra().Inserir(compraPrivate));

						RecarregarCompra(compraPrivate);
					} else {
						RecarregarCompra(compraPrivate);
					}

					if (new ControlCompraItens().AdicionarItem(compraPrivate, item) != 1)
						throw new Exception("Não foi possivel inserir o item na compra");
					else {
						txtCodProduto.setText("");
						txtProduto.setText("");
						QtdProximoProduto = 1;
						RecarregarCompra(compraPrivate);
					}
				} else {
					throw new Exception("Informe um produto para prosseguir.");
				}
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.showAndWait();
		}
	}

	@FXML
	void btnBuscarFornecedor_Action(ActionEvent event) {
		CadCompra.close();
		CadCompra = null;
		PesquisaFornecedorGeralController.cadPesqCompra = "CADCOMPRA";
		new PesquisaFornecedorGeralController().getPesquisaFornecedorGeral().show();
	}

	@FXML
	void btnBuscarProduto_Action(ActionEvent event) {
		CadCompra.close();
		CadCompra = null;
		PesquisaProdutoGeralController.compraVenda = "COMPRA";
		new PesquisaProdutoGeralController().getPesquisaProdutoGeral().show();
	}

	void Enviar() {
		try {
			Alert ConfirmaEnvio = new Alert(AlertType.CONFIRMATION);

			ConfirmaEnvio.setTitle("Finalizar");
			ConfirmaEnvio.setHeaderText("Deseja realmente enviar esta compra?");

			Optional<ButtonType> result = ConfirmaEnvio.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK) {
				if (txtCodCompra.getText().equals("") == false) {
					Compra compraEnviar = new ControlCompra().Carregar(Integer.parseInt(txtCodCompra.getText()));

					if (compraEnviar != null) {
						if (new ControlCompra().Enviar(compraEnviar) == 1) {
							Limpar();
							Alert alert = new Alert(AlertType.INFORMATION);

							alert.setTitle("Atenção");
							alert.setHeaderText("Compra enviada com sucesso");

							alert.showAndWait();
						} else {
							throw new Exception("Não foi possivel enviar a compra");
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
	void btnEnviar_Action(ActionEvent event) {
		try {
			Enviar();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);

			alert.setTitle("Atenção");
			alert.setHeaderText(e.getMessage());

			alert.showAndWait();
		}
	}

	void Finalizar() {
		try {
			Alert ConfirmaRemover = new Alert(AlertType.CONFIRMATION);

			ConfirmaRemover.setTitle("Finalizar");
			ConfirmaRemover.setHeaderText("Deseja realmente finalizar esta compra?");

			Optional<ButtonType> result = ConfirmaRemover.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK) {
				if (txtCodCompra.getText().equals("") == false) {
					Compra compraFinalizar = new ControlCompra().Carregar(Integer.parseInt(txtCodCompra.getText()));

					if (compraFinalizar != null) {
						if (new ControlCompra().Finalizar(compraFinalizar) == 1) {
							Limpar();
							Alert alert = new Alert(AlertType.INFORMATION);

							alert.setTitle("Atenção");
							alert.setHeaderText("Compra finalizada com sucesso");

							alert.showAndWait();
						} else {
							throw new Exception("Não foi possivel finalizar a compra");
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
		try {
			this.Finalizar();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);

			alert.setTitle("Atenção");
			alert.setHeaderText(e.getMessage());

			alert.showAndWait();
		}
	}

	@FXML
	void btnLimparFornecedor_Action(ActionEvent event) {
		txtCodFornecedor.setText("");
		txtFornecedor.setText("");
		FornecedorEstatico = null;
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

		txtStatusCompra.setText("");

		btnEditar.setVisible(false);
		btnAlterarQtd.setVisible(false);

		compraCarregada = null;

		btnAddProduto.setVisible(true);
		btnBuscarFornecedor.setVisible(true);
		btnLimparFornecedor.setVisible(true);
		btnBuscarProduto.setVisible(true);
		btnLimparProduto.setVisible(true);
		
		QtdProximoProduto = 1;
		txtQtdProxProduto.setText(QtdProximoProduto + "");
	}

	@FXML
	void btnLimpar_Action(ActionEvent event) {
		Limpar();
	}

	@FXML
	void btnVoltar_Action(ActionEvent event) {
		Limpar();
		CadCompra.close();
		CadCompra = null;
		compraPrivate = null;
		FornecedorEstatico = null;
		new PesquisaCompraController().getPesquisaCompra().show();
	}

	@FXML
	void btnEditar_Action(ActionEvent event) {
		try {
			if (compraCarregada != null) {
				EditarCompra(compraCarregada);
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);

			alert.setTitle("Atenção");
			alert.setHeaderText(e.getMessage());

			alert.showAndWait();
		}
	}

	void EditarCompra(Compra compra) {
		try {
			if (new ControlCompra().Editar(compra) == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);

				alert.setTitle("Atenção");
				alert.setHeaderText("Compra editada com sucesso");

				alert.showAndWait();
			}

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);

			alert.setTitle("Atenção");
			alert.setHeaderText(e.getMessage());

			alert.showAndWait();
		}
	}

	void RemoverItem() {
		try {

		} catch (Exception e) {
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
				txtQtdProxProduto.setText(QtdProximoProduto + "");
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
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);

			alert.setTitle("Atenção");
			alert.setHeaderText(e.getMessage());

			alert.showAndWait();
		}
	}

	void CarregarFornecedor(Fornecedor fornecedor) throws Exception {
		try {
			if (fornecedor != null) {
				txtCodFornecedor.setText(fornecedor.getCod() + "");
				txtFornecedor.setText(fornecedor.getNome());
			} else {
				throw new Exception("Não foi possivel carregar o fornecedor selecionado");
			}
		}

		catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);

			alert.setTitle("Atenção");
			alert.setHeaderText(e.getMessage());

			alert.showAndWait();
		}
	}

	void CarregarCompra(Compra compra) throws Exception {
		try {
			if (compra != null) {

				compraPrivate = compra;

				iniciarColunas(compra);
				
				txtCodCompra.setText(compra.getCod() + "");
				txtStatusCompra.setText(compra.getStatus());
				txtDataCompra.setText(compra.getData_origem() + "");

				txtCodFornecedor.setText(compra.getFornecedor().getCod() + "");
				txtFornecedor.setText(compra.getFornecedor().getNome());

				lblTotalCompra.setText("Total: R$ 0.00");

				lblTotalCompra.setText("Total: R$" + compra.TotalCompra());

				if (compra.getStatus().equals("F")) {
					btnAddProduto.setVisible(false);
					btnBuscarFornecedor.setVisible(false);
					btnLimparFornecedor.setVisible(false);
					btnBuscarProduto.setVisible(false);
					btnLimparProduto.setVisible(false);
					btnEditar.setVisible(false);
					btnAlterarQtd.setVisible(false);
					btnEnviar.setVisible(false);
					btnFinalizar.setVisible(false);
				} else if (compra.getStatus().equals("E")) {
					btnAddProduto.setVisible(false);
					btnBuscarFornecedor.setVisible(false);
					btnLimparFornecedor.setVisible(false);
					btnBuscarProduto.setVisible(false);
					btnLimparProduto.setVisible(false);
					btnEditar.setVisible(false);
					btnAlterarQtd.setVisible(false);
					btnEnviar.setVisible(false);
					btnFinalizar.setVisible(true);
				} else {
					btnEditar.setVisible(true);
					btnAlterarQtd.setVisible(true);
				}
			} else {
				throw new Exception("Não foi possivel carregar a compra selecionada");
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

				compraPrivate = compra;

				iniciarColunas(compra);
				
				txtCodCompra.setText(compra.getCod() + "");
				txtDataCompra.setText(compra.getData_origem() + "");

				txtCodFornecedor.setText(compra.getFornecedor().getCod() + "");
				txtFornecedor.setText(compra.getFornecedor().getNome());

				txtStatusCompra.setText(compra.getStatus());
				lblTotalCompra.setVisible(true);
				lblTotalCompra.setText("Total: R$ 0.00");

				lblTotalCompra.setText("Total: R$" + compra.TotalCompra());

				if (compra.getStatus().equals("F")) {
					btnAddProduto.setVisible(false);
					btnBuscarFornecedor.setVisible(false);
					btnLimparFornecedor.setVisible(false);
					btnBuscarProduto.setVisible(false);
					btnLimparProduto.setVisible(false);
					btnEditar.setVisible(false);
					btnAlterarQtd.setVisible(false);
					btnEnviar.setVisible(false);
					btnFinalizar.setVisible(false);
				} else if (compra.getStatus().equals("E")) {
					btnAddProduto.setVisible(false);
					btnBuscarFornecedor.setVisible(false);
					btnLimparFornecedor.setVisible(false);
					btnBuscarProduto.setVisible(false);
					btnLimparProduto.setVisible(false);
					btnEditar.setVisible(false);
					btnAlterarQtd.setVisible(false);
					btnEnviar.setVisible(false);
					btnFinalizar.setVisible(true);
				} else {
					btnEditar.setVisible(true);
					btnAlterarQtd.setVisible(true);
				}

			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);

			alert.setTitle("Atenção");
			alert.setHeaderText(e.getMessage());

			alert.showAndWait();
		}
	}

	public static final String TRASH_SOLID = "M432 32H312l-9.4-18.7A24 24 0 0 0 281.1 0H166.8a23.72 23.72 0 0 0-21.4 13.3L136 32H16A16 16 0 0 0 0 48v32a16 16 0 0 0 16 16h416a16 16 0 0 0 16-16V48a16 16 0 0 0-16-16zM53.2 467a48 48 0 0 0 47.9 45h245.8a48 48 0 0 0 47.9-45L416 128H32z";
	
	void iniciarColunas(Compra compra) {
		
		tableView.setItems(FXCollections.observableArrayList(compra.getItens()));

		cCOD.setCellValueFactory(new PropertyValueFactory<>("num_item"));
		cNome.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Compra_Item, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Compra_Item, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getProduto().getDescricao());
            }
        });
		cValor.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Compra_Item, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Compra_Item, String> param) {
                return new ReadOnlyStringWrapper("R$ " + param.getValue().getValor_unitario());
            }
        });
		cQtd.setCellValueFactory(new PropertyValueFactory<>("qtd_item"));
		setcellEditarQtdFactory();
		cCategoria.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Compra_Item, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Compra_Item, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getProduto().getCategoria().getNome());
            }
        });
		cMedida.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Compra_Item, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Compra_Item, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getProduto().getUnidadeMedida().getCod());
            }
        });
		cTotal.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Compra_Item, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Compra_Item, String> param) {
				return new ReadOnlyStringWrapper("R$ " + (param.getValue().getQtd_item() * param.getValue().getValor_unitario()));
			}
		});
		
		GenericTableButton.initButtons(cRemover, 15, TRASH_SOLID, "svg-red",
				(Compra_Item compraItem, ActionEvent event) -> {
					try {
						Alert ConfirmaRemover = new Alert(AlertType.CONFIRMATION);

						ConfirmaRemover.setTitle("Remover Item");
						ConfirmaRemover.setHeaderText("Deseja realmente remover o item selecionado?");

						Optional<ButtonType> result = ConfirmaRemover.showAndWait();
						if (result.isPresent() && result.get() == ButtonType.OK) {
							Compra compraEscolhida = new ControlCompra()
									.Carregar(Integer.parseInt(txtCodCompra.getText()));

							if (compra != null && compraItem != null) {
								if (new ControlCompraItens().RemoverItem(compraEscolhida, compraItem) == 1) {
									this.RecarregarCompra(compraEscolhida);
								}
							}
						}
					} catch (Exception e) {
						Alert alert = new Alert(AlertType.WARNING);

						alert.setTitle("Atenção");
						alert.setHeaderText(e.getMessage());

						alert.showAndWait();
					}

				});
	}
	
	private void setcellEditarQtdFactory() {
		Callback<TableColumn<Compra_Item, Void>, TableCell<Compra_Item, Void>> cellQtd = new Callback<TableColumn<Compra_Item, Void>, TableCell<Compra_Item, Void>>() {
            @Override
            public TableCell<Compra_Item, Void> call(final TableColumn<Compra_Item, Void> param) {
                final TableCell<Compra_Item, Void> cell = new TableCell<Compra_Item, Void>() {
                	
                	                	
                	private final Button btn = new Button("Alterar");{
                        btn.setOnAction((ActionEvent event) -> {
							try {
								TextInputDialog textDialog = new TextInputDialog();
								textDialog.setTitle("Alterar quantidade");
								textDialog.setHeaderText("Alterar quantidade do proximo produto");
								textDialog.setContentText("Informe uma quantidade: ");

								Optional<String> resultado = textDialog.showAndWait();
								String quantidadeString = resultado.map(Object::toString).orElse(null);

								int qtd = Integer.parseInt(quantidadeString);
								atualizarQtd(getTableView().getItems().get(getIndex()), qtd);
								RecarregarCompra(compraPrivate);
								
							} catch (Exception e) {
								Alert alert = new Alert(AlertType.WARNING);
								alert.setTitle("Atenção");
								alert.setHeaderText("Não foi possivel alterar a quantidade");
								alert.showAndWait();
							}
                        });
                        
                        btn.setPrefWidth(200);
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        cEditarQtd.setCellFactory(cellQtd);
	}
	
	private void atualizarQtd(Compra_Item compraItem, int qtd) {
		try {
    		compraItem.setQtd_item(qtd);
    		if(new ControlCompraItens().AlterarQuantidadeItem(compraPrivate, compraItem) != 1) {
				throw new Exception("Não foi possivel inserir o item na compra");
    		}
		} catch (Exception e) {
			e.printStackTrace();
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
			if (FornecedorEstatico != null) {
				this.CarregarFornecedor(FornecedorEstatico);
			}
			if (compraCarregada != null) {
				this.CarregarCompra(compraCarregada);
			}
			
			txtQtdProxProduto.setText(QtdProximoProduto + "");

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);

			alert.setTitle("Atenção");
			alert.setHeaderText(e.getMessage());

			alert.showAndWait();
		}
	}

}
