package com.blogspot.nurkiewicz;

import com.googlecode.flyway.core.Flyway;
import org.eclipse.persistence.jpa.PersistenceProvider;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaDialect;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author Tomasz Nurkiewicz
 * @since 6/2/13, 5:06 PM
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories
public class Spring {

	@Bean
	public DataSource dataSource() {
		final JdbcDataSource ds = new JdbcDataSource();
		ds.setURL("jdbc:h2:mem:books;DB_CLOSE_DELAY=-1");
		return ds;
	}

	@Bean
	@DependsOn("flyway")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
		final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(dataSource());
		factoryBean.setPackagesToScan("com.blogspot.nurkiewicz");
		factoryBean.setMappingResources("META-INF/orm.xml");
		factoryBean.setJpaDialect(jpaDialect());
		factoryBean.setPersistenceProvider(persistenceProvider());
		factoryBean.setJpaProperties(jpaProperties());
		return factoryBean;
	}

	@Bean
	public Properties jpaProperties() {
		final Properties properties = new Properties();
		properties.setProperty("javax.persistence.schema-generation.database.action", "none");
		properties.setProperty("eclipselink.weaving", Boolean.FALSE.toString());
		properties.setProperty("eclipselink.logging.logger", Slf4jSessionLogger.class.getName());
		properties.setProperty("eclipselink.logging.level.sql", "FINE");
		properties.setProperty("eclipselink.logging.parameters", Boolean.TRUE.toString());
		return properties;
	}

	@Bean
	public EclipseLinkJpaDialect jpaDialect() {
		return new EclipseLinkJpaDialect();
	}

	@Bean
	public PersistenceProvider persistenceProvider() {
		return new PersistenceProvider();
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new JpaTransactionManager(
				entityManagerFactory().getObject()
		);
	}

	@Bean(initMethod = "migrate")
	public Flyway flyway() {
		final Flyway flyway = new Flyway();
		flyway.setDataSource(dataSource());
		return flyway;
	}

}
