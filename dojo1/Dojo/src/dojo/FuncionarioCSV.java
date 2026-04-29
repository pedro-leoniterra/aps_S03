package dojo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class FuncionarioCSV {

	private static String caminho = "src\\pergaminho_servo.csv";

	private static int gerarId() {
		int maiorId = 1;
		File arquivo = new File(caminho);
		if (arquivo.exists()) {
			try (Scanner scanner = new Scanner(new FileReader(arquivo, StandardCharsets.ISO_8859_1))) {
				while (scanner.hasNextLine()) {
					String linha = scanner.nextLine();
					String[] dados = linha.split(";");
					try {
						String semPrefixo = dados[0].replaceFirst("^30", "");
						int idAtual = Integer.parseInt(semPrefixo);
						if (idAtual > maiorId) {
							maiorId = idAtual;
						}
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
				}
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		return maiorId + 1;
	}

	// Função para adicionar a classe Anciao num arquivo CSV
	public static void adicionaFuncionario(Funcionario s) {
		String novoId = "30" + gerarId();
		s.setId(novoId);
		try {
			boolean existe = new File(caminho).exists();

			FileWriter escritor = new FileWriter(caminho, StandardCharsets.ISO_8859_1, true);
			if (!existe) {
				// Aqui está escrevendo vazio simplesmente para criar o arquivo e para que não
				// haja problemas com a tabela Jtable
				escritor.write("");
			}
			escritor.write(s.getId() + ";" + s.getNome() + ";" + s.getIdade() + ";" + s.getNivelPoder() + ";"
					+ s.getTecnica() + ";" + s.getStatus() + "\n");
			escritor.flush();
			escritor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Função para listar toda informação presente no CSV
	public static ArrayList<Funcionario> listarFuncionario() {
		ArrayList<Funcionario> lista = new ArrayList<Funcionario>();
		try {
			BufferedReader leitor = new BufferedReader(new FileReader(caminho));
			String linha;
			boolean primeiraLinha = true;

			while ((linha = leitor.readLine()) != null) {
				// Aqui verificamos se tem primeira linha e pulamos ela
				if (primeiraLinha) {
					primeiraLinha = false;
					continue;
				}
				// Dividimos a informação recebia em blocos e atribuimos essa informação em
				// variaveis
				String[] partes = linha.split(";");
				int id = Integer.parseInt(partes[0]);
				String nome = partes[1];
				int idade = Integer.parseInt(partes[2]);
				String nivel = partes[3];
				String tecnica = partes[4];

				// Criamos o objeto
				Funcionario s = new Funcionario(nome, idade, nivel, tecnica, "");
				lista.add(s);
				System.out.println("Id: " + id + "\nNome: " + nome + "\nIdade: " + idade + "\nNível: " + nivel
						+ "\nTécnica: " + tecnica + "\nPosição: " + s.getStatus());
			}
			leitor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	// Função para retornar ao usuario apenas uma informação presente no CSV
	public static ArrayList<Funcionario> pesquisaFuncionario(int a, String b) {
		ArrayList<Funcionario> lista = new ArrayList<Funcionario>();

		int opcao = a;
		String objetivo = b;
		System.out.println("Buscando por: " + objetivo);
		try {
			BufferedReader leitor = new BufferedReader(new FileReader(caminho));
			String linha;
			boolean primeiraLinha = true;
			boolean achou = false;

			while ((linha = leitor.readLine()) != null) {
				if (primeiraLinha) {
					primeiraLinha = false;
					continue;
				}
				String[] partes = linha.split(";");
				int id = Integer.parseInt(partes[0]);
				String nome = partes[2];
				String nivel = partes[3];
				String tecnica = partes[4];

				String posicao = partes[5];
				int idade = Integer.parseInt(partes[1]);
				if (opcao == 1) {
					try {
						int idValor = Integer.parseInt(objetivo);
						if (id == idValor) {
							achou = true;
						}

					} catch (NumberFormatException e) {
						System.out.println("Você quis buscar por id, mas não digitou um número");
						break;
					}
				}
				if (opcao == 2 && nome.toLowerCase().contains(objetivo.toLowerCase())) {
					achou = true;
				} else if (opcao == 3) {
					try {
						int idadeValor = Integer.parseInt(objetivo);
						if (idade == idadeValor) {
							achou = true;
						}

					} catch (NumberFormatException e) {
						System.out.println("Você quis buscar por idade, mas não digitou um número");
						break;
					}
				} else if (opcao == 4 && nivel.toLowerCase().contains(objetivo.toLowerCase())) {
					achou = true;
				} else if (opcao == 5 && tecnica.toLowerCase().contains(objetivo.toLowerCase())) {
					achou = true;
				} else if (opcao == 6 && posicao.toLowerCase().contains(objetivo.toLowerCase())) {
					achou = true;
				}
				if (achou) {
					Funcionario s = new Funcionario(nome, idade, nivel, tecnica, "");
					lista.add(s);

					System.out.println("Id: " + id + "\nNome: " + nome + "\nIdade: " + idade + "\nNível: " + nivel
							+ "\nTécnica: " + tecnica + "\nPosição: " + s.getStatus());
					break;
				}

			}
			if (!achou) {
				System.out.println("Não há nenhum servo com esses dados em nosso acervo! Tente novamente \n");
			}
			leitor.close();
		} catch (Exception e) {
			System.out.println("Ocorreu um erro! Tente novamente \n");
			e.printStackTrace();
		}
		return lista;
	}
}
