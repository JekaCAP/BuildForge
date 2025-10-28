package ru.open.source.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * ErrorResponse — описание класса.
 * <p>
 * Класс для возврата из обработчиков ошибок
 * </p>
 *
 * @author agent
 * @since 28.10.2025
 */
@Getter
@RequiredArgsConstructor
public class ErrorResponse {
    private final int status;
    private final String message;
    private final String timestamp;
}