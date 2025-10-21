package ru.open.source;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Главный класс запуска приложения User Service в системе BuildForge.
 * <p>
 * Отвечает за инициализацию Spring Boot контекста, запуск встроенного веб-сервера
 * и автоматическую конфигурацию компонентов (сущностей, репозиториев, сервисов и контроллеров).
 * </p>
 * <p>
 * При запуске приложения выполняется метод {@link #main(String[])}.
 * В дальнейшем через этот класс инициализируется весь стек User Service,
 * включая подключение к базе данных, настройку Spring Security и другие компоненты.
 * </p>
 *
 * <p>Пример запуска:</p>
 * <pre>{@code
 * mvn spring-boot:run
 * }</pre>
 *
 * <p>После запуска доступны REST API эндпоинты для работы с пользователями,
 * профилями и сборками.</p>
 *
 * @author agent
 * @since 21.10.2025
 */
@SpringBootApplication
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}