package views.controllers;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import control.compra_venda.ControlCompra;
import dao.FornecedorDAO;
import entitys.Compra;
import entitys.Fornecedor;
import entitys.Funcionario;
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
import utils.Logado;
import utils.Verifica;
import views.controllers.fornecedor.PesquisaFornecedorGeralController;

public class PesquisaCompraController implements Initializable {

	private static Stage PesquisaCompra;

	public static Fornecedor fornecedorEstatico;

	@FXML
	private JFXButton btnPesquisar;

	@FXML
	private JFXButton btnVoltar;

	@FXML
	private JFXButton btnCadCompra;

	@FXML
	private JFXTextField txtFornecedor;

	@FXML
	private JFXTextField txtFuncionario;

	@FXML
	private JFXTextField txtCodCompra;

	@FXML
	private JFXButton btnBuscarFuncionario;

	@FXML
	private JFXButton btnLimparFuncionario;

	@FXML
	private JFXButton btnBuscarFornecedor;

	@FXML
	private JFXButton btnLimparFornecedor;

	@FXML
	private JFXListView<Compra> lvCompras;

	@FXML
	private JFXTextField txtCodFuncionario;

	@FXML
	private JFXTextField txtCodFornecedor;

	@FXML
	private JFXTextField txtStatus;

	@FXML
	private JFXTextField txtDataIni;

	@FXML
	private JFXTextField txtDataFinal;

	public Stage getPesquisaCompra() {
		if (PesquisaCompra == null) {
			try {
				Stage primaryStage = new Stage();
				AnchorPane root = (AnchorPane) FXMLLoader
						.load(getClass().getResource("/views/compraVenda/PesquisaCompra.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.initStyle(StageStyle.TRANSPARENT);
				PesquisaCompra = primaryStage;
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
				alert.showAndWait();
			}
		}
		return PesquisaCompra;
	}

	@FXML
	void btnBuscarFornecedor_Action(ActionEvent event) {
		PesquisaCompra.close();
		PesquisaCompra = null;
		PesquisaFornecedorGeralController.cadPesqCompra = "PESQUISACOMPRA";
		new PesquisaFornecedorGeralController().getPesquisaFornecedorGeral().show();
	}

	@FXML
	void btnBuscarFuncionario_Action(ActionEvent event) {

	}

	@FXML
	void btnCadCompra_Action(ActionEvent event) {
		PesquisaCompra.hide();
		PesquisaCompra = null;
		new CadCompraController().getCadCompra().show();
	}

	@FXML
	void btnLimparFornecedor_Action(ActionEvent event) {

	}

	@FXML
	void btnLimparFuncionario_Action(ActionEvent event) {

	}

	@FXML
	void btnPesquisar_Action(ActionEvent event) {
		try {
			this.ListarCompras();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.showAndWait();
		}
	}

	@FXML
	void btnVoltar_Action(ActionEvent event) {
		try {
			PesquisaCompra.hide();
			PesquisaCompra = null;
			fornecedorEstatico = null;
			new HomeController().getHome().show();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.showAndWait();
		}
	}

	@FXML
	void lvCompras_MouseClicked(MouseEvent event) {
		try {
			Compra compra = lvCompras.getSelectionModel().getSelectedItem();

			Fornecedor fornecedorCompra = new FornecedorDAO().buscarId(compra.getFornecedor().getCod());
			compra.setFornecedor(fornecedorCompra);

			if (compra != null) {
				CadCompraController.compraCarregada = compra;
				CadCompraController.FornecedorEstatico = fornecedorCompra;
				new CadCompraController().getCadCompra().show();
				PesquisaCompra.close();
				PesquisaCompra = null;
				fornecedorEstatico = null;
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);

			alert.setTitle("Atenção");
			alert.setHeaderText(e.getMessage());

			alert.showAndWait();
		}
	}

	@FXML
	void txtCliente_MouseClicked(MouseEvent event) {

	}

	@FXML
	void txtCodVenda_MouseClicked(MouseEvent event) {

	}

	@FXML
	void txtFuncionario_MouseClicked(MouseEvent event) {

	}

	@FXML
	void txtFornecedor_MouseClicked(MouseEvent event) {

	}

	void ListarCompras() throws Exception {
		List<Compra> lstCompras;
		lvCompras.getItems().clear();

		try {
			/*
			 * if (txtDataIni.getText().equals("") && txtDataFinal.getText().equals(""))
			 */
			String dataInit = txtDataIni.getText();
			Date dataIni = new SimpleDateFormat("dd/MM/yyyy").parse(dataInit);

			String dataFim = txtDataFinal.getText();
			Date dataFinal = new SimpleDateFormat("dd/MM/yyyy").parse(dataFim);

			int codFuncionario = Verifica.ehNumeroInt(txtCodFuncionario.getText()) == true
					? Integer.parseInt(txtCodFuncionario.getText())
					: 0;

			int codFornecedor = Verifica.ehNumeroInt(txtCodFornecedor.getText()) == true
					? Integer.parseInt(txtCodFornecedor.getText())
					: 0;

			lstCompras = new ControlCompra().Listar(dataIni, dataFinal, txtStatus.getText(), codFuncionario,
					codFornecedor);

			if (lstCompras != null)
				lstCompras.forEach(c -> lvCompras.getItems().add(c));

			/*
			 * else { throw new Exception("Informe datas válidas para a pesquisa"); }
			 */
		}

		catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.showAndWait();
		}
	}

	void ValidarFuncionarioLogado(Funcionario funcionario) {
		try {
			if (funcionario != null) {
				// vendedor/ balconista - poode fazer compras e vendas
				if (funcionario.getCod_acesso() == 2) {
					txtCodFuncionario.setText(funcionario.getCod() + "");
					txtFuncionario.setText(funcionario.getNome());

					btnBuscarFuncionario.setVisible(false);
					btnLimparFuncionario.setVisible(false);

					btnBuscarFornecedor.setVisible(false);
					btnLimparFornecedor.setVisible(false);

					this.ListarCompras();
				}

				// Estoquista, pode fazer compras, não pode vendas
				else if (funcionario.getCod_acesso() == 3) {
					txtCodFuncionario.setText(funcionario.getCod() + "");
					txtFuncionario.setText(funcionario.getNome());

					btnBuscarFuncionario.setVisible(false);
					btnLimparFuncionario.setVisible(false);

					btnBuscarFornecedor.setVisible(false);
					btnLimparFornecedor.setVisible(false);

					this.ListarCompras();
				}

			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);

			alert.setTitle("Atenção");
			alert.setHeaderText(e.getMessage());

			alert.showAndWait();
		}
	}

	void CarregarFornecedor(Fornecedor fornecedor) {
		try {
			if (fornecedor.getCod() > 0) {
				txtCodFornecedor.setText(fornecedor.getCod() + "");
				txtFornecedor.setText(fornecedor.getNome());

				fornecedorEstatico = null;
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
			ValidarFuncionarioLogado(Logado.Funcionario);

			if (fornecedorEstatico != null && fornecedorEstatico.getCod() > 0) {
				this.CarregarFornecedor(fornecedorEstatico);
			}

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.showAndWait();
		}
	}

}
