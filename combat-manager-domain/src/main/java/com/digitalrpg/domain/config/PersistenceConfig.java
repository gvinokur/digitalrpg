package com.digitalrpg.domain.config;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource({ "classpath:persistence.properties" })
@ComponentScan({ "com.digitalrpg.domain.**" })
public class PersistenceConfig {

   @Autowired
   private Environment env;

   @Bean
   public AnnotationSessionFactoryBean sessionFactory() throws URISyntaxException {
	   AnnotationSessionFactoryBean sessionFactory = new AnnotationSessionFactoryBean();
      sessionFactory.setDataSource(restDataSource());
      sessionFactory.setPackagesToScan(new String[] { "com.digitalrpg.domain.**" });
      sessionFactory.setHibernateProperties(hibernateProperties());

      return sessionFactory;
   }

   @Bean
   public DataSource restDataSource() throws URISyntaxException {
      BasicDataSource dataSource = new BasicDataSource();
      String databaseUrl = env.getProperty("DATABASE_URL");
      URI uri = new URI(databaseUrl);
      dataSource.setDriverClassName(env.getProperty("database.driverClassName"));
      dataSource.setUrl("jdbc:postgresql://" + uri.getHost() + ":" + uri.getPort() + uri.getPath());
      dataSource.setUsername(uri.getUserInfo().split(":")[0]);
      dataSource.setPassword(uri.getUserInfo().split(":")[1]);

      return dataSource;
   }

   @Bean
   public HibernateTransactionManager transactionManager() throws URISyntaxException {
      HibernateTransactionManager txManager = new HibernateTransactionManager();
      txManager.setSessionFactory(sessionFactory().getObject());

      return txManager;
   }

   @Bean
   public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
      return new PersistenceExceptionTranslationPostProcessor();
   }

   Properties hibernateProperties() {
      return new Properties() {
         {
            setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
            setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
         }
      };
   }
}