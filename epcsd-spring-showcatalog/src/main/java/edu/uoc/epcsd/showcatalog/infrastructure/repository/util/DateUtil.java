package edu.uoc.epcsd.showcatalog.infrastructure.repository.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtil {
	
	private static final String DATE_FORMAT_DATABASE = "dd/MM/yyyy";
	
	/**
	 * Parsea una fecha en String a Date.
	 * 
	 * @param date
	 * @return
	 */
	public static Date parseDate(String date) {
		 try {
			return new SimpleDateFormat(DATE_FORMAT_DATABASE).parse(date);
		} catch (ParseException e) {
			log.error("Se ha producido un error al parsear la fecha: " + date, e);
		}
		return null;  
	}
	
	/**
	 * Comprueba que una fecha es una fecha valida.
	 * 
	 * @param dateStr
	 * @return
	 */
	 public static boolean isValidStringDate(String dateStr) {
        final DateFormat sdf = new SimpleDateFormat(DATE_FORMAT_DATABASE);
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
        } catch (ParseException e) {
        	log.error("Fecha no valida: " + dateStr);
            return false;
        }
        return true;
    }
}
