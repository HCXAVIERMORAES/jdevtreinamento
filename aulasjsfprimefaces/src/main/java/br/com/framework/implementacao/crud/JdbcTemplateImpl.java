package br.com.framework.implementacao.crud;

import java.io.Serializable;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * O transation recebe parametro de anota�oes:
 * propagation = Propagation.REQUIRED (se n�o existir ele cria)
 * rollbackFor = Exception.class ( da um rollback se houver alguma excessao)
 * @author hphoe
 *
 */
@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class JdbcTemplateImpl extends JdbcTemplate implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//construtor que recebe a conex�o, o datasouce sera passado na configura��o do spring
	public JdbcTemplateImpl(DataSource dataSource) {
		super(dataSource);
		
	}

}
