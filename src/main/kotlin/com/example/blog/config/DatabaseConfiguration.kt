package com.example.blog.config

import com.zaxxer.hikari.HikariConfig
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.env.Environment
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
class DatabaseConfiguration(private val env: Environment){

  companion object {
    const val PRIMARY_DB = "primaryDataSource"
    const val SECONDARY_DB = "secondDataSource"
    const val PRIMARY_DOMAIN = "com.example.blog.domain.primary"
    const val PRIMARY_PACKAGE = "com.example.blog.repository.primary"
    const val SECONDARY_DOMAIN = "com.example.blog.domain.second"
    const val SECONDARY_PACKAGE = "com.example.blog.repository.second"
  }

  @Primary
  @Bean(name = [PRIMARY_DB])
  @Qualifier(PRIMARY_DB)
//  @ConfigurationProperties(prefix = "primary.datasource")
  fun primaryDataSource(): DataSource = DataSourceBuilder.create().driverClassName("org.mariadb.jdbc.Driver")
    .url("jdbc:mariadb://localhost:3306/first").username("root").password("666666").build()

  @Bean(name = [SECONDARY_DB])
  @Qualifier(SECONDARY_DB)
//  @ConfigurationProperties(prefix = "second.datasource")
  fun secondDataSource(): DataSource = DataSourceBuilder.create().driverClassName("org.mariadb.jdbc.Driver")
    .url("jdbc:mariadb://localhost:3306/second").username("root").password("666666").build()

  @Bean
  @Qualifier("hikariConfig")
  @ConfigurationProperties(prefix = "spring.datasource.primary.hikari")
  fun hikariConfig(): HikariConfig = HikariConfig()

}

@Configuration
@EnableConfigurationProperties(JpaProperties::class)
@EnableJpaRepositories(entityManagerFactoryRef = "emFactoryPrimary",
  transactionManagerRef = "tmPrimary", basePackages=[DatabaseConfiguration.PRIMARY_PACKAGE])
class PrimaryConfig(val jpaProperties: JpaProperties) {

  @Primary
  @Bean(name = ["emFactoryPrimary"])
  fun entityManager(builder: EntityManagerFactoryBuilder, @Qualifier(DatabaseConfiguration.PRIMARY_DB) dataSource: DataSource): LocalContainerEntityManagerFactoryBean =
    builder.dataSource(dataSource)
      .properties(jpaProperties.properties)
      .packages(DatabaseConfiguration.PRIMARY_PACKAGE)
      .persistenceUnit("primary")
      .build()

  @Primary
  @Bean(name = ["tmPrimary"])
  fun primaryTransactionManager(@Qualifier("emFactoryPrimary") emFactory: EntityManagerFactory): PlatformTransactionManager = JpaTransactionManager(emFactory)
}


@Configuration
@EnableConfigurationProperties(JpaProperties::class)
@EnableJpaRepositories(entityManagerFactoryRef = "emFactorySecondary", transactionManagerRef = "tmSecondary", basePackages = [DatabaseConfiguration.SECONDARY_PACKAGE])
class SecondaryConfig(val jpaProperties: JpaProperties){

  @Bean(name = ["emFactorySecondary"])
  fun entityManager(builder: EntityManagerFactoryBuilder, @Qualifier(DatabaseConfiguration.SECONDARY_DB) dataSource: DataSource): LocalContainerEntityManagerFactoryBean =
    builder.dataSource(dataSource)
          .properties(jpaProperties.properties)
          .packages(DatabaseConfiguration.SECONDARY_DOMAIN)
          .persistenceUnit("second").build()

  @Bean(name = ["tmSecondary"])
  fun primaryTransactionManager(@Qualifier("emFactorySecondary") emFactory: EntityManagerFactory): PlatformTransactionManager = JpaTransactionManager(emFactory)

}






