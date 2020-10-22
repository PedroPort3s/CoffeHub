package views.controllers.fornecedor;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.sun.istack.internal.Nullable;

import dao.FornecedorDAO;
import entitys.Fornecedor;
import exceptions.CampoVazioException;
import exceptions.MoreThanOneException;
import exceptions.TextoInvalidoException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.Formatacao;

public class CadFornecedorController implements Initializable {

	private static Stage CadFornecedor;

	private static FornecedorDAO dao = new FornecedorDAO();

	public static Fornecedor fornecedorStatic;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private JFXButton btnGravar;

	@FXML
	private JFXButton btnVoltar;

	@FXML
	private JFXTextField txtCod;

	@FXML
	private JFXTextField txtNome;

	@FXML
	private JFXButton btnLimpar;

	@FXML
	private JFXTextField txtTelefone;

	@FXML
	private JFXTextField txtEmail;

	@FXML
	private JFXTextField txtEndereco;

	@FXML
	private JFXButton btnExcluir;

	@FXML
	private JFXButton btnEditar;

	@FXML
	private JFXTextField txtDocumento;

	@FXML
	private DatePicker dataPickerContrato;

	public Stage getCadFornecedor() {
		try {
			Stage primaryStage = new Stage();
			AnchorPane root = FXMLLoader.load(getClass().getResource("/views/fornecedor/CadFornecedor.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			CadFornecedor = primaryStage;
			return CadFornecedor;
		} catch (Exception e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.showAndWait();
		}
		throw new RuntimeException("Erro ao carregar tela CadFornecedor");
	}

	@FXML
	void btnEditar_Action(ActionEvent event) {
		try {
			conferirCampos();

			if (!fornecedorStatic.getDocumento().equals(txtDocumento.getText().replaceAll("[^0-9]+", "")))				
				if (dao.verificaRG(txtDocumento.getText().replaceAll("[^0-9]+", "")))
					throw new MoreThanOneException("Rg existente");

			dao.editar(new Fornecedor(dataPickerContrato.getValue(), fornecedorStatic.getCod(), txtDocumento.getText(),
					txtTelefone.getText(), txtNome.getText(), txtEndereco.getText(), txtEmail.getText()));

			Alert alert = new Alert(AlertType.CONFIRMATION, "Fornecedor editado com sucesso", ButtonType.OK);
			alert.setHeaderText("Fornecedor editado!!");
			alert.showAndWait();
			close();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING, e.getMessage(), ButtonType.OK);
			alert.setHeaderText("Atenção");
			alert.showAndWait();
		}
	}

	@FXML
	void btnExcluir_Action(ActionEvent event) {
		try {
			dao.deletar(fornecedorStatic.getCod());

			Alert alert = new Alert(AlertType.CONFIRMATION, "Fornecedor excluído com sucesso", ButtonType.OK);
			alert.setHeaderText("Fornecedor excluído!!");
			alert.showAndWait();
			close();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.setHeaderText("Atenção");
			alert.showAndWait();
		}
	}

	@FXML
	void btnGravar_Action(ActionEvent event) {
		try {
			conferirCampos();

			if (dao.verificaRG(txtDocumento.getText().replaceAll("[^0-9]+", "")))
				throw new MoreThanOneException("Rg existente");
			
			dao.inserir(new Fornecedor(dataPickerContrato.getValue(), txtDocumento.getText(), txtTelefone.getText(),
					txtNome.getText(), txtEndereco.getText(), txtEmail.getText()));

			Alert alert = new Alert(AlertType.CONFIRMATION, "Fornecedor cadastrado com sucesso", ButtonType.OK);
			alert.setHeaderText("Fornecedor cadastrado!!");
			alert.showAndWait();
			close();
		} catch (CampoVazioException e) {
			Alert alert = new Alert(AlertType.WARNING, e.getMessage(), ButtonType.OK);
			alert.setHeaderText("Atenção");
			alert.showAndWait();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING, e.getMessage(), ButtonType.OK);
			alert.setHeaderText("Atenção");
			alert.showAndWait();
		}
	}

	private void conferirCampos() {
		passou(txtNome.getText(), "Insira um Nome");
		verificaDocumento(txtDocumento.getText(), "Insira um documento");
		verificaNumero(txtTelefone.getText(), "Insira um Telefone");
		verificaEmail(txtEmail.getText(), "Insira um Email");
		passou(txtEndereco.getText(), "Insira um Endereço");
	}

