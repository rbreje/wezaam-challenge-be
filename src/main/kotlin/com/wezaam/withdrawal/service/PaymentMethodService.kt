package com.wezaam.withdrawal.service

import com.wezaam.withdrawal.exception.PaymentMethodNotFoundException
import com.wezaam.withdrawal.model.PaymentMethod
import com.wezaam.withdrawal.repository.PaymentMethodRepository
import org.springframework.stereotype.Service

@Service
class PaymentMethodService(
        private val paymentMethodRepository: PaymentMethodRepository
) {

    fun findById(id: Long): PaymentMethod {
        return paymentMethodRepository.findById(id).orElseThrow { throw PaymentMethodNotFoundException(id.toString()) }
    }

}