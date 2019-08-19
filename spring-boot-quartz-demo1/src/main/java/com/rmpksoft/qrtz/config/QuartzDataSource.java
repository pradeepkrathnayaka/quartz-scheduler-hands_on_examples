package com.rmpksoft.intro.config;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
public class QuartzDataSource {

	private static final String TMP_DIR = System.getProperty("java.io.tmpdir");

	// @Bean
	public DataSource dataSource_1() {
		// no need shutdown, EmbeddedDatabaseFactoryBean will take care of this
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.HSQL) // .H2 or .DERBY
				.addScript("db/sql/create-db.sql").addScript("db/sql/insert-data.sql").build();
		return db;
	}

	@Bean
	public DataSource getDataSource() {
		DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName("org.hsqldb.jdbc.JDBCDriver ");
		dataSourceBuilder.url("jdbc:hsqldb:" + TMP_DIR + "/test");
		dataSourceBuilder.username("SA");
		dataSourceBuilder.password("");
		return dataSourceBuilder.build();
	}

}
