package br.com.project.util.all;

/**
 * Classe responsavel por mandar as mesagens de acesso ao banco de dados
 * @author hphoe
 *
 */

public enum EstatusPercistencia {
	
	ERROR("Erro"), SUCESSO("Sucesso"),OBJETO_REFERENCIADO(
			"Esse registro n�o pode ser apagado por possuir refer�ncias ao mesmo.");

	private String name;

	private EstatusPercistencia(String s) {
		this.name = s;
	}
	
	@Override
	public String toString() {
		return this.name;
	}

}
