package dojo;

public class Funcionario extends Discipulo {

	public Funcionario(String aNome, int aIdade, String aNivelPoder, String aTecnica, String aGuia) {
		super(aNome, aIdade, aNivelPoder, aTecnica, aGuia);

	}

	// Lógica Wuxia para definir a entrada na Seita
	@Override
	public String definirStatus(String nivel) {
		return "Funcionário";
	}

}
