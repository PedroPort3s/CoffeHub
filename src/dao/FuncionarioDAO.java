package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.ConexaoMySql;

import dao.interfaces.IFuncionarioDAO;
import entitys.Funcionario;
import entitys.Pessoa;



public class FuncionarioDAO implements IFuncionarioDAO {

	@Override
	public void inserir(Funcionario obj) {
		
		try {
			
			Connection connection = ConexaoMySql.getInstance().getConnection();
			String sql = "INSERT INTO funcionario() VALUES (?, ?, ?)";
			
			PreparedStatement statement= connection.prepareStatement(sql);
			
			
			
			
			/*******
			statement.setString(1, obj.getDescricao());
			statement.setDouble(2, obj.getValor_un());
			statement.setInt(3, obj.getCategoria().getCod());
			statement.execute();
			connection.close();
			********/
			
			
			
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deletar(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editar(Funcionario obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Funcionario> buscarId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Funcionario> listar() {
		// TODO Auto-generated method stub
		return null;
	}

}
