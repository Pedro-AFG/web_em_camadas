package control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.InputMismatchException;

public class ServidorValidadorCNPJ {

	private ServerSocket sckServidor;

	public ServidorValidadorCNPJ() throws IOException {
		this.sckServidor = new ServerSocket(4000);

		for (;;) {
			Socket sckEcho;
			InputStream canalEntrada;
			OutputStream canalSaida;
			BufferedReader entrada;
			PrintWriter saida;

			sckEcho = this.sckServidor.accept();
			canalEntrada = sckEcho.getInputStream();
			canalSaida = sckEcho.getOutputStream();
			entrada = new BufferedReader(new InputStreamReader(canalEntrada));
			saida = new PrintWriter(canalSaida, true);

			while (true) {
				String linhaPedido = entrada.readLine();

				if (linhaPedido == null || linhaPedido.length() == 0)
					break;

				boolean cnpjValido = validarCNPJ(linhaPedido);
				String mensagem = cnpjValido ? "CNPJ válido" : "CNPJ inválido";

				saida.println(mensagem);
			}
			sckEcho.close();
		}
	}

	private boolean validarCNPJ(String cnpj) {
		// Verifica se o CNPJ tem 14 caracteres (somente dígitos)
		if (cnpj == null || cnpj.length() != 14) {
			return false;
		}

		char digitoVerificador13, digitoVerificador14;
		int soma = 0, numero, resto, multiplicador = 2;

		try {
			// Cálculo do 1º dígito verificador
			// O cálculo é feito da direita para a esquerda, multiplicando cada dígito por pesos variáveis (2 a 9)
			for (int i = 11; i >= 0; i--) {
				numero = (int) (cnpj.charAt(i) - 48); // Converte o caractere para o número correspondente
				soma += numero * multiplicador; // Multiplica pelo peso e soma
				multiplicador++;
				if (multiplicador == 10) { // Reseta o peso para 2 após 9
					multiplicador = 2;
				}
			}

			// O resto da divisão da soma por 11 determina o valor do 1º dígito verificador
			resto = soma % 11;
			if (resto < 2) {
				digitoVerificador13 = '0';
			} else {
				digitoVerificador13 = (char) ((11 - resto) + 48); // Converte o número para o caractere correspondente
			}

			// Cálculo do 2º dígito verificador (agora inclui o 1º dígito verificador)
			soma = 0;
			multiplicador = 2;
			for (int i = 12; i >= 0; i--) {
				numero = (int) (cnpj.charAt(i) - 48);
				soma += numero * multiplicador;
				multiplicador++;
				if (multiplicador == 10) {
					multiplicador = 2;
				}
			}

			resto = soma % 11;
			if (resto < 2) {
				digitoVerificador14 = '0';
			} else {
				digitoVerificador14 = (char) ((11 - resto) + 48);
			}

			// Verifica se os dígitos verificadores calculados conferem com os dígitos do CNPJ informado
			return (digitoVerificador13 == cnpj.charAt(12)) && (digitoVerificador14 == cnpj.charAt(13));
		} catch (InputMismatchException erro) {
			return false;
		}
	}
}
