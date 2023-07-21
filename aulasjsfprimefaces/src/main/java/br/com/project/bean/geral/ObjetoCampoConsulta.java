package br.com.project.bean.geral;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

/**
 * classe que ira comparar os dados da alicação até chegar na tela
 * carrega o objeto
 * @author hphoe
 *
 */

public class ObjetoCampoConsulta implements Serializable, Comparator<ObjetoCampoConsulta>{

	private static final long serialVersionUID = 1L;
	
	private String descricao;
	private String campoBanco;
	private Object tipoClasse; //se é estado, cidade, etc
	private Integer principal;
	
	
	//metodo que ira fazer a comparção dos objetos
	@Override
	public int compare(ObjetoCampoConsulta o1, ObjetoCampoConsulta o2) {
		return ((ObjetoCampoConsulta)o1).getPrincipal().compareTo(((ObjetoCampoConsulta) o2).getPrincipal());
	}
	
	//get e set

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCampoBanco() {
		return campoBanco;
	}

	public void setCampoBanco(String campoBanco) {
		this.campoBanco = campoBanco;
	}

	public Object getTipoClasse() {
		return tipoClasse;
	}

	public void setTipoClasse(Object tipoClasse) {
		this.tipoClasse = tipoClasse;
	}

	public Integer getPrincipal() {
		return principal;
	}

	public void setPrincipal(Integer principal) {
		this.principal = principal;
	}
	
	//hashCode and equals do banco que não pode ser repetido

	@Override
	public int hashCode() {
		return Objects.hash(campoBanco);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ObjetoCampoConsulta other = (ObjetoCampoConsulta) obj;
		return Objects.equals(campoBanco, other.campoBanco);
	}

//metodo que mostra a  descrição do objeto na tela se não for especificado
	@Override
	public String toString() {
		return getDescricao();
	}
	

}
