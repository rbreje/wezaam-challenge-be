package com.wezaam.withdrawal.config

import com.wezaam.withdrawal.exception.PaymentMethodNotFoundException
import com.wezaam.withdrawal.exception.UserNotFoundException
import com.wezaam.withdrawal.rest.response.ErrorResponse
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
class ApplicationExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler
    fun handleUserNotFound(userNotFoundException: UserNotFoundException): ResponseEntity<ErrorResponse> {
        val errorMessage = ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            userNotFoundException.message
        )
        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler
    fun handlePaymentMethodNotFound(paymentMethodNotFoundException: PaymentMethodNotFoundException): ResponseEntity<ErrorResponse> {
        val errorMessage = ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            paymentMethodNotFoundException.message
        )
        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)
    }

}