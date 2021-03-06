package views.controllers;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import control.compra_venda.ControlVenda;
import dao.ClienteDAO;
import entitys.Cliente;
import entitys.Funcionario;
import entitys.Venda;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.Logado;
import utils.Verifica;
import views.controllers.cliente.PesquisaClienteGeralController;

public class PesquisaVendaController implements Initializable {

	private static Stage PesquisaVenda;

	public static Cliente clienteEstatico = null;

	@FXML
	private JFXButton btnCadVenda;

	@FXML
	private JFXButton btnPesquisar;

	@FXML
	private JFXButton btnVoltar;

	@FXML
	private JFXTextField txtCliente;

	@FXML
	private JFXTextField txtFuncionario;

	@FXML
	private JFXTextField txtCodVenda;

	@FXML
	private JFXButton btnBuscarFuncionario;

	@FXML
	private JFXButton btnLimparFuncionario;

	@FXML
	private JFXButton btnBuscarCliente;

	@FXML
	private JFXButton btnLimparCliente;

	@FXML
	private JFXListView<Venda> lvVendas;

	@FXML
	private JFXTextField txtCodFuncionario;

	@FXML
	private JFXTextField txtCodCliente;

	@FXML
	private JFXTextField txtStatus;

	@FXML
	private JFXTextField txtDataIni;

	@FXML
	private JFXTextField txtDataFinal;

	public Stage getPesquisaVenda() {
		if (PesquisaVenda == null) {
			try {
				Stage primaryStage = new Stage();
				AnchorPane root = (AnchorPane) FXMLLoader
						.load(getClass().getResource("/views/compraVenda/PesquisaVenda.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.initStyle(StageStyle.TRANSPARENT);
				PesquisaVenda = primaryStage;
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
				alert.showAndWait();
			}
		}
		return PesquisaVenda;
	}

	@FXML
	void btnBuscarCliente_Action(ActionEvent event) {
		PesquisaVenda.close();
		PesquisaVenda = null;
		PesquisaClienteGeralController.cadPesqVenda = "PESQUISAVENDA";
		new PesquisaClienteGeralController().getPesquisaClienteGeral().show();
	}

	@FXML
	void btnBuscarFuncionario_Action(ActionEvent event) {

	}

	@FXML
	void btnLimparCliente_Action(ActionEvent event) {
		txtCodCliente.setText("");
		txtCliente.setText("");
	}

	@FXML
	void btnLimparFuncionario_Action(ActionEvent event) {
		txtCodFuncionario.setText("");
		txtFuncionario.setText("");
	}

	@FXML
	void btnPesquisar_Action(ActionEvent event) {
		this.ListarVendas();
	}

	@FXML
	void btnVoltar_Action(ActionEvent event) {
		PesquisaVenda.close();
		PesquisaVenda = null;
		new HomeController().getHome().show();
	}

	@FXML
	void btnCadVenda_Action(ActionEvent event) {
		PesquisaVenda.hide();
		PesquisaVenda = null;
		new CadVendaController().getCadVenda().show();
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
	void lvVendas_MouseClicked(MouseEvent event) {
		try {
			Venda venda = lvVendas.getSelectionModel().getSelectedItem();

			Cliente clienteCompra = new ClienteDAO().buscarId(venda.getCliente().getCod());
			venda.setCliente(clienteCompra);

			if (venda != null) {
				CadVendaController.VendaPrivate = venda;
				CadVendaController.ClienteEstatico = clienteCompra;
				new CadVendaController().getCadVenda().show();
				PesquisaVenda.close();
				PesquisaVenda = null;
				clienteEstatico = null;
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);

			alert.setTitle("Aten??o");
			alert.setHeaderText(e.getMessage());

			alert.showAndWait();
		}
	}

	void ListarVendas() {
		List<Venda> lstVendas;
		lvVendas.getItems().clear();

		try {
			String dataInit = txtDataIni.getText();
			Date dataIni = new SimpleDateFormat("dd/MM/yyyy").parse(dataInit);

			String dataFim = txtDataFinal.getText();
			Date dataFinal = new SimpleDateFormat("dd/MM/yyyy").parse(dataFim);
			
			int codFuncionario = Verifica.ehNumeroInt(txtCodFuncionario.getText()) == true
					? Integer.parseInt(txtCodFuncionario.getText())
					: 0;

			int codCliente = Verifica.ehNumeroInt(txtCodCliente.getText()) == true
					? Integer.parseInt(txtCodCliente.getText())
					: 0;
			
			lstVendas = new ControlVenda().Listar(dataIni, dataFinal, txtStatus.getText(), codFuncionario, codCliente);

			if (lstVendas != null)
				lstVendas.forEach(v -> lvVendas.getItems().add(v));

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

					btnBuscarCliente.setVisible(false);
					btnLimparCliente.setVisible(false);

					this.ListarVendas();
				}

				// Estoquista, pode fazer compras, n?o pode vendas
				else if (funcionario.getCod_acesso() == 3) {
					txtCodFuncionario.setText(funcionario.getCod() + "");
					txtFuncionario.setText(funcionario.getNome());

					btnBuscarFuncionario.setVisible(false);
					btnLimparFuncionario.setVisible(false);

					btnBuscarCliente.setVisible(false);
					btnLimparCliente.setVisible(false);
					btnCadVenda.setVisible(false);
					btnPesquisar.setVisible(false);
				}

			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);

			alert.setTitle("Aten??o");
			alert.setHeaderText(e.getMessage());

			alert.showAndWait();
		}
	}

	void CarregarCliente(Cliente cliente) {
		try {
			if (cliente.getCod() > 0) {
				txtCodCliente.setText(cliente.getCod() + "");
				txtCliente.setText(cliente.getNome());

				clienteEstatico = null;
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);

			alert.setTitle("Aten??o");
			alert.setHeaderText(e.getMessage());

			alert.showAndWait();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			this.ValidarFuncionarioLogado(Logado.Funcionario);

			if (clienteEstatico != null && clienteEstatico.getCod() > 0) {
				this.CarregarCliente(clienteEstatico);
			}

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.showAndWait();
		}
	}

}
