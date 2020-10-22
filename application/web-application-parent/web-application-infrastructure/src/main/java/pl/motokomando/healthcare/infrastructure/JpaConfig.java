package pl.motokomando.healthcare.infrastructure;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("pl.motokomando.healthcare")
public class JpaConfig {
}
