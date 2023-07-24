package teste.junit;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

import br.com.project.report.util.DateUtils;

/**
 * Classe de teste do Junit. com ela não é preciso startar o sistema
 * Teste de data
 * @author hphoe
 *
 */

public class TesteData {

	@Test
	public void testData() {

		// System.out.println(DateUtils.getDateAtualReportName());// teste de como esta
		// saindo a data

		// exemplo de teste unitatiro
		try {
			assertEquals("22072023", DateUtils.getDateAtualReportName()); 

			assertEquals("'2023-07-22'", DateUtils.formatDateSql(Calendar.getInstance().getTime()));
			
			assertEquals("2023-07-22", DateUtils.formatDateSqlSimple(Calendar.getInstance().getTime()));
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

		// fail("Not yet implemented");
	}

}
