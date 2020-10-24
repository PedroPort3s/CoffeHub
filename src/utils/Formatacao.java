package utils;

import java.text.ParseException;

import javax.swing.text.MaskFormatter;

public class Formatacao {

	public static void main(String[] args) {
		try {
			MaskFormatter mask = new MaskFormatter("###.###.###-##");
			mask.setValueContainsLiteralCharacters(false);
			System.out.println("CNPJ : " + mask.valueToString("09574303969"));
		} catch (ParseException ex) {
			System.out.println("deu ruim");
		}
	}

	public static String formatarDocumento(String cpf_RG) {
		String cpfRGFormatado = "";
		try {
			if (cpf_RG.length() == 11) {
				MaskFormatter mask = new MaskFormatter("###.###.###-##");
				mask.setValueContainsLiteralCharacters(false);
				cpfRGFormatado = mask.valueToString(cpf_RG);
			} else if (cpf_RG.length() == 14) {
				MaskFormatter mask = new MaskFormatter("##.###.###/####-##");
				mask.setValueContainsLiteralCharacters(false);
				cpfRGFormatado = mask.valueToString(cpf_RG);
			} else {
				throw new RuntimeException("Erro na hora de formatar o documento");
			}
			return cpfRGFormatado;
		} catch (Exception e) {
			throw new RuntimeException("Erro na hora de formatar o documento");
		}
	}

	public static String formatarTelefone(String telefone) {
		String telefoneFormatado = "";
		try {
			if (telefone.length() == 8) {
				MaskFormatter mask = new MaskFormatter("####-####");
				mask.setValueContainsLiteralCharacters(false);
				telefoneFormatado = mask.valueToString(telefone);
			} else if (telefone.length() == 9) {
				MaskFormatter mask = new MaskFormatter("# ####-####");
				mask.setValueContainsLiteralCharacters(false);
				telefoneFormatado = mask.valueToString(telefone);
			} else if (telefone.length() == 10) {
				MaskFormatter mask = new MaskFormatter("(##) ####-####");
				mask.setValueContainsLiteralCharacters(false);
				telefoneFormatado = mask.valueToString(telefone);
			} else if (telefone.length() == 11) {
				MaskFormatter mask = new MaskFormatter("(##) # ####-####");
				mask.setValueContainsLiteralCharacters(false);
				telefoneFormatado = mask.valueToString(telefone);
			} else {
				throw new RuntimeException("Erro na hora de formatar o telefone");
			}
			
			return telefoneFormatado;
		} catch (Exception e) {
			throw new RuntimeException("Erro na hora de formatar o telefone");
		}
	}
}
