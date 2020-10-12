package views.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.sun.istack.internal.Nullable;

import dao.ClienteDAO;
import entitys.Cliente;
import exceptions.CampoVazioException;
import exceptions.MoreThanOneException;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CadClienteController implements Initializable {

	
	private static Stage CadCliente;

	private static ClienteDAO dao = new ClienteDAO();

	public static Cliente clienteStatic;

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
	private DatePicker dataPickerNascimento;

	public Stage getCadCliente() {
		try {
			Stage primaryStage = new Stage();
			AnchorPane root = FXMLLoader.load(getClass().getResource("/views/cliente/CadCliente.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			CadCliente = primaryStage;
			return CadCliente;
		} catch (Exception e) {
			System.out.println("ueeeeeee");
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.showAndWait();
		}
		throw new RuntimeException("Erro ao carregar tela CadCliente");
	}

	@FXML
	void btnEditar_Action(ActionEvent event) {
		try {
			conferirCampos();
			
			if(!clienteStatic.getDocumento().equals(txtDocumento.getText())) 
				if(dao.verificaRG(txtDocumento.getText())) throw new MoreThanOneException("Rg existente");
			
			dao.editar(new Cliente(dataPickerNascimento.getValue(), clienteStatic.getCod(), txtDocumento.getText(),
					txtTelefone.getText(), txtNome.getText(), txtEndereco.getText(), txtEmail.getText()));
			
			Alert alert = new Alert(AlertType.CONFIRMATION, "Cliente editado com sucesso", ButtonType.OK);
			alert.setHeaderText("Cliente editado!!");
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
			dao.deletar(clienteStatic.getCod());
			
			Alert alert = new Alert(AlertType.CONFIRMATION, "Cliente excluído com sucesso", ButtonType.OK);
			alert.setHeaderText("Cliente excluído!!");
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
			
			if(dao.verificaRG(txtDocumento.getText())) throw new MoreThanOneException("Rg existente");
			
			dao.inserir(new Cliente(dataPickerNascimento.getValue(), txtDocumento.getText(), txtTelefone.getText(),
					txtNome.getText(), txtEndereco.getText(), txtEmail.getText()));
			
			Alert alert = new Alert(AlertType.CONFIRMATION, "Cliente cadastrado com sucesso", ButtonType.OK);
			alert.setHeaderText("Cliente cadastrado!!");
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
		passou(txtDocumento.getText(), "Insira um RG");
		passou(txtTelefone.getText(), "Insira um Telefone");
		passou(txtEmail.getText(), "Insira um Email");
		passou(txtEndereco.getText(), "Insira um Endereço");
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
		dataPickerNascimento.setValue(LocalDate.now());
		txtEndereco.setText("");
	}

	@FXML
	void btnVoltar_Action(ActionEvent event) {
		close();
	}

	private void paraEditarCliente() {
		txtCod.setText(clienteStatic.getCod() + "");
		txtNome.setText(clienteStatic.getNome());
		txtDocumento.setText(clienteStatic.getDocumento());
		txtTelefone.setText(clienteStatic.getTelefone());
		txtEmail.setText(clienteStatic.getEndereco());
		dataPickerNascimento.setValue(clienteStatic.getData_nascimento());
		txtEndereco.setText(clienteStatic.getEndereco());

		btnGravar.setVisible(false);
		btnEditar.setVisible(true);
		btnExcluir.setVisible(true);
	}

	private void paraGravarCliente() {
		dataPickerNascimento.setValue(LocalDate.now());
		btnGravar.setVisible(true);
		btnEditar.setVisible(false);
		btnExcluir.setVisible(false);
	}

	private void close() {
		new PesquisaClienteController().getPesquisaCliente().show();
		clienteStatic = null;
		CadCliente.close();
	}

	@FXML
	public void initialize() {
		assert btnGravar != null : "fx:id=\"btnGravar\" was not injected: check your FXML file 'CadCliente.fxml'.";
		assert btnVoltar != null : "fx:id=\"btnVoltar1\" was not injected: check your FXML file 'CadCliente.fxml'.";
		assert txtCod != null : "fx:id=\"txtCod\" was not injected: check your FXML file 'CadCliente.fxml'.";
		assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'CadCliente.fxml'.";
		assert btnLimpar != null : "fx:id=\"btnLimpar\" was not injected: check your FXML file 'CadCliente.fxml'.";
		assert txtTelefone != null : "fx:id=\"txtTelefone\" was not injected: check your FXML file 'CadCliente.fxml'.";
		assert txtEmail != null : "fx:id=\"txtEmail\" was not injected: check your FXML file 'CadCliente.fxml'.";
		assert txtEndereco != null : "fx:id=\"txtEndereco\" was not injected: check your FXML file 'CadCliente.fxml'.";
		assert btnExcluir != null : "fx:id=\"btnExcluir\" was not injected: check your FXML file 'CadCliente.fxml'.";
		assert btnEditar != null : "fx:id=\"btnEditar\" was not injected: check your FXML file 'CadCliente.fxml'.";
		assert txtDocumento != null : "fx:id=\"txtDocumento\" was not injected: check your FXML file 'CadCliente.fxml'.";
		assert dataPickerNascimento != null : "fx:id=\"dataPickerNascimento\" was not injected: check your FXML file 'CadCliente.fxml'.";
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (clienteStatic != null) {
			paraEditarCliente();
		} else {
			paraGravarCliente();
		}
	}
}
