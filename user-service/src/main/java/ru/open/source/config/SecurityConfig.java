package ru.open.source.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * SecurityConfig - конфигурация безопасности приложения.
 * <p>
 * Настраивает компоненты безопасности, такие как кодировщик паролей.
 * В текущей реализации предоставляет bean {@link PasswordEncoder} для хеширования паролей
 * с использованием алгоритма BCrypt.
 * </p>
 *
 * @author agent
 * @since 01.11.2025
 */
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}