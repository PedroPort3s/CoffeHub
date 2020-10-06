package views.controllers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dao.ClienteDAO;
import entitys.Cliente;

public class initial {

	public enum Teste{teste1,teste2,teste3}
	
	public static void main(String[] args) {

		ClienteDAO dao = new ClienteDAO();
		
		dao.inserir(new Cliente(new Date(), "14.367.998-5", "984941246", "Vitor Hainosz Cardoso", "rua jose michna filho", "vitorhainosz2@gmail.com"));
//		dao.deletar("095.743.039-69");

//		Calendar cal = Calendar.getInstance();
//		cal.set(Calendar.YEAR, 2001);
//		cal.set(Calendar.MONTH, 156);
//		cal.set(Calendar.DATE, 19);
//		System.out.println(cal.getTime());
		
//		dao.editar(new Cliente(cal.getTime(), "095.743.039-69", "14.367.970-5", "999999999", "Vitor Hainosz", "rua jose michna Neto HA", "vitorhainosz@gmail.com"));
//		List<Cliente> lista = dao.listar();
//		lista.forEach(item -> System.out.println(item));
	}

}


















