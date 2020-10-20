package utils;

public class Formatacao {

	public static void main(String[] args) {
		System.out.println(formatarDocumento("09574303969"));
	}
	
	public static String formatarDocumento(String cpf_RG) {
		String cpfRGFormatado = "";

		if (cpf_RG.length() == 11) {
			for (int i = 0; i < cpf_RG.length(); i++) {
				cpfRGFormatado += cpf_RG.charAt(i);
				switch (i) {
				case 2:
				case 5:
					cpfRGFormatado += ".";
					break;
				case 8:
					cpfRGFormatado += "-";
					break;
				default:
					break;
				}
			}
		} else if (cpf_RG.length() == 14) {
			for (int i = 0; i < cpf_RG.length(); i++) {
				cpfRGFormatado += cpf_RG.charAt(i);
				switch (i) {
				case 1:
				case 4:
					cpfRGFormatado += ".";
					break;
				case 7:
					cpfRGFormatado += "/";
					break;
				case 11:
					cpfRGFormatado += "-";
					break;
				default:
					break;
				}
			}
		} else {
			throw new RuntimeException();
		}

		return cpfRGFormatado;
	}

}
