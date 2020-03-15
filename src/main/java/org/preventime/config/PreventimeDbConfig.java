package org.preventime.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "org.preventime.data",
        entityManagerFactoryRef = "preventimeDbEntityManager",
        transactionManagerRef = "preventimeDbTransactionManager"
)
@EnableConfigurationProperties(PreventimeDbDatasourceConfigurationProperties.class)
public class PreventimeDbConfig {

    private final PreventimeDbDatasourceConfigurationProperties properties;

    public PreventimeDbConfig(PreventimeDbDatasourceConfigurationProperties properties) {
        this.properties = properties;
    }

    @Bean
    @Primary
    public DataSource preventimeDbDataSource() {
        final HikariDataSource ds = new HikariDataSource();
        ds.setPoolName("Preventime");
        ds.setMaximumPoolSize(properties.getMaxPoolSize());
        ds.setMinimumIdle(properties.getMinPoolSize());
        ds.setDriverClassName(properties.getDriver());
        ds.setSchema(properties.getSchema());
        ds.setIdleTimeout(TimeUnit.MINUTES.toMillis(9)); // one less than 10 minutes set by server.
        ds.setJdbcUrl(getUrl());
        ds.addDataSourceProperty("user", properties.getUser());
        ds.addDataSourceProperty("password", properties.getPassword());
        ds.addDataSourceProperty("currentSchema", properties.getSchema());
        ds.addDataSourceProperty("cachePrepStmts", true);
        ds.addDataSourceProperty("prepStmtCacheSize", 250);
        ds.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        ds.addDataSourceProperty("useServerPrepStmts", true);
        ds.addDataSourceProperty("useLocalSessionState", true);
        ds.addDataSourceProperty("rewriteBatchedStatements", true);
        ds.addDataSourceProperty("cacheResultSetMetadata", true);
        ds.addDataSourceProperty("cacheServerConfiguration", true);
        ds.addDataSourceProperty("elideSetAutoCommits", true);
        ds.addDataSourceProperty("maintainTimeStats", false);
        return ds;
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean preventimeDbEntityManager() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(preventimeDbDataSource());
        em.setPackagesToScan("org.preventime.data");

        HibernateJpaVendorAdapter vendorAdapter
                = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.globally_quoted_identifiers", "true");
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Primary
    @Bean
    public PlatformTransactionManager preventimeDbTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(preventimeDbEntityManager().getObject());
        return transactionManager;
    }
    
    private String getUrl() {
        String hostname = properties.getHostname();
        String port = String.valueOf(properties.getPort());
        String protocol = properties.getProtocol();
        return protocol + "://" + hostname + ":" + port + "/" + properties.getDatabase();
    }

}
