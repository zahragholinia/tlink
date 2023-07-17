package ir.tinyLink.config;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Configuration for hibernate
 *
 * @author Zahra Gholinia
 * @since 2023-07-12
 */

@Configuration
@EnableTransactionManagement
public class HibernateSettings {

    private static final int CONNECTION_LEAK_TIME = 100000;
    @Value("${spring.datasource.driverClassName}")
    private String datasourceDriver;
    @Value("${spring.datasource.url}")
    private String datasourceUrl;
    @Value("${spring.datasource.username}")
    private String datasourceUsername;
    @Value("${spring.datasource.password}")
    private String datasourcePassword;
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String datasourceDDLAuto;
    @Value("${datasource.max-pool-size}")
    private String datasourceMaxPoolSize;

    @Primary
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("ir.tinyLink");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setDriverClassName(datasourceDriver);
        ds.setJdbcUrl(datasourceUrl);
        ds.setUsername(datasourceUsername);
        ds.setPassword(datasourcePassword);
        ds.setIdleTimeout(15000);
        ds.setAllowPoolSuspension(false);
        ds.setLeakDetectionThreshold(CONNECTION_LEAK_TIME);
        ds.setMinimumIdle(10);
        ds.setPoolName("wd_db_pool");
        ds.setMaxLifetime(100000);
        ds.setMaximumPoolSize(Integer.parseInt(datasourceMaxPoolSize));

        return ds;
    }

    @Bean
    public PlatformTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    @Qualifier
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("ir.tinyLink");
        factory.setDataSource(dataSource());
        return factory;
    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", datasourceDDLAuto);
        hibernateProperties.setProperty("hibernate.show_sql", Boolean.FALSE.toString());
        hibernateProperties.setProperty("hibernate.connection.autocommit", Boolean.FALSE.toString());
        hibernateProperties.setProperty("hibernate.format_sql", Boolean.FALSE.toString());
        hibernateProperties.setProperty("hibernate.temp.use_jdbc_metadata_default", Boolean.FALSE.toString());
        hibernateProperties.setProperty("hibernate.globally_quoted_identifiers", "true");
        hibernateProperties.setProperty("hibernate.hikari.maximumPoolSize", datasourceMaxPoolSize);
        hibernateProperties.setProperty("hibernate.hikari.dataSource.idleTimeout", "15000");

        /*
          This property controls the minimum number of idle connections that HikariCP
          tries to maintain in the pool. If the idle connections dip below this value
          and total connections in the pool are less than maximumPoolSize, HikariCP will
          make a best effort to add additional connections quickly and efficiently.
          However, for maximum performance and responsiveness to spike demands,
          we recommend not setting this value and instead allowing HikariCP to
          act as a fixed size connection pool. Default: same as maximumPoolSize
         */
        hibernateProperties.setProperty("hibernate.hikari.dataSource.minimumIdle", "10");
        hibernateProperties.setProperty("hibernate.hikari.dataSource.connectionTimeout", "3000");
        hibernateProperties.setProperty("hibernate.hikari.dataSource.maxLifetime", String.valueOf(CONNECTION_LEAK_TIME));
        hibernateProperties.setProperty("hibernate.hikari.leakDetectionThreshold", String.valueOf(CONNECTION_LEAK_TIME));
        hibernateProperties.setProperty("hibernate.hikari.dataSource.cachePrepStmts", "true");
        hibernateProperties.setProperty("hibernate.hikari.dataSource.prepStmtCacheSize", "250");
        hibernateProperties.setProperty("hibernate.hikari.dataSource.prepStmtCacheSqlLimit", "2048");

        return hibernateProperties;
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager dbTransactionManager() {
        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                entityManagerFactory().getObject());
        return transactionManager;
    }
}
