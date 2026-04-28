package dojo;

public class Mestre extends Discipulo {

	public Mestre(String aNome, int aIdade, String aNivelPoder, String aTecnica, String aGuia) {
		super(aNome, aIdade, aNivelPoder, aTecnica, aGuia);

	}

	// Lógica Wuxia para definir a entrada na Seita
	@Override
	public String definirStatus(String nivel) {
		return switch (nivel) {

		case "Faixa Preta(1 dan)" -> "Mestre Iniciante";
		case "Faixa Preta(2 dan)" -> "Mestre Adepto";
		case "Faixa Preta(3 dan)" -> "Mestre de Elite";
		case "Faixa Preta(4 dan)" -> "Mestre Consagrado";
		case "Faixa Preta(5 dan)" -> "Mestre Marcial";

		default -> "Rejeitado";
		};
	}

}
