package views.controllers.funcionario;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.sun.istack.internal.Nullable;

import dao.FuncionarioDAO;
import entitys.Acesso;
import entitys.Funcionario;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.Formatacao;

public class CadFuncionarioController implements Initializable {

	private static Stage CadFuncionario;

	private static FuncionarioDAO dao = new FuncionarioDAO();

	public static Funcionario funcionarioStatic;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private JFXTextField txtNome;

	@FXML
	private JFXTextField txtDocumento;

	@FXML
	private JFXTextField txtTelefone;

	@FXML
	private JFXTextField txtEmail;

	@FXML
	private JFXTextField txtEndereco;

	@FXML
	private JFXTextField txtSalario;

	@FXML
	private JFXComboBox<Acesso> cbxAcesso;

	@FXML
	private DatePicker dataPickerContratacao;

	@FXML
	private PasswordField firstPassword;

	@FXML
	private PasswordField confirnPassword;

	@FXML
	private JFXButton btnGravar;

	@FXML
	private JFXButton btnLimpar;

	@FXML
	private JFXButton btnVoltar;

	@FXML
	private JFXButton btnExcluir;

	@FXML
	private JFXButton btnEditar;

	@FXML
	private JFXTextField txtCod;

	@FXML
	private JFXButton btnDemitir;

	@FXML
	private Label lblInsiraSenha;

	@FXML
	private Label lblConfirmSenha;

	@FXML
	void btnDemitir_Action(ActionEvent event) {
		try {
			dao.editarDemissao(funcionarioStatic.getCod());

			Alert alert = new Alert(AlertType.CONFIRMATION, "Funcionario demitido com sucesso", ButtonType.OK);
			alert.setHeaderText("Funcionario demitido!!");
			alert.showAndWait();
			fechar();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.setHeaderText("Atenção");
			alert.showAndWait();
		}
	}

