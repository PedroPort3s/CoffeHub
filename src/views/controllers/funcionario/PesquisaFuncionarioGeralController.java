package views.controllers.funcionario;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import dao.FuncionarioDAO;
import entitys.Funcionario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PesquisaFuncionarioGeralController {

	private static Stage pesquisaFuncionario;

	public static String cadPesqCompra;

	private FuncionarioDAO dao = new FuncionarioDAO();

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private JFXButton btnPesquisar;

	@FXML
	private JFXButton btnVoltar;

	@FXML
	private JFXTextField txtNomePesquisa;

	@FXML
	private JFXListView<Funcionario> lvFuncionarios;

	@FXML
	private JFXTextField txtCodPesquisa;

	@FXML
	void btnVoltar_Action(ActionEvent event) {
		//TODO: voltar para onde? depois que chegar nessa tela?
	}

	public Stage getPesquisaFuncionarioGeral() {
		if (pesquisaFuncionario == null) {
			try {
				Stage primaryStage = new Stage();
				AnchorPane root = (AnchorPane) FXMLLoader
						.load(getClass().getResource("/views/pesquisa/PesquisaFuncionarioGeral.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.initStyle(StageStyle.TRANSPARENT);
				pesquisaFuncionario = primaryStage;
			} catch (NullPointerException e) {
				Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
				alert.showAndWait();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return pesquisaFuncionario;
	}

	@FXML
	void btnPesquisar_Action(ActionEvent event) {
		listarFuncionarios();
	}

	@FXML
	void lvFuncionario_MouseClicked(MouseEvent event) {
		//TODO: quando selecionar o carinha, vai fazer o que?
	}

	private void listarFuncionarios() {
		List<Funcionario> lstFuncionario;
		lvFuncionarios.getItems().clear();

		try {
			lstFuncionario = dao.listar();
			if (lstFuncionario != null) {
				if (txtCodPesquisa != null && conferirNumero(txtCodPesquisa.getText(), "Insira um número")) {
					List<Funcionario> listaCod = new ArrayList<Funcionario>();
					for (Funcionario forn : lstFuncionario) {
						if ((forn.getCod() == Integer.parseInt(txtCodPesquisa.getText()))) {
							listaCod.add(forn);
						}
					}
					lstFuncionario = listaCod;
				}
				if (txtNomePesquisa != null) {
					List<Funcionario> listaNome = new ArrayList<Funcionario>();
					for (Funcionario forn : lstFuncionario) {
						if (forn.getNome().matches(".*" + txtNomePesquisa.getText() + ".*")) {
							listaNome.add(forn);
						}
					}
					lstFuncionario = listaNome;
				}
				lstFuncionario.forEach(f -> lvFuncionarios.getItems().add(f));
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.showAndWait();
		}
	}

	private Boolean conferirNumero(String texto, String msg) {
		try {
			if (!texto.equals("")) {
				Integer.parseInt(texto);
			} else if (texto.equals("")) {
				return false;
			}
		} catch (Exception e) {
			throw new NumberFormatException(msg);
		}
		return true;
	}

	@FXML
	void initialize() {
		assert txtCodPesquisa != null
				: "fx:id=\"txtCodPesquisa\" was not injected: check your FXML file 'PesquisaFuncionario.fxml'.";
		assert txtNomePesquisa != null
				: "fx:id=\"txtNomePesquisa\" was not injected: check your FXML file 'PesquisaFuncionario.fxml'.";
		assert btnPesquisar != null
				: "fx:id=\"btnPesquisar\" was not injected: check your FXML file 'PesquisaFuncionario.fxml'.";
		assert btnVoltar != null
				: "fx:id=\"btnVoltar\" was not injected: check your FXML file 'PesquisaFuncionario.fxml'.";
		assert lvFuncionarios != null
				: "fx:id=\"lvFuncionarioes\" was not injected: check your FXML file 'PesquisaFuncionario.fxml'.";

		listarFuncionarios();
	}
}
