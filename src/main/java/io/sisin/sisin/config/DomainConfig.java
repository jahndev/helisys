package io.sisin.sisin.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("io.sisin.sisin.domain")
@EnableJpaRepositories("io.sisin.sisin.repos")
@EnableTransactionManagement
public class DomainConfig {
}