	@FXML
	void btnEditar_Action(ActionEvent event) {
		try {
			conferirCampos();

			if (!funcionarioStatic.getDocumento().equals(txtDocumento.getText().replaceAll("[^0-9]+", "")))
				if (dao.verificaRG(txtDocumento.getText().replaceAll("[^0-9]+", "")))
					throw new MoreThanOneException("Rg existente");

			if(verificaSenha(firstPassword.getText(), confirnPassword.getText(), "Insira uma senha adequada")) {
				dao.editar(new Funcionario(dataPickerContratacao.getValue(), LocalDate.of(2022, 3, 10),
						Double.parseDouble(txtSalario.getText()), null, cbxAcesso.getValue().getCod(),
						funcionarioStatic.getCod(), txtDocumento.getText(), txtTelefone.getText(), txtNome.getText(),
						txtEndereco.getText(), txtEmail.getText()));
			} else {
				dao.editar(new Funcionario(dataPickerContratacao.getValue(), LocalDate.of(2022, 3, 10),
						Double.parseDouble(txtSalario.getText()), firstPassword.getText(), cbxAcesso.getValue().getCod(),
						funcionarioStatic.getCod(), txtDocumento.getText(), txtTelefone.getText(), txtNome.getText(),
						txtEndereco.getText(), txtEmail.getText()));
			}

			Alert alert = new Alert(AlertType.CONFIRMATION, "Funcionario editado com sucesso", ButtonType.OK);
			alert.setHeaderText("Funcionario editado!!");
			alert.showAndWait();
			fechar();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING, e.getMessage(), ButtonType.OK);
			alert.setHeaderText("Atenção");
			alert.showAndWait();
		}
	}

	@FXML
	void btnExcluir_Action(ActionEvent event) {
		try {
			dao.deletar(funcionarioStatic.getCod());

			Alert alert = new Alert(AlertType.CONFIRMATION, "Funcionario excluído com sucesso", ButtonType.OK);
			alert.setHeaderText("Funcionario excluído!!");
			alert.showAndWait();
			fechar();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.setHeaderText("Atenção");
			alert.showAndWait();
		}
	}

	public Stage getCadFuncionario() {
		try {
			Stage primaryStage = new Stage();
			AnchorPane root = FXMLLoader.load(getClass().getResource("/views/funcionario/CadFuncionario.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			CadFuncionario = primaryStage;
			return CadFuncionario;
		} catch (Exception e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.showAndWait();
		}
		throw new RuntimeException("Erro ao carregar tela CadCliente");
	}

	@FXML
	void btnGravar_Action(ActionEvent event) {
		try {
			conferirCampos();

			if (dao.verificaRG(txtDocumento.getText().replaceAll("[^0-9]+", "")))
				throw new MoreThanOneException("Rg existente");

			dao.inserir(new Funcionario(dataPickerContratacao.getValue(), LocalDate.of(2022, 3, 10),
					Double.parseDouble(txtSalario.getText()), firstPassword.getText(), cbxAcesso.getValue().getCod(),
					txtDocumento.getText(), txtTelefone.getText(), txtNome.getText(), txtEndereco.getText(),
					txtEmail.getText()));

			Alert alert = new Alert(AlertType.CONFIRMATION, "Funcionario cadastrado com sucesso", ButtonType.OK);
			alert.setHeaderText("Funcionario cadastrado!!");
			alert.showAndWait();
			fechar();
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
		verificaDocumento(txtDocumento.getText(), "Insira um documento adequado");
		verificaNumero(txtTelefone.getText(), "Insira um Telefone adequado");
		verificaEmail(txtEmail.getText(), "Insira um Email adequado");
		passou(txtEndereco.getText(), "Insira um Endereço");
		verificaSalario(txtSalario.getText(), "Insira um salário adequado");
		verificaSenha(firstPassword.getText(), confirnPassword.getText(), "Insira uma senha adequada");
		verificaAcesso("Selecione um cargo");
	}

	private Boolean verificaAcesso(String msg) {
		if(cbxAcesso.getValue() != null) {
			return true;
		} else {
			throw new CampoVazioException(msg);
		}
	}
	
	private void verificaSalario(String texto, String msg) {
		if (txtSalario.getText().equals("") || txtSalario.getText() == null) {
			throw new CampoVazioException(msg);
		}
		try {
			Double.parseDouble(txtSalario.getText());
		} catch (Exception e) {
			throw new NumberFormatException("Insira apenas numeros no salário");
		}
	}

	private Boolean verificaSenha(String senha1, String senha2, String msg) {
		if (senha1.equals("") && senha2.equals("")) {
			return true;
		} else {
			if (senha1.equals("") || senha1 == null || senha2.equals("") || senha2 == null) {
				throw new CampoVazioException(msg);
			}
			if (!senha1.equals(senha2))
				throw new CampoVazioException("Senhas não batem");
			return false;
		}
	}

	private Boolean verificaEmail(String texto, String msg) {
		if (texto.equals("") || texto == null)
			throw new CampoVazioException(msg);
		if (!texto.matches(".*@.*") && !texto.matches(".*.com.*"))
			throw new TextoInvalidoException("Senhas não estão iguais");
		return true;
	}

	private Boolean verificaDocumento(String texto, String msg) {
		if (texto.equals("") || texto == null)
			throw new CampoVazioException(msg);
		texto = texto.replaceAll("[^0-9]+", "");
		if (texto.length() == 11 || texto.length() == 14)
			return true;

		throw new TextoInvalidoException(msg);
	}

	private Boolean verificaNumero(@Nullable String texto, String msg) {
		try {
			texto = texto.replaceAll("[^0-9]+", "");
			if (texto.length() > 11 || texto.length() < 8)
				throw new TextoInvalidoException(msg);
			if (texto.length() > 9 && texto.length() <= 11)
				texto = texto.substring(2, texto.length());
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
		dataPickerContratacao.setValue(LocalDate.now());
		txtEndereco.setText("");
		txtSalario.setText("");
		firstPassword.setText("");
		confirnPassword.setText("");
		cbxAcesso.getSelectionModel().select(null);
	}

	@FXML
	void btnVoltar_Action(ActionEvent event) {
		fechar();
	}

	private void fechar() {
		new PesquisaFuncionarioController().getPesquisaFuncionario().show();
		funcionarioStatic = null;
		CadFuncionario.close();
	}

	private void paraEditarFuncionario() {
		txtCod.setText(funcionarioStatic.getCod() + "");
		txtNome.setText(funcionarioStatic.getNome());
		txtDocumento.setText(Formatacao.formatarDocumento(funcionarioStatic.getDocumento()));
		txtTelefone.setText(Formatacao.formatarTelefone(funcionarioStatic.getTelefone() + ""));
		txtEmail.setText(funcionarioStatic.getEmail());
		txtEndereco.setText(funcionarioStatic.getEndereco());
		dataPickerContratacao.setValue(funcionarioStatic.getData_contratacao());
		txtSalario.setText(funcionarioStatic.getSalario() + "");
		cbxAcesso.setValue(Acesso.acharAcesso(funcionarioStatic.getCod_acesso()));

		btnGravar.setVisible(false);
		btnEditar.setVisible(true);
		btnExcluir.setVisible(true);
		btnDemitir.setVisible(true);
	}

	private void paraGravarFuncionario() {
		dataPickerContratacao.setValue(LocalDate.now());
		btnGravar.setVisible(true);
		btnEditar.setVisible(false);
		btnExcluir.setVisible(false);
		btnDemitir.setVisible(false);
	}

	private void preencherCBX() {
		cbxAcesso.getItems().addAll(Acesso.listaAcessos());
	}

	@FXML
	void initialize() {
		assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'CadFuncionario.fxml'.";
		assert txtDocumento != null : "fx:id=\"txtDocumento\" was not injected: check your FXML file 'CadFuncionario.fxml'.";
		assert txtTelefone != null : "fx:id=\"txtTelefone\" was not injected: check your FXML file 'CadFuncionario.fxml'.";
		assert txtEmail != null : "fx:id=\"txtEmail\" was not injected: check your FXML file 'CadFuncionario.fxml'.";
		assert txtEndereco != null : "fx:id=\"txtEndereco\" was not injected: check your FXML file 'CadFuncionario.fxml'.";
		assert txtSalario != null : "fx:id=\"txtSalario\" was not injected: check your FXML file 'CadFuncionario.fxml'.";
		assert cbxAcesso != null : "fx:id=\"cbxAcesso\" was not injected: check your FXML file 'CadFuncionario.fxml'.";
		assert dataPickerContratacao != null : "fx:id=\"dataPickerContratacao\" was not injected: check your FXML file 'CadFuncionario.fxml'.";
		assert firstPassword != null : "fx:id=\"firstPassword\" was not injected: check your FXML file 'CadFuncionario.fxml'.";
		assert confirnPassword != null : "fx:id=\"confirnPassword\" was not injected: check your FXML file 'CadFuncionario.fxml'.";
		assert btnGravar != null : "fx:id=\"btnGravar\" was not injected: check your FXML file 'CadFuncionario.fxml'.";
		assert btnLimpar != null : "fx:id=\"btnLimpar\" was not injected: check your FXML file 'CadFuncionario.fxml'.";
		assert btnVoltar != null : "fx:id=\"btnVoltar\" was not injected: check your FXML file 'CadFuncionario.fxml'.";
		assert btnExcluir != null : "fx:id=\"btnExcluir\" was not injected: check your FXML file 'CadFuncionario.fxml'.";
		assert btnEditar != null : "fx:id=\"btnEditar\" was not injected: check your FXML file 'CadFuncionario.fxml'.";
		assert txtCod != null : "fx:id=\"txtCod\" was not injected: check your FXML file 'CadFuncionario.fxml'.";
		assert btnDemitir != null : "fx:id=\"btnDemitir\" was not injected: check your FXML file 'CadFuncionario.fxml'.";

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (funcionarioStatic != null) {
			paraEditarFuncionario();
		} else {
			paraGravarFuncionario();
		}
		preencherCBX();
	}
}
