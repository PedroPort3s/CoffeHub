package views.controllers;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import control.produto.ControlUnidadeMedida;
import entitys.UnidadeMedida;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CadUnidadeMedidaController implements Initializable {

	private static Stage CadUnidadeMedida;

	public static UnidadeMedida UnidadeEstatica = null;

	@FXML
	private JFXTextField txtCodUnidade;

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

	@FXML
	private RadioButton optSim;

	@FXML
	private ToggleGroup permite;

	@FXML
	private RadioButton optNao;

	@FXML
	private JFXTextField txtIdUnidade;

	public Stage getCadUnidadeMedida() {
		if (CadUnidadeMedida == null) {
			try {
				Stage primaryStage = new Stage();
				AnchorPane root = (AnchorPane) FXMLLoader
						.load(getClass().getResource("/views/unidadeMedida/CadUnidadeMedida.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.initStyle(StageStyle.TRANSPARENT);
				CadUnidadeMedida = primaryStage;
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
				alert.showAndWait();
			}
		}
		return CadUnidadeMedida;
	}

	@FXML
	void btnEditar_Action(ActionEvent event) {
		try {
			UnidadeMedida unidade = new UnidadeMedida();
			unidade.setCod(txtCodUnidade.getText());
			unidade.setNome(txtDescricao.getText());

			if (optNao.isSelected())
				unidade.setPermiteFracionado(false);
			else if(optSim.isSelected())
				unidade.setPermiteFracionado(true);
			else
				throw new Exception("Informe se a unidade permite fracionamento");
	
			new ControlUnidadeMedida().Editar(unidade);

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);

			alert.setTitle("Atenção");
			alert.setHeaderText(e.getMessage());

			alert.showAndWait();
		}
	}

	@FXML
	void btnExcluir_Action(ActionEvent event) {
		try {
			Alert ConfirmaExcluir = new Alert(AlertType.CONFIRMATION);

			ConfirmaExcluir.setTitle("Exclusão");
			ConfirmaExcluir.setHeaderText("Deseja realmente excluir a unidade de medida selecionada?");

			Optional<ButtonType> result = ConfirmaExcluir.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK) {
				if (Integer.parseInt(txtIdUnidade.getText()) > 0) {
					if (new ControlUnidadeMedida().Deletar(Integer.parseInt(txtIdUnidade.getText())) == 1) {
						Limpar();
						Alert alert = new Alert(AlertType.INFORMATION);

						alert.setTitle("Sucesso");
						alert.setHeaderText("Unidade de medida deletada com sucesso");

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
	void btnGravar_Action(ActionEvent event) {
		try {
			UnidadeMedida unidade = new UnidadeMedida();
			unidade.setCod(txtCodUnidade.getText());
			unidade.setNome(txtDescricao.getText());

			if (optNao.isSelected())
				unidade.setPermiteFracionado(false);
			else if(optSim.isSelected())
				unidade.setPermiteFracionado(true);
			else
				throw new Exception("Informe se a unidade permite fracionamento");
	
			new ControlUnidadeMedida().Inserir(unidade);
			
		} catch (Exception e) {
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
		CadUnidadeMedida = null;
		CadUnidadeMedida.close();
		new HomeController().getHome().show();
	}

	private void Limpar() {
		txtCodUnidade.setText("");
		txtDescricao.setText("");
		optNao.setSelected(true);

		btnEditar.setVisible(false);
		btnExcluir.setVisible(false);
		btnGravar.setVisible(true);
	}

	private void CarregarUnidade(UnidadeMedida unidade) {
		try {
			if (unidade != null && !unidade.getCod().equals("")) {
				txtCodUnidade.setText(unidade.getCod());
				txtDescricao.setText(unidade.getNome());

				if (unidade.getPermiteFracionado() == true)
					optSim.setSelected(true);

				else
					optNao.setSelected(true);

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
			if (UnidadeEstatica != null && !UnidadeEstatica.getCod().equals("")) {
				CarregarUnidade(UnidadeEstatica);
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.showAndWait();
		}
	}

}
