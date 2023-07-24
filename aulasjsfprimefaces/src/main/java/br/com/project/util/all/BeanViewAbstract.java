package br.com.project.util.all;

import org.springframework.stereotype.Component;

/**
 * aula 29.22 - criando a abstração para os managedBeans JSF.
 * Classe responsavel pelos métodos mais padrões dos manageBeans, sendo as abstrações dos manage beans
 * Mantendo como abstrata se obriga que as demais classes implementem os metodos  usados
 * @author hphoe
 *
 */

@Component
public abstract class BeanViewAbstract implements ActionViewPadrao {

	private static final long serialVersionUID = 1L;

	@Override
	public void limparLista() throws Exception {
		
	}

	@Override
	public String save() throws Exception {
		
		return null;
	}

	@Override
	public void saveNoReturn() throws Exception {
		
	}

	@Override
	public void saveEdit() throws Exception {
		
	}

	@Override
	public void excluir() throws Exception {
		
	}

	@Override
	public String ativar() throws Exception {
		return null;
	}

	@Override
	public String novo() throws Exception {
		return null;
	}

	@Override
	public String editar() throws Exception {
		return null;
	}

	@Override
	public void setarVariaveisNulas() throws Exception {

	}

	@Override
	public void consultarEntidade() throws Exception {
		
	}

	@Override
	public void statusOperation(EstatusPercistencia a) throws Exception {
		Messagens.responseOperation(a);
	}
	
	/*com o protectd só quem implementa tem acesso ao metodo*/
	protected void sucesso() throws Exception {
		statusOperation(EstatusPercistencia.SUCESSO);
	}
	
	protected void errot() throws Exception {
		statusOperation(EstatusPercistencia.ERROR);
	}

	@Override
	public String redirecionarNewEntidade() throws Exception {
		return null;
	}

	@Override
	public String redirecionarFindEntidade() throws Exception {
		return null;
	}

	@Override
	public void addMsg(String msg) throws Exception {
		Messagens.msg(msg);
	}

}
