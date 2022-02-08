package pacoteEstacionamento;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Estacionamento {

	
	public static void main(String[] args) {
		
/*
 * Programa para controlar estacionamento com "n" vagas
 * Segue um misto da espec da prova + o exerc�cio de aula (com a mesma regra de pagamento):
 * Tempo a ser Cobrado:
 *       0 - 5 minutos - 0 reais
 *       5 - 60 minutos - 4 reais
 *       Acima de 60 - cobrado o valor de 6 reais por hora adicional
 * Prev� uma inicia��o do terminal (como "stubs", inicialmente). 
 * QUando um carro entra, imprime relat�rio de entrada do carro
 * Quando um carro sai, imprime relat�rio de sa�da do carro
 * Quando fecha o sistema, imprime relt�rio completo dos carros que estacionaram
 * Em um sistema real, dados de inicia��o e log seriam guardados em uma E2PROM ("flash" EPROM),
 *  em um arquivo em disco ou em um BD
 *  
 * Programa Principal
 * 	Inicia��o
 * 	l� a placa do carro
 * 	switch (trataCarro (placa)) {
 * 		"entrando":
 * 			trataCarroEntrando
 * 			break
 * 		"saindo":
 * 			trataCarroSaindo
 * 			break
 * 		"lotado":
 * 			trataEstacionamnetoLotado
 * 			break
 * 		default:
 * 			BUG! bum!
 * 			break		
 * 	}
 */
		Scanner lerString = new Scanner (System.in);
		Scanner lerInt = new Scanner (System.in);
	
			int qtasVagas = iniciacao ();
			String [] placas = new String [qtasVagas]; // placas de carros estacionados
			long [] horaEntrada = new long [qtasVagas];  // hora entrada
			long [] duracao = new long [qtasVagas]; // tempo estacionado
			double [] valorCobrado = new double [qtasVagas * 5]; // log
			String [] logPlacas = new String [qtasVagas * 5]; // log para relat�rio
			double [] logHoraEntrada = new double [qtasVagas * 5]; // log para relat�rio
			double [] logDuracao = new double [qtasVagas * 5]; // log para relat�rio
			int vagasLivres = qtasVagas;
			
		
		String opcao = "S";
		
		do {
			System.out.println("Digite 'S' ou 's' para Sair ou qualquer letra para entrar com a placa: \n");
			opcao = lerString.next ();
			if ((opcao.equals("S")) || (opcao.equals("s")))
				break;
			
			
			System.out.println("Digite o n�mero da placa");
			String placa = lerString.next ();
			
			switch (trataCarro (placa, placas, horaEntrada, vagasLivres)) {
				case "entrando":
					trataCarroEntrando (placa, placas, horaEntrada, vagasLivres);
					imprimeRelatEntrada (placa);
					vagasLivres--;
					break;
				case "saindo":
					trataCarroSaindo (placa, placas, horaEntrada, vagasLivres);
//					cobraValor ();
//					imprimeRelatSaida ();
//					checaLog ();
					vagasLivres++;
					break;
				case "lotado":
					trataEstacionamentoLotado ();
					break;
				default: 
					System.out.println("Chiii!! Deu pau!");
			}
		} while (opcao != "S" || opcao != "s");
	}

//	UTILITIES
	
	private static int iniciacao () {
		
		Scanner lerString = new Scanner (System.in);
		Scanner lerInt = new Scanner (System.in);
		
		
		String  nomeOp = null;
		int senhaOp = 0;
		int numVagas = 0;
		
		do {
			System.out.println("Digite nome operador: ");
			nomeOp = lerString.next ();
		} while (nomeOp == null);
		
		do {
		System.out.println("Digite senha operador: ");
		senhaOp = lerInt.nextInt ();
		} while (senhaOp == 0);
		
		do {
		System.out.println("Digite n�mero de vagas do estacionamento: ");
		numVagas = lerInt.nextInt ();
		} while (numVagas == 0);
		
		return numVagas;
	}

	private static String trataCarro (String placa, String [] placas, long[] horaEntrada, int vagasLivres) {
		if (vagasLivres == 0) {
			return "lotado";
		}
		for (int i = 0; i < placas.length; i++) {
			if (placas [i] != null && placas [i].equals (placa)) { 
				return "saindo";
			}
		}
		return "entrando";
	}
	
	private static void trataCarroEntrando (String placa, String [] placas, long[] horaEntrada, int vagasLivres) {
		for (int i = 0; i < placas.length; i++) {
			if (placas [i] == null) {
				placas [i] = placa;
				horaEntrada [i] = System.currentTimeMillis();
//				vagasLivres--;
			break;
			}
		} 
			
	}
	
	private static void imprimeRelatEntrada (String placa) {
//		Fiz diferente para testar estas intr�nsecas
		DateTimeFormatter data = DateTimeFormatter.ofPattern ("dd/MM/yyyy");
		DateTimeFormatter hora = DateTimeFormatter.ofPattern ("HH:mm");
		System.out.printf("Carro de placa %s ", placa);
		System.out.printf ( "entrou dia " + data.format(LocalDateTime.now())+ 
			"  Hora: "+ hora.format(LocalDateTime.now()) + "%n");	
	}
	
	private static void trataCarroSaindo (String placa, String [] placas, long[] horaEntrada, int vagasLivres) {
		for (int i = 0; i < placas.length; i++) {
			if (placas [i] != null && placas [i].equals(placa)) { // achou!
				placas [i] = null;
				break;
//				vagasLivres++;
			}
		}
	}
	
	private static void trataEstacionamentoLotado () {
		System.err.println("Estacionamento lotado!");
	}
	
	
} // final da classe