	private Boolean verificaEmail(String texto, String msg) {
		if (texto.equals("") || texto == null)
			throw new CampoVazioException(msg);
		if (!texto.matches(".*@.*") && !texto.matches(".*.com.*"))
			throw new TextoInvalidoException(msg);
		return true;
	}

	private Boolean verificaDocumento(String texto, String msg) {
		if (texto.equals("") || texto == null)
			throw new CampoVazioException(msg);
		texto = texto.replaceAll("[^0-9]+", "");
		if (texto.length() == 11 || texto.length() == 14) return true;

		throw new TextoInvalidoException(msg);
	}

	private Boolean verificaNumero(@Nullable String texto, String msg) {
		try {
			texto = texto.replaceAll("[^0-9]+", "");
			if(texto.length() > 11 || texto.length() < 8) throw new TextoInvalidoException(msg);
			if(texto.length() > 9 && texto.length() <= 11 ) texto = texto.substring(2, texto.length());

			Integer.parseInt(texto);
			
		} catch (Exception e) {
			throw new NumberFormatException(msg);
		}
		return true;
	}

	private Boolean passou(@Nullable String texto, String msg) {
		if (texto.equals("") || texto == null)
			throw new CampoVazioException(msg);
		return true;
	}

	@FXML
	void btnLimpar_Action(ActionEvent event) {
		txtNome.setText("");
		txtTelefone.setText("");
		txtDocumento.setText("");
		txtEmail.setText("");
		dataPickerContrato.setValue(LocalDate.now());
		txtEndereco.setText("");
	}

	@FXML
	void btnVoltar_Action(ActionEvent event) {
		close();
	}

	private void paraEditarFornecedor() {
		txtCod.setText(fornecedorStatic.getCod() + "");
		txtNome.setText(fornecedorStatic.getNome());
		txtDocumento.setText(fornecedorStatic.getDocumento());
		txtTelefone.setText(fornecedorStatic.getTelefone() + "");
		txtEmail.setText(fornecedorStatic.getEmail());
		dataPickerContrato.setValue(fornecedorStatic.getData_contrato());
		txtEndereco.setText(fornecedorStatic.getEndereco());

		btnGravar.setVisible(false);
		btnEditar.setVisible(true);
		btnExcluir.setVisible(true);
	}

	private void paraGravarFornecedor() {
		dataPickerContrato.setValue(LocalDate.now());
		btnGravar.setVisible(true);
		btnEditar.setVisible(false);
		btnExcluir.setVisible(false);
	}

	private void close() {
		new PesquisaFornecedorController().getPesquisaFornecedor().show();
		fornecedorStatic = null;
		CadFornecedor.close();
	}

	@FXML
	public void initialize() {
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'CadFornecedor.fxml'.";
        assert txtDocumento != null : "fx:id=\"txtDocumento\" was not injected: check your FXML file 'CadFornecedor.fxml'.";
        assert txtTelefone != null : "fx:id=\"txtTelefone\" was not injected: check your FXML file 'CadFornecedor.fxml'.";
        assert txtEmail != null : "fx:id=\"txtEmail\" was not injected: check your FXML file 'CadFornecedor.fxml'.";
        assert dataPickerContrato != null : "fx:id=\"dataPickerContrato\" was not injected: check your FXML file 'CadFornecedor.fxml'.";
        assert txtEndereco != null : "fx:id=\"txtEndereco\" was not injected: check your FXML file 'CadFornecedor.fxml'.";
        assert btnGravar != null : "fx:id=\"btnGravar\" was not injected: check your FXML file 'CadFornecedor.fxml'.";
        assert btnLimpar != null : "fx:id=\"btnLimpar\" was not injected: check your FXML file 'CadFornecedor.fxml'.";
        assert btnVoltar != null : "fx:id=\"btnVoltar\" was not injected: check your FXML file 'CadFornecedor.fxml'.";
        assert btnExcluir != null : "fx:id=\"btnExcluir\" was not injected: check your FXML file 'CadFornecedor.fxml'.";
        assert btnEditar != null : "fx:id=\"btnEditar\" was not injected: check your FXML file 'CadFornecedor.fxml'.";
        assert txtCod != null : "fx:id=\"txtCod\" was not injected: check your FXML file 'CadFornecedor.fxml'.";

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (fornecedorStatic != null) {
			paraEditarFornecedor();
		} else {
			paraGravarFornecedor();
		}
	}
}
