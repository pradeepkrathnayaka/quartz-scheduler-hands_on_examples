package com.rmpksoft.qrtz.cfg;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
public class DbConfig {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
	@Bean
	public DataSource dataSource() {
		log.info("Init database.");
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		return builder
				.setType(EmbeddedDatabaseType.HSQL)
				.addScript("sql/tables_hsqldb.sql")
				.build();
	}
	
	@Bean
	public JdbcTemplate createJdbcTeamplate() {
	    JdbcTemplate jdbcTemplate = new JdbcTemplate();
	    jdbcTemplate.setDataSource(dataSource());
	    return jdbcTemplate;
	}

}
