package dojo;

public class Discipulo {
	private String id;
	private String nome;
	private int idade;
	private String nivelPoder;
	private String tecnica;
	private String status;
	private String guia;

	public Discipulo(String aNome, int aIdade, String aNivelPoder, String aTecnica, String aGuia) {

		this.nome = aNome;
		this.idade = aIdade;
		this.nivelPoder = aNivelPoder;
		this.tecnica = aTecnica;
		this.guia = aGuia;
		this.status = definirStatus(nivelPoder);
	}

	// Lógica Wuxia para definir a entrada na Seita
	public String definirStatus(String nivel) {
		return switch (nivel) {
		case "Nenhum" -> "Discípulo Iniciante";
		case "Faixa Branca" -> "Discípulo Aprendiz";
		case "Faixa Cinza" -> "Discípulo Aprendiz";
		case "Faixa Amarela" -> "Discípulo Adepto";
		case "Faixa Laranja" -> "Discípulo Adepto";
		case "Faixa Verde" -> "Discípulo Adepto";
		case "Faixa Roxa" -> "Discípulo Proficiente";
		case "Faixa Azul" -> "Discípulo Proficiente";
		case "Faixa Vermelha" -> "Discípulo Proficiente";

		default -> "Rejeitado";
		};
	}

	// Transforma em linha para o CSV
	public String paraCSV() {
		return nome + ";" + idade + ";" + nivelPoder + ";" + tecnica + ";" + status;
	}

	// Getters para a tabela do Administrador
	public String getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public int getIdade() {
		return idade;
	}

	public String getNivelPoder() {
		return nivelPoder;
	}

	public String getTecnica() {
		return tecnica;
	}

	public String getStatus() {
		return status;
	}

	public String getGuia() {
		return guia;
	}

}
