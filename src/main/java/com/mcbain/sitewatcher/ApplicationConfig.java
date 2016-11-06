package com.mcbain.sitewatcher;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

public class ApplicationConfig {
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("org.h2.Driver");
		ds.setUrl("jdbc:h2:mem:test");
		ds.setUsername("sa");
		ds.setPassword("sa");
		return ds;
	}
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
	   LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
	   em.setDataSource(dataSource());
	   em.setPackagesToScan(new String[] { "com.mcbain.sitewatcher.domain" });
	 
	   JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	   em.setJpaVendorAdapter(vendorAdapter);
	   em.setJpaProperties(additionalProperties());
	 
	   return em;
	}
	 
	 
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
	   JpaTransactionManager transactionManager = new JpaTransactionManager();
	   transactionManager.setEntityManagerFactory(emf);
	 
	   return transactionManager;
	}
	 
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
	   return new PersistenceExceptionTranslationPostProcessor();
	}
	 
	Properties additionalProperties() {
	   Properties properties = new Properties();
	   properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
	   properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
	   return properties;
	}
}
