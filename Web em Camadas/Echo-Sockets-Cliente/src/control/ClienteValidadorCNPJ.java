package control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClienteValidadorCNPJ {

	public static void main(String[] args) {

		Socket socket;
		InputStream canalEntrada;
		OutputStream canalSaida;
		BufferedReader entrada;
		PrintWriter saida;

		try {
			socket = new Socket("127.0.0.1", 4000); // IP Faculdade: 127.0.0.1
			canalEntrada = socket.getInputStream();
			canalSaida = socket.getOutputStream();

			entrada = new BufferedReader(new InputStreamReader(canalEntrada));
			saida = new PrintWriter(canalSaida, true);

			Scanner leitor = new Scanner(System.in);
			System.out.println("Informe um CNPJ:");
			String leitura = leitor.nextLine();
			saida.println(leitura);

			String resultado = entrada.readLine();
			System.out.println(resultado);

			socket.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
