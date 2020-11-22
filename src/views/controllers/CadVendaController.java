package views.controllers;

import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import control.compra_venda.ControlVenda;
import control.compra_venda.ControlVendaItens;
import control.produto.ControlProduto;
import dao.ClienteDAO;
import entitys.Cliente;
import entitys.Produto;
import entitys.Venda;
import entitys.Venda_Item;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import utils.GenericTableButton;
import utils.Logado;
import utils.Verifica;
import views.controllers.cliente.PesquisaClienteGeralController;

public class CadVendaController implements Initializable {

	private static double QtdProximoProduto;

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

	@FXML
	private JFXTextField txtQtdProxProduto;

	@FXML
    private TableView<Venda_Item> tableView;

    @FXML
    private TableColumn<Venda_Item, Integer> cCod;

    @FXML
    private TableColumn<Venda_Item, String> cNome;

    @FXML
    private TableColumn<Venda_Item, String> cValor;

    @FXML
    private TableColumn<Venda_Item, String> cQtd;
    
    @FXML
    private TableColumn<Venda_Item, Void> cEditarQtd;

    @FXML
    private TableColumn<Venda_Item, String> cCategoria;

    @FXML
    private TableColumn<Venda_Item, String> cMedida;
    
    @FXML
    private TableColumn<Venda_Item, String> cTotal;

    @FXML
    private TableColumn<Venda_Item, Venda_Item> cRemover;

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

					if (QtdProximoProduto >= 1) {
						item.setQtd_item(QtdProximoProduto);
						txtQtdProxProduto.setText(QtdProximoProduto + "");
					} else {
						item.setQtd_item(QtdProximoProduto = 1);
						txtQtdProxProduto.setText(QtdProximoProduto + "");
					}
					item.setValor_venda(produtoCarregado.getValor_un());

