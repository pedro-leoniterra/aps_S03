package dojo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MestreCSV {
	private static String caminho = "src\\pergaminho_anciao.csv";

	private static int gerarId() {
		int maiorId = 1;
		File arquivo = new File(caminho);
		if (arquivo.exists()) {
			try (Scanner scanner = new Scanner(new FileReader(arquivo, StandardCharsets.ISO_8859_1))) {
				while (scanner.hasNextLine()) {
					String linha = scanner.nextLine();
					String[] dados = linha.split(";");
					try {
						String semPrefixo = dados[0].replaceFirst("^20", "");
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
	public static void adicionaMestre(Mestre a) {
		String novoId = "20" + gerarId();
		a.setId(novoId);
		try {
			boolean existe = new File(caminho).exists();

			FileWriter escritor = new FileWriter(caminho, StandardCharsets.ISO_8859_1, true);
			if (!existe) {
				// Aqui está escrevendo vazio simplesmente para criar o arquivo e para que não
				// haja problemas com a tabela Jtable
				escritor.write("");
			}
			escritor.write(a.getId() + ";" + a.getNome() + ";" + a.getIdade() + ";" + a.getNivelPoder() + ";"
					+ a.getTecnica() + ";" + a.getStatus() + "\n");
			escritor.flush();
			escritor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Função para listar toda informação presente no CSV
	public static ArrayList<Mestre> listarMestre() {
		ArrayList<Mestre> lista = new ArrayList<Mestre>();
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
				Mestre a = new Mestre(nome, idade, nivel, tecnica, "");
				lista.add(a);
				System.out.println("Id: " + id + "Nome: " + nome + "\nIdade: " + idade + "\nNível: " + nivel
						+ "\nTécnica: " + tecnica + "\nPosição: " + a.getStatus());
			}
			leitor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	// Função para retornar ao usuario apenas uma informação presente no CSV
	public static ArrayList<Mestre> pesquisaMestre(int a, String b) {
		ArrayList<Mestre> lista = new ArrayList<Mestre>();

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
					Mestre an = new Mestre(nome, idade, nivel, tecnica, "");
					lista.add(an);

					System.out.println("id: " + id + "\nNome: " + nome + "\nIdade: " + idade + "\nNível: " + nivel
							+ "\nTécnica: " + tecnica + "\nPosição: " + an.getStatus());
					break;
				}

			}
			if (!achou) {
				System.out.println("Não há nenhum ancião com esses dados em nosso acervo! Tente novamente \n");
			}
			leitor.close();
		} catch (Exception e) {
			System.out.println("Ocorreu um erro! Tente novamente \n");
			e.printStackTrace();
		}
		return lista;
	}

	public static void vincularDiscipulo(String nomeDiscipulo, String mestreSorteado) {
		File original = new File(caminho);
		List<String> linhaAtualizada = new ArrayList<>();
		try (Scanner scanner = new Scanner(new FileReader(original, StandardCharsets.ISO_8859_1))) {
			while (scanner.hasNextLine()) {
				String linha = scanner.nextLine();
				String[] dados = linha.split(";");
				if (dados[1].equalsIgnoreCase(mestreSorteado)) {
					if (dados.length == 6) {
						linha = linha + ";" + nomeDiscipulo;
					} else {
						linha = linha + "," + nomeDiscipulo;
					}
					linhaAtualizada.add(linha);
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			FileWriter escritor = new FileWriter(original, false);
			for (String linhaAtualizda : linhaAtualizada) {
				escritor.write(linhaAtualizda + "\n");
			}
			escritor.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}