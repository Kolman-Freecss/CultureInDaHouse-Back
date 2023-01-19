package edu.uoc.epcsd.showcatalog.infrastructure.repository.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;

/**
 * Clase de test de de utilidades de fecha
 * 
 * @author Grupo Slack Chronicles
 */
@Log4j2
public class DateUtilUnitTest {

	@Test
	public void parse_date() {
		log.debug("Test: parse_date()");
		final String dateString = "01/02/2023";
		final Date parseDate = DateUtil.parseDate(dateString);
		
		assertThat(parseDate).isNotNull();
	}
	
	@Test
	public void parse_date_error() {
		log.debug("Test: parse_date()");
		final String dateString = "XX";
		final Date parseDate = DateUtil.parseDate(dateString);
		
		assertThat(parseDate).isNull();
	}
	
	@Test
	public void is_valid_string_date() {
		log.debug("Test: is_valid_string_date()");

		final String dateString = "01/02/2023";

		final boolean isValidDate = DateUtil.isValidStringDate(dateString);
		assertThat(isValidDate).isTrue();
	}
	
	@Test
	public void is_not_valid_string_date() {
		log.debug("Test: is_valid_string_date()");

		final String dateString = "31/31/2023";

		final boolean isValidDate = DateUtil.isValidStringDate(dateString);
		assertThat(isValidDate).isFalse();
	}
}