					if (VendaPrivate == null || VendaPrivate.getCod() == 0) {
						Date date = new Date();
						VendaPrivate = new Venda();
						VendaPrivate.setData_origem(date);

						if (txtCodCliente.getText().equals("") || !Verifica.ehNumeroInt(txtCodCliente.getText())) {
							throw new Exception("Informe um cliente para a venda");
						}

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
						txtQtdProxProduto.setText(QtdProximoProduto + "");
						CarregarVenda(VendaPrivate);
					}
				}
			} 
			else {
				throw new Exception("Informe um produto para prosseguir.");
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

		lblValorTotal.setVisible(false);
		
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
		CadVenda.close();
		CadVenda = null;
		new PesquisaVendaController().getPesquisaVenda().show();
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
		PesquisaClienteGeralController.cadPesqVenda = "CADVENDA";
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

	void Editar(Venda venda) {
		try {
			if (Integer.parseInt(txtCodCliente.getText()) != venda.getCliente().getCod()) {
				Cliente clienteAlterado = new ClienteDAO().buscarId(Integer.parseInt(txtCodCliente.getText()));
				if (clienteAlterado != null && clienteAlterado.getCod() > 0) {
					venda.setCliente(clienteAlterado);
					if (new ControlVenda().Editar(venda) == 1) {
						CarregarVenda(venda);
						Alert alert = new Alert(AlertType.INFORMATION);

						alert.setTitle("Atenção");
						alert.setHeaderText("Venda editada com sucesso");

						alert.showAndWait();
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
	void btnEditar_Action(ActionEvent event) {
		try {
			if (VendaPrivate != null && VendaPrivate.getCod() > 0)
				this.Editar(VendaPrivate);

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);

			alert.setTitle("Atenção");
			alert.setHeaderText(e.getMessage());

			alert.showAndWait();
		}
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

				iniciarColunas(VendaPrivate);
				
				txtCodVenda.setText(venda.getCod() + "");
				txtStatus.setText(venda.getStatus());
				txtDataOrigem.setText(venda.getData_origem() + "");

				txtCodCliente.setText(venda.getCliente().getCod() + "");
				txtCliente.setText(venda.getCliente().getNome());

				lblValorTotal.setText("Total: R$ 0.00");

				// Lista de produto para o list view
				lblValorTotal.setText("Total: R$" + venda.TotalVenda());

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

	public static final String TRASH_SOLID = "M432 32H312l-9.4-18.7A24 24 0 0 0 281.1 0H166.8a23.72 23.72 0 0 0-21.4 13.3L136 32H16A16 16 0 0 0 0 48v32a16 16 0 0 0 16 16h416a16 16 0 0 0 16-16V48a16 16 0 0 0-16-16zM53.2 467a48 48 0 0 0 47.9 45h245.8a48 48 0 0 0 47.9-45L416 128H32z";
	
	void iniciarColunas(Venda venda) {
		
		System.out.println(venda.getItens());
		
		tableView.setItems(FXCollections.observableArrayList(venda.getItens()));

//		cCOD.setCellValueFactory(new PropertyValueFactory<>("cod"));
		cNome.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Venda_Item, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Venda_Item, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getProduto().getDescricao());
            }
        });
		cValor.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Venda_Item, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Venda_Item, String> param) {
                return new ReadOnlyStringWrapper("R$ " + param.getValue().getProduto().getValor_un());
            }
        });
		cQtd.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Venda_Item, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Venda_Item, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getQtd_item()+"");
            }
        });
		setcellEditarQtdFactory();
		cCategoria.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Venda_Item, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Venda_Item, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getProduto().getCategoria().getNome());
            }
        });
		cMedida.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Venda_Item, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Venda_Item, String> param) {
                return new ReadOnlyStringWrapper(param.getValue().getProduto().getUnidadeMedida().getCod());
            }
        });
		cTotal.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Venda_Item, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Venda_Item, String> param) {
				return new ReadOnlyStringWrapper("R$ " + (param.getValue().getQtd_item() * param.getValue().getProduto().getValor_un()));
			}
		});
		
		GenericTableButton.initButtons(cRemover, 15, TRASH_SOLID, "svg-red",
				(Venda_Item compraItem, ActionEvent event) -> {
					try {
						Alert ConfirmaRemover = new Alert(AlertType.CONFIRMATION);

						ConfirmaRemover.setTitle("Remover Item");
						ConfirmaRemover.setHeaderText("Deseja realmente remover o item selecionado?");

						Optional<ButtonType> result = ConfirmaRemover.showAndWait();
						if (result.isPresent() && result.get() == ButtonType.OK) {
							Venda vendaEscolhida = new ControlVenda()
									.Carregar(Integer.parseInt(txtCodVenda.getText()));

							if (venda != null && compraItem != null) {
								if (new ControlVendaItens().RemoverItem(vendaEscolhida, compraItem) == 1) {
									this.CarregarVenda(vendaEscolhida);
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
		Callback<TableColumn<Venda_Item, Void>, TableCell<Venda_Item, Void>> cellQtd = new Callback<TableColumn<Venda_Item, Void>, TableCell<Venda_Item, Void>>() {
            @Override
            public TableCell<Venda_Item, Void> call(final TableColumn<Venda_Item, Void> param) {
                final TableCell<Venda_Item, Void> cell = new TableCell<Venda_Item, Void>() {
                	
                	                	
                	private final Button btn = new Button("Alterar");{
                        btn.setOnAction((ActionEvent event) -> {
							try {
								TextInputDialog textDialog = new TextInputDialog();
								textDialog.setTitle("Alterar quantidade");
								textDialog.setHeaderText("Alterar quantidade do proximo produto");
								textDialog.setContentText("Informe uma quantidade: ");

								Optional<String> resultado = textDialog.showAndWait();
								String quantidadeString = resultado.map(Object::toString).orElse(null);

								Double qtd = Double.parseDouble(quantidadeString);
								atualizarQtd(getTableView().getItems().get(getIndex()), qtd);
								CarregarVenda(VendaPrivate);
								
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
	
	private void atualizarQtd(Venda_Item vendaItem, Double qtd) {
		try {
			vendaItem.setQtd_item(qtd);
    		if(new ControlVendaItens().AlterarQuantidadeItem(VendaPrivate, vendaItem) != 1) {
				throw new Exception("Não foi possivel inserir o item na venda");
    		}
		} catch (Exception e) {
			e.printStackTrace();
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
			
			txtQtdProxProduto.setText(QtdProximoProduto + "");

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);

			alert.setTitle("Atenção");
			alert.setHeaderText(e.getMessage());

			alert.showAndWait();
		}
	}
}
