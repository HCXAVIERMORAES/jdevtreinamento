package br.com.project.report.util;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Classe que auxilia na geração de relatorios da aplicação (1ª classe)
 * Trabalha com datas.
 * @author hphoe
 *
 */

public class DateUtils implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//metodo que retorna a data atual
	public static String getDateAtualReportName () {
		
		DateFormat df = new SimpleDateFormat("ddMMyyyy"); //dia mes e ano
		return df.format(Calendar.getInstance().getTime()); //data atual
	}
	
	//metodo q recebe uma data e retorna com aspas simple
	public static String formatDateSql (Date data) {
		StringBuffer retorno = new StringBuffer();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); //formato que se grava no banco
		retorno.append("'");
		retorno.append(df.format(data));
		retorno.append("'");
		return retorno.toString();
	}
	
	//metodo q recebe uma data e retorna sem aspas 
		public static String formatDateSqlSimple (Date data) {
			StringBuffer retorno = new StringBuffer();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
			retorno.append(df.format(data));
			return retorno.toString();
		}

}
